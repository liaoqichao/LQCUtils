package lqcUtils.qrcode;

/**
 * ��ά���ʵ����
 * @author Liaoqichao
 * @date 2016��6��29��
 */
public class QRCodeBean {

	/**
	 * LOGO���ڶ�ά���м�
	 */
	public static final int LOGO_POSITION_CENTER = 1;
	/**
	 * LOGO���ڶ�ά�����½�
	 */
	public static final int LOGO_POSITION_BOTTOM_RIGHT = 2;
	
	private String filepath;  // ͼƬ·��
	private String content; // ͼƬ����
	private int width = 100 ; // ����ͼƬ�Ŀ�
	private int height = 100; // ����ͼƬ�ĸ�
	private String logopath; // logoͼƬ��·��
	private int logoPosition = 1; // logo��λ��,Ĭ�����м�
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getLogopath() {
		return logopath;
	}
	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}
	public int getLogoPosition() {
		return logoPosition;
	}
	public void setLogoPosition(int logoPosition) {
		this.logoPosition = logoPosition;
	}
	@Override
	public String toString() {
		return "QRCodeBean [filepath=" + filepath + ", content=" + content + ", width=" + width + ", height=" + height + ", logopath=" + logopath + ", logoPosition=" + logoPosition
				+ "]";
	}
}
