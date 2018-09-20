package com.azdio.adminui.vod;

import java.util.Arrays;

import com.azdio.adminui.BrowserExtensions;
import com.azdio.adminui.CommonMethods;
import com.azdio.adminui.Config;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Repeat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VODAssetTests extends BrowserExtensions {

    CommonMethods commonHelperMethods = new CommonMethods();
    Config config = new Config();

    private String url = config.getConfigProperty("int.server.url");
    private String username = config.getConfigProperty("mdw.user");
    private String password = config.getConfigProperty("mdw.password");
    private String assetName = config.getConfigProperty("assetName");

    @Before
    public void initialize() {
        driver.get(url);
        commonHelperMethods.login(username, password);
    }

    @Test
    @Repeat(3)
    public void v01crateVODAsset() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("VOD");
        commonHelperMethods.PopUpMenuSelecting("VOD Assets");
        commonHelperMethods.ToolbarButtonClick("Create VOD Assets");

        commonHelperMethods.fillRowInfo("Housenumber", "TestHousenumber");
        commonHelperMethods.fillRowInfo("Name", assetName);
        commonHelperMethods.fillRowInfo("Description", "TestAssetDescription");
        commonHelperMethods.openDropdown("Content Type");
        commonHelperMethods.selectAnELementFromDropdown("CLIP");

        // commonHelperMethods.enterDate("Publish On","02.12.14","00:22:33");

        commonHelperMethods.openDropdown("TV Series");
        commonHelperMethods.searchForItem("Yordan");
        commonHelperMethods.selectAnELementFromDropdown("Yordan Test Series");

        commonHelperMethods.openDropdown("TV Season");
        commonHelperMethods.selectAnELementFromDropdown("Yordan Test Season 1");

        commonHelperMethods.fillRowInfo("Video Title", "Test Video Title");
        commonHelperMethods.fillRowInfo("Original Title", "Test Original Title Name");
        commonHelperMethods.fillRowInfo("Title Brief", "Test Brief Title");
        commonHelperMethods.fillRowInfo("Title Brief", "Test Brief Title");
        commonHelperMethods.fillRowInfo("Summary Short", "Test short summray");
        commonHelperMethods.fillRowInfo("Summary Long", "Test long summray test long summray");

        // commonHelperMethods.fillRowInfo("Genres", "Test genre 1, Test genre 2, Test genre 3, Test genre 4");
        commonHelperMethods.fillRowInfo("Actors", "Test actor 1, Test actor 2, Test actor 3, Test actor 4");
        commonHelperMethods.fillRowInfo("Director", "Test Director");
        commonHelperMethods.fillRowInfo("Country of Origin", "Test Country");

        commonHelperMethods.fillRowInfo("Content URL", url);
        commonHelperMethods.fillRowInfo("Subtitle URL", url);

        commonHelperMethods.openDropdown("Tenants");
        commonHelperMethods.selectAnELementFromDropdown("AMB-LV");

        // FEW Integer fields are missing here

        commonHelperMethods.openDropdown("VOD Type");
        commonHelperMethods.selectAnELementFromDropdown("SAVOD");

        commonHelperMethods.openDropdown("VOD Packages");
        commonHelperMethods.selectAnELementFromDropdown(Arrays.asList("AMB", "LT_VOD"));

        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");

        // Verify saved item by given property
        waitForSpinner();
        commonHelperMethods.verifyCreatedItem("id");

        driver.close();
    }


    @Test
    public void v02editVODAsset() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("VOD");
        commonHelperMethods.PopUpMenuSelecting("VOD Assets");
        // Prepare for edit
        commonHelperMethods.selectPropertyForSearching("Name");
        commonHelperMethods.toolbarSearch(assetName);
        commonHelperMethods.ToolbarButtonClick("Search");

        commonHelperMethods.ToolbarButtonClick("Edit VOD Asset");

        commonHelperMethods.fillRowInfo("Name", "Edit " + assetName);
        commonHelperMethods.openDropdown("Content Type");
        commonHelperMethods.selectAnELementFromDropdown("EPISODE");
        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");
        commonHelperMethods.verifyEditedItem();

        driver.close();
    }


    @Test
    public void v03deleteVODAsset() {

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
