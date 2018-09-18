package com.azdio.adminui.product;


import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import com.azdio.adminui.BrowserExtensions;
import com.azdio.adminui.CommonMethods;
import com.azdio.adminui.Config;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChannelPackageTests extends BrowserExtensions {
    CommonMethods commonHelperMethods = new CommonMethods();
    Config config = new Config();

    private String url = config.getConfigProperty("uat.server.url");
    private String username = config.getConfigProperty("mdw.user");
    private String password = config.getConfigProperty("mdw.password");

    @Before
    public void initialize() {
        driver.get(url);
        commonHelperMethods.login(username, password);
    }

    @Test
    public void p01editChannelPackage() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("Products");
        commonHelperMethods.PopUpMenuSelecting("Channel Package");

        // Prepare for edit
        commonHelperMethods.selectPropertyForSearching("Id");
        commonHelperMethods.toolbarSearch("29");
        commonHelperMethods.ToolbarButtonClick("Search");

        commonHelperMethods.ToolbarButtonClick("Edit Channel Package");

        commonHelperMethods.fillRowInfo("Name", "TestName Edit");
        commonHelperMethods.fillRowInfo("Description", "TestDescription Edit");

        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");
        commonHelperMethods.verifyEditedItem();

        driver.close();
    }

    @Test
    public void p02crеateChannelPackage() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("Products");
        commonHelperMethods.PopUpMenuSelecting("Channel Packages");
        commonHelperMethods.ToolbarButtonClick("Create Channel Package");

        // Fill mandatory fields Plus try to fill the readonly fields
        commonHelperMethods.fillRowInfo("Name", "TestChannelName");
        commonHelperMethods.fillRowInfo("Description", "TestChannelDescription");

        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");

        // Verify saved item by given property
        commonHelperMethods.verifyCreatedItem("id");

        driver.close();
    }

}
