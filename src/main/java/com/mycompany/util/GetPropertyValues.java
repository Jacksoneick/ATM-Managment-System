package com.mycompany.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;


public class GetPropertyValues {
    InputStream inputStream;

    public String getPropValues(String property) throws IOException {
        /** Takes in an array of property names and returns the corresponding property values as an array. */
        String result = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }


            result = prop.getProperty(property);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }

        return result;
    }
}
