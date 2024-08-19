package com.mysite.core.services;

import org.apache.sling.api.resource.PersistenceException;

public interface PageManager {
    void createPage(String pagePath, String title) throws PersistenceException;
}
