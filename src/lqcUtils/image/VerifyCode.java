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
	 * 生成验证码图片，获得验证码文本进行匹配。
	 * VerifyCode v = new VerifyCode();
	 * v.output(v.getImage(),new FileOutputStream("E:/verifyCode.jpeg");
	 */

	private String alpha = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
	private String[] fonts = {"宋体","华文楷体","黑体","微软雅黑","楷体_GB2312"};
	private String text="";
	
	private static final int WIDTH = 70;
	private static final int HEIGHT = 35;
	
	public VerifyCode(){}
	
	private Color randomColor(){
		int red = (int)(Math.random()*200);//包括0不包括1。*200而不是*255是因为如果颜色太浅和背景一样看不清。
		int green = (int)(Math.random()*200);
		int blue = (int)(Math.random()*200);
		return new Color(red,green,blue);
	}
	
	
	private int  randomFontSize(){ //随机字体大小
		return (int)(Math.random()*5)+18;	//字体大小18~23(不包括23)
	}
	
	private Font randomFont(){
		String font = fonts[(int)(Math.random()*5)];
		//随机样式,由粗体,斜体排列组合的4种情况
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
	
	//-------------------外部调用的------------------------------
	public BufferedImage getImage(){
			
		//获得图片缓冲区
		BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		//获得绘制环境(画笔)
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		
		//设置背景颜色
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, WIDTH, HEIGHT);//rectangle 矩形.左上角顶点的坐标(x,y)到(x+WIDTH,y+HEIGHT)的矩形
		
		for(int i=0;i<4;i++){
			//设置字体
			g2.setFont(randomFont());
			//设置字体颜色
			g2.setColor(randomColor());
			
			//向图片写入字符串,他们的位置根据i变化,否则字重叠
			g2.drawString(randomSingleCode(), 8+15*i, 25);//10+15*i 换成 i*1.0F * WIDTH /4
			
			if(randomFlag()==1){
				g2.setColor(randomColor());	//换颜色
				int[] arr = randomLine();	//获取线的两点坐标
				g2.drawLine(arr[0], arr[1], arr[2], arr[3]);
			}
			
		}
		//向图片画若干个不同颜色的点
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
	
	
	//判断输入的验证码是否与生成的验证码匹配(不区分大小写)
	public boolean isCorrect(String str){
		if(str.equalsIgnoreCase(text))
			return true;
		return false;
	}
	
	//保存图片到指定的输出流
	public void output(BufferedImage bi, OutputStream out){
		try {
			ImageIO.write(bi, "JPEG", out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

