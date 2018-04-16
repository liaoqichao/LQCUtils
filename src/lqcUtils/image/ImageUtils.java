package lqcUtils.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageUtils {

	/**
	 *  ͼƬ����
	 * @param bi ͼ��
	 * @param w �µĿ�
	 * @param h �µĸ�
	 * @return �µ�ͼ��
	 */
	public static BufferedImage resize(BufferedImage bi, int w, int h) {
		int type = bi.getType();
		double sx = (double) w/bi.getWidth();
		double sy = (double) w/bi.getHeight();
		BufferedImage target = new BufferedImage(w,h,type);
		Graphics2D g = target.createGraphics();
		g.drawRenderedImage(bi, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		target.flush();
		return target;
	}
}
