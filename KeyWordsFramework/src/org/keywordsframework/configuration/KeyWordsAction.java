package org.keywordsframework.configuration;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.keywordsframework.testScripts.TestSuiteByExcel;
import org.keywordsframework.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

public class KeyWordsAction {
	public static WebDriver dr;
	private static ObjectMap objectMap = new ObjectMap(Constans.Path_ConfigurationFile);
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	
	//ѡ�������
	public static void open_browser(String string,String browser) {
		try {
			if(browser.equals("ie")) {
				dr = new InternetExplorerDriver();
				Log.info("IE����������ɹ�");
			} else if(browser.equals("firefox")) {
				dr = new FirefoxDriver();
				Log.info("firefox����������ɹ�");
			} else if(browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", 
						".\\tools\\chromedriver.exe");
				dr = new ChromeDriver();
				dr.manage().window().maximize();
				Log.info("chrome����������ɹ�");
			} else {
				System.out.println("ֻ֧�֡�IE������firefox������chrome�������");
			}
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	
	//url·��
	public static void navigate(String string,String url) {
		try {
			dr.get(url);
			Log.info("��ַ��" + url + "�򿪳ɹ�");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("��ַ��" + url + "��ʧ�ܣ������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//����
	public static void click(String locatorExpression, String string) {
		try {
			dr.findElement(objectMap.getLocator(locatorExpression)).click();
			Log.info("����" + locatorExpression + "ҳ��Ԫ��");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("����" + locatorExpression + "ҳ��Ԫ��ʧ�ܣ������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//����
	public static void input(String locatorExpression, String string) {
		try {
			dr.findElement(objectMap.getLocator(locatorExpression)).clear();
			dr.findElement(objectMap.getLocator(locatorExpression)).sendKeys(string);
			Log.info("��" + locatorExpression + "Ԫ���У����룺" + string);
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("��" + locatorExpression + "Ԫ���У����룺" + string + "ʧ�ܣ������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//ת��Iframe
	public static void switch_to_Iframe(String string,String id) {
		try {
			dr.switchTo().frame(id);
			Log.info("ת��ID��" + id + "Iframeҳ��ɹ�");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("ת��ID��" + id + "Iframeҳ��ʧ�ܣ������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}	
	}
	
	//�ȴ�ʱ��
	public static void sleep(String string,String sleepTime) {
		try {
			WaitUitl.sleep(Integer.parseInt(sleepTime));
			Log.info("���ߣ�" + Integer.parseInt(sleepTime)/1000 + "��");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("�����쳣�������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//ת��Iframe
	public static void turn_out_Iframe(String string1,String string) {
		try {
			dr.switchTo().defaultContent();
			Log.info("ת��Iframe");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("ת��Iframe�쳣�������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}	
	}

	//����Ctrl+V��������
	public static void press_CtrlV(String string1,String string) {
		try {
			KeyBoardUtil.setAndCtrlVClipboardData(string);
			Log.info("ʹ�ü��а帴�����ݣ�" + string);
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("ʹ�ü��а帴�������쳣�������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}	
	}
	
	//����Tab
	public static void press_Tab(String string1,String string2) {
		try {
			KeyBoardUtil.pressTabKey();
			Log.info("����Tab��");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("����Tab���쳣�������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}	
	}
	
	//����Enter��
	public static void press_Enter(String string1,String string2) {
		try {
			KeyBoardUtil.pressEnterKey();
			Log.info("����Enter��");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("����Enter���쳣�������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//�������
	public static void click_SendMailButton(String string1,String string) {
		try {
			List<WebElement> buttons = dr.findElements(By.xpath("//*[@id=\"toolbar\"]/div/a[1]"));
			buttons.get(0).click();
			Log.info("�������Ͱ�ť");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("�������Ͱ�ť�쳣�������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Assert_String
	public static void Assert_String(String string1,String assertString) {
		try {
			Assert.assertTrue(dr.getPageSource().contains(assertString));
			Log.info("���Գɹ�");
		} catch (AssertionError e) {
			TestSuiteByExcel.testResult = false;
			Log.info("�����쳣�������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//�ر������
	public static void close_browser(String string1,String string) {
		try {
			dr.quit();
			Log.info("�ر������");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("�ر�������쳣�������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//�쳣�ر������
	public static void error_browser(String string1,String string2) {
		try {
			dr.quit();
			Log.error("�ر������");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	
	//��ͼ
	public static void takesScreenhot(String string1,String TestCaseName) {
		try {
			Date date = new Date();
			String picDir = "E:\\" + TestCaseName + "\\" + String.valueOf(DateUtil.getYear(date)) 
						+ "-" + String.valueOf(DateUtil.getMonth(date))
						+ "-" + String.valueOf(DateUtil.getDay(date));	
			if(!new File(picDir).exists()) {
				FileUtil.createDir(picDir);
			}
			
			String filepath = picDir + "\\"+ String.valueOf(DateUtil.getHour(new Date()))
						+ "-" + String.valueOf(DateUtil.getMinute(new Date()))
						+ "-" + String.valueOf(DateUtil.getSecond(new Date())) + ".png";
			File srcFile = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(filepath));
			Log.info("��ȡͼƬ�ɹ�������·����" + filepath);
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("��ȡͼƬʧ�ܣ������쳣��Ϣ��" + e.getMessage());
			e.printStackTrace();
		}
	}
}
