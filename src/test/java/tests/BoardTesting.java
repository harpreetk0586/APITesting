package tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.BasePage;

/**
 * The BoardTesing program implements API testing Cases for Creating and
 * updating an existing Board functions in Trello Web Application. Additionally,
 * it validates the created Board, and validates the updated Board. Also User
 * need to update the Trello Key and Token values in config.properties file if
 * needed.
 *
 * This program also provides the BoardID to ListTEsting and DeleteBoard
 * Classes. Board Testing, ListTesting, Card Testing and DeleteBoard should be
 * run in chronological order as listed in testng.xml file. ListTesting, Card
 * Testing and DeleteBoard are using the BoardID, ListIO in created in
 * BoardTesing and ListTesting for tests.
 * 
 * @author Harpreet Kaur
 * @version 1.0
 * @since 2023-03-31
 */

public class BoardTesting {

	BasePage bp = new BasePage();
	String updatedBoardId;
	
	// Creates http request and response by calling methods from base page class
	@BeforeClass
	public void setup() throws IOException {
	
		bp.setupRequest();
		bp.setUpResponseBoard();

	}

	// creates a board, test the API request Status Code, Jason Response Name, time
	// less 2000ms
	@Test(priority = 1)
	public void createBoard() throws IOException {

		bp.postBoard(bp.prop.getProperty("BoardURL"), bp.boardName);

	}

	// validates a board is created and test: the API request Status Code, Jason
	// Response Name, time less 2000ms
	@Test(priority = 2)
	public void getBoard() throws InterruptedException {
		bp.get(bp.prop.getProperty("GetBoardURL"), bp.idBoard, bp.httpResponse);

	}

	// updates an exiting board and test: the API request Status Code, Jason
	// Response Name, time less 2000ms
	@Test(priority = 3)
	public void updateBoard() throws IOException {
		updatedBoardId=	bp.put(bp.prop.getProperty("GetBoardURL"), bp.idBoard,bp.httpResponse);

	}

	// validates the updated Board and test: the API request Status Code, Jason
	// Response Name, time less 2000ms
	@Test(priority = 4)
	public void getUpdatedBoard() throws IOException {
		bp.get(bp.prop.getProperty("GetBoardURL"), updatedBoardId, bp.httpResponse);

	}

	// assign attribute value to BoardId to use in other classes.
	@AfterClass(alwaysRun = true)
	public void inserBoardIdIntoAttribute(ITestContext ctx) {
		ctx.setAttribute("BoardId", bp.idBoard);
	}
}
