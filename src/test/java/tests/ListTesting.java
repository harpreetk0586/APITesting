package tests;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BasePage;

/**
 * The ListTesing class implements API testing Cases for Creating and updating
 * an existing list functions in Trello Web Application. Additionally, it
 * validates the created and updated list. Also User may need to update the
 * Trello Key and Token values in config.properties file.
 *
 * This program also provides the ListID to CardTesting Class.
 *
 * @author Harpreet Kaur
 * @version 1.0
 * @since 2023-03-31
 */

public class ListTesting {
	BasePage bp = new BasePage();
	String updatedlistId;
	String idBoard2;
	String listId;

	// Creates http request and response by calling methods from base page class
	@BeforeClass
	public void setup(ITestContext ctx) throws IOException {

		idBoard2 = (String) ctx.getAttribute("BoardId");
		bp.setupRequest();
		bp.setUpResponseList();

	}

	// creates a list, test the API request Status Code, Jason Response Name, time
	// less 2000ms
	@Test(priority = 1)
	public void createList() throws IOException {
		listId = bp.postListOrCard(bp.prop.getProperty("ListURL"), bp.listName, "idBoard", idBoard2, bp.httpResponseL);

	}

	// validates that list is created and test: the API request Status Code, Jason
	// Response Name, time less 2000ms
	@Test(priority = 2)
	public void getList() throws InterruptedException {
		bp.get(bp.prop.getProperty("GetListURL"), listId, bp.httpResponseL);

	}

	// updates an exiting list and test: the API request Status Code, Jason Response
	// Name, time less 2000ms

	@Test(priority = 3)
	public void updateList() throws IOException {
		updatedlistId = bp.put(bp.prop.getProperty("GetListURL"), listId, bp.httpResponseL);

	}

	// validates the updated list and test: the API request Status Code, Jason
	// Response Name, time less 2000ms
	@Test(priority = 4)
	public void getUpdatedList() throws IOException {
		bp.get(bp.prop.getProperty("GetListURL"), updatedlistId, bp.httpResponseL);

	}

	// assign attribute value to listId to use in other classes.
	@AfterClass(alwaysRun = true)
	public void inserBoardIdIntoAttribute(ITestContext ctx) {
		ctx.setAttribute("idList", listId);

	}

}
