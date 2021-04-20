package model;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import impresario.IModel;
import impresario.IView;

import java.util.Properties;
import java.sql.SQLException;
import java.util.*;

public class Worker extends EntityBase {
    private static final String myTableName = "Worker";
    protected Properties dependencies;
    private String updateStatusMessage = "Table was updated successfully";
    private boolean exists = true;

    public Worker(String bannerId, String pass)
            throws InvalidPrimaryKeyException, PasswordMismatchException {
        super(myTableName);

        String idToQuery = bannerId;

        String query = "SELECT * FROM " + myTableName + " WHERE (bannerId = " + idToQuery + ")";

        Vector allDataRetrieved =  getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved.isEmpty() == false)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple workers matching user bannerId : "
                        + idToQuery + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedCustomerData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedCustomerData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedCustomerData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no account found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No worker matching bannerId : "
                    + idToQuery + " found.");
        }

        String password = pass;

        String accountPassword = persistentState.getProperty("password");

        if (accountPassword != null)
        {
            boolean passwordCheck = accountPassword.equals(password);
            if (passwordCheck == false)
            {
                throw new PasswordMismatchException("Password mismatch");
            }

        }
        else
        {
            throw new PasswordMismatchException("Password missing for account");
        }
    }

    public Worker(String bannerId) throws InvalidPrimaryKeyException {
        super(myTableName);
        this.setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (bannerId = " + bannerId + ")";
        Vector allDataFromDB = this.getSelectQueryResult(query);
        if (allDataFromDB.isEmpty() == false) {
            int dataLen = allDataFromDB.size();
            if (dataLen != 1) {
                throw new InvalidPrimaryKeyException("Multiple Workers matching id : " + bannerId + " found.");
            } else {
                Properties workerIdData = (Properties)allDataFromDB.elementAt(0);
                this.persistentState = new Properties();
                Enumeration workerKeys = workerIdData.propertyNames();

                while(workerKeys.hasMoreElements()) {
                    String nextKey = (String)workerKeys.nextElement();
                    String nextValue = workerIdData.getProperty(nextKey);
                    if (nextValue != null) {
                        this.persistentState.setProperty(nextKey, nextValue);
                    }
                }
                exists = true;

            }
        } else {
            throw new InvalidPrimaryKeyException("No Worker matching id : " + bannerId + " found.");
        }

    }

    public Worker(Properties props) {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
        exists = false;

    }

    //----------------------------------------------------------


    public Worker() {
        exists = false;
    }

    public void setExistsTrue(boolean x){
        exists = true;
    }

    private void setDependencies(){
        this.dependencies = new Properties();
        this.myRegistry.setDependencies(this.dependencies);
    }

    @Override
    public Object getState(String key) {
        return persistentState.getProperty(key);
    }

    @Override
    public void stateChangeRequest(String key, Object value) {

    }

    public String toString()
    {
        return "StudentBorrower: ID: " + getState("bannerId") + " name: " + getState("name") + " email: " + getState("email");
    }

    public static int compare(Worker a, Worker b) {
        String ba = (String)a.getState("firstName");
        String bb = (String)b.getState("firstName");
        return ba.compareTo(bb);
    }

    //-----------------------------------------------------------------------------------
    public void update()
    {
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase()
    {
        try
        {
            if (exists == true)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("bannerId", persistentState.getProperty("bannerId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "BannerId data for worker number : " + persistentState.getProperty("bannerId") + " updated successfully in database!";
            }
            else
            {
                insertPersistentState(mySchema, persistentState);
                updateStatusMessage = "Worker data for new Worker : " +  persistentState.getProperty("bannerId")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error: " + ex.toString());
            updateStatusMessage = "Error in installing Patron data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    @Override
    //------------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {

        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("bannerId"));
        v.addElement(persistentState.getProperty("password"));
        v.addElement(persistentState.getProperty("firstName"));
        v.addElement(persistentState.getProperty("lastName"));
        v.addElement(persistentState.getProperty("contactPhone"));
        v.addElement(persistentState.getProperty("email"));
        v.addElement(persistentState.getProperty("credentials"));
        v.addElement(persistentState.getProperty("dateOfLatestCredentialsStatus"));
        v.addElement(persistentState.getProperty("dateOfHire"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

}