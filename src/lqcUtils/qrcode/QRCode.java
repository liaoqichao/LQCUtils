package lqcUtils.qrcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.data.QRCodeImage;
import lqcUtils.image.ImageUtils;

/**
 * 二维码工具类：通过QRCode方式生成和解析二维码
 * @author Liaoqichao
 * @date 2016年6月29日
 */
public final class QRCode extends QRCodeBean implements QRCodeInter{

	/**
	 * String content ： 二维码存储信息。如果字符串满足URL规范，微信扫描后则直接跳转到该页面。
	 */
	private Qrcode qr; // 生成二维码的相关类
	private BufferedImage bi; // 存储二维码图像
	
	public QRCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QRCode(QRCodeBean bean){
		this.setContent(bean.getContent());
		this.setFilepath(bean.getFilepath());
		this.setWidth(bean.getWidth());
		this.setHeight(bean.getHeight());
		this.setLogopath(bean.getLogopath());
		this.setLogoPosition(bean.getLogoPosition());
	}
	
	public QRCode(QRCodeBean bean, Qrcode qrcode){
		this.setContent(bean.getContent());
		this.setFilepath(bean.getFilepath());
		this.setWidth(bean.getWidth());
		this.setHeight(bean.getHeight());
		this.setLogopath(bean.getLogopath());
		this.setLogoPosition(bean.getLogoPosition());
		this.setQr(qrcode);
	}
	
	/**
	 * 二维码编码：从字符串生成二维码。
	 * 将文本编码成为二维码，并保存到图片中。
	 */
	private void createQRCode(){
		/*
		 * ECC:容错率
		 * 	设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小 
		 * 
		 * EncodeMode:编码模式
		 * 	Numeric			数字
		 * 	Alphanumeric	英文字母
		 * 	Binary			二进制
		 * 	Kanji			汉字	
		 * 
		 * Version:版本
		 * 	1-40一共40个版本。
		 * 	1	21x21模块
		 *  40 	177x177模块
		 *  每增加一个版本，边长增加4个模块。不设置会自动选择适当的版本。
		 */
		try {
			this.qr = this.getQr();
			if(this.getContent() == null) this.setContent("");
			byte[] contentBytes = this.getContent().getBytes("UTF-8");
			// 设置最大字节数限制
			if(contentBytes.length >0 || contentBytes.length < 120){
				// 通过字节数组，变成布尔类型的二维数组.值为真，对应二维码就是黑格子。
				boolean[][] b = qr.calQrcode(contentBytes);
				// 画图
				this.bi = new BufferedImage(b.length, b[0].length, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = bi.createGraphics();
				g.setBackground(Color.WHITE);
				g.clearRect(0, 0, b.length, b[0].length);
				g.setColor(Color.BLACK);
				int mulriple = 1; // 倍数
//				int pixoff = 2; // 设置偏移量，不设置可能导致解析出错
				for(int i = 0 ; i < b.length ; i++){
					for(int j = 0 ; j < b[0].length ; j++){
						if(b[i][j])	g.fillRect(j * mulriple, i * mulriple,//
								mulriple, mulriple);
					}
				}
				g.dispose();
				bi.flush();
			} else {
				System.out.println("内容字节数组长度为"+contentBytes.length+",大于120");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 二维码解码。从二维码图片生成对应的字符串。
	 * 这里使用ZXing的解码，QRCode的解码报错
	 * @param filepath 二维码图片文件路径
	 * @return 二维码图片解码后的字符串
	 */
	public String decodeQRCode(String filepath){
		return new ZXing().decodeQRCode(filepath);
//		BufferedImage bi = null;
//		String content = null;
//		try {
//			File file = new File(filepath);
//			if(!file.exists()) throw new Exception("文件不存在");
//			bi = ImageIO.read(file);
//			QRCodeDecoder decoder = new QRCodeDecoder();
//			content = new String(decoder.decode(new J2SEImage(bi)),"UTF-8");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return content;
	}
	
	
	@Override
	public void createImage() {
		// TODO Auto-generated method stub
		try {
			// 创建二维码
			createQRCode();
			// 创建图片
			File file = new File(this.getFilepath());
			if(!file.exists()) file.createNewFile();
			// 图像缩放
			bi = ImageUtils.resize(bi,this.getWidth(),this.getHeight());
			//添加logo
			if(this.getLogopath() != null && !this.getLogopath().trim().equals("") ){
				addLogo();
			}
			// 把图片写到文件
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
			
			// 绘图
			Graphics2D g = bi.createGraphics();
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
	
	public Qrcode getQr() {
		if(this.qr == null){
			this.qr = new Qrcode();
			this.qr.setQrcodeErrorCorrect('M');
			this.qr.setQrcodeEncodeMode('B');
			this.qr.setQrcodeVersion(7);
		}
		return this.qr;
	}

	public void setQr(Qrcode qr) {
		this.qr = qr;
	}
}

class J2SEImage implements QRCodeImage{

	private BufferedImage bi;
	
	J2SEImage(BufferedImage bi){
		this.bi = bi;
	}
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return bi.getHeight();
	}

	@Override
	public int getPixel(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return bi.getRGB(arg0, arg1);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return bi.getWidth();
	}
}
