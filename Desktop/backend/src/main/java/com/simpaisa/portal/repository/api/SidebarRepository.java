package com.simpaisa.portal.repository.api;

import com.simpaisa.portal.entity.api.sidebar.DocumentSidebar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SidebarRepository extends MongoRepository<DocumentSidebar , String> {
}
