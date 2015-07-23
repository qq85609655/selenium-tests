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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Time Plot Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class TimePlotComponent {

  //Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( TimePlotComponent.class );

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
    // The URL for the TimePlotComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/TimePlotComponent
    this.driver.get( PageUrl.TIMEPLOT_COMPONENT );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );
    // Wait for visibility of 'VisualizationAPIComponent'
    String sampleTitle = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ), "timePlotComponent" );

    // Validate the sample that we are testing is the one
    assertEquals( "timePlotComponent", sampleTitle );
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
  @Test
  public void tc2_ReloadSample_SampleReadyToUse() {
    this.log.info( "tc2_ReloadSample_SampleReadyToUse" );
    /*
     *  ## Step 1
     */
    // Render again the sample
    this.elemHelper.ClickJS( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.ClickJS( this.driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = this.driver.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );

    //It could be possible to raise an error of "Error processing component" and the workaround is refresh the page.
    WebElement chart = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.timeplot-container.timeplot" ), 2 );
    if ( chart == null ) {
      this.driver.navigate().refresh();
      this.log.debug( "Refreshing" );
      this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
      chart = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.timeplot-container.timeplot" ), 2 );
      if ( chart == null ) {
        this.driver.navigate().refresh();
        this.log.debug( "Refreshing" );
        this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
        chart = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.timeplot-container.timeplot" ), 2 );
        assertNotNull( chart );
      }
    }
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Time Plot
   * Description:
   *    For this component we need to validate when user move mouse over plot
   *    we have new values for Total Price.
   * Steps:
   *    1. Check if the graphic is presented
   *    2. Move mouse over graphic and check the expected value for Total Price
   */
  @Test
  public void tc3_MouseOverPlot_TotalPriceChanged() {
    this.log.info( "tc3_MouseOverPlot_TotalPriceChanged" );
    /*
     * ## Step 1
     */
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='sampleObject']/div/span" ) );
    assertEquals( "Total order income", this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sampleObject']/div/span" ) ) );
    assertEquals( "Total Price", this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sampleObject']/div/span[2]" ) ) );

    WebElement element2004 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(text(), '2004')]" ) );
    assertNotNull( element2004 );

    /*
     * ## Step 2
     */
    this.elemHelper.MoveToElement( this.driver, By.cssSelector( "canvas.timeplot-canvas" ), 10, 10 );

    String totalExpected = "Total Price = 6,864";
    String totalText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sampleObject']/div/span[2]" ) );
    assertTrue( totalText.startsWith( totalExpected ) );
  }
}
