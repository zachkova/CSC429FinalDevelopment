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
import model.StudentBorrower;
import model.StudentBorrowerCollection;
import model.Worker;
import model.WorkerCollection;

import java.util.Enumeration;
import java.util.Vector;

public class StudentSelectionView extends View
{
    protected TableView<StudentBorrowerTableModel> tableOfStudents;
    protected Button cancelButton;
    protected Button submitButton;
    protected Button update;
    protected Button delete;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public StudentSelectionView(IModel student)
    {

        super(student, "StudentSelectionView");
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

            Vector entryList = (Vector)studentCollection.getState("StudentBorrowers");

            Enumeration entries = entryList.elements();
            int x = 0;
            while (entries.hasMoreElements() == true)
            {
                x++;
                System.out.println(x);
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

        Text titleText = new Text(" Student Borrower Selection View ");
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

        Text prompt = new Text("LIST OF STUDENTS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfStudents = new TableView<StudentBorrowerTableModel>();
        tableOfStudents.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn studentBannerIdColumn = new TableColumn("bannerId") ;
        studentBannerIdColumn.setMinWidth(25);
        studentBannerIdColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("bannerId"));

        TableColumn firstNameColumn = new TableColumn("firstName") ;
        firstNameColumn.setMinWidth(25);
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("firstName"));

        TableColumn lastNameColumn = new TableColumn("lastName") ;
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("lastName"));

        TableColumn contactPhoneColumn = new TableColumn("contactPhone") ;
        contactPhoneColumn.setMinWidth(100);
        contactPhoneColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("contactPhone"));

        TableColumn emailColumn = new TableColumn("email") ;
        emailColumn.setMinWidth(25);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("email"));

        TableColumn borStatColumn = new TableColumn("borrowerStatus") ;
        borStatColumn.setMinWidth(25);
        borStatColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("borrowerStatus"));

        TableColumn dOLCSColumn = new TableColumn("dateOfLatestBorrowerStatus") ;
        dOLCSColumn.setMinWidth(25);
        dOLCSColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("dateOfLatestBorrowerStatus"));

        TableColumn dOHColumn = new TableColumn("dateOfRegistration") ;
        dOHColumn.setMinWidth(25);
        dOHColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("dateOfRegistration"));

        TableColumn notesColumn = new TableColumn("notes") ;
        notesColumn.setMinWidth(25);
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("notes"));

        TableColumn statusColumn = new TableColumn("status") ;
        statusColumn.setMinWidth(25);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<StudentBorrowerTableModel, String>("status"));

        tableOfStudents.getColumns().addAll(studentBannerIdColumn, firstNameColumn, lastNameColumn, contactPhoneColumn, emailColumn
                , borStatColumn, dOLCSColumn, dOHColumn, notesColumn, statusColumn);

        tableOfStudents.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    processWorkerSelected();
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfStudents);

        update = new Button("Submit");
        update.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                // do the inquiry
                processPatronSelected();
            }
        });


        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                try {
                    int delmod = (int) myModel.getState("delmod");
                    myModel.stateChangeRequest("SearchStudent", delmod);
                }
                catch(Exception x){
                    myModel.stateChangeRequest("doYourJob", null);
                }
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(update);
        btnContainer.getChildren().add(cancelButton);

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
    protected void processPatronSelected()
    {
        StudentBorrowerTableModel selectedItem = tableOfStudents.getSelectionModel().getSelectedItem();
        try {
            int delmod = (Integer)myModel.getState("delmod");
            if (selectedItem != null) {
                String selectedItemBannerId = selectedItem.getBannerId();

                myModel.stateChangeRequest("StudentSelected", selectedItemBannerId);
            }
        }
        catch (Exception x)
        {
            if (selectedItem != null){
                String selectedItemBannerId = selectedItem.getBannerId();
                if (selectedItem.getBorrowerStatus().equals("Delinquent") || selectedItem.getStatus().equals("Inactive")){
                    errorMessageDelInac();
                }
                else
                    myModel.stateChangeRequest("StudentSelected",selectedItemBannerId);
            }
        }
    }

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    protected void processWorkerSelected()
    {
        StudentBorrowerTableModel selectedItem = tableOfStudents.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedBannerId = selectedItem.getBannerId();

            myModel.stateChangeRequest("StudentSelected", selectedBannerId);
        }
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
