package lqcUtils.service.tx;

public enum Propagation {
	/**
	 * �����ǰ�ѿ����������õ�ǰ���������ǰû��������������
	 */
	REQUIRED,
	/**
	 * ����������
	 */
	NOT_REQUIRED,
	/**
	 * �����ǰ�ѿ����������õ�ǰ���������ǰû�п��������򲻿�������
	 */
	SUPPORTS,
	/**
	 * �����ǰ�ѿ����������õ�ǰ���������ǰû�п�ʼ�������׳��쳣
	 */
	MANDATORY
}
