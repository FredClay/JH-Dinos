package com.qa.may.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    @FindBy(id = "dinoForm")
    private WebElement dinoForm;

    @FindBy(css = "#dinoName")
    private WebElement nameInput;

    @FindBy(css = "#dinoAge")
    private WebElement ageInput;

    @FindBy(css = "#dinoSpecies")
    private WebElement speciesInput;

    @FindBy(css = "#dinoForm > button.btn.btn-primary")
    private WebElement submitButton;

    @FindBy(css = "#dinoForm > button.btn.btn-danger")
    private WebElement resetButton;

    @FindBy(id = "output")
    private WebElement outputArea;

    public void createDino(String name, Integer age, String species) {
        nameInput.sendKeys(name);
        ageInput.sendKeys(age.toString());
        speciesInput.sendKeys(species);
        submitButton.click();
    }

    public String getSecondDino(WebDriverWait myWait) {
        WebElement secondDino = myWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#output > div:nth-child(2)")));
        String dinoName = secondDino.findElement(By.cssSelector("div > div > h2")).getText();
        return dinoName;
    }

    public void fillInputs(String name, Integer age, String species) {
        nameInput.sendKeys(name);
        ageInput.sendKeys(age.toString());
        speciesInput.sendKeys(species);
    }

    public void resetInputFields() {
        resetButton.click();
    }

    public boolean checkInputsAreBlank() {
        List<WebElement> results = this.dinoForm.findElements(By.xpath("input"));
        int countOfElements = results.size();
        int countOfBlanks = 0;
        for (WebElement elem : results) {
            if (elem.getAttribute("value").isBlank()) {
                countOfBlanks++;
            }
        }
        return countOfBlanks == countOfElements;
    }

}
