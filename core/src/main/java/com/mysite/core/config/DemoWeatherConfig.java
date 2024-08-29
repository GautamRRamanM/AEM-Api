package com.mysite.core.config;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@ObjectClassDefinition(name="weather api configuration same")
public @interface DemoWeatherConfig {

    @AttributeDefinition(name = "API Key", description = "API KEY for the weather API")
    String apiKey() default "1f87e5de93f1954a9246f0b344a4558a";

    @AttributeDefinition(name="API URL", description = "URl for the weather API")
    String apiUrl() default "https://api.openweathermap.org/data/2.5/weather?zip=%s,in&appid=%s&units=imperial";

}


