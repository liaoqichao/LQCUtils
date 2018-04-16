package lqcUtils.barcode;

import java.io.File;
import java.util.HashMap;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class BarcodeUtils {


	@Test
	public void test(){
		String text = "200706290544";
		String path = "C:\\Users\\13670\\Desktop\\200706290544.png";
		try {
			craeteBarcode(text, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void craeteBarcode(String text, String path) throws Exception{
			int width = 300;
			int height = 300;
			// int width = 105;
			// int height = 50;
			// 条形码的输入是13位的数字
			// String text = "6923450657713";
			// 二维码的输入是字符串
			String format = "png";
			HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 条形码的格式是 BarcodeFormat.EAN_13
			// 二维码的格式是BarcodeFormat.QR_CODE
			BitMatrix bm = new MultiFormatWriter().encode(text,
					BarcodeFormat.EAN_13 , width, height, hints);

			File out = new File(path);
			// 生成条形码图片
			// File out = new File("ean3.png");

			WriteBitMatricToFile.writeBitMatricToFile(bm, format, out);
	}
}
