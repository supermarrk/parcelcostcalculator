package com.pajunma.home.exam.parcelcostcalculator.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gcash.parcel")
public class GcashParcelConfig {

    private String url;
    private String voucherApi;
    private Rule reject;
    private Rule heavy;
    private Rule small;
    private Rule medium;
    private Rule large;


    @Data
    public static class Rule {
        String limit;
        String multiplier;
    }
}
