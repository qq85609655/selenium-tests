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
package org.pentaho.ctools.issues.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-45
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-946
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDA45 {
  // Instance of the driver (browser emulator)
  private WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDA45.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting info button that appears in input fields has display property set as block
   * Description:
   *    The test pretends validate the CDA-45 issue, no matter how long the string the user inputs on the input fields the
   *    text never goes behind the "?".
   *
   * Steps:
   *    1. Select Data Access "SQL Query on SampleData - Jdbc"
   *    2. Click number beside Data Access and check info button's style property for "display: block;"
   *    3. Click "Status:" input field and check info button's style property for "display: block;"
   *    4. Click "orderDate:" input field and check info button's style property for "display: block;"
   *
   */
  public void tc1_CdaFileViewer_OutputInfo() {
    LOG.info( "t01_CdaFileViewer_OutputInfo" );

    /*
     * ## Step 1
     */
    //Open Sql-Jdbc Sample
    this.driver.get( this.baseUrl + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/sql-jdbc.cda" );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( this.driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "1" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

    //wait to render page
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    /*
     * ## Step 2
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "outputIndexId" ) );
    assertNotNull( element );
    element.click();
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='helpButton helpButtonShort']" ) );
    String styleText = this.elemHelper.FindElement( this.driver, By.id( "outputIndexId" ) ).getAttribute( "class" );
    assertEquals( "cdaButton cdaButtonShort cdaButtonSelected", styleText );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "status" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='helpButton']" ) );
    assertNotNull( element );
    String styleText1 = this.elemHelper.FindElement( this.driver, By.id( "status" ) ).getAttribute( "class" );
    assertEquals( "cdaButton cdaButtonSelected", styleText1 );

    /*
     * ## Step 4
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "orderDate" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='parameterHolder']/div[2]/div[2]/div" ) );
    assertNotNull( element );
    String styleText2 = this.elemHelper.FindElement( this.driver, By.id( "orderDate" ) ).getAttribute( "class" );
    assertEquals( "cdaButton cdaButtonSelected", styleText2 );
  }
}
