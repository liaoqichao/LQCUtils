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
	 * @param file 被切割的文件
	 * @param dir 切割后的文件存储的目录	
	 * @param n 每个被切割文件的大小为nMB
	 * @throws IOException
	 */
	public void splitFile(File file,File dir,int n) throws IOException {
		
		//创建被切割文件存放的目录
		if(!dir.isDirectory()){
			throw new RuntimeException("dir参数不是目录");
		}
		
		//用读取流关联源文件
		BufferedInputStream  bis = new BufferedInputStream(new FileInputStream(file));
		//定义缓冲区大小
		final int SIZE = n*1048576;//1024*1024 = 1MB
		byte[] buf = new byte[SIZE];
		
		File destdir = new File(dir.getAbsolutePath()+"\\splitFile");//在指定目录下建立文件夹,把分割文件存到文件夹中
		if(!destdir.exists())
			destdir.mkdir();
		//记录配置信息
		FileOutputStream propfos = new FileOutputStream(new File(destdir,"Properties.properties"));
		Properties prop = new Properties();
		prop.setProperty("filename", file.getName());
		//切割输出流
		FileOutputStream fos = null;
		int len;
		int count = 1;
		while((len = bis.read(buf))!= -1){
			//碎片文件被切割后一般不能直接阅读,除了文本。自己定义拓展名
			//可以写死也可以作为函数的参数, new fos(new File(dir,(count++)+".part");
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
	 * 读取dir目录下的.part文件和配置文件，合并文件并保存到dir目录的上一级目录
	 * @param dir splitFile文件夹所在的目录
	 * @throws IOException
	 */
	public void mergeFile(File dir) throws IOException{
		/*
		 * 应该每个功能独立为一个函数
		 * 1.读取配置信息
		 * 2.获取碎片文件,放入list集合中
		 * 3.合并成时序流
		 * 4.写合并后文件
		 */
		if(!dir.isDirectory()){
			throw new RuntimeException("dir参数不是目录");
		}
		
		File splitDir = new File(dir,"splitFile");
		
		//读取配置信息
		File properties = new File(splitDir,"Properties.properties");
		if(!properties.exists()){
			throw new RuntimeException(splitDir+",该目录下没有配置文件");	//自定义子类异常也可以
		}
		FileReader fr  = new FileReader(properties); 
		Properties prop = new Properties();
		prop.load(fr);
		//File[] files = dir.listFiles();
		List<FileInputStream> list = new ArrayList<FileInputStream>();//类型FileInputStream不是File
		/*
		for(File file : files){
			if(file.getName().endsWith(".part"))//创建过滤器实现filenameFilter也可以
			list.add(new FileInputStream(file));
		}*/
		//获取碎片文件
		int count = Integer.valueOf(prop.getProperty("partcount"));
		//健壮性判断
		File[] parts = splitDir.listFiles(new SuffixFilter(".part"));//前面写的后缀名过滤器
		if(parts.length!=count){
			System.out.println("碎片文件数目与配置文件信息不匹配,应该是"+count+"个");
			throw new RuntimeException();
		}
		//把每个part的输入流添加到list集合
		for(int i = 1 ; i<=count;i++){
			list.add(new FileInputStream(new File(splitDir,prop.getProperty("part"+i))));
		}
		Enumeration<FileInputStream> en = Collections.enumeration(list);//参数列表
		SequenceInputStream sis = new SequenceInputStream(en);//参数枚举
		//创建输出流
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
	 * 演示代码
	 */
//	@Test
//	public void Test(){
//		
//		//文件切割
//		File srcFile = new File("E:/Eclipse/LQCUtilsTest/FileUtils/倉木麻衣-Stay By My Side.ape");
//		File destDir = new File("E:/Eclipse/LQCUtilsTest/FileUtils/");
//		try {
//			splitFile(srcFile,destDir,5);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//切割文件合并
//		File partDir = new File("E:/Eclipse/LQCUtilsTest/FileUtils");
//		try {
//			mergeFile(partDir);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
