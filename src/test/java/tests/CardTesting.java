package tests;
import java.io.IOException;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BasePage;
/**
 * The CardTesing class implements API testing Cases for Creating and updating an existing card in the
 * list (which is created in ListTesting Class) functions in Trello Web Application.
 * Additionally, it validates the created and updated list.
 * Also User may need to update the Trello Key and Token values in config.properties file.
 *
 *
 * @author  Harpreet Kaur
 * @version 1.0
 * @since   2023-03-31
 */
public class CardTesting {
	BasePage bp=new BasePage();
	String  cardId;
	String idList;
	String updatedcardId;



	//Creates http request and response by calling methods from base page class 
	@BeforeClass
	public void setup(ITestContext ctx) throws IOException {

		idList=(String) ctx.getAttribute("idList");
		bp.setupRequest();
		bp.setUpResponseCard();

	}
	//creates a card, test the API request Status Code, Jason Response Name, time less 2000ms
	@Test(priority=1)
	public void createCard() throws IOException {
		cardId=bp.postListOrCard( bp.prop.getProperty("CardURL"), bp.cardName, "idList",idList,bp.httpResponseC);

	}
	// validates that card is created and test: the API request Status Code, Jason Response Name, time less 2000ms
	@Test(priority=2)
	public void getCard() throws InterruptedException {
		bp.get(bp.prop.getProperty("GetCardURL"), cardId, bp.httpResponseC);
	
	}
	//	updates an exiting card and test: the API request Status Code, Jason Response Name, time less 2000ms
	@Test(priority=3)
	public void updateList() throws IOException {
		updatedcardId=bp.put(bp.prop.getProperty("GetCardURL"), cardId, bp.httpResponseC);
	}
	//validates the updated card and test: the API request Status Code, Jason Response Name, time less 2000ms
	@Test(priority=4)
	public void getUpdatedCard() throws IOException {
		bp.get(bp.prop.getProperty("GetCardURL"), updatedcardId, bp.httpResponseC);
	
	}

}
