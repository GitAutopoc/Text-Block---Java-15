package com.config.assessment;

import org.json.JSONObject;

public class ConfigParser {
    private JSONObject config;

    public ConfigParser(String defaultConfig) {
        this.config = new JSONObject(defaultConfig);
    }

    public void updateConfig(String newConfig) {
        JSONObject update = new JSONObject(newConfig);
        for (String key : update.keySet()) {
            Object value = update.get(key);
            if (value instanceof JSONObject) {
                if (!this.config.has(key)) {
                    this.config.put(key, new JSONObject());
                }
                JSONObject subConfig = this.config.getJSONObject(key);
                JSONObject newSubConfig = update.getJSONObject(key);
                for (String subKey : newSubConfig.keySet()) {
                    subConfig.put(subKey, newSubConfig.get(subKey));
                }
            } else {
                this.config.put(key, value);
            }
        }
    }

    public Object getConfigValue(String keyPath) {
        String[] keys = keyPath.split("\\.");
        JSONObject current = this.config;
        for (int i = 0; i < keys.length - 1; i++) {
            current = current.getJSONObject(keys[i]);
        }
        return current.get(keys[keys.length - 1]);
    }

    public static void main(String[] args) {
        // Define default configuration using a text block
        String defaultConfig = """
            {
                "server": "localhost",
                "port": 8080,
                "useSSL": false,
                "userPreferences": {
                    "theme": "dark",
                    "language": "en"
                }
            }
            """;

        ConfigParser parser = new ConfigParser(defaultConfig);

        // Print initial configuration
        System.out.println("Initial Server Config: " + parser.getConfigValue("server"));
        System.out.println("Initial Port Config: " + parser.getConfigValue("port"));
        System.out.println("Initial SSL Config: " + parser.getConfigValue("useSSL"));
        System.out.println("Initial Theme Config: " + parser.getConfigValue("userPreferences.theme"));
        System.out.println("Initial Language Config: " + parser.getConfigValue("userPreferences.language"));

        // Define updates using a text block
        String updates = """
            {
                "port": 8443,
                "useSSL": true,
                "userPreferences": {
                    "theme": "light"
                }
            }
            """;

        // Update configuration
        parser.updateConfig(updates);

        // Print updated configuration
        System.out.println("Updated Server Config: " + parser.getConfigValue("server"));
        System.out.println("Updated Port Config: " + parser.getConfigValue("port"));
        System.out.println("Updated SSL Config: " + parser.getConfigValue("useSSL"));
        System.out.println("Updated Theme Config: " + parser.getConfigValue("userPreferences.theme"));
        System.out.println("Updated Language Config: " + parser.getConfigValue("userPreferences.language"));
    }
}
