// specify the package
package userinterface;

// system imports
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


// project imports
import impresario.IModel;

import java.awt.*;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class TransactionChoiceView extends View
{

	// other private data
	private final int labelWidth = 120;
	private final int labelHeight = 25;

	// GUI components

	private Button delBook;
	private Button modBook;
	private Button withdrawButton;
	private Button transferButton;
	private Button balanceInquiryButton;
	private Button imposeServiceChargeButton;
	private Button depositButton;
	private Button modS;
	private Button modW;
	private Button delW;
	private Button checkOB;
	private Button checkIB;
	private Button delCheck;
	private Button listCheckBooks;
	private Button listAvailableBooks;
	private Button listStudentBooksCheckedOut;



	private Button cancelButton;

	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public TransactionChoiceView(IModel librarian)
	{
		super(librarian, "TransactionChoiceView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// how do you add white space?
		container.getChildren().add(new Label(" "));

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContents());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

		myModel.subscribe("TransactionError", this);
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	private VBox createTitle()
	{
		VBox container = new VBox(10);
		//container.setPadding(new Insets(15, 5, 5, 200));

		Text titleText = new Text("       Library Transactions          ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

		String accountHolderGreetingName = (String)myModel.getState("firstName");
		Text welcomeText = new Text("Welcome, " + accountHolderGreetingName + "!");
		welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		welcomeText.setWrappingWidth(300);
		welcomeText.setTextAlignment(TextAlignment.CENTER);
		welcomeText.setFill(Color.DARKGREEN);
		container.getChildren().add(welcomeText);

		Text inquiryText = new Text("What do you wish to do today?");
		inquiryText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		inquiryText.setWrappingWidth(300);
		inquiryText.setTextAlignment(TextAlignment.CENTER);
		inquiryText.setFill(Color.BLACK);
		container.getChildren().add(inquiryText);

		container.setAlignment(Pos.CENTER);

		return container;
	}

	private VBox createFormContents()
	{

		VBox container = new VBox(15);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		depositButton = new Button("Add Worker");
		depositButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		depositButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("AddWorker", null);
			}
		});
		//depositButton.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(depositButton, HPos.CENTER);
		grid.add(depositButton, 0, 0);

		withdrawButton = new Button("Add Book");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("AddBook", null);
			}
		});
		//withdrawButton.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(withdrawButton, HPos.CENTER);
		grid.add(withdrawButton, 1, 0);

		transferButton = new Button("Add Student Borrower");
		transferButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		transferButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("AddStudentBorrower", null);
			}
		});
		//transferButton.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(transferButton, HPos.CENTER);
		grid.add(transferButton,2,0);

		balanceInquiryButton = new Button("Delete Book");
		balanceInquiryButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		balanceInquiryButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("BarcodeSearchView", 2);
			}
		});
		//balanceInquiryButton.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(balanceInquiryButton, HPos.CENTER);
		grid.add(balanceInquiryButton,1,1);

		modBook = new Button("Modify Book");
		modBook.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		modBook.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("BarcodeSearchView", 1);
			}
		});
		//modBook.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(modBook, HPos.CENTER);
		grid.add(modBook,1,2);

		imposeServiceChargeButton = new Button("Delete Student");
		imposeServiceChargeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		imposeServiceChargeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("SearchStudent", 2);
			}
		});
		//imposeServiceChargeButton.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(imposeServiceChargeButton, HPos.CENTER);
		grid.add(imposeServiceChargeButton,2,1);

		modS = new Button("Modify Student");
		modS.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		modS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("SearchStudent", 1);
			}
		});
		//modS.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(modS, HPos.CENTER);
		grid.add(modS,2,2);

		delW = new Button("Delete Worker");
		delW.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		delW.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("SearchWorker", 2);
			}
		});
		//delW.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(delW, HPos.CENTER);
		grid.add(delW,0,1);

		modW = new Button("Modify Worker");
		modW.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		modW.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("SearchWorker", 1);
			}
		});
		//modW.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(modW, HPos.CENTER);
		grid.add(modW,0,2);

		checkOB = new Button("Check Out Book");
		checkOB.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		checkOB.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("CheckoutBook", null);
			}
		});

		//checkOB.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(checkOB, HPos.CENTER);
		grid.add(checkOB,1,3);

		checkIB = new Button("Check In Book");
		checkIB.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		checkIB.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("CheckInBook", null);
			}
		});
		//checkIB.setMaxWidth(Double.MAX_VALUE);
		grid.setHalignment(checkIB, HPos.CENTER);
		grid.add(checkIB,1,4);


		delCheck = new Button("Delinquency Check");
		delCheck.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		delCheck.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("DelCheck", null);
			}
		});

		grid.setHalignment(delCheck, HPos.CENTER);
		//delCheck.setMaxWidth(Double.MAX_VALUE);
		grid.add(delCheck,0,3);

		listCheckBooks = new Button("List All Checked Out Books");
		listCheckBooks.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		listCheckBooks.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("CheckedOutBooks", null);
			}
		});
		grid.setHalignment(listCheckBooks, HPos.CENTER);
		//listCheckBooks.setMaxWidth(Double.MAX_VALUE);
		grid.add(listCheckBooks,1,5);

		listAvailableBooks = new Button("List All Available Books");
		listAvailableBooks.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		listAvailableBooks.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Change This Later", null);
			}
		});
		grid.setHalignment(listAvailableBooks, HPos.CENTER);
		//listAvailableBooks.setMaxWidth(Double.MAX_VALUE);
		grid.add(listAvailableBooks,1,6);

		listStudentBooksCheckedOut = new Button("List Students with \nBook Checked Out");
		listStudentBooksCheckedOut.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		listStudentBooksCheckedOut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("StudentCheckedOutBooks", null);
			}
		});
		grid.setHalignment(listStudentBooksCheckedOut, HPos.CENTER);
		grid.add(listStudentBooksCheckedOut,2,3);

		cancelButton = new Button("Logout");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("Logout", null);
			}
		});
		grid.add(cancelButton,1,10);
		grid.setHalignment(cancelButton, HPos.CENTER);

		container.getChildren().add(grid);

		return container;
	}
	// Create the navigation buttons
	//-------------------------------------------------------------


	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{

	}


	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if (key.equals("TransactionError"))
		{
			// display the passed text
			displayErrorMessage((String)value);
		}
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
}


