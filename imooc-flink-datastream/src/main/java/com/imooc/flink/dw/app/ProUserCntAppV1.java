package com.imooc.flink.dw.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.flink.dw.domain.Access;
import com.imooc.flink.dw.udf.GaoDeLocationMapFunction;
import com.imooc.flink.dw.utils.MapperUtils;
import com.imooc.flink.dw.utils.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class ProUserCntAppV1 {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> source = env.readTextFile("data-file/access-v2.txt");

        SingleOutputStreamOperator<Tuple3<String, String, Integer>> result =
                source.map(new MapFunction<String, Access>() {
            @Override
            public Access map(String s) throws Exception {

                ObjectMapper objectMapper = MapperUtils.getInstance();
                Access access = objectMapper.readValue(s, Access.class);

                return access;
            }
        }).filter(x -> x != null && x.event.equals("startup"))
                .map(new GaoDeLocationMapFunction()).map(new MapFunction<Access, Tuple3<String, String, Integer>>() {
            @Override
            public Tuple3<String, String, Integer> map(Access access) throws Exception {

                return Tuple3.of(access.province, String.valueOf(access.nu), 1);

            }
        }).keyBy(new KeySelector<Tuple3<String, String, Integer>, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> getKey(Tuple3<String, String, Integer> in) throws Exception {
                return Tuple2.of(in.f0, in.f1);
            }
        }).sum(2);

//
        FlinkJedisPoolConfig conf = new FlinkJedisPoolConfig.Builder().setHost("127.0.0.1").setPort(6379).build();

        result.addSink(new RedisSink<Tuple3<String, String, Integer>>(conf, new RedisExampleMapper()));


        env.execute();
    }

    static class RedisExampleMapper implements RedisMapper<Tuple3<String, String, Integer>> {

        @Override
        public RedisCommandDescription getCommandDescription() {
            return new RedisCommandDescription(RedisCommand.HSET, "pro_user-cnt");
        }

//        @Override
//        public RedisCommandDescription getCommandDescription() {
//            return new RedisCommandDescription(RedisCommand.SET);
//        }

        @Override
        public String getKeyFromData(Tuple3<String, String, Integer> data) {
            return data.f0 + "_" + data.f1;
        }

        @Override
        public String getValueFromData(Tuple3<String, String, Integer> data) {
            return data.f2 + "";
        }
    }
}
