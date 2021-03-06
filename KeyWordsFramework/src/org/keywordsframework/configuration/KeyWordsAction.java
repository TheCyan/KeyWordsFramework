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
	
	//选择浏览器
	public static void open_browser(String string,String browser) {
		try {
			if(browser.equals("ie")) {
				dr = new InternetExplorerDriver();
				Log.info("IE浏览器启动成功");
			} else if(browser.equals("firefox")) {
				dr = new FirefoxDriver();
				Log.info("firefox浏览器启动成功");
			} else if(browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", 
						".\\tools\\chromedriver.exe");
				dr = new ChromeDriver();
				dr.manage().window().maximize();
				Log.info("chrome浏览器启动成功");
			} else {
				System.out.println("只支持“IE”、“firefox”、“chrome”浏览器");
			}
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	
	//url路径
	public static void navigate(String string,String url) {
		try {
			dr.get(url);
			Log.info("地址：" + url + "打开成功");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("地址：" + url + "打开失败，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//单击
	public static void click(String locatorExpression, String string) {
		try {
			dr.findElement(objectMap.getLocator(locatorExpression)).click();
			Log.info("单击" + locatorExpression + "页面元素");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("单击" + locatorExpression + "页面元素失败，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//输入
	public static void input(String locatorExpression, String string) {
		try {
			dr.findElement(objectMap.getLocator(locatorExpression)).clear();
			dr.findElement(objectMap.getLocator(locatorExpression)).sendKeys(string);
			Log.info("在" + locatorExpression + "元素中，输入：" + string);
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("在" + locatorExpression + "元素中，输入：" + string + "失败，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//转入Iframe
	public static void switch_to_Iframe(String string,String id) {
		try {
			dr.switchTo().frame(id);
			Log.info("转入ID：" + id + "Iframe页面成功");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("转入ID：" + id + "Iframe页面失败，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}	
	}
	
	//等待时间
	public static void sleep(String string,String sleepTime) {
		try {
			WaitUitl.sleep(Integer.parseInt(sleepTime));
			Log.info("休眠：" + Integer.parseInt(sleepTime)/1000 + "秒");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("休眠异常，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//转出Iframe
	public static void turn_out_Iframe(String string1,String string) {
		try {
			dr.switchTo().defaultContent();
			Log.info("转出Iframe");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("转出Iframe异常，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}	
	}

	//键盘Ctrl+V复制数据
	public static void press_CtrlV(String string1,String string) {
		try {
			KeyBoardUtil.setAndCtrlVClipboardData(string);
			Log.info("使用剪切板复制数据：" + string);
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("使用剪切板复制数据异常，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}	
	}
	
	//键盘Tab
	public static void press_Tab(String string1,String string2) {
		try {
			KeyBoardUtil.pressTabKey();
			Log.info("按下Tab键");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("按下Tab键异常，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}	
	}
	
	//按下Enter键
	public static void press_Enter(String string1,String string2) {
		try {
			KeyBoardUtil.pressEnterKey();
			Log.info("按下Enter键");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("按下Enter键异常，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//点击发送
	public static void click_SendMailButton(String string1,String string) {
		try {
			List<WebElement> buttons = dr.findElements(By.xpath("//*[@id=\"toolbar\"]/div/a[1]"));
			buttons.get(0).click();
			Log.info("单击发送按钮");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("单击发送按钮异常，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Assert_String
	public static void Assert_String(String string1,String assertString) {
		try {
			Assert.assertTrue(dr.getPageSource().contains(assertString));
			Log.info("断言成功");
		} catch (AssertionError e) {
			TestSuiteByExcel.testResult = false;
			Log.info("断言异常，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//关闭浏览器
	public static void close_browser(String string1,String string) {
		try {
			dr.quit();
			Log.info("关闭浏览器");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("关闭浏览器异常，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//异常关闭浏览器
	public static void error_browser(String string1,String string2) {
		try {
			dr.quit();
			Log.error("关闭浏览器");
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	
	//截图
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
			Log.info("截取图片成功，所在路径：" + filepath);
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			Log.info("截取图片失败，具体异常信息：" + e.getMessage());
			e.printStackTrace();
		}
	}
}
