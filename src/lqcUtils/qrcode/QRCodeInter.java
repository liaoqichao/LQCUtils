package lqcUtils.qrcode;

/**
 * 二维码的接口，有生成二维码和解析二维码
 * @author Liaoqichao
 * @date 2016年6月29日
 */
public interface QRCodeInter {

	/**
	 * 编码:
	 * 	- 1. 创建QRCodeBuilder,参数为内容和路径。
	 * 	- 2. 通过QRCodeBuilder的方法创建生成二维码的工具类，有QECode和ZXing两种方式，通过QRCodeBuilder的静态常量选择。
	 * 		使用QRCodeInter类型接收。
	 *  - 3. 通过QRCodeInter的createImage方法生成二维码文件。
	 */
	
	// ZXing 编码和解码
//	QRCodeBean bean = new QRCodeBean();
//	bean.setContent("ZXINGXINGXING");
//	bean.setFilepath("E:\\Eclipse\\QRCodeOutput\\ZXingBean.png");
//	bean.setWidth(700);
//	bean.setHeight(700);
//	QRCodeBuilder builder = new QRCodeBuilder(bean);
//	QRCodeInter qr = builder.createQRCodeInstance(QRCodeBuilder.METHOD_ZXING);
//	qr.createImage();
//	String data = qr.decodeQRCode("E:\\Eclipse\\QRCodeOutput\\ZXingBean.png");
//	System.out.println(data);
	
	// QRCode 编码和解码
//	Qrcode qrcode = new Qrcode();
//	qrcode.setQrcodeEncodeMode('B');
//	qrcode.setQrcodeErrorCorrect('M');
//	qrcode.setQrcodeVersion(7);
//	QRCodeBean bean = new QRCodeBean();
//	bean.setContent("QRQRQRR");
//	bean.setFilepath("E:\\Eclipse\\QRCodeOutput\\QRQRQRR.png");
//	bean.setHeight(300);
//	bean.setWidth(300);
//	QRCodeBuilder builder = new QRCodeBuilder(bean,qrcode);
////	QRCodeBuilder builder = new QRCodeBuilder(bean);
//	QRCodeInter qr = builder.createQRCodeInstance(QRCodeBuilder.METHOD_QRCODE);
//	qr.createImage();
//	String data = qr.decodeQRCode("E:\\Eclipse\\QRCodeOutput\\QRQRQRR.png");
//	System.out.println(data);
	
	/**
	 * 把字符串内容存储到二维码中。这是并没有生成二维码图片文件
	 */
	
	/**
	 * 二维码解码，返回字符串信息
	 * @param filepath 二维码图片路径
	 * @return 二维码存储的字符串，不一定是JSON格式字符串
	 */
	/*
	 *	String zx_data = qrinter.decodeQRCode(filepath);
	 *	System.out.println(zx_data);
	 */
	abstract public String decodeQRCode(String filepath);
	
	/**
	 * 生成二维码图片
	 */
	abstract public void createImage();
	
	/**
	 * 给二维码图片添加logo
	 */
}
