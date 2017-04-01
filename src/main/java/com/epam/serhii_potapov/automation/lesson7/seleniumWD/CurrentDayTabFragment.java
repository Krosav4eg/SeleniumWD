package com.epam.serhii_potapov.automation.lesson7.seleniumWD;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CurrentDayTabFragment extends BasePage {

    private By nextDayTabLocator = By.id("bd2");

    public CurrentDayTabFragment(WebDriver driver) {
        super(driver);
    }

    public NextDayTabFragment clickOnNextDayTab() {
        wait.until(ExpectedConditions.presenceOfElementLocated(nextDayTabLocator)).click();
        return new NextDayTabFragment(driver);
    }
}
