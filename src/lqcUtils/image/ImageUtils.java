package lqcUtils.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageUtils {

	/**
	 *  图片缩放
	 * @param bi 图像
	 * @param w 新的宽
	 * @param h 新的高
	 * @return 新的图像
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
