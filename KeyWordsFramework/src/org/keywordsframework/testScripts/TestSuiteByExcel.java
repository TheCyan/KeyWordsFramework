package org.keywordsframework.testScripts;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.xml.DOMConfigurator;
import org.keywordsframework.configuration.Constans;
import org.keywordsframework.configuration.KeyWordsAction;
import org.keywordsframework.util.ExcelUtil;
import org.keywordsframework.util.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestSuiteByExcel {
	public static Method method[];
	public static String keyword;
	public static String value;
	public static String locatorExpression;
	public static KeyWordsAction keyWordsAction;
	public static int testStep;
	public static int testLastStep;
	public static String testCaseID;
	public static String testCaseRunFlag;
	public static boolean testResult;
	
	
	//����������ִ��
	@Test
	public void TestSuite() throws Exception {
		
		//ͨ��������ƻ�ȡkeyWordsAction���е����з���
		keyWordsAction = new KeyWordsAction();
		method = keyWordsAction.getClass().getMethods();
		//��ȡExcel·��������ʵ��
		String excelPath = Constans.Path_ExcelFile;
		ExcelUtil.setExcelFile(excelPath);
		
		//��ȡ���������׼��еĲ�����������
		int testCasesCount = ExcelUtil.getRowCount(Constans.Sheet_TestSuite);
		//�����õ���������ID����������Ƿ�ִ���ж�ֵ
		for(int testCaseNo=1;testCaseNo<=testCasesCount;testCaseNo++) {
			testCaseID = ExcelUtil.getCellData(Constans.Sheet_TestSuite, testCaseNo, Constans.Col_TestCaseID);
			testCaseRunFlag = ExcelUtil.getCellData(Constans.Sheet_TestSuite, testCaseNo, Constans.Col_RunFlag);
			
			if(testCaseRunFlag.equalsIgnoreCase("y")) {
				Log.startTestCase(testCaseID);
				testResult = true;
				testStep = ExcelUtil.getFirstRowContainsTestCaseID(Constans.Sheet_TestSteps, testCaseID, Constans.Col_TestCaseID);
				testLastStep = ExcelUtil.getTestCaseLastStepRow(Constans.Sheet_TestSteps, testCaseID, testStep);
				
				//��ȡ���������Ĺؼ��֣���λ���ʽ������ֵ
				for (;testStep<testLastStep;testStep++) {
					keyword = ExcelUtil.getCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_KeyWordAction);
					Log.info("��Excel�ļ��ж�ȡ���Ĺؼ��֣�" + keyword);
					locatorExpression = ExcelUtil.getCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_LocatorExpression);
					Log.info("��Excel�ļ��ж�ȡ���Ķ�λ���ʽ��" + locatorExpression);
					value = ExcelUtil.getCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_ActionValue);
					Log.info("��Excel�ļ��ж�ȡ���Ĳ���ֵ��" + value);
					execute_Actions();
					//����������ִ��ʧ�ܺ�д��ʧ����Ϣ��ִ���¸�����
					if (testResult == false) {
						ExcelUtil.setCellData(Constans.Sheet_TestSuite, testCaseNo, Constans.Col_TestRuiteTestRuselt, "��������ִ��ʧ��");
						Log.endTestCase(testCaseID);
						break;
					}
					//����������ִ�гɹ���д��ɹ���Ϣ
					if (testResult == true) {
						ExcelUtil.setCellData(Constans.Sheet_TestSuite, testCaseNo, Constans.Col_TestRuiteTestRuselt, "��������ִ�гɹ�");
						
					}
					
				}
				Log.endTestCase(testCaseID);
			}
		}
	}
	
	
	//��������������ִ��
	@Test(dataProvider = "testData")
	public void ParameterizationTestSuite(String ParameterizationData) throws Exception {
		keyWordsAction = new KeyWordsAction();
		method = keyWordsAction.getClass().getMethods();
		String excelPath = Constans.Path_ExcelFile;
		ExcelUtil.setExcelFile(excelPath);
		
		int testCasesCount = ExcelUtil.getRowCount(Constans.Sheet_TestSuite);
		for(int testCaseNo=1;testCaseNo<=testCasesCount;testCaseNo++) {
			testCaseID = ExcelUtil.getCellData(Constans.Sheet_TestSuite, testCaseNo, Constans.Col_TestCaseID);
			testCaseRunFlag = ExcelUtil.getCellData(Constans.Sheet_TestSuite, testCaseNo, Constans.Col_RunFlag);
			
			if(testCaseRunFlag.equalsIgnoreCase("y")) {
				Log.startTestCase(testCaseID);
				testResult = true;
				testStep = ExcelUtil.getFirstRowContainsTestCaseID(Constans.Sheet_TestSteps, testCaseID, Constans.Col_TestCaseID);
				testLastStep = ExcelUtil.getTestCaseLastStepRow(Constans.Sheet_TestSteps, testCaseID, testStep);

				for (;testStep<testLastStep;testStep++) {
					keyword = ExcelUtil.getCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_KeyWordAction);
					Log.info("��Excel�ļ��ж�ȡ���Ĺؼ��֣�" + keyword);
					locatorExpression = ExcelUtil.getCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_LocatorExpression);
					Log.info("��Excel�ļ��ж�ȡ���Ķ�λ���ʽ��" + locatorExpression);
					//��������������
					if (ExcelUtil.getCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_ActionValue).equals("Parameterization")) {
						value = ParameterizationData;
					} else {
						value = ExcelUtil.getCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_ActionValue);
					}
					
					Log.info("��Excel�ļ��ж�ȡ���Ĳ���ֵ��" + value);
					execute_Actions();
					if (testResult == false) {
						ExcelUtil.setCellData(Constans.Sheet_TestSuite, testCaseNo, Constans.Col_TestRuiteTestRuselt, "��������ִ��ʧ��");
						Log.endTestCase(testCaseID);
						break;
					}
					if (testResult == true) {
						ExcelUtil.setCellData(Constans.Sheet_TestSuite, testCaseNo, Constans.Col_TestRuiteTestRuselt, "��������ִ�гɹ�");
						
					}
					
				}
				Log.endTestCase(testCaseID);
			}
		}
	}
	
	//ͨ��keyWordsAction�Ĺؼ���ƥ�����Ӧ�ķ������������ִ�з���
	private static void execute_Actions() {
		try {
			for (int i=0;i<method.length;i++) {
				if (method[i].getName().equals(keyword)) {
					method[i].invoke(keyWordsAction, locatorExpression, value);
					if (testResult == true) {
						ExcelUtil.setCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_TestStepTestRuselt, "������������ִ�гɹ�");
						break;
					} else {
						ExcelUtil.setCellData(Constans.Sheet_TestSteps, testStep, Constans.Col_TestStepTestRuselt, "������������ִ��ʧ��");
						KeyWordsAction.error_browser("","");
						break;
					}
					
				}
			}
		} catch (Exception e) {
			Assert.fail("��������ִ��ʧ��");
		}
	}
	
	@BeforeClass
	public void beforeClass() {
		//��־��Ϣ��ӡ
		DOMConfigurator.configure("log4j.xml");
	}
	
	@DataProvider(name = "testData")
	public static Object[][] data() throws IOException {
		//�������ļ�����
		return ExcelUtil.getTestData("E:\\ͳһ֧���ֱ�����\\svn\\trunk\\doc\\03_�����ĵ�\\07_�ڲ�����\\KeyWordsFramework\\src\\org\\keywordsframework\\data\\����������.xlsx", "Sheet1");
	}
}
