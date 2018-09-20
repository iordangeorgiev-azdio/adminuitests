package com.azdio.adminui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static java.lang.Thread.sleep;

public class CommonMethods extends BrowserExtensions {

    CommonWebElements elements = new CommonWebElements();

    // Navigation methods
    public void login(String username, String password) {
        driver.findElement(By.xpath(".//*[@name='username']")).sendKeys(username);
        driver.findElement(By.xpath(".//*[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath(".//button")).click();
    }

    public void leftPanelNavigation(String panelName) {
        driver.findElement(By.xpath("//*[contains(text(),'" + panelName + "')]/../..")).click();
    }

    public void PopUpMenuSelecting(String panelName) {
        WebElement element = driver.findElement(By.xpath(".//button[contains(text(),'" + panelName + "')]"));
        waitAndClick(element);
    }

    public void ToolbarButtonClick(String buttonName) {
        WebElement buttoAt = driver.findElement(By.xpath(" //div[@id='cdk-describedby-message-container']/*[normalize-space(text()) = '" + buttonName + "']"));
        String buttonAttribute = buttoAt.getAttribute("id").toString();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement button = driver.findElement(By.xpath("//*[@aria-describedby='" + buttonAttribute + "']"));
        waitAndClick(button);
        waitForPageLoaded();
    }

    // Edit methods
    public void selectPropertyForSearching(String propertyName) {
        WebElement openSearchDropdownButton = driver.findElement(By.xpath("(//*[contains(@class, 'mat-expansion-indicator ng')])[1]"));
        waitAndClick(openSearchDropdownButton);
        try {
            sleep(200);
        } catch (InterruptedException e) {
        }
        ToolbarButtonClick(propertyName);
    }

    public void toolbarSearch(String valueForSearch) {
        WebElement searchField = driver.findElement(By.xpath("//input[@placeholder]"));
        waitToBeClickable(searchField);
        try {
            sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        searchField.sendKeys(valueForSearch);
    }

    public void fillRowInfo(String elementName, String data) {
        WebElement element = elements.rowForInput(getElementIndex(elementName));
        waitAndClick(element);

        // Check if the field is readonly or not by the class attribute
        if (!element.getAttribute("class").contains("htDimmed")) {
            element = driver.findElement(By.xpath("(//textarea)[1]"));
            element.sendKeys(data + Keys.ENTER);
        }
    }

    public void searchForItem(String searchValue) {
        try {
            sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        WebElement element = driver.findElement(elements.searchInput());

        waitToBeClickable(element);
        element.sendKeys(searchValue);
        // waitForElements(elements.dropdownElements(),5);
        try {
            sleep(600);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // For single selection
    public void selectAnELementFromDropdown(String values) {

        try {
            sleep(200);
        } catch (Exception e) {
        }
        List<WebElement> list = driver.findElements(elements.dropdownElements());
        for (int i = 0; i < list.size(); i++) {
            String eleText = list.get(i).getText();
            if (eleText.equals(values)) {
                waitAndClick(list.get(i));
                break;
            }
        }
    }

    // For multi selection
    public void selectAnELementFromDropdown(List<String> values) {

        List<WebElement> list = driver.findElements(elements.dropdownElements());

        for (int t = 0; t < values.size(); t++) {
            for (int i = 0; i < list.size(); i++) {
                String eleText = list.get(i).getText();
                if (eleText.equals(values.get(t))) {
                    waitAndClick(list.get(i));
                    list = driver.findElements(elements.dropdownElements());
                    break;
                }
            }
        }
    }

    public void selectRowForEditDelete(String seasonName){
        getGridCellIndex("Name");
    }

    private void waitAndClick(WebElement element) {
        waitToBeClickable(element);
        element.click();
    }

    public void openDropdown(String rowName) {
        WebElement parentRow = elements.rowForInput(getElementIndex(rowName));
        waitAndClick(parentRow);

        WebElement arrow = parentRow.findElement(elements.dropdownArrow());
        waitToBeClickable(arrow);
        parentRow.findElement(elements.dropdownArrow()).click();
    }

    public void expandSeason(){
        WebElement cell = elements.tvSeriesExpandCell();

        Actions action = new Actions(driver);

        action.moveToElement(cell).click().build().perform();
        WebElement dropdown = elements.tvSeriesExpand();
        waitAndClick(dropdown);
    }

    public void enterDate(String elementName, String date, String time) {
        openCalendar(elementName);
        enterTime(time);
    }

    private void openCalendar(String fieldName) {
        // Open calendar
        WebElement element = elements.rowForInput(getElementIndex(fieldName));
        waitToBeClickable(element);
        element.click();
        WebElement button = element.findElement(By.xpath("//app-ultimate"));
        waitToBeClickable(element);
        button.click();
    }
    //TO BE REWORKED START
    private void enterTime(String time) {
        char[] numbers = time.replaceAll("[^0-9]", "").toCharArray();
        List<WebElement> clockElements = elements.clockNumbers();
        JavascriptExecutor jse2 = (JavascriptExecutor) driver;
        for(int i=0; i< numbers.length;i++) {


            Integer faff = asciNumber(numbers[i]);
            keyDown(faff);
            String ch = String.valueOf(numbers[i]);
        }
    }

    private Integer asciNumber(char ch) {
        return  (int) ch;
    }
    private void keyDown(Integer key) {
        JavascriptExecutor jse2 = (JavascriptExecutor) driver;

        jse2.executeScript("arguments[0].setAttribute(\"class\", \"ng-star-inserted flip-clock-before\")");
        jse2.executeScript("var e = jQuery.Event(\"keydown\"); e.which = "+key+"; e.keyCode = "+key+"; $(“.flip blink”).trigger(e);");
    }

//TO BE REWORKED END

    // Verify methods
    public void verifyCreatedItem(String property) {
        String value = elements.rowForInput(getElementIndex(property)).getText();

        Assert.assertTrue(!value.equals(""));
    }

    public void verifyEditedItem() {
        String updateOnDate = elements.rowForInput(getElementIndex("Updated On")).getText();
        String updatedBy = elements.rowForInput(getElementIndex("Updated By")).getText();
        updateOnDate = updateOnDate.substring(0, updateOnDate.length() - 3);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        Assert.assertTrue(updateOnDate.equals(timeStamp));

        Config config = new Config();
        String username = config.getConfigProperty("mdw.user");
        Assert.assertTrue(updatedBy.equals(username));
    }

    public void confirmDelete(String yesOrNo) {
        WebElement yesNoButton = driver.findElement(By.xpath(".//*[@class='mat-button-wrapper' and contains(text(),'" + yesOrNo + "')]/.."));
        waitToBeClickable(yesNoButton);
        yesNoButton.click();
    }

    public void verifyIsDelete(Integer before, Integer after, String propertyBefore, String propertyAfter) {
        Assert.assertTrue("Items befeore were "+before+" and now they are "+after+"",before > after);
        Assert.assertTrue("The property was "+propertyBefore+" and now the proerprty is "+propertyAfter+"",!propertyBefore.equals(propertyAfter));
    }

    // Helper string and integer methods

    public String getGridPropety(String property, Integer rowNumber) {
        String propertyText = "";
        try {
            propertyText = elements.gridRow(getGridCellIndex(property),rowNumber).getText();
        } catch (Exception e) {
            return "no elements";
        }
        return propertyText;
    }

    public Integer numberOfGridRows() {

        List<WebElement> elements = null;

        try {
            Thread.sleep(2000);
            elements = driver.findElements(By.xpath("//div[@class='ht_master handsontable']//tbody/tr"));
        } catch (Exception e) {
            return 0;
        }

        return elements.size();
    }

    private Integer getElementIndex(String elementName) {

        Integer elementIndex = 0;
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='ht_master handsontable']//thead//span"));
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().equals(elementName)) {
                elementIndex = i;
                break;
            }
        }
        return elementIndex;
    }


    private Integer getGridCellIndex(String elementName) {

        Integer elementIndex = 0;
        List<WebElement> elements = null;
        try {
            elements = driver.findElements(By.xpath("//div[@class='ht_clone_top handsontable']//span[@class='colHeader']"));
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getText().equals(elementName)) {
                    elementIndex = i;
                    break;
                }
            }
        } catch (Exception e) {
            return 0;
        }

        return elementIndex;
    }


}