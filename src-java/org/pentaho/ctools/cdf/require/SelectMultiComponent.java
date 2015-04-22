/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.pentaho.ctools.cdf.require;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Select Multi Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class SelectMultiComponent {
  //Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  //Log instance
  private static Logger LOG = LogManager.getLogger( SelectMultiComponent.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    LOG.info( "setUp##" + SelectMultiComponent.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
    BASE_URL = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the SelectMultiComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/SelectMultiComponent
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A19-SelectMultiComponent%3Aselect_multi_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
    LOG.info( "tc1_PageContent_DisplayTitle" );

    // Wait for title become visible and with value 'Community Dashboard Framework'
    WAIT.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", DRIVER.getTitle() );
    assertEquals( "SelectMultiComponent", ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
    LOG.info( "tc2_ReloadSample_SampleReadyToUse" );

    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( ElementHelper.FindElement( DRIVER, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = DRIVER.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Select options one by one
   * Description:
   *    We pretend validate the selection of each option one by one.
   * Steps:
   *    1. Select Southern
   *    2. Select Eastern
   *    3. Select Central
   *    4. Select Western
   */
  @Test( timeout = 60000 )
  public void tc3_SelectEachItem_AlertDisplayed() {
    LOG.info( "tc3_SelectEachItem_AlertDisplayed" );

    // ## Step 1
    WAIT.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.cssSelector( "select" ) ) );
    Select list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Southern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    Alert alert = DRIVER.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );

    // ## Step 2
    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Southern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    alert.accept();

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern", confirmationMsg );

    // ## Step 3
    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Eastern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    alert.accept();

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Central" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );

    // ## Step 4
    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Central" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    alert.accept();

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Western" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );

    //RESET
    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Western" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    alert.accept();
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Select and deselect all
   * Description:
   *    We pretend validate the selection and deselection of all options.
   * Steps:
   *    1. Select all options
   *    2. Deselect all options
   */
  @Test( timeout = 60000 )
  public void tc4_SelectAll_AlertDisplayed() {
    LOG.info( "tc4_SelectAll_AlertDisplayed" );

    // ## Step 1
    WAIT.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.cssSelector( "select" ) ) );
    Select list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Southern" );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    Alert alert = DRIVER.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Central" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Western" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central,Western", confirmationMsg );

    // ## Step 2
    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Southern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Central,Western", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Eastern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central,Western", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Central" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Western" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: ", confirmationMsg );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Select arbitrary
   * Description:
   *    We pretend validate the selection options arbitrary.
   * Steps:
   *    1. Select Arbitrary
   */
  @Test( timeout = 60000 )
  public void tc5_SelectArbitrary_AlertDisplayed() {
    LOG.info( "tc5_SelectArbitrary_AlertDisplayed" );

    // ## Step 1
    WAIT.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.cssSelector( "select" ) ) );
    Select list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    Alert alert = DRIVER.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Central" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Central", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Southern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Eastern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Central", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.selectByValue( "Western" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central,Western", confirmationMsg );

    list = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Central" );
    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Western", confirmationMsg );
  }

  @AfterClass
  public static void tearDown() {
    LOG.info( "tearDown##" + SelectMultiComponent.class.getSimpleName() );
  }
}
