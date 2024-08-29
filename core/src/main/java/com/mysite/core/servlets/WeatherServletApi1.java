package com.mysite.core.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.swing.*;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/raman/Api")
public class WeatherServletApi1 extends SlingSafeMethodsServlet {
    public static final Logger log = LoggerFactory.getLogger(WeatherServletApi1.class);
    String ApiKey = "e76fdf80e38a77dbbc395de0a65377c3";
    String Api;
    String lat;
    String logi;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        lat = request.getParameter("lat");
        logi = request.getParameter("lon");
        log.info("lattitude1 " + lat);
        response.setContentType("application/json");
//        response.getWriter().write(String.valueOf(jsonResponseObj));
        response.getWriter().write(fetchApi().toString());
//        fetchApi();
    }

    public JsonObject fetchApi() throws IOException {
        log.info("hi raman");
        Api = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + logi + "&appid=" + ApiKey+"&units=imperial";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(Api);
        CloseableHttpResponse resp = httpClient.execute(get);
        log.info("response1 " + resp);
        HttpEntity entity = resp.getEntity();
        String weathdata = EntityUtils.toString(resp.getEntity());
        log.info("weath data"+weathdata);
//    return weathdata;
        JsonObject jsonObject = JsonParser.parseString(weathdata).getAsJsonObject();
        JsonObject main = jsonObject.getAsJsonObject("main");
        JsonObject sys=jsonObject.getAsJsonObject("sys");
        String country=sys.get("country").getAsString();
        String sunrise=sys.get("sunrise").getAsString();
        String sunset=sys.get("sunset").getAsString();
        String temp = main.get("temp").getAsString();
        String humidity = main.get("humidity").getAsString();
        String pressure = main.get("pressure").getAsString();
        String name = jsonObject.get("name").getAsString();

        JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
        String description = weather.get("description").getAsString();
        String icon=weather.get("icon").getAsString();
        JsonObject jsonRespObj = new JsonObject();
        jsonRespObj.addProperty("humidity", humidity);
        jsonRespObj.addProperty("temp", temp);
        jsonRespObj.addProperty("pressure", pressure);
        jsonRespObj.addProperty("name", name);
        jsonRespObj.addProperty("description", description);
        jsonRespObj.addProperty("country",country);
        jsonRespObj.addProperty("sunrise",sunrise);
        jsonRespObj.addProperty("sunset",sunset);
        jsonRespObj.addProperty("icon",icon);
//    response.setContentType("application/json");

        return jsonRespObj;
    }
}

