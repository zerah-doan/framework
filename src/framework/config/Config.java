package framework.config;

public class Config {
	// Project location
	public static String PROJECT_PATH = System.getProperty("user.dir");

	// Run config
	public static final String APP_URL = "http://proair.soxes-projects.ch/app/";
	public static final String BROWSER = "gc";
	public static final String DEVICE = "Nexus10";

	// Selenium config
	public static final int TIME_OUT = 20;
	public static final int SLEEP_BETWEEN_POLL = 500;
	public static final int IMPLICIT_WAIT = 1;
	public static final int PAGELOAD_TIMEOUT = 600;
	public static final String CHROME_DRIVER_PATH = PROJECT_PATH + "/src/framework/driver/chromedriver.exe";

}
