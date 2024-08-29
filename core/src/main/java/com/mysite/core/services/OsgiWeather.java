package com.mysite.core.services;

import com.mysite.core.config.DemoWeatherConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = OsgiWeather.class)
@Designate(ocd=DemoWeatherConfig.class)
public class OsgiWeather {
    private volatile String apiKey;
    private  volatile  String apiUrl;

    @Activate
    @Modified
    protected void activate(DemoWeatherConfig config){
        this.apiKey=config.apiKey();
        this.apiUrl=config.apiUrl();

    }

public String getApiKey(){
        return apiKey;
}
public String getApiUrl(){
        return apiUrl;
}
}
