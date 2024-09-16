package org.where.moduleapi.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/.well-known")
public class DeepLinkController {
    @GetMapping("/apple-app-site-association")
    public Map<String, Object> iosDeeplinkHandler() {
        return Map.of(
                "applinks", Map.of(
                        "apps", List.of(),
                        "details", List.of(
                                Map.of(
                                        "appID", "DD8NQWK3XA.com.app.where",
                                        "paths", List.of("NOT /_/*", "/*")
                                )
                        )
                )
        );
    }

    @GetMapping("/assetlinks.json")
    public List<Map<String, Object>> androidDeeplinkHandler() {
        return List.of(
                Map.of(
                        "relation", List.of("delegate_permission/common.handle_all_urls"),
                        "target", Map.of(
                                "namespace", "android_app",
                                "package_name", "com.app.odiya",
                                "sha256_cert_fingerprints", List.of(
                                        "1E:AD:1A:D3:B0:07:ED:2E:67:E0:BF:1B:D7:05:40:11:7F:1D:15:EC:38:78:38:BF:F6:FD:03:A2:11:68:56:87"
                                )
                        )
                )
        );
    }
}

