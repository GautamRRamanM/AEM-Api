package com.mysite.core.models;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.adapter.Adaptable;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables= SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Api1 {
    public static final Logger log= LoggerFactory.getLogger(Api1.class);
    @Inject
    int fname;

    String name;
    String humid;
    String weatherDescription;


    @PostConstruct
    public void init() throws IOException, JSONException {
        fetchApi(fname);
    }

    public void fetchApi(int i) throws IOException, JSONException {
        List<String> lt=new ArrayList<>();
        log.info("1");
        String Api="https://api.openweathermap.org/data/2.5/weather?zip="+i+",in&appid=1f87e5de93f1954a9246f0b344a4558a&units=imperial";

//         ======================1 method==================
//         URL url=new URL(Api);
//         log.info("url man "+url);
//         HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
//         log.info("httpURL"+httpURLConnection);
//          httpURLConnection.setRequestMethod("GET");
//         InputStreamReader inpStream = new InputStreamReader(httpURLConnection.getInputStream());
//         log.info("inpStream "+inpStream);
//         log.info("stream1 "+inpStream.toString());
//=================================2 method===========================================
        CloseableHttpClient httpReq = HttpClients.createDefault();
        log.info("httpreq "+httpReq);
        HttpGet getReq = new HttpGet(Api);
        log.info("getReq "+getReq);
        CloseableHttpResponse closereq = httpReq.execute(getReq);
        log.info("closeReq "+closereq);
        String jsonRes = EntityUtils.toString(closereq.getEntity());
        log.info("jsonRes "+jsonRes);
        JSONObject json = new JSONObject(jsonRes);
        log.info("jsonFormate "+json);
        weatherDescription = json.getJSONArray("weather").getJSONObject(0).getString("description");
        humid = json.getJSONObject("main").get("humidity").toString();

        log.info("weatherDescription"+weatherDescription);
        name = json.get("name").toString();
        log.info("name1 "+name);
        log.info("humid1 "+humid);
    }

    public String getHumid(){
        return humid;
    }
    public String getName(){
        return name;
    }
    public String getWeatherDescription(){
        return weatherDescription;
    }
}
