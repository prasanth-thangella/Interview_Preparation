package com.automation.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

public class ConfigManager {
    
    // Define the interface for mapping properties
    @Config.Sources({"file:src/main/resources/config.properties"})
    public interface FrameworkConfig extends Config {
        @Key("base.uri")
        String baseUri();

        @Key("env")
        String env();
        
        @Key("report.type")
        String reportType();
    }

    // Singleton instance
    private static FrameworkConfig config;

    private ConfigManager() {}

    public static FrameworkConfig getConfig() {
        if (config == null) {
            config = ConfigFactory.create(FrameworkConfig.class);
        }
        return config;
    }
}
