package com.mysite.core.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysite.core.services.OsgiWeather;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Api2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Api2.class);

    @OSGiService
    private OsgiWeather osgiWeather;

    @ValueMapValue
    private String fname;

    private String name;
    private String humidity;
    private String weatherDescription;
    private String windspeed;
    private String degree;
    private String longitude;
    private String description;
    private String icon;

    @PostConstruct
    protected void init() {
        if (fname != null && !fname.isEmpty()) {
            fetchWeatherData(fname);
        } else {
            LOGGER.warn("fname is null or empty");
        }
    }

    private void fetchWeatherData(String pin) {
        String apiKey = osgiWeather.getApiKey();
        String apiUrl = String.format(osgiWeather.getApiUrl(), pin, apiKey);
        LOGGER.info("this is api url"+apiUrl);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    LOGGER.info("API Response: {}", result);
                    parseWeatherData(result);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error fetching weather data", e);
        }
    }

    private void parseWeatherData(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonObject main = jsonObject.getAsJsonObject("main");
        JsonObject wind = jsonObject.getAsJsonObject("wind");
        JsonObject coord = jsonObject.getAsJsonObject("coord");
        JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();

        longitude = coord.get("lon").getAsString();
        humidity = main.get("humidity").getAsString();
        windspeed = wind.get("speed").getAsString();
        degree = wind.get("deg").getAsString();
        weatherDescription = main.get("temp").getAsString();
        description = weather.get("description").getAsString();
        name = jsonObject.get("name").getAsString();
        icon = weather.get("icon").getAsString();

        LOGGER.info("Longitude: {}", longitude);
        LOGGER.info("Humidity: {}", humidity);
        LOGGER.info("Windspeed: {}", windspeed);
        LOGGER.info("Degree: {}", degree);
        LOGGER.info("Weather Description: {}", weatherDescription);
        LOGGER.info("Description: {}", description);
        LOGGER.info("Name: {}", name);
        LOGGER.info("Icon: {}", icon);
    }

    public String getFname() {
        return fname;
    }

    public String getName() {
        return name;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public String getDegree() {
        return degree;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}