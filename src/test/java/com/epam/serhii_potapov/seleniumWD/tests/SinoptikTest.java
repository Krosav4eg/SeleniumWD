package com.epam.serhii_potapov.seleniumWD.tests;

import com.epam.serhii_potapov.automation.lesson7.seleniumWD.CurrentDayTabFragment;
import com.epam.serhii_potapov.automation.lesson7.seleniumWD.NextDayTabFragment;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

@Test
public class SinoptikTest {

    private static final String DRIVER_NAME = "webdriver.chrome.driver";
    private static final String PATH_TO_DRIVER = "src/main/resources/drivers/chromedriver.exe";
    private static final String DRIVER_NAME_OPERA = "webdriver.opera.driver";
    private static final String PATH_TO_DRIVER_OPERA = "src/main/resources/drivers/operadriver.exe";
    private final String URL_SITE = "https://ua.sinoptik.ua/погода-харків";
    private CurrentDayTabFragment currentDayTabFragment;
    private NextDayTabFragment nextDayTabFragment;
    private final String LOCALHOST_URL = "http://localhost:4444/wd/hub";
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ThreadLocal<Capabilities> capabilities = new ThreadLocal<Capabilities>() {
        @Override
        protected Capabilities initialValue() {
            return DesiredCapabilities.firefox();
        }
    };

    @BeforeClass
    @Parameters("browser")
    public void openSiteAndTabForTesting(String browser) {
        URL hubUrl = null;
        try {
            hubUrl = new URL(LOCALHOST_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        switch (browser) {
            case "chrome":
                System.setProperty(DRIVER_NAME, PATH_TO_DRIVER);
                capabilities.set(DesiredCapabilities.chrome());
                driver.set(new RemoteWebDriver(hubUrl, capabilities.get()));
                driver.get().manage().window().maximize();
                break;
            case "firefox":
                capabilities.set(DesiredCapabilities.firefox());
                driver.set(new RemoteWebDriver(hubUrl, capabilities.get()));
                driver.get().manage().window().maximize();
                break;
        }
        currentDayTabFragment = new CurrentDayTabFragment(driver.get());
        driver.get().get(URL_SITE);
        currentDayTabFragment.clickOnNextDayTab();
        nextDayTabFragment = new NextDayTabFragment(driver.get());

    }

    @Test
    public void verifyThatMinAndMaxTemperaturesForDayMatchesMinAndMaxInDetailedList() {
        int minTemperatureForDay = nextDayTabFragment.getMinTemperature();
        int maxTemperatureForDay = nextDayTabFragment.getMaxTemperature();
        int minTemperatureInDetailedList = nextDayTabFragment.findMinTemperatureInDetailedListOfTemperatures();
        int maxTemperatureInDetailedList = nextDayTabFragment.findMaxTemperatureInDetailedListOfTemperatures();
        assertEquals(minTemperatureForDay, minTemperatureInDetailedList, "Minimum temperatures do not match.");
        assertEquals(maxTemperatureForDay, maxTemperatureInDetailedList, "Maximum temperatures do not match.");
    }

    @AfterClass
    public void tearDown() {
        driver.get().close();
        driver.get().quit();
    }
}
