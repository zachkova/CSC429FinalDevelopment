package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;
public class WorkerCollection   extends EntityBase implements IView
{
    private static final String myTableName = "Worker";

    private Vector<Worker> workers;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------

    public WorkerCollection(){
        super(myTableName);
        workers = new Vector<Worker>();
    }

    public WorkerCollection( Worker worker) throws
            Exception
    {
        super(myTableName);

        if (worker == null)
        {
            new Event(Event.getLeafLevelClassName(this), "<init>",
                    "Missing worker information", Event.FATAL);
            throw new Exception
                    ("UNEXPECTED ERROR: WorkerCollection.<init>: worker information is null");
        }

        String workerId = (String)worker.getState("bannerId");

        if (workerId == null)
        {
            new Event(Event.getLeafLevelClassName(this), "<init>",
                    "Data corrupted: Worker has no id in database", Event.FATAL);
            throw new Exception
                    ("UNEXPECTED ERROR: WorkerCollection.<init>: Data corrupted: Worker has no id in repository");
        }

        String query = "SELECT * FROM " + myTableName + " WHERE (bannerId = " + workerId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            workers = new Vector<Worker>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextWorkerData = (Properties)allDataRetrieved.elementAt(cnt);

                Worker worker1 = new Worker(nextWorkerData);

                if (worker1 != null)
                {
                    addWorker(worker1);
                }
            }

        }
        else
        {
            throw new InvalidPrimaryKeyException("No bannerId for worker : "
                    + workerId + ". Name : " + worker.getState("firstName"));
        }

    }
    public void getFirstAndLastName(String fName, String lName) {
        String query = "SELECT * FROM " + myTableName + " WHERE firstName LIKE '%" + fName + "%' AND lastName LIKE '%" + lName + "%'";
        System.out.println(query);
        try {
            queryer(query);
        } catch (Exception x) {
            System.out.println("Error: " + x);
        }
    }
    public void getBannerId(String stZip) {
        String query = "SELECT * FROM " + myTableName + " WHERE (bannerId LIKE "+ stZip + ")";
        try {
            queryer(query);
        } catch (Exception x) {
            System.out.println("Error: " + x);
        }
    }

    public void queryer(String d) throws InvalidPrimaryKeyException {
        Vector allDataRetrieved = getSelectQueryResult(d);

        if (allDataRetrieved.isEmpty() == false)
        {
            System.out.println("query is getting results");
            workers = new Vector<Worker>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);
                System.out.println(nextPatronData);

                Worker worker = new Worker(nextPatronData);

                if (worker != null)
                {
                    addWorker(worker);
                }
            }

        }
        else
        {
            System.out.println("It is not getting results");
            throw new InvalidPrimaryKeyException("No books that match criteria");
        }
    }


    //----------------------------------------------------------------------------------
    private void addWorker(Worker a)
    {
        //accounts.add(a);
        int index = findIndexToAdd(a);
        workers.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Worker a)
    {
        //users.add(u);
        int low=0;
        int high = workers.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Worker midSession = workers.elementAt(middle);

            int result = Worker.compare(a,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Workers")) {
            return workers;
        }
        else
        if (key.equals("WorkerList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Worker retrieve(String accountNumber)
    {
        Worker retValue = null;
        for (int cnt = 0; cnt < workers.size(); cnt++)
        {
            Worker nextWorker = workers.elementAt(cnt);
            String nextWorkerId = (String)nextWorker.getState("bannerId");
            if (nextWorkerId.equals(accountNumber) == true)
            {
                retValue = nextWorker;
                return retValue; // we should say 'break;' here
            }
        }

        return retValue;
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
    protected void createAndShowView()
    {

        Scene localScene = myViews.get("WorkerSelectionView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("WorkerSelectionView", this);
            localScene = new Scene(newView);
            myViews.put("WorkerSelectionView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);

    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}

