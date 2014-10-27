/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

import static org.junit.Assert.*;

/**
 * Testing the functionalities related with component Schedule Prpt.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchedulePrptComponent {
	// Instance of the driver (browser emulator)
	private static WebDriver       driver;
	// Instance to be used on wait commands
	private static Wait<WebDriver> wait;
	// The base url to be append the relative url in test
	private static String          baseUrl;
	// The schedule name for TC3
	private static String schNameTc3 = "SchedulePSTc3";
	
	@Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

	/**
	 * Shall initialized the test before run each test case.
	 */
	@BeforeClass
	public static void setUp() {
		driver = CToolsTestSuite.getDriver();
		wait = CToolsTestSuite.getWait();
		baseUrl = CToolsTestSuite.getBaseUrl();

		// Go to sample
		init();
	}

	/**
	 * Go to the TableComponent web page.
	 */
	public static void init() {
		// The URL for the VisualizationAPIComponent under CDF samples
		// This samples is in: Public/plugin-samples/CDF/Documentation/Component
		// Reference/Core Components/SchedulePrptComponent
		driver.get(baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:86-SchedulePrptComponent:schedule_prpt_component.xcdf/generatedContent");

		// Not we have to wait for loading disappear
		ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
	}

	/**
	 * ############################### Test Case 1 ###############################
	 *
	 * Test Case Name: 
	 * 		Validate Page Contents 
	 * Description: 
	 * 		Here we want to validate the page contents. 
	 * Steps: 
	 * 		1. Check the widget's title.
	 */
	@Test
	public void tc1_PageContent_DisplayTitle() {
		// Wait for title become visible and with value 'Community Dashboard Framework'
		wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
		// Wait for visibility of 'VisualizationAPIComponent'
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

		// Validate the sample that we are testing is the one
		assertEquals("Community Dashboard Framework", driver.getTitle());
		assertEquals("SchedulePrptComponent",ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
	}

	/**
	 * ############################### Test Case 2 ###############################
	 *
	 * Test Case Name: 
	 * 		Reload Sample 
	 * Description: 
	 * 		Reload the sample (not refresh page). 
	 * Steps: 
	 * 		1. Click in Code and then click in button 'Try me'.
	 */
	@Test
	public void tc2_ReloadSample_SampleReadyToUse() {
		// ## Step 1
		// Render again the sample
		ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
		ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();

		// Not we have to wait for loading disappear
		ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

		// Now sample element must be displayed
		assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());
	}
	
	/**
	 * ############################### Test Case 2 ###############################
	 *
	 * Test Case Name: 
	 * 		Create a Schedule 
	 * Description: 
	 * 		It is supposed to create a schedule and validate it was created with
	 *    specify data and date. 
	 * Steps: 
	 * 		1. Click to create a schedule prpt.
	 *    2. Fill the form and submit
	 *    3. Check for alert.
	 *    4. On Schedule Manager, it is set the schedule.
	 */
	@Test
	public void tc3_SchedulePrpt_ScheduleCreatedSuccessful() throws InterruptedException{
		String schLocation = "/public";
		
		//Initialize some data
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
		Date dNow = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dNow);
		c.add(Calendar.DATE, 1);
		Date dTomorrow = c.getTime();
		c.add(Calendar.DATE, 30); //30th days from today
		Date d30days = c.getTime();
		
		// ## Step 1
		ElementHelper.FindElement(driver, By.xpath("//span[@id='sampleObject']/button")).click();

		
		// ## Step 2
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("jqi_state_basicState")));
		//Set schedule name
		ElementHelper.FindElement(driver, By.id("nameIn")).clear();
		ElementHelper.FindElement(driver, By.id("nameIn")).sendKeys(schNameTc3);
    //Set schedule location		
		ElementHelper.FindElement(driver, By.id("locationIn")).clear();
		ElementHelper.FindElement(driver, By.id("locationIn")).sendKeys(schLocation);
    //Select Month
    Select slRecurrence = new Select(ElementHelper.FindElement(driver, By.id("recurrId")));
    slRecurrence.selectByValue("monthly");    
    //Select Hour
    Select slHours = new Select(ElementHelper.FindElement(driver, By.id("hours")));
    slHours.selectByValue("9");
    //Select Minutes
    Select slMinutes = new Select(ElementHelper.FindElement(driver, By.id("minutes")));
    slMinutes.selectByValue("17");
    //Select AM/FM
    Select slAMFM = new Select(ElementHelper.FindElement(driver, By.id("amPm")));
    slAMFM.selectByValue("pm");
    //Select Option 'The x y of every month
    ElementHelper.FindElement(driver, By.xpath("//div[@id='patternMonth']/input[2]")).click();
		//Select Month
		Select slOccDay = new Select(ElementHelper.FindElement(driver, By.id("monthOpt1Select")));
		slOccDay.selectByValue("1");
		//Select Wednesday
		Select slWeekday = new Select(ElementHelper.FindElement(driver, By.id("monthOpt2Select")));
		slWeekday.selectByValue("3");		
		//Select Range Of Recurrence
		//Start - tomorrow
		ElementHelper.FindElement(driver, By.id("rangeStartIn")).clear();
		ElementHelper.FindElement(driver, By.id("rangeStartIn")).sendKeys(sdf.format(dTomorrow));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='ui-datepicker-calendar']")));
		WebElement dateCalendar = ElementHelper.FindElement(driver, By.xpath("//table[@class='ui-datepicker-calendar']"));
		List<WebElement> columns = dateCalendar.findElements(By.tagName("td"));
		String tomorrowDay = sdfDay.format(dTomorrow);
		for(WebElement cell: columns) {
        String strCell = cell.getText();
		  if (strCell.equals(tomorrowDay)){        
        cell.findElement(By.linkText(tomorrowDay)).click();
		    break;
		  }
		}
		//End
		ElementHelper.FindElement(driver, By.id("endByRadio")).click();
		ElementHelper.FindElement(driver, By.id("endByIn")).sendKeys(sdf.format(d30days));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='ui-datepicker-calendar']")));
		WebElement dateCalendar2 = ElementHelper.FindElement(driver, By.xpath("//table[@class='ui-datepicker-calendar']"));
    List<WebElement> columns2 = dateCalendar2.findElements(By.tagName("td"));
    String day = sdfDay.format(d30days);
    for(WebElement cell2: columns2) {
      String strCell2 = cell2.getText();
      if (strCell2.equals(day)){
        cell2.findElement(By.linkText(day)).click();
        break;
      }
    }
		//Submit Form
		ElementHelper.FindElement(driver, By.id("jqi_basicState_buttonOk")).click();
		//Wait for the new window.
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("jqi_state_mailState")));
		ElementHelper.FindElement(driver, By.id("jqi_mailState_buttonOk")).click();
		
		
		// ## Step 3
		wait.until(ExpectedConditions.alertIsPresent());		
		Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals(confirmationMsg, "Successfully scheduled.");

    
		// ## Step 4
    //-->Need to check if the schedule was created
    //Go to home page
    driver.get(baseUrl + "Home");
  	// Not we have to wait for loading disappear
  	ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  		
  	//Click in Schedule
  	wait.until(ExpectedConditions.titleContains("Pentaho User Console"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
  	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='mantle-perspective-switcher']/table/tbody/tr/td[2]")));
  	ElementHelper.FindElement(driver, By.xpath("//div[@id='mantle-perspective-switcher']/table/tbody/tr/td[2]")).click();
  	WebElement listMenyTr = ElementHelper.FindElement(driver, By.xpath("//div[@id='customDropdownPopupMajor']/div/div/table/tbody"));
  	List<WebElement> listMenuElementsTrs = listMenyTr.findElements(By.xpath("//td[@class='gwt-MenuItem']"));
  	for (int i = 0; i< listMenuElementsTrs.size(); i++) {
  		WebElement element = listMenuElementsTrs.get(i);
  		if(element.getText().equals("Schedules")){
  			element.click();
  			break;
  		}
  	}
  	wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.workspaceHeading")));
  	// Now we are in Schedule page
 		List<WebElement> listScheduleTrs = ElementHelper.FindElement(driver, By.xpath("//table[@id='schedule-table']/tbody")).findElements(By.tagName("tr"));
 		String scheduleName = "";
 		String scheduleRepeats = "";
 		String scheduleSourceFile = "";
 		String scheduleOuputLocation = "";
 		String scheduleLastRun = "";
 		String scheduleNextRun = "";
 		String scheduleCreatedBy = "";
 		String scheduleStatus = "";
 		for (int j = 1; j <= listScheduleTrs.size(); j++) {
 			WebElement elementFirstDiv = ElementHelper.FindElement(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td/div"));
 			scheduleName = elementFirstDiv.getText();
      if(scheduleName.equals(schNameTc3)){
 			  
        scheduleRepeats = ElementHelper.GetText(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td[2]/div"));
 			  scheduleSourceFile= ElementHelper.GetText(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td[3]/div"));
 			  scheduleOuputLocation= ElementHelper.GetText(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td[4]/div"));
 			  scheduleLastRun= ElementHelper.GetText(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td[5]/div"));
 			  scheduleNextRun= ElementHelper.GetText(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td[6]/div"));
 			  scheduleCreatedBy= ElementHelper.GetText(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td[7]/div"));
 			  scheduleStatus= ElementHelper.GetText(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td[8]/div"));
 			  break;
 			}
 		}
 		
 		//Getting the week day of next run
 		String dayOfWeek = "";
 		if ( !scheduleNextRun.isEmpty() ) {
   		try {
        Date dateNextRun = new SimpleDateFormat("yyyy MMM dd hh:mm:ss").parse(scheduleNextRun);
        dayOfWeek = (new SimpleDateFormat("EE", Locale.US)).format(dateNextRun);
      } catch (ParseException e) {
        e.printStackTrace();
      }
 		}
 		
 		assertEquals(scheduleName, schNameTc3);
 		assertEquals(scheduleRepeats, "The second Wednesday of every month at 21:17:00");
 		assertEquals(scheduleSourceFile, "/public/Steel Wheels/Widget Library/Report Snippets/Product Sales");
 		assertEquals(scheduleOuputLocation, schLocation);
 		assertEquals(scheduleLastRun, "-");
 		assertEquals(dayOfWeek, "Wed");
 		assertEquals(scheduleCreatedBy, "admin");
 		assertEquals(scheduleStatus, "Normal");
	}
	
	/**
	 * This method will remove all created schedules on the tests.
	 * Note - for a new schedule update code.
	 */
	private static void removeAllCreatedSchedules() {
		//Go to home page
		driver.get(baseUrl + "Home");
		// Not we have to wait for loading disappear
		ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

		
		//Click in Schedule
		wait.until(ExpectedConditions.titleContains("Pentaho User Console"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='mantle-perspective-switcher']/table/tbody/tr/td[2]")));
		ElementHelper.FindElement(driver, By.xpath("//div[@id='mantle-perspective-switcher']/table/tbody/tr/td[2]")).click();
		WebElement listMenyTr = ElementHelper.FindElement(driver, By.xpath("//div[@id='customDropdownPopupMajor']/div/div/table/tbody"));
		List<WebElement> listMenuElementsTrs = listMenyTr.findElements(By.xpath("//td[@class='gwt-MenuItem']"));
		for (int i = 0; i< listMenuElementsTrs.size(); i++) {
			WebElement element = listMenuElementsTrs.get(i);
			if(element.getText().equals("Schedules")){
				element.click();
				break;
			}
		}
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.workspaceHeading")));
		
		
	  // Now we are in Schedule page
		Boolean someThingToDelete = true;
		int listElements = 0;
		int listElementsPrevious = -1;
		while(someThingToDelete) {
		  someThingToDelete = false;
		  List<WebElement> listScheduleTrs = ElementHelper.FindElement(driver, By.xpath("//table[@id='schedule-table']/tbody")).findElements(By.tagName("tr"));
		  listElements = listScheduleTrs.size();
  		
		  //The new list must be different from previous list
		  while( listElements == listElementsPrevious) {
  		  listScheduleTrs = ElementHelper.FindElement(driver, By.xpath("//table[@id='schedule-table']/tbody")).findElements(By.tagName("tr"));
        listElements = listScheduleTrs.size();
  		}		  
		  
  		for (int j = 1; j <= listElements; j++) {
  		  WebElement elementFirstDiv = ElementHelper.FindElement(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]/td/div"));
  		  
  		  if(elementFirstDiv.getText().equals(schNameTc3)){
  				elementFirstDiv.click();//Select the row

  				//Wait for row to be selected
  				for(int t = 0; t<100; t++) {
  				  WebElement elementRow = ElementHelper.FindElement(driver, By.xpath("//table[@id='schedule-table']/tbody/tr["+j+"]"));
  				  
  				  if( elementRow.getAttribute("class").contains("cellTableSelectedRow"))
  				    break;
  				}
  				
  				//Click to remove the schedule item (the selected row)
  				ElementHelper.FindElement(driver, By.cssSelector("img.gwt-Image.pentaho-deletebutton")).click();
  				
  				wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//div[@class='pentaho-dialog']")));
  				ElementHelper.FindElement(driver, By.id("okButton")).click();
  				
          ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='pentaho-dialog']"));
          
  				someThingToDelete = true;//Continue checking if there is something to delete  				
  				break;
  			}
  		}
  		listElementsPrevious = listElements;
		}
	}

	@AfterClass
	public static void tearDown() {
		removeAllCreatedSchedules();
	}
}
