package lqcUtils.file;

import java.io.File;
import java.io.FilenameFilter;


/**
 * ������һ����׺���ļ�
 * @author Administrator
 *
 */
public class SuffixFilter implements FilenameFilter{			//������һ����׺�����ļ�

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
