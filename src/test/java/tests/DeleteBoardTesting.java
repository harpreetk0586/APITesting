package tests;

import static io.restassured.RestAssured.*;
import java.io.IOException;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BasePage;
/**
 * The DeleteBoardTesing class implements API testing Cases for deleting a board,
 *  created by Board testing Class for Trello Web Application.
 * Additionally, it validates the delete API request.
 * Also User may need to update the Trello Key and Token values in config.properties file.
 *
 *
 * @author  Harpreet Kaur
 * @version 1.0
 * @since   2023-03-31
 */

public class DeleteBoardTesting {
	BasePage bp =new BasePage();
	String idBoard1;


	//Creates http request and response by calling methods from BasePage class 
	@BeforeClass
	public void setup(ITestContext ctx) throws IOException {

		idBoard1=(String) ctx.getAttribute("BoardId");
		bp.setupRequest();
		bp.setUpResponseDelete();
	}

	//Delete the board and assert the status code and response time
	@Test(priority=1)
	public void deleteBoard() throws IOException {
		
		//bp.deleteBoard(bp.prop.getProperty("GetBoardURL"), idBoard1, bp.httpResponseDel);
		//bp.httpResponseDel.statusCode(Integer.valueOf(bp.prop.getProperty("postSCode")));
		given(bp.httpRequest)
		.pathParam("id", idBoard1)
		.when()
		.delete("/1/boards/{id}")
		.then()
		.spec(bp.httpResponseDel)
		.statusCode(Integer.valueOf(bp.prop.getProperty("postSCode")));
	}

	//validates the delete http request and assert the status code and response time
	@Test(priority=2)
	public void verifyBoardDeleted() throws IOException {
	/*	bp.get(bp.prop.getProperty("GetBoardURL"), idBoard1, bp.httpResponseDel);
		bp.httpResponseDel.statusCode(404);*/
		given(bp.httpRequest)
		.pathParam("id", idBoard1)
		.when()
		.get("/1/boards/{id}")
		.then()
		.spec(bp.httpResponseDel)
		.statusCode(Integer.valueOf(bp.prop.getProperty("delSCode")));
	}



}


