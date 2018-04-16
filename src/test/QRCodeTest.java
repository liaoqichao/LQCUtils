package test;

import org.junit.Test;

import lqcUtils.qrcode.QRCodeBean;
import lqcUtils.qrcode.QRCodeBuilder;
import lqcUtils.qrcode.QRCodeInter;

public class QRCodeTest {

	@Test
	public void test(){
		QRCodeBuilder builder = new QRCodeBuilder();
		
		QRCodeBean bean = new QRCodeBean();
		bean.setContent("ZJGZKF09081700389");
		bean.setFilepath("E:/qrcode/ZJGZKF09081700389.png");
		bean.setHeight(700);
		bean.setWidth(700);
//		bean.setLogopath(logopath);
//		bean.setLogoPosition(QRCodeBean.LOGO_POSITION_CENTER);
		builder.setBean(bean);
		
		QRCodeInter qrcode =  builder.createQRCodeInstance(QRCodeBuilder.METHOD_QRCODE);
		
		qrcode.createImage();
		
//		QRCodeBean bean = new QRCodeBean();
//		bean.setContent("ZJGZKF09081700389");
//		bean.setFilepath("E:/qrcode/ZJGZKF09081700389.png");
//		bean.setHeight(700);
//		bean.setWidth(700);
//		QRCode qrcode = new QRCode(bean);
//		qrcode.createImage();
	}
}
