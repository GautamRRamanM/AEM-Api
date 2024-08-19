package com.mysite.core.models;

import com.adobe.cq.wcm.core.components.models.Title;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class},adapters = Auth.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Author1 implements Auth {
private static Logger log=LoggerFactory.getLogger(Author1.class);

    @ValueMapValue
    @Default(longValues = 123456789)
    long password ;
    @ValueMapValue
    @Default(values = "sxcghjuytrsfdgh")
    String color;

    @ScriptVariable
    Page currentPage;

    @ScriptVariable
    Title homePage;

    public String getPage(){
        return currentPage.getTitle();
    }
    public String getTitle(){
        return homePage.getType();
    }

    @Override
    public long getPassword() {
        return password;
    }
    @Override
    public String getColor() {
        return  color;
    }
}
