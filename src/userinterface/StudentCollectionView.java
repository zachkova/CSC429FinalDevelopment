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

import javax.swing.table.TableModel;
import java.util.Enumeration;
import java.util.Vector;

public class StudentCollectionView extends View
{
    protected TableView<StudentBorrowerTableModel> tableOfStudents;
    protected Button cancelButton;
    protected Button submitButton;
    protected Button update;
    protected Button delete;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public StudentCollectionView(IModel student)
    {

        super(student, "StudentCollectionView");
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

        ObservableList<StudentBorrowerTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            StudentBorrowerCollection studentCollection = (StudentBorrowerCollection)myModel.getState("StudentBorrowerList");

            Vector entryList = (Vector) studentCollection.getState("StudentBorrowers");

            Enumeration entries = entryList.elements();
            int x = 0;
            while (entries.hasMoreElements() == true)
            {
                x++;
                StudentBorrower nextStudent = (StudentBorrower) entries.nextElement();
                Vector<String> view = nextStudent.getEntryListView();

                // add this list entry to the list
                StudentBorrowerTableModel nextTableRowData = new StudentBorrowerTableModel(view);
                tableData.add(nextTableRowData);
            }

            tableOfStudents.setItems(tableData);
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

        Text titleText = new Text(" Student Borrowers With Books Checked Out ");
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

        Text prompt = new Text("LIST OF SUDENTS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfStudents = new TableView<StudentBorrowerTableModel>();
        tableOfStudents.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn bookBarcodeColumn = new TableColumn("bannerId") ;
        bookBarcodeColumn.setMinWidth(25);
        bookBarcodeColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("bannerId"));

        TableColumn titleColumn = new TableColumn("firstName") ;
        titleColumn.setMinWidth(25);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("firstName"));

        TableColumn disciplineColumn = new TableColumn("lastName") ;
        disciplineColumn.setMinWidth(100);
        disciplineColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("lastName"));

        TableColumn author1Column = new TableColumn("contactPhone") ;
        author1Column.setMinWidth(100);
        author1Column.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("contactPhone"));

        TableColumn author2Column = new TableColumn("email") ;
        author2Column.setMinWidth(100);
        author2Column.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("email"));

        TableColumn author3Column = new TableColumn("borrowerStatus") ;
        author3Column.setMinWidth(100);
        author3Column.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("borrowerStatus"));

        TableColumn author4Column = new TableColumn("dateOfLatestBorrowerStatus") ;
        author4Column.setMinWidth(100);
        author4Column.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("dateOfLatestBorrowerStatus"));

        TableColumn publisherColumn = new TableColumn("dateOfRegistration") ;
        publisherColumn.setMinWidth(25);
        publisherColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("dateOfRegistration"));

        TableColumn yearOfPublicationColumn = new TableColumn("notes") ;
        yearOfPublicationColumn.setMinWidth(25);
        yearOfPublicationColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("notes"));

        TableColumn isbnColumn = new TableColumn("status") ;
        isbnColumn.setMinWidth(25);
        isbnColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("status"));

        tableOfStudents.getColumns().addAll(bookBarcodeColumn, titleColumn, disciplineColumn, author1Column, author2Column
                , author3Column, author4Column, publisherColumn,yearOfPublicationColumn, isbnColumn);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfStudents);

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

