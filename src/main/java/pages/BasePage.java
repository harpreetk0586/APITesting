package pages;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.*;
import java.io.FileInputStream;
import java.io.IOException;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.Properties;

public class BasePage {
	public Properties prop=new Properties();
	public RequestSpecification httpRequest;
	public ResponseSpecification httpResponse, httpResponseL,httpResponseC, httpResponseDel;
	public final String boardName=boardNameSet ();
	public final String listName=listNameSet ();
	public final String cardName=cardNameSet ();
	public String idBoard;
	

	public void loadproperties() throws IOException {
		FileInputStream file= new FileInputStream("/Users/harpreetkaur/Desktop/QA/CucmberWorkSpace/RestAssured_Trello_APITests/src/main/java/utilties/config.properties");
		prop.load(file);
	}
	public void setupRequest() throws IOException {
		loadproperties();

		httpRequest= given()
				.filters(new RequestLoggingFilter(),new RequestLoggingFilter(), new ErrorLoggingFilter())
				.baseUri("https://api.trello.com")
				.queryParam("key", prop.getProperty("key"))
				.queryParam("token",prop.getProperty("token"))
				.header("Content-Type" ,"application/json")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON);
		
	}
	public void setUpResponseBoard() throws IOException {

		ResponseSpecBuilder resBuilder=new ResponseSpecBuilder();
		httpResponse= resBuilder
				.expectStatusCode(Integer.valueOf(prop.getProperty("postSCode")))
				.expectResponseTime(lessThan(2000L))
				.expectBody("name",equalTo(boardName))
				.build();
	}
	public void setUpResponseList() throws IOException {
		// db.loadProperties();
		ResponseSpecBuilder resBuilder=new ResponseSpecBuilder();
		httpResponseL= resBuilder
				.setDefaultParser(Parser.JSON)
				.expectStatusCode(Integer.valueOf( prop.getProperty("postSCode")))
				.expectResponseTime(lessThan(2000L))
				.expectBody("name",equalTo(listName))
				.build();	       
	}
	public void setUpResponseCard() throws IOException {
		// db.loadProperties();
		ResponseSpecBuilder resBuilder=new ResponseSpecBuilder();
		httpResponseC= resBuilder
				.setDefaultParser(Parser.JSON)
				.expectStatusCode(Integer.valueOf( prop.getProperty("postSCode")))
				.expectResponseTime(lessThan(2000L))
				.expectBody("name",equalTo(cardName))
				.build();	       
	}

	
	
	

	public void setUpResponseDelete() throws IOException {
		//	 db.loadProperties();
		ResponseSpecBuilder resBuilder=new ResponseSpecBuilder();
		httpResponseDel= resBuilder
				.setDefaultParser(Parser.JSON)
				.expectResponseTime(lessThan(2000L))		
				.build();	       
	}
	
public void postBoard( String basepath, String name ) {
		
		Response response	=(Response) given(httpRequest)
				.queryParam("name",name)    
				.when()
				.post(basepath)
				.then()
				.spec(httpResponse)
				.extract()
				.response();;		
		    	idBoard=response.path("id");
		}
public void get(  String basepath,String id,ResponseSpecification httpResponse) {
	
	
			given(httpRequest)
			.pathParam("id", id)
			.when()
			.get(basepath)
			.then()
			.spec(httpResponse);
	}

public String put( String basepath,String id,ResponseSpecification httpResponse) {
	Response response	=given(httpRequest)
			.pathParam("id", id)
			.when()
			.put(basepath)
			.then()
			.spec(httpResponse)
			.extract()
			.response();
	 return response.path("id");
}	
	public String postListOrCard( String basepath,String name,String QueryParamName,String id, ResponseSpecification httpResponse) {
		
		Response response	=given(httpRequest)
				.basePath(basepath)
				.queryParam("name", name)
				.queryParam(QueryParamName,id)
				.when()
				.post()
				.then()
				.spec(httpResponse)
				.extract()
				.response();
				return response.path("id");
		

	}
	
	
	public void deleteBoard(String basepath,String id, ResponseSpecification httpResponse) {
		
		given(httpRequest)
		.pathParam("id", id)
		.when()
		.delete(basepath)
		.then()
		.spec(httpResponse);
		
	
	
	}

	public String boardNameSet (){

		return "BoardTest"+String.valueOf((int) (Math.random()*1000));
	}
	public String cardNameSet (){
		return "CardTest"+String.valueOf((int) (Math.random()*1000));
	}

	public String listNameSet (){
		return "ListTest"+String.valueOf((int) (Math.random()*1000));
	}





}

