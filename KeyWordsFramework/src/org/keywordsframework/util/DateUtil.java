package org.keywordsframework.util;

public class DateUtil {
	
	//��ȡ��ǰϵͳ���ڣ���ʽ�����ַ�������
	public static String format(java.util.Date date, String format) {
		String result = "";
		
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	//��ȡ���
	public static int getYear (java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}
	
	//��ȡ�·�
	public static int getMonth (java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}
	
	//��ȡ���µڼ���
	public static int getDay (java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}
	
	//��ȡСʱ
	public static int getHour (java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}
	
	//��ȡ����
	public static int getMinute (java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}
	
	//��ȡ��
	public static int getSecond (java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}
}
