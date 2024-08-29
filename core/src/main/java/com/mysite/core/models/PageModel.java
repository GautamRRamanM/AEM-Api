package com.mysite.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(
        adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class PageModel {

    @Inject
    private Resource resource;

    private String pageTitle;

    @PostConstruct
    protected void init() {
        if (resource != null) {
            // Assuming the title is stored in the "jcr:title" property of the resource
            pageTitle = resource.getValueMap().get("jcr:title", String.class);
        }
    }

    public String getPageTitle() {
        return pageTitle;
    }
}

