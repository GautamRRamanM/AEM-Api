//package com.mysite.core.servlets;
//
//import com.drew.lang.annotations.NotNull;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.sling.api.SlingHttpServletResponse;
//import org.apache.sling.api.servlets.SlingAllMethodsServlet;
//import org.apache.sling.servlets.annotations.SlingServletPaths;
//import org.osgi.service.component.annotations.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.Servlet;
//import javax.servlet.ServletException;
//import java.io.IOException;
//
//@Component(service = Servlet.class)
//@SlingServletPaths("/bin/weatherApiData")
//public class WeatherApiServlet extends SlingAllMethodsServlet {
//
//    private static final Logger log = LoggerFactory.getLogger(WeatherApiServlet.class);
//
//        private static final String API_KEY = "b2414ba1e88d59ea74dd45ec33b4614a"; // Replace with your actual API key
//        private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=imperial";
//
//        @Override
//        public void doGet( SlingHttpServletRequest request,  SlingHttpServletResponse response) throws ServletException, IOException {
//            log.info("hi raman");

//            String latitude = request.getParameter("lat");
//            String longitude = request.getParameter("lon");
//
//            if (latitude != null && longitude != null) {
//                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//                    String apiUrl = String.format(API_URL, latitude, longitude, API_KEY);
////                  String apiUrl="https://api.openweathermap.org/data/2.5/weather?lat=50&lon=60&appid=b2414ba1e88d59ea74dd45ec33b4614a&units=imperial";
//                    Log.info("Weather API URL : "+apiUrl);
//                    HttpGet httpGet = new HttpGet(apiUrl);
//                    HttpResponse httpResponse = httpClient.execute(httpGet);
//
//                    String weatherData = EntityUtils.toString(httpResponse.getEntity());
//
//                    JsonObject jsonObject = JsonParser.parseString(weatherData).getAsJsonObject();
//
//                    JsonObject main = jsonObject.getAsJsonObject("main");
//                    String temperature = main.get("temp").getAsString();
//
//                    String humidity = main.get("humidity").getAsString();
//                    String placeName = jsonObject.get("name").getAsString();
//                    JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
//                    String description = weather.get("description").getAsString();
//                    JsonObject jsonResponseObj = new JsonObject();
//                    jsonResponseObj.addProperty("temperature", temperature);
//                    jsonResponseObj.addProperty("humidity", humidity);
//                    jsonResponseObj.addProperty("placeName", placeName);
//                    jsonResponseObj.addProperty("description", description);
//
//                response.setContentType("application/json");
//                    response.getWriter().write(String.valueOf(jsonResponseObj));
//                } catch (Exception e) {
//                    Log.error("Error fetching weather data", e);
//                    response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch weather data.");
//                }
//            } else {
//                response.sendError(SlingHttpServletResponse.SC_BAD_REQUEST, "Missing latitude or longitude parameter.");
//            }
//        }

//    }



