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
import model.Rental;
import model.StudentBorrower;
import model.Worker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        Text titleText = new Text(" Rental Verification: ");
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
        checkInWorkerId.setEditable(false);
        grid.add(checkInWorkerId, 1, 5);

        Text auth3 = new Text(" Check Out Date : ");
        auth3.setFont(myFont);
        auth3.setWrappingWidth(150);
        auth3.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth3, 0, 6);

        checkOutDate = new TextField();
        checkOutDate.setEditable(false);
        grid.add(checkOutDate, 1, 6);

        Text auth4 = new Text(" Check Out Worker ID : ");
        auth4.setFont(myFont);
        auth4.setWrappingWidth(150);
        auth4.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth4, 0, 7);

        checkOutWorkerId = new TextField();
        checkOutWorkerId.setEditable(false);
        grid.add(checkOutWorkerId, 1, 7);

        Text pub = new Text(" Due Date : ");
        pub.setFont(myFont);
        pub.setWrappingWidth(150);
        pub.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pub, 0, 8);

        dueDate = new TextField();
        dueDate.setEditable(false);
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

        try {

            myModel.getState("student");
            clearErrorMessage();

            String bid = bookId.getText();
            String borid = borrowerId.getText();
            String cod = checkOutDate.getText();
            String cow = checkOutWorkerId.getText();
            String dD = dueDate.getText();
            String ciw = checkInWorkerId.getText();


            Properties p2 = new Properties();

            p2.setProperty("bookId", bid);
            p2.setProperty("borrowerId", borid);
            p2.setProperty("checkOutDate", cod);
            p2.setProperty("checkOutWorkerId", cow);
            p2.setProperty("dueDate", dD);

            p2.setProperty("checkinWorkerId", ciw);


            myModel.stateChangeRequest("InsertRental", p2);
            myModel.stateChangeRequest("CancelTransaction", null);
        }
        catch (Exception x)
        {
            clearErrorMessage();
            Rental rent =(Rental) myModel.getState("rental");
            String bid = bookId.getText();
            String borid = borrowerId.getText();
            String cod = checkOutDate.getText();
            String cow = checkOutWorkerId.getText();
            String cID = checkInDate.getText();
            String dD = dueDate.getText();
            String ciw = checkInWorkerId.getText();


            Properties p2 = new Properties();

            p2.setProperty("id", (String)rent.getState("id"));
            p2.setProperty("bookId", bid);
            p2.setProperty("borrowerId", borid);
            p2.setProperty("checkinDate", cID);
            p2.setProperty("checkOutDate", cod);
            p2.setProperty("checkOutWorkerId", cow);
            p2.setProperty("dueDate", dD);

            p2.setProperty("checkinWorkerId", ciw);


            myModel.stateChangeRequest("InsertRental", p2);
            myModel.stateChangeRequest("CancelTransaction", null);

        }
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
        try {
            Book b = (Book) myModel.getState("book");
            StudentBorrower sb = (StudentBorrower) myModel.getState("student");
            Worker w = (Worker) myModel.getState("worker");
            bookId.setText((String) b.getState("barcode"));
            borrowerId.setText((String) sb.getState("bannerId"));
            checkInDate.setText("NULL");
            checkInWorkerId.setText("");
            checkOutWorkerId.setText((String) w.getState("bannerId"));

            //date object
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime ret = now.plusDays(14);

            checkOutDate.setText(dtf.format(now));
            //checkOutWorkerId.;
            dueDate.setText(dtf.format(ret));
        }
        catch (Exception z)
        {
            //date object
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();

            Worker w = (Worker)myModel.getState("worker");
            Rental r = (Rental)myModel.getState("rental");

            bookId.setText((String) r.getState("bookId"));
            borrowerId.setText((String) r.getState("borrowerId"));
            checkInDate.setText(dtf.format(now));
            checkInWorkerId.setText((String) w.getState("bannerId"));
            checkOutWorkerId.setText((String)r.getState("checkOutWorkerId"));
            dueDate.setText((String)r.getState("dueDate"));
            checkOutDate.setText((String)r.getState("checkOutDate"));
        }

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
