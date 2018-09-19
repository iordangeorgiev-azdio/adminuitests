package com.azdio.adminui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class CommonWebElements extends BrowserExtensions{

    public By dropdownArrow () {
        return By.className("htAutocompleteArrow");
    }

    public By dropdownElements () {
        return By.xpath(".//div[@class='ss-list']/div");
    }

    public WebElement gridRow(Integer elementIndex, Integer rowNumber) {
        List<WebElement> elements;
        WebElement element = null;
        try {
                elements =driver.findElements(By.xpath("(//div[@class='ht_master handsontable']//tbody/tr)["+rowNumber+"]/td"));
                element = elements.get(elementIndex);
        } catch (Exception e) {

        }
        return element;
    }
    public List<WebElement> gridRows(){
        return driver.findElements(By.xpath("//div[@class='ht_master handsontable']//tbody/tr"));
    }


    public WebElement rowForInput(Integer elementIndex) {
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='ht_master handsontable']//tbody//td"));
        WebElement element = elements.get(elementIndex);
        return element;
    }

    public By searchInput() {
        return By.xpath("//input[@type='search']");
    }

    public WebElement day(String day) {
        return driver.findElement(By.xpath("//td[contains(@class,'calendar')]/div[normalize-space(text()) = '"+day+"']")) ;
    }

    public List<WebElement> clockNumbers() {
        return driver.findElements(By.xpath("//*[@class='flip-counter clock flip-clock-wrapper']/ul"));
    }
    public List<WebElement> clockNumbers2() {
        return driver.findElements(By.cssSelector("div:contains(flip-counter clock flip-clock-wrapper)"));
    }

    public WebElement spinner(){
       return driver.findElement(By.className("loading-spinner"));
    }

    public WebElement tvSeriesExpand(){
        return driver.findElement(By.xpath("(//*[contains(@class,'ht_nestingLevels')]//*[contains(@class,'ht_nestingButton')])[1]"));
    }

    public WebElement tvSeriesExpandCell() { return driver.findElement(By.xpath("//div[@class='ht_clone_left handsontable']//*[contains(@class,'ht_nestingLevels')]"));

    }



}
