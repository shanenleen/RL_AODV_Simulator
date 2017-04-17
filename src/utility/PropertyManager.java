package utility;

import java.util.Properties;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 23, 2006
 * Time: 1:10:09 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * a class for loading a properties file and read its properities
 */
public class PropertyManager {
    public static final String FILE_NAME = "configs.properties";
    private static Properties properties;

    static {
        properties = new Properties();
    }

    /**
     * a public function to load a properity from a properties file
     *
     * @param propertyName key name for that property
     * @return a String value of that property
     */
    public static int readProperty(String propertyName) {
        System.out.println(properties.getProperty(propertyName));
        return Integer.parseInt(properties.getProperty(propertyName));
    }

    public static String readProperty(String fileName, String propertyName) {
        Properties propertiesfile = new Properties();
        try {
            propertiesfile.load(PropertyManager.class.getResourceAsStream("/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return propertiesfile.getProperty(propertyName);

    }

}
