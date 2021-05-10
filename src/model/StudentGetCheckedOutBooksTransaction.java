package model;

import event.Event;
import exception.InvalidPrimaryKeyException;
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

public class StudentGetCheckedOutBooksTransaction implements IView, IModel, ISlideShow {

    private ModelRegistry myRegistry;
    private Hashtable<String, Scene> myViews;
    private Stage myStage;
    private Properties dependencies;
    private StudentBorrowerCollection sc;
    private Rental r;
    private String id = "";
    private String bCode = "";
    private String lName = " ";
    private int check;


    protected StudentGetCheckedOutBooksTransaction(){
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();
        myRegistry = new ModelRegistry("StudentsCheckedOutBooks");

        setDependencies();
    }


    @Override
    public void updateState(String key, Object value) {

    }

    @Override
    public Object getState(String key) {
        if (key.equals("StudentBorrowerList") == true)
        {
            return sc;
        }
        else if (key.equals("rental") == true)
        {
            return r;
        }
        else
            return null;
    }

    @Override
    public void subscribe(String key, IView subscriber) {
        myRegistry.subscribe(key, subscriber);
    }

    @Override
    public void unSubscribe(String key, IView subscriber) {
        myRegistry.unSubscribe(key, subscriber);
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        if(key.equals("doYourJob")) {
            int check = getStudentsFromRentals();
            if (check == 1) {
                databaseDelCheckError();
            } else {
                createAndShowStudentCollectionView();
            }
        }
        else

            myRegistry.updateSubscribers(key, this);
    }

    private int getStudentsFromRentals() {
        int run;
        RentalCollection r = new RentalCollection();
        r.getNonCheckedOutRentals();
        Vector<Rental> check = (Vector)r.getState("Rentals");
        if(check.isEmpty() == true)
        {
            run = 1;
        }
        else
            run = 0;
        sc = new StudentBorrowerCollection();
        Vector<Rental> col = (Vector)r.getState("Rentals");
        for (int i = 0; i < col.size(); i++){
            try {
                System.out.println("here " + i);
                //StudentBorrower s = new StudentBorrower((String)col.elementAt(i).getState("borrowerId"));
                //sc.addStudent(s);

                StudentBorrower s = new StudentBorrower((String)col.elementAt(i).getState("borrowerId"));

                if (i == 0)
                    sc.addStudent(s);

                else{
                    Vector<StudentBorrower> v1 = ((Vector)sc.getState("StudentBorrowers"));
                    for (int y = 0; y < v1.size(); y++){
                        StudentBorrower ccs = (StudentBorrower)v1.elementAt(y);
                        String banId = (String)s.getState("bannerId");
                        String nameCheck = (String)ccs.getState("bannerId");
                        if (banId.equals(nameCheck)) {
                            System.out.println("Duplicate Student Removed. Multiple Books Checked out by the same person.");
                        }else{
                            sc.addStudent(s);
                        }

                    }
                }
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
        }
        return run;
    }

    private int runDelinquency() {
        int run;
        RentalCollection r = new RentalCollection();
        r.getDelinquencyCheck();
        Vector<Rental> check = (Vector)r.getState("Rentals");
        if(check.isEmpty() == true)
        {
            run = 1;
        }
        else
            run = 0;
        Vector<Rental> col = (Vector)r.getState("Rentals");
        for (int i = 0; i < col.size(); i++){
            try {
                StudentBorrower s = new StudentBorrower((String)col.elementAt(i).getState("borrowerId"));
                s.stateChangeRequest("borrowerStatus", "Delinquent");
                s.update();

            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
        }
        return run;
    }

    private void errorBookIserted() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Book is unavailable to rent at the moment!");
        alert.setContentText("Please checkout a different book.");

        alert.showAndWait();
    }

    private void createAndShowBookCollectionView()
    {
        Scene currentScene = null;

        // create our initial view
        View newView = ViewFactory.createView("BookCollectionView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    private void createAndShowStudentCollectionView()
    {
        Scene currentScene = null;

        // create our initial view
        View newView = ViewFactory.createView("StudentCollectionView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    private void createAndShowBarcodeView()
    {
        Scene currentScene = null;

        // create our initial view
        View newView = ViewFactory.createView("BarcodeSearchView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    private void createAndShowRentBook()
    {
        Scene currentScene = null;

        // create our initial view
        View newView = ViewFactory.createView("RentBook", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    public void swapToView(Scene newScene)
    {


        if (newScene == null)
        {
            System.out.println("Teller.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();


        //Place in center
        WindowPosition.placeCenter(myStage);

    }

    private void setDependencies(){
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }

    @Override
    public void swapToView(IView viewName) {

    }

    public void databaseUpdated(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Database");
        alert.setHeaderText("Book Check Out Successful ");

        alert.showAndWait();
    }

    public void databaseError(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an error accessing the database.");
        alert.setContentText("Please make sure everything is filled out correctly and try again.");

        alert.showAndWait();
    }

    public void databaseDelCheckError(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue comleting your request.");
        alert.setContentText("There are no students with books checked out currently.");

        alert.showAndWait();
    }
}

