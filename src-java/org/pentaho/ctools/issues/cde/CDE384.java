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
package org.pentaho.ctools.issues.cde;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
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
 * - http://jira.pentaho.com/browse/CDE-384
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-940
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE384 {

  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE384.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE384.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check external resources path when editing
   *
   * Description:
   *    The test pretends validate the CDE-384 issue, so when user edits two different external
   *    resources the path to both of them is accurate.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Add Resource"
   *    2. Wait for popup to appear, assert text and click "Ok"
   *    3. Wait for properties and click on "^", wait for popup and expand "public", expand "plugin-samples",
   *    expand "pentaho-cdf-dd", click "cdeReference.css" and click "Ok"
   *    4. Click to edit resource, wait for popup, assert text on path, click "X"
   *    5. Go to Datasources Panel, expand Community Data Access and click CDA Datasource
   *    6. On properties click "^", wait for popup and expand "public", expand "plugin-samples",
   *    expand "cda", expand "cdafiles", click "compundJoin.cda" and click "Ok"
   *    7. Click to edit cda file, wait for popup, assert text on path, click "X"
   *    8. Go to Layout Panel, click to edit resource, wait for popup, assert text on path, click "X"
   */
  @Test( timeout = 120000 )
  public void tc01_ExternalResources_AssertPath() {
    LOG.info( "tc01_ExternalResources_AssertPath" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    DRIVER.get( BASE_URL + "api/repos/wcdf/new" );
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Save as Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Resource']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//a[@title='Add Resource']" ) );

    /*
     * ## Step 2
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//select[@id='resourceType']" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( DRIVER, By.xpath( "//select[@id='resourceType']" ) ) );
    select.selectByValue( "Css" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//select[@id='resourceSource']" ) );
    assertNotNull( element );
    Select select1 = new Select( this.elemHelper.FindElement( DRIVER, By.xpath( "//select[@id='resourceSource']" ) ) );
    select1.selectByValue( "file" );
    this.elemHelper.Click( DRIVER, By.xpath( "//button[@id='popup_state0_buttonOk']" ) );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@class='cdfdd-resourceFileExplorerRender']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//button[@class='cdfdd-resourceFileExplorerRender']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "container_id" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/cdeReference.css']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/cdeReference.css']" ) );
    this.elemHelper.Click( DRIVER, By.xpath( "//button[@id='popup_browse_buttonOk']" ) );
    this.elemHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@class='cdfdd-resourceFileNameRender']" ), "${solution:/public/plugin-samples/pentaho-cdf-dd/cdeReference.css}" );
    String pathToResource = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='cdfdd-resourceFileNameRender']" ) ).getText();
    assertEquals( "${solution:/public/plugin-samples/pentaho-cdf-dd/cdeReference.css}", pathToResource );

    /*
     * ## Step 4
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@class='cdfddInput']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//button[@class='cdfddInput']" ) );
    WebElement elementFrame = this.elemHelper.FindElement( DRIVER, By.xpath( "//iframe" ) );
    WebDriver frame = DRIVER.switchTo().frame( elementFrame );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//span[@id='infoArea']" ) );
    assertNotNull( element );
    String pathText = this.elemHelper.WaitForTextPresence( frame, By.xpath( "//span[@id='infoArea']" ), "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css" );
    assertEquals( "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css", pathText );
    DRIVER.switchTo().defaultContent();
    this.elemHelper.Click( DRIVER, By.id( "popup_edit_buttonClose" ) );

    /*
     * ## Step 5
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a" ) );
    assertNotNull( element );
    String cdaText = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a" ) );
    assertEquals( "Community Data Access", cdaText );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='CDA Data Source']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//a[@title='CDA Data Source']" ) );

    /*
     * ## Step 6
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfdd-resourceFileExplorerRender']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfdd-resourceFileExplorerRender']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "container_id" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']" ) );
    this.elemHelper.Click( DRIVER, By.xpath( "//button[@id='popup_browse_buttonOk']" ) );

    /*
     * ## Step 7
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfddInput']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfddInput']" ) );
    elementFrame = this.elemHelper.FindElement( DRIVER, By.xpath( "//iframe" ) );
    frame = DRIVER.switchTo().frame( elementFrame );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//span[@id='infoArea']" ) );
    assertNotNull( element );
    String pathText1 = this.elemHelper.WaitForTextPresence( frame, By.xpath( "//span[@id='infoArea']" ), "/public/plugin-samples/cda/cdafiles/compoundJoin.cda" );
    assertEquals( "/public/plugin-samples/cda/cdafiles/compoundJoin.cda", pathText1 );
    DRIVER.switchTo().defaultContent();
    this.elemHelper.Click( DRIVER, By.id( "popup_edit_buttonClose" ) );

    /*
     * ## Step 8
     */
    DRIVER.switchTo().defaultContent();
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@class='layoutPanelButton']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@class='cdfddInput']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//button[@class='cdfddInput']" ) );
    elementFrame = this.elemHelper.FindElement( DRIVER, By.xpath( "//iframe" ) );
    frame = DRIVER.switchTo().frame( elementFrame );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//span[@id='infoArea']" ) );
    assertNotNull( element );
    String pathText3 = this.elemHelper.WaitForTextPresence( frame, By.xpath( "//span[@id='infoArea']" ), "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css" );
    assertEquals( "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css", pathText3 );
    DRIVER.switchTo().defaultContent();
    this.elemHelper.Click( DRIVER, By.id( "popup_edit_buttonClose" ) );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE384.class.getSimpleName() );
  }
}
