package lqcUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ��ȡ�����ļ�����,Ҫ����/src·����
 * ���ﲻ��getResourceAsStream�����Բ�������޸��������ļ���prop���ݲ�������
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
			System.out.println("û�ж�ȡ��Properties�ļ�����ȷ��·��");
			e.printStackTrace();
		}
		this.properties = prop;
	}
	
	public Properties getProperties(){
		return this.properties;
	}
}
