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
 * ��ά�빤���ࣺͨ��QRCode��ʽ���ɺͽ�����ά��
 * @author Liaoqichao
 * @date 2016��6��29��
 */
public final class QRCode extends QRCodeBean implements QRCodeInter{

	/**
	 * String content �� ��ά��洢��Ϣ������ַ�������URL�淶��΢��ɨ�����ֱ����ת����ҳ�档
	 */
	private Qrcode qr; // ���ɶ�ά��������
	private BufferedImage bi; // �洢��ά��ͼ��
	
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
	 * ��ά����룺���ַ������ɶ�ά�롣
	 * ���ı������Ϊ��ά�룬�����浽ͼƬ�С�
	 */
	private void createQRCode(){
		/*
		 * ECC:�ݴ���
		 * 	���ö�ά���Ŵ��ʣ���ѡL(7%)��M(15%)��Q(25%)��H(30%)���Ŵ���Խ�߿ɴ洢����ϢԽ�٣����Զ�ά�������ȵ�Ҫ��ԽС 
		 * 
		 * EncodeMode:����ģʽ
		 * 	Numeric			����
		 * 	Alphanumeric	Ӣ����ĸ
		 * 	Binary			������
		 * 	Kanji			����	
		 * 
		 * Version:�汾
		 * 	1-40һ��40���汾��
		 * 	1	21x21ģ��
		 *  40 	177x177ģ��
		 *  ÿ����һ���汾���߳�����4��ģ�顣�����û��Զ�ѡ���ʵ��İ汾��
		 */
		try {
			this.qr = this.getQr();
			if(this.getContent() == null) this.setContent("");
			byte[] contentBytes = this.getContent().getBytes("UTF-8");
			// ��������ֽ�������
			if(contentBytes.length >0 || contentBytes.length < 120){
				// ͨ���ֽ����飬��ɲ������͵Ķ�ά����.ֵΪ�棬��Ӧ��ά����Ǻڸ��ӡ�
				boolean[][] b = qr.calQrcode(contentBytes);
				// ��ͼ
				this.bi = new BufferedImage(b.length, b[0].length, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = bi.createGraphics();
				g.setBackground(Color.WHITE);
				g.clearRect(0, 0, b.length, b[0].length);
				g.setColor(Color.BLACK);
				int mulriple = 1; // ����
//				int pixoff = 2; // ����ƫ�����������ÿ��ܵ��½�������
				for(int i = 0 ; i < b.length ; i++){
					for(int j = 0 ; j < b[0].length ; j++){
						if(b[i][j])	g.fillRect(j * mulriple, i * mulriple,//
								mulriple, mulriple);
					}
				}
				g.dispose();
				bi.flush();
			} else {
				System.out.println("�����ֽ����鳤��Ϊ"+contentBytes.length+",����120");
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
	 * ��ά����롣�Ӷ�ά��ͼƬ���ɶ�Ӧ���ַ�����
	 * ����ʹ��ZXing�Ľ��룬QRCode�Ľ��뱨��
	 * @param filepath ��ά��ͼƬ�ļ�·��
	 * @return ��ά��ͼƬ�������ַ���
	 */
	public String decodeQRCode(String filepath){
		return new ZXing().decodeQRCode(filepath);
//		BufferedImage bi = null;
//		String content = null;
//		try {
//			File file = new File(filepath);
//			if(!file.exists()) throw new Exception("�ļ�������");
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
			// ������ά��
			createQRCode();
			// ����ͼƬ
			File file = new File(this.getFilepath());
			if(!file.exists()) file.createNewFile();
			// ͼ������
			bi = ImageUtils.resize(bi,this.getWidth(),this.getHeight());
			//���logo
			if(this.getLogopath() != null && !this.getLogopath().trim().equals("") ){
				addLogo();
			}
			// ��ͼƬд���ļ�
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addLogo() {
		try {
			File logo_file = new File(this.getLogopath());
			if(!logo_file.exists()) throw new Exception("Logo�ļ�������");
			BufferedImage logo = ImageIO.read(logo_file);
			
			logo = ImageUtils.resize(logo,
					logo.getWidth() > this.bi.getWidth()*20/100 ? this.bi.getWidth()*20/100 : logo.getWidth(),
					logo.getHeight() > this.bi.getHeight()*20/100 ? this.bi.getHeight()*20/100 : logo.getHeight());
			
			int x = 0, y = 0;
			if(this.getLogoPosition() == 1){ // logo���м�
				x = (bi.getWidth() - logo.getWidth()) / 2 ;
				y = (bi.getHeight() - logo.getHeight()) / 2;
			} else if(this.getLogoPosition() == 2){ // logo�����½�
				x = (bi.getWidth() - logo.getWidth());
				y = (bi.getHeight() - logo.getHeight());
			}
			
			// ��ͼ
			Graphics2D g = bi.createGraphics();
			g.drawImage(logo, x, y, logo.getWidth(), logo.getHeight(), null);
			g.drawRoundRect(x, y, logo.getWidth(), logo.getHeight(), 15, 15);
            g.setStroke(new BasicStroke(2)); // logo�߿���
            g.setColor(Color.WHITE); // logo�߿���ɫ
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
