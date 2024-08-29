package com.mysite.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = "mysite/components/page",selectors = {"add","sub"},extensions = {"txt","json","xml"})
public class DisplaydataWithoutModal extends SlingSafeMethodsServlet {
   public static final Logger log=LoggerFactory.getLogger(DisplaydataWithoutModal.class);

    @Override
    protected void doGet( SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String pagePath=request.getParameter("pagePath");
        log.info("it is page"+pagePath);
        if (pagePath==null){
            pagePath="/content/mysite/us/en";

        }
    }

    //    @Override
//    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
//        //super.doGet(request, response);
//        response.getWriter().write("displaycode");
//        String pagePath=request.getParameter("pagePath");
////        log.info("it is page"+pagePath);
//        if (pagePath==null){
//            pagePath="/content/mysite/us/en";
//
//        }
//        log.info("it is page"+pagePath);
//
//
//    }
}
