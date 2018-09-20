package com.azdio.adminui.vod;

import com.azdio.adminui.BrowserExtensions;
import com.azdio.adminui.CommonMethods;
import com.azdio.adminui.Config;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;


import java.util.Arrays;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VODSeriesTests extends BrowserExtensions {
    CommonMethods commonHelperMethods = new CommonMethods();
    Config config = new Config();

    private String url = config.getConfigProperty("int.server.url");
    private String username = config.getConfigProperty("mdw.user");
    private String password = config.getConfigProperty("mdw.password");
    private String assetName = config.getConfigProperty("assetName");
    private String tvSeriesName = config.getConfigProperty("tvSeriesName");

    @Before
    public void initialize() {
        driver.get(url);
        commonHelperMethods.login(username, password);
    }

   // @Test
    public void vs01crateTVSeries() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("VOD");
        commonHelperMethods.PopUpMenuSelecting("TV Series");
        commonHelperMethods.ToolbarButtonClick("Create TV Series");

        commonHelperMethods.fillRowInfo("Name", tvSeriesName);
        commonHelperMethods.fillRowInfo("Description", "TestTVSeriesDescription");
        commonHelperMethods.fillRowInfo("Title", "TestTitle");
        commonHelperMethods.fillRowInfo("Title", "Test TitleTitle");
        commonHelperMethods.fillRowInfo("Slug", "Test slug");


        commonHelperMethods.fillRowInfo("Summary Short", "Test Series short summray");
        commonHelperMethods.fillRowInfo("Summary Long", "Test Series long summray test long summray");

        // commonHelperMethods.fillRowInfo("Genres", "Test genre 1, Test genre 2, Test genre 3, Test genre 4");
        commonHelperMethods.fillRowInfo("Actors", "Test series actor 1, Test series actor 2, Test series actor 3, Test series actor 4");
        commonHelperMethods.fillRowInfo("Director", "Test Series Director");
        commonHelperMethods.fillRowInfo("Country of Origin", "Test Country");

        commonHelperMethods.openDropdown("VOD Packages");
        commonHelperMethods.selectAnELementFromDropdown(Arrays.asList("AMB", "LT_VOD"));


        commonHelperMethods.openDropdown("Tenants");
        commonHelperMethods.selectAnELementFromDropdown("AMB-LV");

        // FEW Integer fields are missing here

        commonHelperMethods.openDropdown("Devices");
        commonHelperMethods.selectAnELementFromDropdown(Arrays.asList("WEB", "STB"));



        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");

        // Verify saved item by given property
        commonHelperMethods.verifyCreatedItem("id");

        driver.close();
    }

   // @Test
    public void vs02crateTVSeason() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("VOD");
        commonHelperMethods.PopUpMenuSelecting("TV Series");
        waitForSpinner();
        commonHelperMethods.selectPropertyForSearching("Name");
        commonHelperMethods.toolbarSearch(tvSeriesName);
        commonHelperMethods.ToolbarButtonClick("Search");
        commonHelperMethods.ToolbarButtonClick("Add Season");

        commonHelperMethods.fillRowInfo("Name", "Seasons"+tvSeriesName);
        commonHelperMethods.fillRowInfo("Description", "Seasons"+"TestTVSeriesDescription");
        commonHelperMethods.fillRowInfo("Title", "Seasons"+"TestTitle");
        commonHelperMethods.fillRowInfo("Title", "Seasons"+"Test TitleTitle");
        commonHelperMethods.fillRowInfo("Slug", "Seasons"+"Test slug");

        commonHelperMethods.fillRowInfo("Summary Short", "Seasons"+"Test Series short summray");
        commonHelperMethods.fillRowInfo("Summary Long", "Seasons"+"Test Series long summray test long summray");


        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");

        // Verify saved item by given property
        commonHelperMethods.verifyCreatedItem("id");

        driver.close();
    }

   // @Test
    public void vs03editTVSeries() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("VOD");
        commonHelperMethods.PopUpMenuSelecting("TV Series");
        // Prepare for edit
        commonHelperMethods.selectPropertyForSearching("Name");
        commonHelperMethods.toolbarSearch(tvSeriesName);
        commonHelperMethods.ToolbarButtonClick("Search");

        commonHelperMethods.ToolbarButtonClick("Edit");

        commonHelperMethods.fillRowInfo("Name", "Edit " + tvSeriesName);
        commonHelperMethods.openDropdown("Tenants");
        commonHelperMethods.selectAnELementFromDropdown("AMB-LT");
        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");
        commonHelperMethods.verifyEditedItem();

        driver.close();
    }
    //@Test
    public void vs04editTVSeason() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("VOD");
        commonHelperMethods.PopUpMenuSelecting("TV Series");
        // Prepare for edit
        waitForSpinner();
        commonHelperMethods.selectPropertyForSearching("Name");
        waitForSpinner();
        commonHelperMethods.toolbarSearch(tvSeriesName);
        commonHelperMethods.ToolbarButtonClick("Search");
      //  commonHelperMethods.selectRowForEditDelete(tvSeriesName);
        commonHelperMethods.expandSeason();
        commonHelperMethods.selectRowForEditDelete("Seasons"+tvSeriesName);

        commonHelperMethods.fillRowInfo("Name", "Edit Season " + tvSeriesName);
        commonHelperMethods.openDropdown("Devices");
        commonHelperMethods.selectAnELementFromDropdown("EMBEDDED");
        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");
        commonHelperMethods.verifyEditedItem();

        driver.close();
    }

   // @Test
    public void vs05deleteTVSeason() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("VOD");
        commonHelperMethods.PopUpMenuSelecting("TV Series");
        // Prepare for edit
        commonHelperMethods.selectPropertyForSearching("Name");
        commonHelperMethods.toolbarSearch(tvSeriesName);
        commonHelperMethods.ToolbarButtonClick("Search");
        // Pre-delete
        Integer before = commonHelperMethods.numberOfGridRows();
        String idBefore = commonHelperMethods.getGridPropety("Id",1);
        commonHelperMethods.ToolbarButtonClick("Delete");
        commonHelperMethods.confirmDelete("Yes");
        waitForSpinner();
        // Pos-delete
        Integer after = commonHelperMethods.numberOfGridRows();
        String idAfter = commonHelperMethods.getGridPropety("Id",1);
        commonHelperMethods.verifyIsDelete(before, after, idBefore, idAfter);

        driver.close();
    }
   // @Test
    public void vs06deleteTVSeries() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("VOD");
        commonHelperMethods.PopUpMenuSelecting("VOD Assets");
        // Prepare for edit
        commonHelperMethods.selectPropertyForSearching("Name");
        commonHelperMethods.toolbarSearch(assetName);
        commonHelperMethods.ToolbarButtonClick("Search");
        // Pre-delete
        Integer before = commonHelperMethods.numberOfGridRows();
        String idBefore = commonHelperMethods.getGridPropety("Id",1);
        commonHelperMethods.ToolbarButtonClick("Delete VOD Assets");
        commonHelperMethods.confirmDelete("Yes");
        waitForSpinner();
        // Pos-delete
        Integer after = commonHelperMethods.numberOfGridRows();
        String idAfter = commonHelperMethods.getGridPropety("Id",1);
        commonHelperMethods.verifyIsDelete(before, after, idBefore, idAfter);

        driver.close();
    }
}
