package framework.device;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;
import util.Log;

public class Devices {
	private String device;
	private DesiredCapabilities cap;

	public Devices(String device) {
		this.device = device;
		this.cap = new DesiredCapabilities();
		switch (device) {
		case "S6":
			setS6();
			break;
		case "Nexus10":
		default:
			setNexus10();
			break;

		case "TabS2":
			setGalaxyTabS2();
			break;
		}
	}

	private void setS6() {
		setCommon();
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.0");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "S6");

	}

	private void setNexus10() {
		setCommon();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Google Nexus 10 - 5.0.0 - API 21 - 2560x1600");
		cap.setCapability(MobileCapabilityType.UDID, "192.168.11.101:5555");

	}

	private void setGalaxyTabS2() {
		setCommon();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "a");
		cap.setCapability(MobileCapabilityType.UDID, "3300c0c76b946207");
	}

	private void setCommon() {
		cap.setCapability("appPackage", "ch.soxes.testproair");
		cap.setCapability("appActivity", ".MainActivity");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60000);

		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.0");
	}

	public DesiredCapabilities getCap() {
		Log.logInfo("Openning device using DesiredCapabilities of " + device);
		return cap;
	}

}
