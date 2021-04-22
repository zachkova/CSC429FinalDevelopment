// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;
import model.StudentBorrower;

/** The class containing the Teller  for the ATM application */
//==============================================================
public class Librarian implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    private Worker workerSearch;
    private Worker worker;
    private WorkerCollection wc;

    private StudentBorrower studentSearch;
    private StudentBorrower student;
    private StudentBorrowerCollection sc;

    private int delmod = 1;
    private String fName = "";
    private String lName = "";


    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage	  	myStage;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";



    // constructor for this class
    //----------------------------------------------------------
    public Librarian()
    {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Librarian");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowLibrarianView();
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Login", "LoginError");
        dependencies.setProperty("Deposit", "TransactionError");
        dependencies.setProperty("Withdraw", "TransactionError");
        dependencies.setProperty("Transfer", "TransactionError");
        dependencies.setProperty("BalanceInquiry", "TransactionError");
        dependencies.setProperty("ImposeServiceCharge", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param	key	Name of database column (field) for which the client wants the value
     *
     * @return	Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("LoginError") == true)
        {
            return loginErrorMessage;
        }
        else
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else
        if (key.equals("delmod") == true)
        {
            return delmod;
        }
        else
        if (key.equals("WorkerList") == true)
        {
            return wc;
        }
        else
        if (key.equals("Worker") == true)
        {
            return workerSearch;
        }
        else
        if (key.equals("StudentBorrowerList") == true)
        {
            return sc;
        }
        else
        if (key.equals("Student") == true)
        {
            return studentSearch;
        }
        else
        if (key.equals("firstName") == true)
        {
            if (worker != null)
            {
                return worker.getState("firstName");
            }
            else
                return "Undefined";
        }
        else
            return "";
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);

        if (key.equals("Login") == true)
        {
            if (value != null)
            {

                loginErrorMessage = "";

                boolean flag = loginWorker((Properties)value);
                if (flag == true)
                {
                    createAndShowTransactionChoiceView();
                }
            }
        }
        else
        if (key.equals("SelectWorkerView") == true && value != null)
        {
            try {
                searchWorkers((Properties)value);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
        }
        else
        if (key.equals("SelectStudentView") == true && value == null)
        {
            try {
                Properties getVal = new Properties();
                getVal.setProperty("firstName", fName);
                getVal.setProperty("lastName", lName);
                searchStudents(getVal);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
        }
        else
        if (key.equals("SelectStudentView") == true && value != null)
        {
            try {
                searchStudents((Properties)value);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
        }
        else
        if (key.equals("StudentSelected") == true && delmod == 1)
        {
            try {
                getStudent((String)value);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
            createAndShowModifyStudentView();
        }
        else
        if (key.equals("SelectWorkerView") == true && value == null)
        {
            try {
                Properties getVal = new Properties();
                getVal.setProperty("firstName", fName);
                getVal.setProperty("lastName", lName);
                searchWorkers(getVal);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
        }
        else
        if (key.equals("AddWorker") == true)
        {
            createAndShowAddWorkerView();
        }
        else
        if (key.equals("SearchWorker") == true)
        {
            delmod = (int)value;
            createAndShowWorkerBannerIdView();
        }
        else
        if (key.equals("WorkerSelected") == true && delmod == 1)
        {
            try {
                getWorker((String)value);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
            createAndShowModifyWorkerView();
        }
        else
        if (key.equals("WorkerSelected") == true && delmod == 0)
        {
            try {
                getWorker((String)value);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
            createAndShowDeleteWorkerVerificationView();
        }
        if (key.equals("insertWorkerModification") == true)
        {
            try {
                insertWorkerModification((Properties)value);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
        }
        if (key.equals("insertStudentModification") == true)
        {
            try {
                insertStudentModification((Properties)value);
            } catch (InvalidPrimaryKeyException e) {
                e.printStackTrace();
            }
        }
        else
        if (key.equals("insertWorker") == true) {
            try {
                insertWorker((Properties) value);
                    if(((Properties) value).getProperty("bannerId").equals("")){
                        databaseError();
                }else
                databaseErrorDuplicate();
            } catch (InvalidPrimaryKeyException e) {
                Worker insertedWorker = new Worker((Properties) value);
                System.out.println("Hunter Thomas here is the prob");
                insertedWorker.update();
                databaseUpdated();
            }
        }
        else
        if (key.equals("AddBook") == true)
        {
            createAndShowAddBookView();
        }
        else
        if (key.equals("AddStudentBorrower") == true)
        {
            createAndShowAddStudentBorrowerView();
        }
        else

        if (key.equals("InsertBook") == true)
        {
            try {
                insertBook((Properties)value);
            } catch (InvalidPrimaryKeyException e) {
                Book insertedBook = new Book((Properties)value);
                insertedBook.update();
            }
        }
        else
        if (key.equals("AddStudent") == true)
        {
        try {
            insertStudent((Properties)value);
        } catch (InvalidPrimaryKeyException e) {
            StudentBorrower insertedStudent = new StudentBorrower((Properties)value);
            insertedStudent.update();
        }

         }
        else
        if (key.equals("CancelTransaction") == true)
        {
            createAndShowTransactionChoiceView();
        }
        else
        if (key.equals("SearchStudent") == true)
        {
            delmod = (int)value;
            createAndShowStudentSearchNameView();
        }
        else
        if (key.equals("Logout") == true)
        {
            worker = null;
            myViews.remove("TransactionChoiceView");

            createAndShowTellerView();
        }

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    /**
     * Login AccountHolder corresponding to user name and password.
     */
    //----------------------------------------------------------
    public boolean loginWorker(Properties props)
    {
        try
        {

            String username = props.getProperty("bannerId");
            String password = props.getProperty("password");

            worker = new Worker(username, password);

            System.out.println("Account Holder: " + worker.getState("firstName") + " successfully logged in");
            return true;
        }
        catch (InvalidPrimaryKeyException ex)
        {

            loginErrorMessage = "ERROR: " + ex.getMessage();
            System.out.println(loginErrorMessage);
            return false;
        }
        catch (PasswordMismatchException exec)
        {
            loginErrorMessage = "ERROR: " + exec.getMessage();
            System.out.println(loginErrorMessage);
            return false;
        }
    }


    /**
     * Create a Transaction depending on the Transaction type (deposit,
     * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
     * create.
     */
    //----------------------------------------------------------


    //----------------------------------------------------------
    private void createAndShowTransactionChoiceView()
    {
        Scene currentScene = (Scene)myViews.get("TransactionChoiceView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("TransactionChoiceView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("TransactionChoiceView", currentScene);
        }


        // make the view visible by installing it into the frame
        swapToView(currentScene);

    }

    private void createAndShowAddWorkerView()
    {
        Scene currentScene = (Scene)myViews.get("AddWorkerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("AddWorkerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("AddWorkerView", currentScene);
        }


        // make the view visible by installing it into the frame
        swapToView(currentScene);

    }

    private void createAndShowAddBookView()
    {
        Scene currentScene = (Scene)myViews.get("AddBookView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("AddBookView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("AddBookView", currentScene);
        }


        // make the view visible by installing it into the frame
        swapToView(currentScene);

    }

    private void createAndShowAddStudentBorrowerView()
    {
        Scene currentScene = (Scene)myViews.get("AddStudentBorrowerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("AddStudentBorrowerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("AddStudentBorrowerView", currentScene);
        }
        // make the view visible by installing it into the frame
        swapToView(currentScene);

    }

    private void createAndShowLibrarianView(){
        Scene currentScene = (Scene)myViews.get("LibrarianView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowDeleteWorkerView()
    {
        Scene currentScene = (Scene)myViews.get("DeleteWorkerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("DeleteWorkerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("DeleteWorkerView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowDeleteWorkerVerificationView()
    {
        Scene currentScene = (Scene)myViews.get("DeleteWorkerVerificationView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("DeleteWorkerVerificationView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("DeleteWorkerVerificationView", currentScene);
        }

        swapToView(currentScene);

    }

    private void getWorker(String id)throws InvalidPrimaryKeyException {
        workerSearch = new Worker(id);
    }

    private void getStudent(String id)throws InvalidPrimaryKeyException {
        studentSearch = new StudentBorrower(id);
    }

    private void searchWorkers(Properties z) throws InvalidPrimaryKeyException {
        wc = new WorkerCollection();
        wc.getFirstAndLastName(z.getProperty("firstName"), z.getProperty("lastName"));
        createAndShowWorkerSelectionView();
        fName = z.getProperty("firstName");
        lName = z.getProperty("lastName");

    }

    private void searchStudents(Properties z) throws InvalidPrimaryKeyException {
        sc = new StudentBorrowerCollection();
        sc.getFirstAndLastName(z.getProperty("firstName"), z.getProperty("lastName"));
        createAndShowStudentSelectionView();
        fName = z.getProperty("firstName");
        lName = z.getProperty("lastName");

    }

    private void createAndShowWorkerSelectionView()
    {
        Scene currentScene = (Scene)myViews.get("WorkerSelectionView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("WorkerSelectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("WorkerSelectionView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowStudentSelectionView()
    {
        Scene currentScene = (Scene)myViews.get("StudentSelectionView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("StudentSelectionView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("StudentSelectionView", currentScene);
        }

        swapToView(currentScene);

    }



    private void insertWorkerModification(Properties p) throws InvalidPrimaryKeyException {
        Worker modWorker = new Worker(p);
        modWorker.setExistsTrue(true);
        modWorker.update();
    }

    private void insertStudentModification(Properties p) throws InvalidPrimaryKeyException {
        StudentBorrower modStudent = new StudentBorrower(p);
        modStudent.setExistsTrue(true);
        modStudent.update();
    }

    private void createAndShowModifyWorkerView()
    {
        Scene currentScene = (Scene)myViews.get("ModifyWorkerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("ModifyWorkerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("ModifyWorker", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowModifyStudentView()
    {
        Scene currentScene = (Scene)myViews.get("StudentModificationView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("StudentModificationView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("StudentModificationView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowWorkerBannerIdView()
    {
        Scene currentScene = (Scene)myViews.get("WorkerBannerIdView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("WorkerBannerIdView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("WorkerBannerIdView", currentScene);
        }


        // make the view visible by installing it into the frame
        swapToView(currentScene);

    }


    //------------------------------------------------------------
    private void createAndShowTellerView()
    {
        Scene currentScene = (Scene)myViews.get("TellerView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("TellerView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("TellerView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowStudentSearchNameView()
    {
        Scene currentScene = null;
            // create our initial view
        View newView = ViewFactory.createView("StudentSearchNameView", this); // USE VIEW FACTORY
        currentScene = new Scene(newView);

        swapToView(currentScene);

    }


    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }



    //-----------------------------------------------------------------------------
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
    public void insertWorker(Properties p) throws InvalidPrimaryKeyException {
        String bannerId = p.getProperty("bannerId");
        System.out.println(bannerId);
        Worker n = new Worker(bannerId);
    }

    public void insertBook(Properties p) throws InvalidPrimaryKeyException {
        String barcode = p.getProperty("barcode");
        Book b = new Book(barcode);
    }

    public void insertStudent(Properties p) throws InvalidPrimaryKeyException {
        String bannerId = p.getProperty("bannerId");
        StudentBorrower b = new StudentBorrower(bannerId);
    }

    public void databaseErrorDuplicate(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there is a duplicate.");
        alert.setContentText("Please try again with a different bannerId.");

        alert.showAndWait();
    }

    public void databaseError(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an error adding to the database.");
        alert.setContentText("Please try again.");

        alert.showAndWait();
    }

    public void databaseUpdated(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Database");
        alert.setHeaderText(null);
        alert.setHeaderText("Added to Database");

        alert.showAndWait();
    }
}