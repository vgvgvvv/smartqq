package com.resetoter.smartqq.manager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/11/21.
 */
public class ConfigManager {
    private static Map<String, Properties> propertyPool = new HashMap<>();

    public static String getProperty(String configName , String name) {
        return getConfig(configName).getProperty(name);
    }

    public static void loadConfig(String fileName) {
        Properties prop = new Properties();
        try {
            InputStream stream = ConfigManager.class.getResourceAsStream(fileName);
            prop.load(stream);
            propertyPool.put(fileName, prop);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties getConfig(String configName) {
        if(propertyPool.containsKey(configName) == false) {
            loadConfig(configName);
        }
        return propertyPool.get(configName);
    }

}
