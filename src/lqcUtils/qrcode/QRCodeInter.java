package lqcUtils.qrcode;

/**
 * ��ά��Ľӿڣ������ɶ�ά��ͽ�����ά��
 * @author Liaoqichao
 * @date 2016��6��29��
 */
public interface QRCodeInter {

	/**
	 * ����:
	 * 	- 1. ����QRCodeBuilder,����Ϊ���ݺ�·����
	 * 	- 2. ͨ��QRCodeBuilder�ķ����������ɶ�ά��Ĺ����࣬��QECode��ZXing���ַ�ʽ��ͨ��QRCodeBuilder�ľ�̬����ѡ��
	 * 		ʹ��QRCodeInter���ͽ��ա�
	 *  - 3. ͨ��QRCodeInter��createImage�������ɶ�ά���ļ���
	 */
	
	// ZXing ����ͽ���
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
	
	// QRCode ����ͽ���
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
	 * ���ַ������ݴ洢����ά���С����ǲ�û�����ɶ�ά��ͼƬ�ļ�
	 */
	
	/**
	 * ��ά����룬�����ַ�����Ϣ
	 * @param filepath ��ά��ͼƬ·��
	 * @return ��ά��洢���ַ�������һ����JSON��ʽ�ַ���
	 */
	/*
	 *	String zx_data = qrinter.decodeQRCode(filepath);
	 *	System.out.println(zx_data);
	 */
	abstract public String decodeQRCode(String filepath);
	
	/**
	 * ���ɶ�ά��ͼƬ
	 */
	abstract public void createImage();
	
	/**
	 * ����ά��ͼƬ���logo
	 */
}
