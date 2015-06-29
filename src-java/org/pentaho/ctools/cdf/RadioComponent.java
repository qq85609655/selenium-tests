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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Radio Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class RadioComponent {
  //Instance of the this.driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // Instance to be used on this.wait commands
  private final Wait<WebDriver> wait = CToolsTestSuite.getWait();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webthis.driver
  private final ElementHelper elemHelper = new ElementHelper();
  //this.log instance
  private final Logger log = LogManager.getLogger( RadioComponent.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );
    // The URL for the RadioComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/RadioComponent
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A52-RadioComponent%3Aradio_component.xcdf/generatedContent" );

    // NOTE - we have to this.wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
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
    this.log.info( "tc1_PageContent_DisplayTitle" );

    // this.wait for title become visible and with value 'Community Dashboard Framework'
    this.wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // this.wait for visibility of 'VisualizationAPIComponent'
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", this.driver.getTitle() );
    assertEquals( "RadioComponent", this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
    this.log.info( "tc2_ReloadSample_SampleReadyToUse" );

    // ## Step 1
    // Render again the sample
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to this.wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = this.driver.findElements( By.id( "sampleObject" ) ).size();
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
   *    1. Select Eastern
   *    2. Select Central
   *    3. Select Western
   *    4. Select Southern
   */
  @Test( timeout = 60000 )
  public void tc3_SelectEachItem_AlertDisplayed() {
    this.log.info( "tc3_SelectEachItem_AlertDisplayed" );

    // ## Step 1
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Eastern']" ) ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).click();
    this.wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = this.driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern", confirmationMsg );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );

    // ## Step 2
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Central']" ) ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).click();
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );

    // ## Step 3
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Western']" ) ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).click();
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );

    // ## Step 4
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Southern']" ) ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).click();
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Select arbitrary options
   * Description:
   *    We pretend validate the selection every available options but arbitrary.
   * Steps:
   *    1. Select Western
   *    2. Select Southern
   *    3. Select Central
   *    4. Select Western
   */
  @Test( timeout = 60000 )
  public void tc4_SelectArbitrary_AlertDisplayed() {
    this.log.info( "tc4_SelectArbitrary_AlertDisplayed" );

    // ## Step 1
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Western']" ) ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).click();
    this.wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = this.driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );

    // ## Step 2
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Southern']" ) ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).click();
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );

    // ## Step 3
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Central']" ) ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).click();
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );

    // ## Step 4
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Western']" ) ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).click();
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Southern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Eastern']" ) ).isSelected() );
    assertFalse( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Central']" ) ).isSelected() );
    assertTrue( this.elemHelper.FindElement( this.driver, By.xpath( "//input[@value='Western']" ) ).isSelected() );
  }
}
