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
 * 缩小图片工具类，jdk1.7和以后不可以使用
 * 2017-9-5
 * @author LQC
 *
 */
public class NarrowImage {

	public float maxSide = 1024; // 最大高或宽 1024*768=786432
	public float maxSize = 10485760; // 1M 1048576  
	/**
	 * @param im 原始图像
	 * @param resizeTimes 倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 * @throws IOException 
	 */
	private BufferedImage zoomImage(File srcfile, float resizeTimes) throws IOException {
		
		BufferedImage result = null;

		BufferedImage im = ImageIO.read(srcfile);

		/* 原始图像的宽度和高度 */
		int width = im.getWidth();
		int height = im.getHeight();
		
		/* 调整后的图片的宽度和高度 */
		int toWidth = (int) (width * resizeTimes);
		int toHeight = (int) (height * resizeTimes);

		/* 新生成结果图片 */
		result = new BufferedImage(toWidth, toHeight,
				BufferedImage.TYPE_INT_RGB);

		result.getGraphics().drawImage(
				im.getScaledInstance(toWidth, toHeight,
						java.awt.Image.SCALE_SMOOTH), 0, 0, null);
			
		return result;

	}
	
	private void writeHighQuality(BufferedImage im, File output, float quantity) throws ImageFormatException, IOException{
        /*输出到文件流*/
        FileOutputStream newimage = new FileOutputStream(output);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
        /* 压缩质量 */
        jep.setQuality(quantity, true);
        encoder.encode(im, jep);
       /*近JPEG编码*/
        newimage.close();
	 }
	 
	/**
	 * 压缩图片，外部调用
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public boolean compressFile(File input, File output){
		File jpgTmpFile = null;
		try {
			// 1. 转成jpg格式
			jpgTmpFile = createJPGFile(input);
			
			// 2. 计算宽、高压缩比例
			float resizeTimes = calResizeTimes(jpgTmpFile);	// 压缩宽、高 
			
			// 3. 计算压缩质量参数
			float quantity = calQuantity(jpgTmpFile);	// 压缩存储大小
			
			// 4. 压缩图片
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
	 * 根据图片文件生成临时jpg格式文件
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
	  * 计算压缩图片高宽的比例
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
	 * 计算质量：压缩文件存储大小的比例。
	 * 如果高宽超过限制，大小也超出限制，那么生成的图片后的大小和原来图片的大小的比例，小于这个比例，因为修改大小的时候也修改了文件大小。
	 * @param input
	 * @return
	 */
	private float calQuantity(File input) {
		float quantity = 1;
		long lenth = input.length(); // 大小
		return lenth < maxSize ? quantity : maxSize / lenth; // MAX_SIZE 占用空间
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
