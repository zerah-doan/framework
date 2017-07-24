package util;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Log {
	private static final Logger logger = LoggerFactory.getLogger(Log.class);
	private static ExtentReports extentReport;
	private static ExtentTest extentTest;

	public static void logDebug(String msg) {
		logger.debug(msg);
		extentTest.log(LogStatus.INFO, msg);
	}

	public static void logInfo(String msg) {
		logger.info(msg);
		extentTest.log(LogStatus.PASS, msg);
	}

	public static void logWarn(String msg) {
		logger.warn(msg);
		extentTest.log(LogStatus.WARNING, msg);
	}

	public static void logError(String msg) {
		logger.error(msg);
		extentTest.log(LogStatus.FAIL, msg);
	}

	public static void logTrace(String msg) {
		logger.trace(msg);
		extentTest.log(LogStatus.INFO, msg);
	}

	public static void startOfTC(String name) {
		logger.info("--------------------Start of " + name + "--------------------");
		extentTest = extentReport.startTest(name);
	}

	public static void endOfTC(ITestResult rs) {
		logTestResult(rs);
		extentReport.endTest(extentTest);
		logger.info("--------------------END of " + rs.getName() + "--------------------\n");

	}

	public static void logTestResult(ITestResult result) {
		// set start end
		extentTest.setStartedTime(getTime(result.getStartMillis()));
		extentTest.setEndedTime(getTime(result.getEndMillis()));
		// assign group
		for (String group : result.getMethod().getGroups()) {
			extentTest.assignCategory(group);
		}
		LogStatus stt = convertStatus(result.getStatus());
		String message = "Test " + stt.toString().toLowerCase() + "ed";

		if (result.getThrowable() != null) {
			message = result.getThrowable().getMessage();
		}
		extentTest.log(stt, message);
	}

	public static void setExtentReport(ExtentReports rp) {
		extentReport = rp;
	}

	private static Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	private static LogStatus convertStatus(int stt) {
		switch (stt) {
		case ITestResult.SUCCESS:
			return LogStatus.PASS;
		case ITestResult.FAILURE:
			return LogStatus.FAIL;
		case ITestResult.SKIP:
			return LogStatus.SKIP;
		default:
			return LogStatus.UNKNOWN;
		}
	}
}
