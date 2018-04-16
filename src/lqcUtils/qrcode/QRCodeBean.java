package lqcUtils.qrcode;

/**
 * 二维码的实体类
 * @author Liaoqichao
 * @date 2016年6月29日
 */
public class QRCodeBean {

	/**
	 * LOGO放在二维码中间
	 */
	public static final int LOGO_POSITION_CENTER = 1;
	/**
	 * LOGO放在二维码右下角
	 */
	public static final int LOGO_POSITION_BOTTOM_RIGHT = 2;
	
	private String filepath;  // 图片路径
	private String content; // 图片内容
	private int width = 100 ; // 生成图片的宽
	private int height = 100; // 生成图片的高
	private String logopath; // logo图片的路径
	private int logoPosition = 1; // logo的位置,默认在中间
	
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
