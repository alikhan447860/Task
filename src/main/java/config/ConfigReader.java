package config;

import constants.FrameworkConstants;
import exceptions.FrameworkException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads configuration values from config.properties.
 * Uses Singleton pattern to ensure a single instance.
 */
public final class ConfigReader {

    private static final Logger LOG = LogManager.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Returns the singleton instance of ConfigReader.
     *
     * @return the singleton ConfigReader instance
     */
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    /**
     * Loads properties from the config file.
     */
    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
            properties.load(fis);
            LOG.info("Configuration properties loaded successfully from: {}", FrameworkConstants.CONFIG_FILE_PATH);
        } catch (IOException e) {
            LOG.error("Failed to load configuration properties", e);
            throw new FrameworkException("Failed to load config.properties file", e);
        }
    }

    /**
     * Retrieves a property value by key.
     *
     * @param key the property key
     * @return the property value, or empty string if key is not found
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key, "");
        LOG.debug("Property '{}' = '{}'", key, value);
        return value;
    }

    /**
     * Retrieves a property value by key, returning a default if not found.
     *
     * @param key          the property key
     * @param defaultValue the default value if key is not found
     * @return the property value or the default
     */
    public String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        LOG.debug("Property '{}' = '{}' (default: '{}')", key, value, defaultValue);
        return value;
    }

    /**
     * Retrieves a property as an integer.
     *
     * @param key          the property key
     * @param defaultValue the default value if parsing fails
     * @return the property value as an integer
     */
    public int getIntProperty(String key, int defaultValue) {
        try {
            String value = properties.getProperty(key);
            if (value != null) {
                return Integer.parseInt(value.trim());
            }
        } catch (NumberFormatException e) {
            LOG.warn("Could not parse integer for key '{}', using default: {}", key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * Retrieves a property as a boolean.
     *
     * @param key          the property key
     * @param defaultValue the default value if parsing fails
     * @return the property value as a boolean
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value.trim());
        }
        return defaultValue;
    }
    public void printAllProperties() {
        System.out.println("===== Loaded Properties =====");
        properties.forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
        System.out.println("=============================");
    }
}
