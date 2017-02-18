package com.github.alexxand.utils;

import com.github.alexxand.utils.exceptions.ResourceNotLoadedException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BasicUtils {
    private BasicUtils(){
    }

    public static Properties getProp(String fileName){
        final Properties prop = new Properties();
        try(InputStream stream = BasicUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null)
                throw new ResourceNotLoadedException("Resource " + fileName + " wasn't found");
            prop.load(stream);
        } catch (IOException e){
            throw new ResourceNotLoadedException("Resource couldn't be loaded",e);
        }
        return prop;
    }
}
