
// specify the package
package userinterface;

// system imports
import java.text.NumberFormat;
import java.util.Properties;

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
import model.Worker;

/** The class containing the Teller View  for the ATM application */
//==============================================================
public class DeleteWorkerVerificationView extends View
{

    // GUI stuff
    private TextField userid;
    private PasswordField password;
    private Button submitButton;
    private Button back;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public DeleteWorkerVerificationView( IModel librarian)
    {

        super(librarian, "Librarian");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        populateFields();

    }

    // Create the label (Text) for the title of the screen
    //-------------------------------------------------------------
    private Node createTitle()
    {

        Text titleText = new Text("       Verify Delete          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);


        return titleText;
    }

    // Create the main form contents
    //-------------------------------------------------------------
    private GridPane createFormContents()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                int delmod = (int) myModel.getState("delmod");
                myModel.stateChangeRequest("SelectWorkerView", null);
            }
        });

        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.BOTTOM_LEFT);
        btnContainer.getChildren().add(submitButton);
        grid.add(btnContainer, 0, 3);

        HBox btnContainer2 = new HBox(10);
        btnContainer2.setAlignment(Pos.BOTTOM_LEFT);
        btnContainer2.getChildren().add(back);
        grid.add(btnContainer2, 1, 3);

        return grid;
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

    // This method processes events generated from our GUI components.
    // Make the ActionListeners delegate to this method
    //-------------------------------------------------------------
    public void processAction(Event evt) {
        Worker wc = (Worker)myModel.getState("Worker");
        String ban = (String)wc.getState("bannerId");
        String pass = (String)wc.getState("password");
        String fName = (String)wc.getState("firstName");
        String lName = (String)wc.getState("lastName");
        String pho = (String)wc.getState("contactPhone");
        String eml = (String)wc.getState("email");
        String credentials = (String)wc.getState("credentials");
        String latestCred = (String)wc.getState("dateOfLatestCredentialsStatus");
        String dateHire =(String)wc.getState("dateOfHire");
        String stat = "Inactive";

        Properties p1 = new Properties();
        p1.setProperty("bannerId", ban);
        p1.setProperty("password", pass);
        p1.setProperty("firstName", fName);
        p1.setProperty("lastName", lName);
        p1.setProperty("contactPhone", pho);
        p1.setProperty("email", eml);
        p1.setProperty("credentials", credentials);
        p1.setProperty("dateOfLatestCredentialsStatus", latestCred);
        p1.setProperty("dateOfHire", dateHire);
        p1.setProperty("status", stat);

        myModel.stateChangeRequest("insertWorkerModification", p1);
    }
    /**
     * Process userid and pwd supplied when Submit button is hit.
     * Action is to pass this info on to the teller object
     */
    //----------------------------------------------------------




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

    @Override
    public void updateState(String key, Object value) {

    }
}

