package com.resetoter.smartqq.manager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/11/21.
 */
public class ConfigManager {
    static HashMap<String, Properties> propertyPool = new HashMap<String, Properties>();

    public static String getProperty(String configName , String name) {
        return getConfig(configName).getProperty(name);
    }

    public static void loadConfig(String fileName) {
        Properties prop = new Properties();
        try {
            InputStream stream = ConfigManager.class.getResourceAsStream(fileName);
            prop.load(new InputStreamReader(stream, "GB2312"));
            propertyPool.put(fileName, prop);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getConfig(String configName) {
        if(!propertyPool.containsKey(configName)) {
            loadConfig(configName);
        }
        return propertyPool.get(configName);
    }

}
