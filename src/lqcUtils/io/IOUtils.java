package lqcUtils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	
	/**
	 * ����������,����ַ�����
	 * @param is
	 * @return String text
	 */
	public static String toString(InputStream is){
		String text = "";
		byte[] buf = new byte[1024];
		int len = 0;
		try {
			while((len = is.read(buf))!=-1){
				String str = new String(buf,0,len);
				text = text + str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * ����Դ�ļ�·����Ŀ���ļ�·��,��Դ�ļ�д��Ŀ���ļ�
	 * @throws IOException 
	 */
	public static void outputStreamWrite(String srcPath, String desPath){
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(srcPath));
			bos = new BufferedOutputStream(new FileOutputStream(desPath));
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = bis.read(buf)) != -1){
				bos.write(buf, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
//	/**
//	 * ��һ���ļ�����������ļ����ַ�����
//	 * @param file
//	 * @return
//	 */
//	public static byte[] toByteArray(File file){
//		BufferedInputStream bis = null;
//		byte[] buf = null;
//		try{
//			bis = new BufferedInputStream(new FileInputStream(file));
//			buf = new byte[bis.available()];
//			bis.read(buf);
//		} catch(Exception e){
//			e.printStackTrace();
//		} finally{
//			try {
//				bis.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return buf;
//	}
	
	/**
	 * �ļ�����
	 * @param in
	 * @param out
	 */
	public static void copy(InputStream in, OutputStream out){
		byte[] buf = new byte[1024];
		int len = 0;
		try{
			while((len = in.read(buf))!=-1){
				out.write(buf, 0, len);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	/**
//	 * ���ļ����ͱ��Blob����,�Ա����浽���ݿ�
//	 * @param file
//	 * @return
//	 */
//	public static Blob fileToBlob(File file){
//		byte[] bin_file = DSTransfer.fileToByteArray(file);
//		Blob blob = null;
//		try {
//			blob = new SerialBlob(bin_file);
//		} catch (SerialException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return blob;
//	}
	
//	/**
//	 * �����ݿ��Blob�������ݱ��浽����Ӳ��
//	 * @param blob
//	 * @param file_path
//	 */
//	public static void blobToLocalDisk(Blob blob, String file_path){
//		
//		File file = new File(file_path);
//		FileOutputStream fos = null;
//		InputStream in = null;
//		try {
//			fos = new FileOutputStream(file);
//			in = blob.getBinaryStream();
//			byte[] buf = new byte[1024];
//			int len = 0;
//			while((len = in.read(buf))!= -1){
//				fos.write(buf, 0, len);
//				fos.flush();
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally{
//			try {
//				in.close();
//				fos.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
}
