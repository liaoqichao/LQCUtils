package lqcUtils.image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ��СͼƬ�����࣬jdk1.7���Ժ󲻿���ʹ��
 * 2017-9-5
 * @author LQC
 *
 */
public class NarrowImage {

	public float maxSide = 1024; // ���߻�� 1024*768=786432
	public float maxSize = 10485760; // 1M 1048576  
	/**
	 * @param im ԭʼͼ��
	 * @param resizeTimes ����,����0.5������Сһ��,0.98�ȵ�double����
	 * @return ���ش�����ͼ��
	 * @throws IOException 
	 */
	private BufferedImage zoomImage(File srcfile, float resizeTimes) throws IOException {
		
		BufferedImage result = null;

		BufferedImage im = ImageIO.read(srcfile);

		/* ԭʼͼ��Ŀ�Ⱥ͸߶� */
		int width = im.getWidth();
		int height = im.getHeight();
		
		/* �������ͼƬ�Ŀ�Ⱥ͸߶� */
		int toWidth = (int) (width * resizeTimes);
		int toHeight = (int) (height * resizeTimes);

		/* �����ɽ��ͼƬ */
		result = new BufferedImage(toWidth, toHeight,
				BufferedImage.TYPE_INT_RGB);

		result.getGraphics().drawImage(
				im.getScaledInstance(toWidth, toHeight,
						java.awt.Image.SCALE_SMOOTH), 0, 0, null);
			
		return result;

	}
	
	private void writeHighQuality(BufferedImage im, File output, float quantity) throws ImageFormatException, IOException{
        /*������ļ���*/
        FileOutputStream newimage = new FileOutputStream(output);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
        /* ѹ������ */
        jep.setQuality(quantity, true);
        encoder.encode(im, jep);
       /*��JPEG����*/
        newimage.close();
	 }
	 
	/**
	 * ѹ��ͼƬ���ⲿ����
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public boolean compressFile(File input, File output){
		File jpgTmpFile = null;
		try {
			// 1. ת��jpg��ʽ
			jpgTmpFile = createJPGFile(input);
			
			// 2. �������ѹ������
			float resizeTimes = calResizeTimes(jpgTmpFile);	// ѹ������ 
			
			// 3. ����ѹ����������
			float quantity = calQuantity(jpgTmpFile);	// ѹ���洢��С
			
			// 4. ѹ��ͼƬ
			writeHighQuality(zoomImage(jpgTmpFile, resizeTimes), output, quantity);
			return true;
		} catch (ImageFormatException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally{
			if(jpgTmpFile != null){
				jpgTmpFile.delete();
			}
		}
	 }

	/**
	 * ����ͼƬ�ļ�������ʱjpg��ʽ�ļ�
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private File createJPGFile(File input) throws IOException {
		File jpgTmpFile = null;
		BufferedImage inputBI = ImageIO.read(input);
		String jptTmpFileName = input.getName().substring(0,input.getName().lastIndexOf("."));
		jpgTmpFile = new File("tmp_"+jptTmpFileName+".jpg");
		ImageIO.write(inputBI, "jpg", jpgTmpFile);
		return jpgTmpFile;
	}

	/**
	  * ����ѹ��ͼƬ�߿�ı���
	  * @param input
	  * @return
	  * @throws IOException
	  */
	private float calResizeTimes(File input) throws IOException {
		float resizeTimes = 1;
		BufferedImage bi = ImageIO.read(input);
		int height = bi.getHeight();
		int width = bi.getWidth();
		int _maxSide = height > width ? height : width;
		return  _maxSide < maxSide ? resizeTimes : maxSide / _maxSide ;
	}
	
	/**
	 * ����������ѹ���ļ��洢��С�ı�����
	 * ����߿������ƣ���СҲ�������ƣ���ô���ɵ�ͼƬ��Ĵ�С��ԭ��ͼƬ�Ĵ�С�ı�����С�������������Ϊ�޸Ĵ�С��ʱ��Ҳ�޸����ļ���С��
	 * @param input
	 * @return
	 */
	private float calQuantity(File input) {
		float quantity = 1;
		long lenth = input.length(); // ��С
		return lenth < maxSize ? quantity : maxSize / lenth; // MAX_SIZE ռ�ÿռ�
	}

	public float getMaxSide() {
		return maxSide;
	}

	public void setMaxSide(float maxSide) {
		this.maxSide = maxSide;
	}

	public float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}
	
	public static void main(String[] args) throws IOException {
        File input = new File("F:\\kenan\\847.png");
//        File input = new File("C:\\Users\\13670\\Desktop\\1.bmp");
        File output = new File("C:\\Users\\13670\\Desktop\\output.bmp");
        
        NarrowImage narrowImage = new NarrowImage();
        narrowImage.compressFile(input, output);
	}
	 

}
