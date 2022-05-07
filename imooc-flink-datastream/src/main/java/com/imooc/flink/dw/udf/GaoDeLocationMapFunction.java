package com.imooc.flink.dw.udf;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.imooc.flink.dw.domain.Access;
import com.imooc.flink.dw.domain.GaoDeLocation;
import com.imooc.flink.dw.utils.MapperUtils;
import com.imooc.flink.dw.utils.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class GaoDeLocationMapFunction extends RichMapFunction<Access, Access> {

    private CloseableHttpClient httpClient;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public void close() throws Exception {
        super.close();
        if(this.httpClient != null) this.httpClient.close();
    }

    @Override
    public Access map(Access access) throws Exception {

        String urlTemplate = "https://restapi.amap.com/v3/ip?ip=%s&output=json&key=%s";
        String url  = String.format(urlTemplate, access.ip, StringUtils.GAODE_KEY);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        GaoDeLocation gaoDeLocation = new GaoDeLocation();
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            if(200 == statusCode) {

                try {
                    gaoDeLocation = MapperUtils.getInstance().readValue(result, GaoDeLocation.class);
                }catch (MismatchedInputException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            System.out.println(url);
            e.printStackTrace();
        } finally {
            if( null != response)
                response.close();
            access.setProvince(gaoDeLocation.province);
            access.setCity(gaoDeLocation.city);
        }
        return access;
    }
}
