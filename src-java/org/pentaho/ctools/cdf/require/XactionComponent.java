/*
 * !*****************************************************************************
 * Selenium Tests For CTools Copyright (C) 2002-2015 by Pentaho :
 * http://www.pentaho.com
 * ********************************************************
 * ********************** Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ****************************************************************************
 */
package org.pentaho.ctools.cdf.require;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Xaction Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class XactionComponent {

  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  public void tc0_PageContent_DisplayTitle() {
    // The URL for the XactionComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/XactionComponent
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A10-XactionComponent%3Axaction_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test( timeout = 60000 )
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    WAIT.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", DRIVER.getTitle() );
    assertEquals( "XactionComponent", this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test( timeout = 60000 )
  public void tc2_ReloadSample_SampleReadyToUse() {
    // ## Step 1
    // Render again the sample
    this.elemHelper.FindElement( DRIVER, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( DRIVER, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( DRIVER, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = DRIVER.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Xacion
   * Description:
   *    We pretend validate the generated graphic (in an image) and if the image
   *    has a valid url.
   * Steps:
   *    1. Check if a graphic was generated
   *    2. Check the http request for the image generated
   */
  @Test( timeout = 60000 )
  public void tc3_GenerateChart_ChartIsDisplayed() {
    // ## Step 1
    WebElement xactionElement = this.elemHelper.FindElement( DRIVER, By.cssSelector( "img" ) );
    assertNotNull( xactionElement );

    String attrSrc = this.elemHelper.FindElement( DRIVER, By.cssSelector( "img" ) ).getAttribute( "src" );
    String attrWidth = this.elemHelper.FindElement( DRIVER, By.cssSelector( "img" ) ).getAttribute( "width" );
    String attrHeight = this.elemHelper.FindElement( DRIVER, By.cssSelector( "img" ) ).getAttribute( "height" );

    assertTrue( attrSrc.startsWith( BASE_URL + "getImage?image=tmp_chart_admin-" ) );
    assertEquals( attrWidth, "500" );
    assertEquals( attrHeight, "600" );

    // ## Step 2
    try {
      URL url = new URL( attrSrc );
      URLConnection connection = url.openConnection();
      connection.connect();

      assertEquals( HttpStatus.SC_OK, ( (HttpURLConnection) connection ).getResponseCode() );

    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
  }
}
