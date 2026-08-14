package com.tangailquiz.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Reads db.properties once when the app starts.
 * Other classes call AppConfig.get("key") to read a value.
 */
public class AppConfig {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("Could not find db.properties on the classpath.");
            }
            PROPS.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Could not load db.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return PROPS.getProperty(key);
    }
}
