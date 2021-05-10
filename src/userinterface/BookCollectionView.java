package userinterface;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.*;

import java.util.Enumeration;
import java.util.Vector;

public class BookCollectionView extends View
{
    protected TableView<BookTableModel> tableOfBooks;
    protected Button cancelButton;
    protected Button submitButton;
    protected Button update;
    protected Button delete;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public BookCollectionView(IModel book)
    {

        super(book, "BookCollectionView");
        System.out.println("-------------------------------------------------------------------------------------------");
        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {

        ObservableList<BookTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            BookCollection bookCollection = (BookCollection)myModel.getState("BookList");

            Vector entryList = (Vector)bookCollection.getState("Books");

            Enumeration entries = entryList.elements();
            int x = 0;
            while (entries.hasMoreElements() == true)
            {
                x++;
                System.out.println(x);
                Book nextBook = (Book) entries.nextElement();
                Vector<String> view = nextBook.getEntryListView();

                // add this list entry to the list
                BookTableModel nextTableRowData = new BookTableModel(view);
                tableData.add(nextTableRowData);
            }

            tableOfBooks.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Unavailable Book List ");
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

        Text prompt = new Text("LIST OF BOOKS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfBooks = new TableView<BookTableModel>();
        tableOfBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn bookBarcodeColumn = new TableColumn("barcode") ;
        bookBarcodeColumn.setMinWidth(25);
        bookBarcodeColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("barcode"));

        TableColumn titleColumn = new TableColumn("title") ;
        titleColumn.setMinWidth(25);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("title"));

        TableColumn disciplineColumn = new TableColumn("discipline") ;
        disciplineColumn.setMinWidth(100);
        disciplineColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("discipline"));

        TableColumn author1Column = new TableColumn("author1") ;
        author1Column.setMinWidth(100);
        author1Column.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("author1"));

        TableColumn author2Column = new TableColumn("author2") ;
        author2Column.setMinWidth(100);
        author2Column.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("author2"));

        TableColumn author3Column = new TableColumn("author3") ;
        author3Column.setMinWidth(100);
        author3Column.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("author3"));

        TableColumn author4Column = new TableColumn("author4") ;
        author4Column.setMinWidth(100);
        author4Column.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("author4"));

        TableColumn publisherColumn = new TableColumn("publisher") ;
        publisherColumn.setMinWidth(25);
        publisherColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("publisher"));

        TableColumn yearOfPublicationColumn = new TableColumn("yearOfPublication") ;
        yearOfPublicationColumn.setMinWidth(25);
        yearOfPublicationColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("yearOfPublication"));

        TableColumn isbnColumn = new TableColumn("isbn") ;
        isbnColumn.setMinWidth(25);
        isbnColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("isbn"));

        TableColumn qualityColumn = new TableColumn("quality") ;
        qualityColumn.setMinWidth(25);
        qualityColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("quality"));

        TableColumn suggestedPriceColumn = new TableColumn("suggestedPrice") ;
        suggestedPriceColumn.setMinWidth(25);
        suggestedPriceColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("suggestedPrice"));

        TableColumn notesColumn = new TableColumn("notes") ;
        notesColumn.setMinWidth(25);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("notes"));

        TableColumn statusColumn = new TableColumn("status") ;
        statusColumn.setMinWidth(25);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("status"));

        tableOfBooks.getColumns().addAll(bookBarcodeColumn, titleColumn, disciplineColumn, author1Column, author2Column
                , author3Column, author4Column, publisherColumn,yearOfPublicationColumn, isbnColumn, qualityColumn, suggestedPriceColumn, notesColumn, statusColumn);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfBooks);

        update = new Button("Okay");
        update.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                // do the inquiry
                processAction();
            }
        });



        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(update);
        //btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }



    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
    }

    //--------------------------------------------------------------------------
    protected void processAction()
    {
       myModel.stateChangeRequest("CancelTransaction", null);
    }

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    public void errorMessageDelInac()
    {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There was an error selecting a student!");
        alert.setContentText("The student is either a Delinquent or is set to Inactive.");

        alert.showAndWait();
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    	/*
	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processAccountSelected();
		}
	}
   */

}
