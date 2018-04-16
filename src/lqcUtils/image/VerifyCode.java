package lqcUtils.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class VerifyCode {
	
	/**
	 * ������֤��ͼƬ�������֤���ı�����ƥ�䡣
	 * VerifyCode v = new VerifyCode();
	 * v.output(v.getImage(),new FileOutputStream("E:/verifyCode.jpeg");
	 */

	private String alpha = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
	private String[] fonts = {"����","���Ŀ���","����","΢���ź�","����_GB2312"};
	private String text="";
	
	private static final int WIDTH = 70;
	private static final int HEIGHT = 35;
	
	public VerifyCode(){}
	
	private Color randomColor(){
		int red = (int)(Math.random()*200);//����0������1��*200������*255����Ϊ�����ɫ̫ǳ�ͱ���һ�������塣
		int green = (int)(Math.random()*200);
		int blue = (int)(Math.random()*200);
		return new Color(red,green,blue);
	}
	
	
	private int  randomFontSize(){ //��������С
		return (int)(Math.random()*5)+18;	//�����С18~23(������23)
	}
	
	private Font randomFont(){
		String font = fonts[(int)(Math.random()*5)];
		//�����ʽ,�ɴ���,б��������ϵ�4�����
		return new Font(font,Font.BOLD*randomFlag()+Font.ITALIC*randomFlag(),randomFontSize());
	}
	
	private String randomSingleCode(){
		char ch =  alpha.charAt((int)(Math.random()*alpha.length()-1));
		text = text+String.valueOf(ch);
		return String.valueOf(ch);
	}
	
	private int randomFlag(){
		int[] arr ={0,1};
		return arr[(int)(Math.random()*2)];
	}
	
	private int[] randomLine(){
		int x1,y1,x2,y2;
		x1 = (int)(Math.random()*70);
		y1 = (int)(Math.random()*35);
		x2 = (int)(Math.random()*70);
		y2 = (int)(Math.random()*35);
		int[] arr ={x1,y1,x2,y2};
		return arr;
		
	}
	
	private int[] randomDot(){
		int x1 = (int)(Math.random()*70);
		int y1 = (int)(Math.random()*35);
		int[] arr = {x1,y1};
		return arr;
	}
	
	//-------------------�ⲿ���õ�------------------------------
	public BufferedImage getImage(){
			
		//���ͼƬ������
		BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		//��û��ƻ���(����)
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		
		//���ñ�����ɫ
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, WIDTH, HEIGHT);//rectangle ����.���ϽǶ��������(x,y)��(x+WIDTH,y+HEIGHT)�ľ���
		
		for(int i=0;i<4;i++){
			//��������
			g2.setFont(randomFont());
			//����������ɫ
			g2.setColor(randomColor());
			
			//��ͼƬд���ַ���,���ǵ�λ�ø���i�仯,�������ص�
			g2.drawString(randomSingleCode(), 8+15*i, 25);//10+15*i ���� i*1.0F * WIDTH /4
			
			if(randomFlag()==1){
				g2.setColor(randomColor());	//����ɫ
				int[] arr = randomLine();	//��ȡ�ߵ���������
				g2.drawLine(arr[0], arr[1], arr[2], arr[3]);
			}
			
		}
		//��ͼƬ�����ɸ���ͬ��ɫ�ĵ�
		for(int i=0; i<(int)(Math.random()*20);i++){
			g2.setColor(randomColor());
			int[] arr = randomDot();
			g2.drawLine(arr[0], arr[1], arr[0], arr[1]);
		}

		return bi;

	}
	
	public  String getText(){
		return text;
	}
	
	
	//�ж��������֤���Ƿ������ɵ���֤��ƥ��(�����ִ�Сд)
	public boolean isCorrect(String str){
		if(str.equalsIgnoreCase(text))
			return true;
		return false;
	}
	
	//����ͼƬ��ָ���������
	public void output(BufferedImage bi, OutputStream out){
		try {
			ImageIO.write(bi, "JPEG", out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

