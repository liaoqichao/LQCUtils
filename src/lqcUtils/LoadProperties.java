package lqcUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件的类,要求在/src路径下
 * 这里不用getResourceAsStream，所以不会出现修改了配置文件，prop内容不变的情况
 */
public class LoadProperties {

	private Properties properties;
	public LoadProperties(String propertiesFileName){
		File file = null;
		InputStream in = null; 
//		InputStream in = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
		Properties prop = new Properties();
		try {
//			file = new File("src/"+propertiesFileName);
			file = new File(propertiesFileName);
			in = new FileInputStream(file);
			prop.load(in);
		} catch (IOException e) {
			System.out.println("没有读取到Properties文件，请确认路径");
			e.printStackTrace();
		}
		this.properties = prop;
	}
	
	public Properties getProperties(){
		return this.properties;
	}
}
