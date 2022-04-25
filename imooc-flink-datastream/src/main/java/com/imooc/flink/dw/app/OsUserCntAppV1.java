package com.imooc.flink.dw.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.flink.dw.domain.Access;
import com.imooc.flink.dw.utils.MapperUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.client.program.StreamContextEnvironment;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class OsUserCntAppV1 {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> source = env.readTextFile("data-file/access-v2.txt");

        source.map(new MapFunction<String, Access>() {
            @Override
            public Access map(String s) throws Exception {

                ObjectMapper objectMapper = MapperUtils.getInstance();
                Access access = objectMapper.readValue(s, Access.class);

                return access;
            }
        }).filter(x->x!=null && x.event.equals("startup"))
                        .map(new MapFunction<Access, Tuple3<String,String,Integer>>() {
                            @Override
                            public Tuple3<String, String, Integer> map(Access access) throws Exception {

                                return Tuple3.of(access.os,String.valueOf(access.nu),1);
                                
                            }
                        }).keyBy(new KeySelector<Tuple3<String, String, Integer>, Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> getKey(Tuple3<String, String, Integer> in) throws Exception {
                        return Tuple2.of(in.f0,in.f1);
                    }
                }).sum(2).print();


        env.execute();

    }
}
