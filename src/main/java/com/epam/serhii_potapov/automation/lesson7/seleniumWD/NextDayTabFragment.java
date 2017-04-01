package com.epam.serhii_potapov.automation.lesson7.seleniumWD;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NextDayTabFragment extends BasePage {
    private By minTemperatureLocator = By.cssSelector("#bd2 .min span");
    private By maxTemperatureLocator = By.cssSelector("#bd2 .max span");
    private By arrayOfTemperaturesInWeatherDetailsLocator = By.cssSelector("#bd2c .temperature td");

    public NextDayTabFragment(WebDriver driver) {
        super(driver);
    }

    private int getDegrees(String temperature) {
        return Integer.parseInt(temperature.trim().substring(1, temperature.length() - 1));
    }

    public int getMinTemperature() {
        return getDegrees(driver.findElement(minTemperatureLocator).getText());
    }

    public int getMaxTemperature() {
        return getDegrees(driver.findElement(maxTemperatureLocator).getText());
    }

    private List<WebElement> getDetailedListOfTemperatures() {
        return driver.findElements(arrayOfTemperaturesInWeatherDetailsLocator);
    }

    private ArrayList<Integer> getAllDegreesForDay() {
        ArrayList<Integer> degrees = new ArrayList<>();
        for (WebElement element : getDetailedListOfTemperatures()) {
            degrees.add(getDegrees(element.getText()));
        }
        return degrees;
    }

    public int findMinTemperatureInDetailedListOfTemperatures() {
        return Collections.min(getAllDegreesForDay());
    }

    public int findMaxTemperatureInDetailedListOfTemperatures() {
        return Collections.max(getAllDegreesForDay());
    }
}
