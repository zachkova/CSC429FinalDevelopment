// specify the package
package userinterface;

// system imports
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		grid.add(depositButton, 0, 0);

		withdrawButton = new Button("Add Book");
		withdrawButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("AddBook", null);
			}
		});
		grid.add(withdrawButton, 1, 0);

		container.getChildren().add(grid);

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


