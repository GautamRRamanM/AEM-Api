package com.mysite.core.schedulers;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

@Component(
        service = { Runnable.class },
        immediate = true
)
public class SchedulerForPageCreation implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerForPageCreation.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory; //this is a service in scheduler

    @Reference
    private Scheduler scheduler;   //this is a service in scheduler

    private static final String SCHEDULER_JOB_NAME = "page creation job";

    @Activate// work when osgi bundle is in active state
    protected void activate() {
        ScheduleOptions options = scheduler.EXPR("*/2000 * * * * ?")
                .name(SCHEDULER_JOB_NAME)
                .canRunConcurrently(false);
        scheduler.schedule(this, options);
        LOG.info("Scheduled task '{}' activated.", SCHEDULER_JOB_NAME);
    }
    @Deactivate  //work when bundle is in deactive state
    protected void deactivate() {
        scheduler.unschedule(SCHEDULER_JOB_NAME);
        LOG.info("Scheduled task '{}' deactivated.", SCHEDULER_JOB_NAME);
    }

    @Override //its a run method present in Runnable class
    public void run() {
        createPage();
    }

    private void createPage() {
        LOG.info("Running Page Creation Task");

        Map<String, Object> param = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "writePage");
       // here we are creating a subservice in path http://localhost:4502/crx/explorer/index.jsp in this create system user by giving admin and path.
        try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param)) {
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class); //its an api

            if (pageManager != null) {
                String parentPath = "/content/mysite/us/en";  //parent path
                String pageName = "page-" + System.currentTimeMillis();
                String templatePath = "/conf/mysite/settings/wcm/templates/MYTEMP1";  // Make sure this template path is correct
                LOG.info("get templatepath :"+templatePath);
               Page page = pageManager.create(parentPath, pageName, templatePath, pageName);

                if (page != null) {
                    LOG.info("Page created: {}", page.getPath());
                } else {
                    LOG.error("Page creation returned null");
                }
            } else {
                LOG.error("PageManager could not be adapted from ResourceResolver");
            }
        } catch (Exception e) {
            LOG.error("Error creating page: {}", e.getMessage(), e);
        }
    }
}
