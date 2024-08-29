package com.mysite.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "mysite/components/page", // Use the correct resource type
        selectors = "add",
        extensions = {"json", "txt", "html"}
)
public class WithoutModel extends SlingSafeMethodsServlet {

    public static final Logger log = LoggerFactory.getLogger(WithoutModel.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        log.info("WithoutModel servlet invoked");

        String pagePath = request.getParameter("pagePath");
        if (pagePath == null) {
            pagePath = "/content/mysite/us/en/demo";
        }

        List<String> pages = getSource(pagePath, request);
        log.info("Pages found: {}", pages);

        // Send response (for example, as JSON)
        response.setContentType("application/json");
        response.getWriter().write(pages.toString());
    }

    public List<String> getSource(String path, SlingHttpServletRequest request) {
        List<String> resultList = new ArrayList<>();
        ResourceResolver resourceResolver = request.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

        if (pageManager == null) {
            log.error("PageManager could not be adapted");
            return resultList;
        }

        Page page = pageManager.getContainingPage(path);
        if (page == null) {
            log.error("Page not found for path: {}", path);
            return resultList;
        }

        log.info("Page retrieved: {}", page.getPath());

        Iterator<Page> childPages = page.listChildren();
        while (childPages.hasNext()) {
            Page childPage = childPages.next();
            Resource resource = childPage.getContentResource();
            if (resource != null) {
                log.info("Resource type: {}", resource.getResourceType());
                Iterator<Resource> components = resource.listChildren();
                while (components.hasNext()) {
                    Resource component = components.next();
                    Iterator<Resource> childResources = component.listChildren();
                    while (childResources.hasNext()) {
                        Resource valueResource = childResources.next();
                        String name = valueResource.getValueMap().get("jcr:createdBy", String.class);
                        if (name != null) {
                            resultList.add(name);
                        }
                    }
                }
            } else {
                log.warn("Content resource is null for page: {}", childPage.getPath());
            }
        }
        return resultList;
    }
}

