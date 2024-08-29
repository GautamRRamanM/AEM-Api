package com.mysite.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Logs {
    private static final Logger LOGGER = LoggerFactory.getLogger(Api2.class);


    public List<String> getPageTitle(){
        return null;
    }

//    protected void init()
//    {
//
//        Process log = null;
//        log.info();
//    }

    }

