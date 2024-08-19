package com.mysite.core.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WeatherFetch2 {
    private static final Logger Log = LoggerFactory.getLogger(WeatherFetch2.class);

    @ValueMapValue
    private String latitude;

    @ValueMapValue
    private String longitude;

    @Self
    private Resource resource;

    private String temp;
    private String humidity;
    private String placeName;
    private String description;
    private String pressure;

    @PostConstruct
    protected void init() {
        Log.info("Initializing WeatherFetch2 model");

        if (latitude == null || longitude == null) {
            Log.error("Latitude or Longitude is not provided");
            return;
        }

        try {
            // Prepare credentials
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    AuthScope.ANY,
                    new UsernamePasswordCredentials("admin", "admin") // Replace with actual username and password
            );

            try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build()) {
                // Construct the URL to call the servlet
//                String encodedLatitude = URLEncoder.encode(latitude);
//                String encodedLongitude = URLEncoder.encode(longitude);

                String apiUrl = String.format("http://localhost:4502/bin/raman/Api?lat=%s&lon=%s", latitude, longitude);

//                URL url=new URL(apiUrl);
//
//                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("GET");
//                InputStream inputStream=httpURLConnection.getInputStream();



                Log.info("Requesting weather data from URL: " + apiUrl);
                HttpGet httpGet = new HttpGet(apiUrl);
                HttpResponse httpResponse = httpClient.execute(httpGet);

                int statusCode = httpResponse.getStatusLine().getStatusCode();
                Log.info("HTTP Response Status: " + statusCode);

                if (statusCode == 200) {
                    String weathdata = EntityUtils.toString(httpResponse.getEntity());
                    Log.info("Weather Data: " + weathdata);

                    JsonObject jsonObject = JsonParser.parseString(weathdata).getAsJsonObject();
                    temp = jsonObject.get("temp").getAsString();
                    humidity = jsonObject.get("humidity").getAsString();
                    placeName = jsonObject.get("name").getAsString();
                    description = jsonObject.get("description").getAsString();
                    pressure=jsonObject.get("pressure").getAsString();

                } else {
                    Log.error("Failed to get valid response, status code: " + statusCode);
                    throw new RuntimeException("Failed to get valid response from weather servlet");
                }
            }
        } catch (UnsupportedEncodingException e) {
            Log.error("Error encoding URL parameters", e);
        } catch (Exception e) {
            Log.error("Error calling weather servlet", e);
        }
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getDescription() {
        return description;
    }
    public String getPressure(){
        return pressure;
    }
}
