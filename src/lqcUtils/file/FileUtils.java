package lqcUtils.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;



public class FileUtils {

	/**
	 * 
	 * @param file ���и���ļ�
	 * @param dir �и����ļ��洢��Ŀ¼	
	 * @param n ÿ�����и��ļ��Ĵ�СΪnMB
	 * @throws IOException
	 */
	public void splitFile(File file,File dir,int n) throws IOException {
		
		//�������и��ļ���ŵ�Ŀ¼
		if(!dir.isDirectory()){
			throw new RuntimeException("dir��������Ŀ¼");
		}
		
		//�ö�ȡ������Դ�ļ�
		BufferedInputStream  bis = new BufferedInputStream(new FileInputStream(file));
		//���建������С
		final int SIZE = n*1048576;//1024*1024 = 1MB
		byte[] buf = new byte[SIZE];
		
		File destdir = new File(dir.getAbsolutePath()+"\\splitFile");//��ָ��Ŀ¼�½����ļ���,�ѷָ��ļ��浽�ļ�����
		if(!destdir.exists())
			destdir.mkdir();
		//��¼������Ϣ
		FileOutputStream propfos = new FileOutputStream(new File(destdir,"Properties.properties"));
		Properties prop = new Properties();
		prop.setProperty("filename", file.getName());
		//�и������
		FileOutputStream fos = null;
		int len;
		int count = 1;
		while((len = bis.read(buf))!= -1){
			//��Ƭ�ļ����и��һ�㲻��ֱ���Ķ�,�����ı����Լ�������չ��
			//����д��Ҳ������Ϊ�����Ĳ���, new fos(new File(dir,(count++)+".part");
			fos = new FileOutputStream(new File(destdir,"splitFile"+count+".part"));
			prop.setProperty("part"+count, "splitFile"+count+".part");
			count++;
			fos.write(buf, 0, len);
		}
		count --;
		Integer c = Integer.valueOf(count);
		prop.setProperty("partcount",c.toString());
		prop.store(propfos, "save file info");
		propfos.close();
		fos.close();
		bis.close();
	}
	
	/**
	 * ��ȡdirĿ¼�µ�.part�ļ��������ļ����ϲ��ļ������浽dirĿ¼����һ��Ŀ¼
	 * @param dir splitFile�ļ������ڵ�Ŀ¼
	 * @throws IOException
	 */
	public void mergeFile(File dir) throws IOException{
		/*
		 * Ӧ��ÿ�����ܶ���Ϊһ������
		 * 1.��ȡ������Ϣ
		 * 2.��ȡ��Ƭ�ļ�,����list������
		 * 3.�ϲ���ʱ����
		 * 4.д�ϲ����ļ�
		 */
		if(!dir.isDirectory()){
			throw new RuntimeException("dir��������Ŀ¼");
		}
		
		File splitDir = new File(dir,"splitFile");
		
		//��ȡ������Ϣ
		File properties = new File(splitDir,"Properties.properties");
		if(!properties.exists()){
			throw new RuntimeException(splitDir+",��Ŀ¼��û�������ļ�");	//�Զ��������쳣Ҳ����
		}
		FileReader fr  = new FileReader(properties); 
		Properties prop = new Properties();
		prop.load(fr);
		//File[] files = dir.listFiles();
		List<FileInputStream> list = new ArrayList<FileInputStream>();//����FileInputStream����File
		/*
		for(File file : files){
			if(file.getName().endsWith(".part"))//����������ʵ��filenameFilterҲ����
			list.add(new FileInputStream(file));
		}*/
		//��ȡ��Ƭ�ļ�
		int count = Integer.valueOf(prop.getProperty("partcount"));
		//��׳���ж�
		File[] parts = splitDir.listFiles(new SuffixFilter(".part"));//ǰ��д�ĺ�׺��������
		if(parts.length!=count){
			System.out.println("��Ƭ�ļ���Ŀ�������ļ���Ϣ��ƥ��,Ӧ����"+count+"��");
			throw new RuntimeException();
		}
		//��ÿ��part����������ӵ�list����
		for(int i = 1 ; i<=count;i++){
			list.add(new FileInputStream(new File(splitDir,prop.getProperty("part"+i))));
		}
		Enumeration<FileInputStream> en = Collections.enumeration(list);//�����б�
		SequenceInputStream sis = new SequenceInputStream(en);//����ö��
		//���������
		FileOutputStream fos = new FileOutputStream(new File(dir,"merge_"+prop.getProperty("filename")));
		byte[] buf = new byte[1024];
		int len = 0;
		while((len = sis.read(buf))!= -1){
			fos.write(buf, 0, len);
		}
		sis.close();
		fos.close();
	}
	
	/**
	 * ��ʾ����
	 */
//	@Test
//	public void Test(){
//		
//		//�ļ��и�
//		File srcFile = new File("E:/Eclipse/LQCUtilsTest/FileUtils/�}ľ����-Stay By My Side.ape");
//		File destDir = new File("E:/Eclipse/LQCUtilsTest/FileUtils/");
//		try {
//			splitFile(srcFile,destDir,5);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//�и��ļ��ϲ�
//		File partDir = new File("E:/Eclipse/LQCUtilsTest/FileUtils");
//		try {
//			mergeFile(partDir);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
