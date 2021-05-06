package model;

import exception.InvalidPrimaryKeyException;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class StudentBorrower extends EntityBase {
    private static final String myTableName = "StudentBorrower";
    protected Properties dependencies;
    private String updateStatusMessage = "Table was updated successfully";
    private boolean exists = true;

    public StudentBorrower(String bannerId) throws InvalidPrimaryKeyException {
        super(myTableName);
        this.setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (bannerId = " + bannerId + ")";
        Vector allDataFromDB = this.getSelectQueryResult(query);
        if (allDataFromDB != null) {
            int dataLen = allDataFromDB.size();
            if (dataLen != 1) {
                throw new InvalidPrimaryKeyException("Multiple StudentBorrowerIds matching id : " + bannerId + " found.");
            } else {
                Properties borrowerIdData = (Properties)allDataFromDB.elementAt(0);
                this.persistentState = new Properties();
                Enumeration borrowerKeys = borrowerIdData.propertyNames();

                while(borrowerKeys.hasMoreElements()) {
                    String nextKey = (String)borrowerKeys.nextElement();
                    String nextValue = borrowerIdData.getProperty(nextKey);
                    if (nextValue != null) {
                        this.persistentState.setProperty(nextKey, nextValue);
                    }
                }
                exists = true;
            }
        }
    }

    public void setExistsTrue()
    {
        exists = true;
    }

    public StudentBorrower(Properties props) {
        super(myTableName);
        this.setDependencies();
        this.persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();

        while(allKeys.hasMoreElements()) {
            String one = (String)allKeys.nextElement();
            String two = props.getProperty(one);
            if (two != null) {
                this.persistentState.setProperty(one, two);
            }
        }
        exists = false;
    }

    public StudentBorrower() { exists = false;
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
        persistentState.setProperty(key, (String)value);

    }

    public String toString()
    {
        return "StudentBorrower: ID: " + getState("bannerId") + " name: " + getState("name") + " email: " + getState("email");
    }

    public static int compare(StudentBorrower a, StudentBorrower b) {
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
                updateStatusMessage = "BannerId data for borrower number : " + persistentState.getProperty("bannerId") + " updated successfully in database!";
            }
            else
            {
                Integer patronId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bannerId", "" + patronId);
                updateStatusMessage = "StudentBorrower data for new StudentBorrower : " +  persistentState.getProperty("bannerId")
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
        v.addElement(persistentState.getProperty("firstName"));
        v.addElement(persistentState.getProperty("lastName"));
        v.addElement(persistentState.getProperty("contactPhone"));
        v.addElement(persistentState.getProperty("email"));
        v.addElement(persistentState.getProperty("borrowerStatus"));
        v.addElement(persistentState.getProperty("dateOfLatestBorrowerStatus"));
        v.addElement(persistentState.getProperty("dateOfRegistration"));
        v.addElement(persistentState.getProperty("notes"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

}
