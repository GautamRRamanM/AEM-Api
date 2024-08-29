package com.mysite.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Author2 implements Auth2{


    @Inject
    @Default(values = "657897")
    String phone;


    @Inject
    @Default(values = "ramanMishra")
    String empname;

    @Inject
    Boolean designation;

    @Override
    public String getPhone() {
        return phone;
    }
    @Override
    public String getEmpName() {
        return empname;
    }
    @Override
    public Boolean getDesignation() {
        return designation;
    }
}
