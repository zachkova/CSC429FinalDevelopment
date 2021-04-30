package userinterface;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import impresario.IModel;
import javafx.event.ActionEvent;
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

import java.util.Properties;

public class RentedBookView<pubilc> extends View{
    // GUI components
    protected TextField bookId;
    protected TextField borrowerId;
    protected TextField checkInDate;
    protected TextField checkInWorkerId;
    protected TextField checkOutDate;
    protected TextField checkOutWorkerId;
    protected TextField dueDate;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public RentedBookView(IModel book)
    {
        super(book, "RentBook");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        // myModel.subscribe("ServiceCharge", this);
        //myModel.subscribe("UpdateStatusMessage", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Rent Book ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("Rental Information");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text bcode = new Text(" Book's ID : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        bcode.setFont(myFont);
        bcode.setWrappingWidth(150);
        bcode.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bcode, 0, 1);

        bookId = new TextField();
        bookId.setEditable(false);
        grid.add(bookId, 1, 1);

        Text tit = new Text(" Borrower ID : ");
        tit.setFont(myFont);
        tit.setWrappingWidth(150);
        tit.setTextAlignment(TextAlignment.RIGHT);
        grid.add(tit, 0, 2);

        borrowerId = new TextField();
        borrowerId.setEditable(false);
        grid.add(borrowerId, 1, 2);


        Text auth1 = new Text(" Check in Date : ");
        auth1.setFont(myFont);
        auth1.setWrappingWidth(150);
        auth1.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth1, 0, 4);

        checkInDate = new TextField();
        checkInDate.setEditable(false);
        grid.add(checkInDate, 1, 4);

        Text auth2 = new Text(" Check in Worker ID : ");
        auth2.setFont(myFont);
        auth2.setWrappingWidth(150);
        auth2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth2, 0, 5);

        checkInWorkerId = new TextField();
        checkInWorkerId.setEditable(true);
        grid.add(checkInWorkerId, 1, 5);

        Text auth3 = new Text(" Check Out Date : ");
        auth3.setFont(myFont);
        auth3.setWrappingWidth(150);
        auth3.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth3, 0, 6);

        checkOutDate = new TextField();
        checkOutDate.setEditable(true);
        grid.add(checkOutDate, 1, 6);

        Text auth4 = new Text(" Check Out Worker ID : ");
        auth4.setFont(myFont);
        auth4.setWrappingWidth(150);
        auth4.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth4, 0, 7);

        checkOutWorkerId = new TextField();
        checkOutWorkerId.setEditable(true);
        grid.add(checkOutWorkerId, 1, 7);

        Text pub = new Text(" Due Date : ");
        pub.setFont(myFont);
        pub.setWrappingWidth(150);
        pub.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pub, 0, 8);

        dueDate = new TextField();
        dueDate.setEditable(true);
        grid.add(dueDate, 1, 8);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        // consider using GridPane.setHgap(10); instead of label space
        HBox buttonCont = new HBox(10);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(submitButton);
        Label space = new Label("               ");
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(space);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(cancelButton);
        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonCont);

        return vbox;
    }

    private void processAction(ActionEvent e) {

        /*
        clearErrorMessage();

        String bar = barcode.getText();
        String titl = title.getText();
        String disi = " ";
        String au1 = author1.getText();
        String au2 = author2.getText();
        String au3 = author3.getText();
        String au4 = author4.getText();
        String publi = publisher.getText();
        String yeaO = yearOfPublication.getText();
        String isb = isbn.getText();
        String condi = (String) quality.getValue();
        String sugPric = suggestedPrice.getText();
        String no = notes.getText();
        String sta = (String) status.getValue();

        Properties p2 = new Properties();

        p2.setProperty("barcode", bar);
        p2.setProperty("title", titl);
        p2.setProperty("discipline", disi);
        p2.setProperty("author1", au1);
        p2.setProperty("author2", au2);
        p2.setProperty("author3", au3);
        p2.setProperty("author4", au4);
        p2.setProperty("publisher", publi);
        p2.setProperty("yearOfPublication", yeaO);
        p2.setProperty("isbn", isb);
        p2.setProperty("quality", condi);
        p2.setProperty("suggestedPrice", sugPric);
        p2.setProperty("notes", no);
        p2.setProperty("status", sta);

        if (yeaO == null || yeaO == "" || yeaO.length() == 0 || yeaO.length() > 4 ||
                bar.length() != 6){
            databaseErrorYear();
        }else {
            myModel.stateChangeRequest("InsertBook", p2);
        }

        barcode.clear();
        title.clear();
        author1.clear();
        author2.clear();
        author3.clear();
        author4.clear();
        publisher.clear();
        yearOfPublication.clear();
        isbn.clear();
        suggestedPrice.clear();
        notes.clear();

        quality.setValue("Good");
        status.setValue("Active");
        suggestedPrice.setText("0.00");
        */
    }


    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
       /* accountNumber.setText((String)myModel.getState("AccountNumber"));
        acctType.setText((String)myModel.getState("Type"));
        balance.setText((String)myModel.getState("Balance"));
        serviceCharge.setText((String)myModel.getState("ServiceCharge"));

        */
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("PopulateAddBookMessage") == true)
        {
            displayMessage((String)value);
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
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    public void databaseUpdated(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Database");
        alert.setHeaderText(null);
        alert.setHeaderText("Book Added to Database");

        alert.showAndWait();
    }

    public void databaseErrorYear(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue adding to the database!");
        alert.setContentText("Cannot add to database. Check year/barcode.");

        alert.showAndWait();
    }
}

//---------------------------------------------------------------
//	Revision History:
//
