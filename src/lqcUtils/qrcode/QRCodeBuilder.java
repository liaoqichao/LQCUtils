package lqcUtils.qrcode;

import com.swetake.util.Qrcode;

/**
 * 二维码工具类的创建类
 * @author Liaoqichao
 * @date 2016年6月29日
 */
public class QRCodeBuilder{

	public static final int METHOD_QRCODE = 1;
	public static final int METHOD_ZXING = 2;
	
	private QRCodeBean bean;
	private Qrcode qrcode ;
	
	public QRCodeBuilder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QRCodeBuilder(QRCodeBean bean){
		this.bean = bean;
	}
	public QRCodeBuilder(QRCodeBean bean, Qrcode qrcode){
		this.bean = bean;
		this.qrcode = qrcode;
	}

	public QRCodeInter createQRCodeInstance(int method){
		QRCodeInter qrcodeinter = null;
		switch(method){
			case 1 : 
					if(this.getQrcode() == null){
						qrcodeinter = new QRCode(this.getBean());
						
					}
					else{
						qrcodeinter = new QRCode(this.getBean(),this.getQrcode());
					}
					break;
			case 2 : qrcodeinter = new ZXing(this.getBean()); break;
			default : qrcodeinter = null;
		}
		return qrcodeinter;
	}

	public Qrcode getQrcode() {
		return qrcode;
	}

	public void setQrcode(Qrcode qrcode) {
		this.qrcode = qrcode;
	}

	public QRCodeBean getBean() {
		return bean;
	}

	public void setBean(QRCodeBean bean) {
		this.bean = bean;
	}
}
