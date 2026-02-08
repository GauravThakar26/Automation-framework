package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	public static Properties loadProperties()
	{
		Properties prop=new Properties();
		try {
			FileInputStream fis=new FileInputStream("src/test/resources/config.properties");
			prop.load(fis);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return prop;
	}
}
