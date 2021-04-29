package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Book;
import model.Worker;

import java.util.Properties;




// specify the package


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
public class DeleteBookVerificationView  extends View{


    // GUI stuff
    private TextField userid;
    private PasswordField password;
    private Button submitButton;
    private Button back;
    private TextField barcode;
    private Text bcode;
    private TextField title;
    private Text tit;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public DeleteBookVerificationView( IModel librarian)
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

        Text prompt = new Text("Barcode: ");
        prompt.setWrappingWidth(80);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt,0,0);

        barcode = new TextField();
        barcode.setEditable(false);
        grid.add(barcode,1, 0);

        Text prompt2 = new Text("Title: ");
        prompt2.setWrappingWidth(80);
        prompt2.setTextAlignment(TextAlignment.CENTER);
        prompt2.setFill(Color.BLACK);
        grid.add(prompt2,0, 1,3,1 );

        title = new TextField();
        title.setEditable(false);
        grid.add(title, 1,1, 3,1);

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

                myModel.stateChangeRequest("BarcodeSearchView", myModel.getState("delmod"));
            }
        });

        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.BOTTOM_LEFT);
        btnContainer.getChildren().add(submitButton);
        grid.add(btnContainer, 1, 3);

        HBox btnContainer2 = new HBox(10);
        btnContainer2.setAlignment(Pos.BOTTOM_LEFT);
        btnContainer2.getChildren().add(back);
        grid.add(btnContainer2, 2, 3);

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
        Book wc = (Book)myModel.getState("Book");
        barcode.setText((String)wc.getState("barcode"));
        title.setText((String)wc.getState("title"));
    }

    // This method processes events generated from our GUI components.
    // Make the ActionListeners delegate to this method
    //-------------------------------------------------------------
    public void processAction(Event evt) {

        Book wc = (Book)myModel.getState("Book");
        String bar = (String)wc.getState("barcode");
        String tit = (String)wc.getState("title");
        String disc = (String)wc.getState("discipline");
        String a1 = (String)wc.getState("author1");
        String a2 = (String)wc.getState("author2");
        String a3 = (String)wc.getState("author3");
        String a4 = (String)wc.getState("author4");
        String pub = (String)wc.getState("publisher");
        String yop = (String)wc.getState("yearOfPublication");
        String isbn = (String)wc.getState("isbn");
        String qual = (String)wc.getState("quality");
        String sugP =(String)wc.getState("suggestedPrice");
        String stat = "Inactive";

        Properties p1 = new Properties();

        p1.setProperty("barcode", bar);
        p1.setProperty("title", tit);
        p1.setProperty("discipline", disc);
        p1.setProperty("author1", a1);
        p1.setProperty("author2", a2);
        p1.setProperty("author3", a3);
        p1.setProperty("author4", a4);
        p1.setProperty("publisher", pub);
        p1.setProperty("yearOfPublication", yop);
        p1.setProperty("isbn", isbn);
        p1.setProperty("quality", qual);
        p1.setProperty("suggestedPrice", sugP);
        p1.setProperty("status", stat);

        myModel.stateChangeRequest("insertBookModification", p1);
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

    public void databaseError(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Please make sure the barcode is 6 digits, all numbers.");
        alert.setContentText("Cannot find in database.");

        alert.showAndWait();
    }
}


