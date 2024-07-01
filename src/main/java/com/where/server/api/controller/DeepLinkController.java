package com.where.server.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/.well-known")
public class DeepLinkController {
    @GetMapping("/apple-app-site-association")
    public Object iosDeeplinkHandler(){
        Resource resource = new ClassPathResource("apple-app-site-association.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(), Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @GetMapping("/assetlinks.json")
    public Object androidDeeplinkHandler(){
        Resource resource = new ClassPathResource("assetlinks.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(), Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

