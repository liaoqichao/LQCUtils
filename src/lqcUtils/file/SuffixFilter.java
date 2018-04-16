package lqcUtils.file;

import java.io.File;
import java.io.FilenameFilter;


/**
 * 过滤任一个后缀名文件
 * @author Administrator
 *
 */
public class SuffixFilter implements FilenameFilter{			//过滤任一个后缀名的文件

	private String suffix;
	public SuffixFilter(String suffix){
		this.suffix = suffix;
	}
	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return name.endsWith(suffix);
	}
	
}
