// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
	
		return container;
	}


	// Create the navigation buttons
	//-------------------------------------------------------------
	private VBox createFormContents()
	{

		VBox container = new VBox(15);

		// create the buttons, listen for events, add them to the container
		HBox dCont = new HBox(10);
		dCont.setAlignment(Pos.CENTER);
		depositButton = new Button("Add Worker");
		depositButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		depositButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddWorker", null);
            	     }
        	});
		dCont.getChildren().add(depositButton);

		container.getChildren().add(dCont);

		HBox wCont = new HBox(10);
		wCont.setAlignment(Pos.CENTER);
		withdrawButton = new Button("Add Book");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddBook", null);
            	     }
        	});
		wCont.getChildren().add(withdrawButton);

		container.getChildren().add(wCont);

		HBox tCont = new HBox(10);
		tCont.setAlignment(Pos.CENTER);
		transferButton = new Button("Add Student Borrower");
		transferButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		transferButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddStudentBorrower", null);
            	     }
        	});
		tCont.getChildren().add(transferButton);
		container.getChildren().add(tCont);

		HBox bitCont = new HBox(10);
		bitCont.setAlignment(Pos.CENTER);
		balanceInquiryButton = new Button("Delete Book");
		balanceInquiryButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		balanceInquiryButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("modifyDelete", null);
			}
		});
		bitCont.getChildren().add(balanceInquiryButton);
		container.getChildren().add(bitCont);

		HBox biCont = new HBox(10);
		biCont.setAlignment(Pos.CENTER);
		modBook = new Button("Modify Book");
		modBook.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		modBook.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("modifyDelete", null);
       		     }
        	});
		biCont.getChildren().add(modBook);
		container.getChildren().add(biCont);

		HBox iscCont = new HBox(10);
		iscCont.setAlignment(Pos.CENTER);
		imposeServiceChargeButton = new Button("Delete Student");
		imposeServiceChargeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		imposeServiceChargeButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	 myModel.stateChangeRequest("SearchStudent", 0);
            	     }
        	});
		iscCont.getChildren().add(imposeServiceChargeButton);
		container.getChildren().add(iscCont);

		HBox iscConta = new HBox(10);
		iscConta.setAlignment(Pos.CENTER);
		modS = new Button("Modify Student");
		modS.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		modS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("SearchStudent", 1);
			}
		});
		iscConta.getChildren().add(modS);
		container.getChildren().add(iscConta);

		HBox iscContasw = new HBox(10);
		iscContasw.setAlignment(Pos.CENTER);
		delW = new Button("Delete Worker");
		delW.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		delW.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("SearchWorker", 0);
			}
		});
		iscContasw.getChildren().add(delW);
		container.getChildren().add(iscContasw);

		HBox iscContas = new HBox(10);
		iscContas.setAlignment(Pos.CENTER);
		modW = new Button("Modify Worker");
		modW.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		modW.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("SearchWorker", 1);
			}
		});
		iscContas.getChildren().add(modW);
		container.getChildren().add(iscContas);

		HBox jok = new HBox(10);
		jok.setAlignment(Pos.CENTER);
		checkOB = new Button("Check Out Book");
		checkOB.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		checkOB.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("modifyStudent", null);
			}
		});
		jok.getChildren().add(checkOB);
		container.getChildren().add(jok);

		HBox jik = new HBox(10);
		jik.setAlignment(Pos.CENTER);
		checkIB = new Button("Check In Book");
		checkIB.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		checkIB.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("modifyStudent", null);
			}
		});
		jik.getChildren().add(checkIB);
		container.getChildren().add(jik);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new Button("Logout");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("Logout", null);    
            	     }
        	});
		doneCont.getChildren().add(cancelButton);

		container.getChildren().add(doneCont);

		return container;
	}

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
		if (key.equals("TransactionError") == true)
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


