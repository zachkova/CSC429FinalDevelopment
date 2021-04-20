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
import model.Worker;
import model.WorkerCollection;

import java.util.Enumeration;
import java.util.Vector;

public class WorkerSelectionView extends View
{
    protected TableView<WorkerTableModel> tableOfWorkers;
    protected Button cancelButton;
    protected Button submitButton;
    protected Button update;
    protected Button delete;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public WorkerSelectionView(IModel worker)
    {

        super(worker, "WorkerSelectionView");
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

        ObservableList<WorkerTableModel> tableData = FXCollections.observableArrayList();
        System.out.println("TTTTTTTTTTHHHHHHHHHOOOOOOMMMMMMMMAAAAAAAASSSSSSSSSSSS");
        try
        {
            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUNNNNNNNNNNNNNNNNNNNNNNNNTTTTTTTTTTTTTTTTTTTTTTTEEEEEEEEEEEEEEEEEEEEEEEERRRRRRRR");
            WorkerCollection workerCollection = (WorkerCollection)myModel.getState("WorkerList");

            Vector entryList = (Vector)workerCollection.getState("Workers");

            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Worker nextWorker = (Worker)entries.nextElement();
                Vector<String> view = nextWorker.getEntryListView();

                // add this list entry to the list
                WorkerTableModel nextTableRowData = new WorkerTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfWorkers.setItems(tableData);
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

        Text titleText = new Text(" Worker Selection View ");
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

        Text prompt = new Text("LIST OF WORKERS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfWorkers = new TableView<WorkerTableModel>();
        tableOfWorkers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn workerBannerIdColumn = new TableColumn("bannerId") ;
        workerBannerIdColumn.setMinWidth(25);
        workerBannerIdColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("bannerId"));

        TableColumn passwordColumn = new TableColumn("password") ;
        passwordColumn.setMinWidth(100);
        passwordColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("password"));

        TableColumn firstNameColumn = new TableColumn("firstName") ;
        firstNameColumn.setMinWidth(25);
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("firstName"));

        TableColumn lastNameColumn = new TableColumn("lastName") ;
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("lastName"));

        TableColumn contactPhoneColumn = new TableColumn("contactPhone") ;
        contactPhoneColumn.setMinWidth(100);
        contactPhoneColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("contactPhone"));

        TableColumn emailColumn = new TableColumn("email") ;
        emailColumn.setMinWidth(25);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("email"));

        TableColumn credentialsColumn = new TableColumn("credentials") ;
        credentialsColumn.setMinWidth(25);
        credentialsColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("credentials"));

        TableColumn dOLCSColumn = new TableColumn("dateOfLatestCredentialsStatus") ;
        dOLCSColumn.setMinWidth(25);
        dOLCSColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("dateOfLatestCredentialsStatus"));

        TableColumn dOHColumn = new TableColumn("dateOfHire") ;
        dOHColumn.setMinWidth(25);
        dOHColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("dateOfHire"));

        TableColumn statusColumn = new TableColumn("status") ;
        statusColumn.setMinWidth(25);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<WorkerTableModel, String>("status"));

        tableOfWorkers.getColumns().addAll(workerBannerIdColumn, passwordColumn, firstNameColumn, lastNameColumn, contactPhoneColumn, emailColumn
                , credentialsColumn, dOLCSColumn, dOHColumn, statusColumn);
        tableOfWorkers.setOnMousePressed(new EventHandler<MouseEvent>() {
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
        scrollPane.setContent(tableOfWorkers);

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
                int delmod = (int) myModel.getState("delmod");
                myModel.stateChangeRequest("SearchWorker", delmod);
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
        WorkerTableModel selectedItem = tableOfWorkers.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedItemBannerId = selectedItem.getBannerId();

            myModel.stateChangeRequest("WorkerSelected", selectedItem.getBannerId());
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
        WorkerTableModel selectedItem = tableOfWorkers.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedBannerId = selectedItem.getBannerId();

            myModel.stateChangeRequest("WorkerSelected", selectedBannerId);
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
