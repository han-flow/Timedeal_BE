package com.timedeal.global.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfig {

    @Getter
    private static String siteBackUrl;

    @Getter
    private static List<String> siteFrontUrlList;

    @Getter
    private static String siteDomain;

    @Value("${custom.site.back-url}")
    public void setSiteBackUrl(String siteBackUrl) {
        AppConfig.siteBackUrl = siteBackUrl;
    }

    @Value("${custom.site.front-url}")
    public void setSiteFrontUrl(String siteFrontUrl) {
        AppConfig.siteFrontUrlList = Arrays.asList(siteFrontUrl.split(","));
    }

    @Value("${custom.site.domain}")
    public void setSiteDomain(String siteDomain) {
        AppConfig.siteDomain = siteDomain;
    }

    public static String getRedirectBaseUrl() {
        List<String> list = getSiteFrontUrlList();
        if (list.size() == 1) return list.get(0);
        return list.get(1);
    }
}
