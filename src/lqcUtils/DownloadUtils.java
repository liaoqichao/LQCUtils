package lqcUtils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Encoder;

public class DownloadUtils {

	/**
	 * Content-Disposition:attachment;filename=我会出乱码.mp3
	 * 解决Content-Disposition响应头中的filename出现乱码的问题。
	 * filename = URLEncoder.encode(filename, "utf-8");空格变成加号。
	 * filename = new String(filename.getBytes("GBK"),"ISO-8859-1");空格不会变加号
	 * @param filename
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
		String agent = request.getHeader("User-Agent"); //获取浏览器
		if (agent.contains("Firefox")) {
			BASE64Encoder base64Encoder = new BASE64Encoder();
			filename = "=?utf-8?B?"
					+ base64Encoder.encode(filename.getBytes("utf-8"))
					+ "?=";
		} else if(agent.contains("MSIE")) {
//			filename = URLEncoder.encode(filename, "utf-8");//用这个空格会变加号
			filename = new String(filename.getBytes("GBK"),"ISO-8859-1");
		} else {
//			filename = URLEncoder.encode(filename, "utf-8");
			filename = new String(filename.getBytes("GBK"),"ISO-8859-1");
		}
		return filename;
	}
}
