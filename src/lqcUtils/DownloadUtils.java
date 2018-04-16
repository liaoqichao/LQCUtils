package lqcUtils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Encoder;

public class DownloadUtils {

	/**
	 * Content-Disposition:attachment;filename=�һ������.mp3
	 * ���Content-Disposition��Ӧͷ�е�filename������������⡣
	 * filename = URLEncoder.encode(filename, "utf-8");�ո��ɼӺš�
	 * filename = new String(filename.getBytes("GBK"),"ISO-8859-1");�ո񲻻��Ӻ�
	 * @param filename
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
		String agent = request.getHeader("User-Agent"); //��ȡ�����
		if (agent.contains("Firefox")) {
			BASE64Encoder base64Encoder = new BASE64Encoder();
			filename = "=?utf-8?B?"
					+ base64Encoder.encode(filename.getBytes("utf-8"))
					+ "?=";
		} else if(agent.contains("MSIE")) {
//			filename = URLEncoder.encode(filename, "utf-8");//������ո���Ӻ�
			filename = new String(filename.getBytes("GBK"),"ISO-8859-1");
		} else {
//			filename = URLEncoder.encode(filename, "utf-8");
			filename = new String(filename.getBytes("GBK"),"ISO-8859-1");
		}
		return filename;
	}
}
