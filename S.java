package com.mysite.core.services;

import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.PersistenceException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = PageManager.class)
public class PageManagerImpl implements PageManager, com.mysite.core.services.PageManager {

    @Reference
    private ResourceResolverService resourceResolverService; // Your service to get ResourceResolver

    @Override
    public void createPage(String pagePath, String title) throws PersistenceException {
        ResourceResolver resolver = resourceResolverService.getResolver();
        Resource parent = resolver.getResource(pagePath);

        if (parent == null) {
            resolver.create(parent, "jcr:primaryType", "cq:Page");
            resolver.create(parent, "jcr:content", "jcr:primaryType", "cq:PageContent", "jcr:title", title);
            resolver.commit();
        }
    }
}

