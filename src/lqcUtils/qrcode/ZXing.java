package lqcUtils.qrcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lqcUtils.image.ImageUtils;

/**
 * 二维码工具类：通过ZXing的方式生成和解析二维码
 * @author Liaoqichao
 * @date 2016年6月29日
 */
public final class ZXing extends QRCodeBean implements QRCodeInter{

	private BufferedImage bi;
	
	public ZXing() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ZXing(QRCodeBean bean){
		this.setContent(bean.getContent());
		this.setFilepath(bean.getFilepath());
		this.setHeight(bean.getHeight());
		this.setWidth(bean.getWidth());
		this.setLogopath(bean.getLogopath());
		this.setLogoPosition(bean.getLogoPosition());
	}
	
	/**
	 * 根据文本生成二维码矩阵
	 */
	private void createQRCode(){
		try {
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			// 设置QR二维码的纠错级别（H为最高级别）具体级别信息  
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hints.put(EncodeHintType.MARGIN, 0);  
			// 生成矩阵
			BitMatrix bitMatrix = new MultiFormatWriter().// 
					encode(this.getContent(), BarcodeFormat.QR_CODE,//
							this.getWidth(), this.getHeight(), hints);
			this.bi = MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据二维码图片的路径，解析二维码图片，生成字符串
	 * @param filepath
	 * @return
	 */
	public String decodeQRCode(String filepath){
		String data = null;
		BufferedImage bi;
		try {
			File file = new File(filepath);
			bi = ImageIO.read(file);
			bi = ImageUtils.resize(bi,100,100); // 图片缩放，防止图片太大读取不了
			LuminanceSource source = new BufferedImageLuminanceSource(bi);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap bineryBitMap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(bineryBitMap, hints);
			data = result.getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public void createImage() {
		// TODO Auto-generated method stub
		try {
			// 生成二维码
			createQRCode();
			// 添加logo
			addLogo();
			// 生成文件
			File file = new File(this.getFilepath());
			if(!file.exists()) file.createNewFile(); 
			// 矩阵 -> 二维码图像 
//			MatrixToImageWriter.writeToFile(this.bitMatrix, "png", file);
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void addLogo() {
		try {
			File logo_file = new File(this.getLogopath());
			if(!logo_file.exists()) throw new Exception("Logo文件不存在");
			BufferedImage logo = ImageIO.read(logo_file);
			
			logo = ImageUtils.resize(logo,
					logo.getWidth() > this.bi.getWidth()*20/100 ? this.bi.getWidth()*20/100 : logo.getWidth(),
					logo.getHeight() > this.bi.getHeight()*20/100 ? this.bi.getHeight()*20/100 : logo.getHeight());
		
			int x = 0, y = 0;
			if(this.getLogoPosition() == 1){ // logo在中间
				x = (bi.getWidth() - logo.getWidth()) / 2 ;
				y = (bi.getHeight() - logo.getHeight()) / 2;
			} else if(this.getLogoPosition() == 2){ // logo在右下角
				x = (bi.getWidth() - logo.getWidth());
				y = (bi.getHeight() - logo.getHeight());
			}
			
			Graphics2D g = this.bi.createGraphics();
			g.drawImage(logo, x, y, logo.getWidth(), logo.getHeight(), null);
			g.drawRoundRect(x, y, logo.getWidth(), logo.getHeight(), 15, 15);
            g.setStroke(new BasicStroke(2)); // logo边框宽度
            g.setColor(Color.WHITE); // logo边框颜色
            g.drawRect(x, y, logo.getWidth(), logo.getHeight());
 
            g.dispose();
            logo.flush();
            bi.flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

// google提供的类
class MatrixToImageWriter {  
    private static final int BLACK = 0xFF000000;  
    private static final int WHITE = 0xFFFFFFFF;  
  
    private MatrixToImageWriter() {  
    }  
  
    public static BufferedImage toBufferedImage(BitMatrix matrix) {  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
            }  
        }  
        return image;  
    }  
  
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        if (!ImageIO.write(image, format, file)) {  
            throw new IOException("Could not write an image of format " + format + " to " + file);  
        }  
    }  
  
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        if (!ImageIO.write(image, format, stream)) {  
            throw new IOException("Could not write an image of format " + format);  
        }  
    }  
}

final class BufferedImageLuminanceSource extends LuminanceSource {  
	  
	  private final BufferedImage image;  
	  private final int left;  
	  private final int top;  
	  
	  public BufferedImageLuminanceSource(BufferedImage image) {  
	    this(image, 0, 0, image.getWidth(), image.getHeight());  
	  }  
	  
	  public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {  
	    super(width, height);  
	  
	    int sourceWidth = image.getWidth();  
	    int sourceHeight = image.getHeight();  
	    if (left + width > sourceWidth || top + height > sourceHeight) {  
	      throw new IllegalArgumentException("Crop rectangle does not fit within image data.");  
	    }  
	  
	    for (int y = top; y < top + height; y++) {  
	      for (int x = left; x < left + width; x++) {  
	        if ((image.getRGB(x, y) & 0xFF000000) == 0) {  
	          image.setRGB(x, y, 0xFFFFFFFF); // = white  
	        }  
	      }  
	    }  
	  
	    this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);  
	    this.image.getGraphics().drawImage(image, 0, 0, null);  
	    this.left = left;  
	    this.top = top;  
	  }  
	  
	  @Override  
	  public byte[] getRow(int y, byte[] row) {  
	    if (y < 0 || y >= getHeight()) {  
	      throw new IllegalArgumentException("Requested row is outside the image: " + y);  
	    }  
	    int width = getWidth();  
	    if (row == null || row.length < width) {  
	      row = new byte[width];  
	    }  
	    image.getRaster().getDataElements(left, top + y, width, 1, row);  
	    return row;  
	  }  
	  
	  @Override  
	  public byte[] getMatrix() {  
	    int width = getWidth();  
	    int height = getHeight();  
	    int area = width * height;  
	    byte[] matrix = new byte[area];  
	    image.getRaster().getDataElements(left, top, width, height, matrix);  
	    return matrix;  
	  }  
	  
	  @Override  
	  public boolean isCropSupported() {  
	    return true;  
	  }  
	  
	  @Override  
	  public LuminanceSource crop(int left, int top, int width, int height) {  
	    return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);  
	  }  
	  
	  @Override  
	  public boolean isRotateSupported() {  
	    return true;  
	  }  
	  
	  @Override  
	  public LuminanceSource rotateCounterClockwise() {  
	  
	    int sourceWidth = image.getWidth();  
	    int sourceHeight = image.getHeight();  
	  
	    AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);  
	  
	    BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);  
	  
	    Graphics2D g = rotatedImage.createGraphics();  
	    g.drawImage(image, transform, null);  
	    g.dispose();  
	  
	    int width = getWidth();  
	    return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);  
	  }  
	  
	} 
