package com.azdio.adminui.product;

import com.azdio.adminui.BrowserExtensions;
import com.azdio.adminui.CommonMethods;
import com.azdio.adminui.Config;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.List;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductTests extends BrowserExtensions {
    CommonMethods commonHelperMethods = new CommonMethods();
    Config config = new Config();

    private String url = config.getConfigProperty("int.server.url");
    private String username = config.getConfigProperty("mdw.user");
    private String password = config.getConfigProperty("mdw.password");

    @Before
    public void initialize() {
        driver.get(url);
        commonHelperMethods.login(username, password);
    }

    // Product Tests
    @Test
    public void p01crateProduct() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("Products");
        commonHelperMethods.PopUpMenuSelecting("Products");
        commonHelperMethods.ToolbarButtonClick("Create Product");

        // Selects from dropdowns
        List<String> channelPackages = Arrays.asList("EE_Gold", "EE_HD");

        // Fill mandatory fields Plus try to fill the readonly fields
        commonHelperMethods.openDropdown("Channel Packages");
        commonHelperMethods.selectAnELementFromDropdown(channelPackages);
        commonHelperMethods.fillRowInfo("Name", "TestName");
        commonHelperMethods.fillRowInfo("Description", "TestDescription");

        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");

        // Verify saved item by given property
        commonHelperMethods.verifyCreatedItem("id");

        driver.close();
    }
    @Test
    public void p02editProduct() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("Products");
        commonHelperMethods.PopUpMenuSelecting("Products");

        // Prepare for edit
        commonHelperMethods.toolbarSearch("TestName");
        commonHelperMethods.ToolbarButtonClick("Search");

        commonHelperMethods.ToolbarButtonClick("Edit Product");

        commonHelperMethods.fillRowInfo("Name", "TestName Edit");
        commonHelperMethods.fillRowInfo("Description", "TestDescription Edit");

        // Save info
        commonHelperMethods.ToolbarButtonClick("Save");
        commonHelperMethods.verifyEditedItem();

        driver.close();
    }

    @Test
    public void p03deleteProduct() {

        // Navigation to the page
        commonHelperMethods.leftPanelNavigation("Products");
        commonHelperMethods.PopUpMenuSelecting("Products");
        // Search for item
        commonHelperMethods.toolbarSearch("TestName");
        commonHelperMethods.ToolbarButtonClick("Search");
        // Pre-delete
        Integer before = commonHelperMethods.numberOfGridRows();
        String idBefore = commonHelperMethods.getGridPropety("Id");
        commonHelperMethods.ToolbarButtonClick("Delete Product");
        commonHelperMethods.confirmDelete("Yes");
        // Pos-delete
        Integer after = commonHelperMethods.numberOfGridRows();
        String idAfter = commonHelperMethods.getGridPropety("Id");
        commonHelperMethods.verifyIsDelete(before, after, idBefore, idAfter);

        driver.close();
    }

}
