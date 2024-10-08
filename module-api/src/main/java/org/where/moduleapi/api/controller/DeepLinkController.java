package org.where.moduleapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/.well-known")
public class DeepLinkController {
    private final ObjectMapper objectMapper;
    private final Resource appleAppSiteAssociation;
    private final Resource assetLinks;

    public DeepLinkController(ObjectMapper objectMapper,
                              @Value("classpath:apple-app-site-association.json") Resource appleAppSiteAssociation,
                              @Value("classpath:assetlinks.json") Resource assetLinks) {
        this.objectMapper = objectMapper;
        this.appleAppSiteAssociation = appleAppSiteAssociation;
        this.assetLinks = assetLinks;
    }

    @GetMapping("/apple-app-site-association")
    public Map<String, Object> iosDeeplinkHandler() throws IOException {
        return objectMapper.readValue(appleAppSiteAssociation.getInputStream(), Map.class);
    }

    @GetMapping("/assetlinks.json")
    public List<Map<String, Object>> androidDeeplinkHandler() throws IOException {
        return objectMapper.readValue(assetLinks.getInputStream(), List.class);
    }
}

