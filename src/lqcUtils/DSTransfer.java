package lqcUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

/**
 * ���ݽṹת��
 * @author LQC
 *
 */
public class DSTransfer {

	/**
	 * �ֽ�����->����
	 * @param bytes
	 * @return
	 */
	public static Object byteToObject(byte[] bytes) {  
		Object obj = null;  
		try {  
		    ByteArrayInputStream bi = new ByteArrayInputStream(bytes);  
		    ObjectInputStream oi = new ObjectInputStream(bi);  
		   
		    obj = oi.readObject();  
		    bi.close();  
		    oi.close();  
		} catch (Exception e) {  
		    System.out.println("translation" + e.getMessage());  
		    e.printStackTrace();  
		}  
        return obj;  
	} 
	/**
	 * ����->�ֽ�����
	 * @param obj
	 * @return
	 */
	public static byte[] objectToByte(java.lang.Object obj) {  
		    byte[] bytes = null;  
		    try {  
		       ByteArrayOutputStream bo = new ByteArrayOutputStream();  
		       ObjectOutputStream oo = new ObjectOutputStream(bo);  
		       oo.writeObject(obj);  
		  
		       bytes = bo.toByteArray();  
		  
		       bo.close();  
		       oo.close();  
		   } catch (Exception e) {  
		       System.out.println("translation" + e.getMessage());  
		       e.printStackTrace();  
		   }  
		   return bytes;  
	}  
	
	/**
	 * ��һ���ļ�����������ļ����ַ�����
	 * @param file
	 * @return
	 */
	public static byte[] fileToByteArray(File file){
		BufferedInputStream bis = null;
		byte[] buf = null;
		try{
			bis = new BufferedInputStream(new FileInputStream(file));
			buf = new byte[bis.available()];
			bis.read(buf);
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("err");
		} finally{
			try {
				if(bis != null)
					bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return buf;
	}
	
	/**
	 * ���ļ����ͱ��Blob����,�Ա����浽���ݿ�
	 * @param file
	 * @return
	 */
	public static Blob fileToBlob(File file){
		byte[] bin_file = DSTransfer.fileToByteArray(file);
		Blob blob = null;
		try {
			blob = new SerialBlob(bin_file);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blob;
	}
	
	/**
	 * �����ݿ��Blob�������ݱ��浽����Ӳ��
	 * @param blob
	 * @param file_path
	 */
	public static void blobToLocalDisk(Blob blob, String file_path){
		
		File file = new File(file_path);
		FileOutputStream fos = null;
		InputStream in = null;
		try {
			fos = new FileOutputStream(file);
			in = blob.getBinaryStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = in.read(buf))!= -1){
				fos.write(buf, 0, len);
				fos.flush();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				in.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * inputStream -> byte[]
	 * ��������ת��Ϊ�ֽ�����
	 * byte[] -> InputStream  InputStream in = new ByteArrayInputStream(byte[]);
	 * @param in
	 * @return 
	 */
	public static byte[] inputStreamToByteArray(InputStream in){
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = in.read(buf))!= -1){
				baos.write(buf, 0, len);
			}
			return baos.toByteArray();
			
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
}
