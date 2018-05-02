package org.keywordsframework.util;

import java.io.File;

public class FileUtil {
	public static boolean createFile(String dastFileName) {
		File file = new File(dastFileName);
		if (file.exists()) {
			System.out.println("���������ļ�" + dastFileName + "ʧ�ܣ�Ŀ���ļ��Ѿ����ڣ�");
			return false;
		}
		
		if (dastFileName.endsWith(File.separator)){
			System.out.println("���������ļ�" + dastFileName + "ʧ�ܣ�Ŀ���ļ�����ΪĿ¼��");
		}
		
		if (!file.getParentFile().exists()){
			System.out.println("Ŀ���ļ�����Ŀ¼�����ڣ�׼��������");
			if(!file.getParentFile().mkdirs()) {
				System.out.println("�����ļ�ʧ������Ŀ¼ʧ�ܣ�");
				return false;
			}
		}
		
		try {
			if (file.createNewFile()) {
				System.out.println("���������ļ�" + dastFileName + "�ɹ�");
				return true;
			} else {
				System.out.println("���������ļ�" + dastFileName + "ʧ��");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���������ļ�" + dastFileName + "ʧ��" + e.getMessage());
			return false;
		}
	}
	
	public static boolean createDir(String dastDirName) {
		File file = new File(dastDirName);
		if(file.exists()) {
			System.out.println("���������ļ�" + dastDirName + "ʧ�ܣ�Ŀ���ļ��Ѿ����ڣ�");
			return false;
		}
		
		if(file.mkdirs()) {
			System.out.println("����Ŀ¼" + dastDirName + "�ɹ�");
			return true;
		} else {
			System.out.println("����Ŀ¼" + dastDirName + "ʧ��");
			return false;
		}
		
	}
}
