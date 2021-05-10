package model;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;
public class BookCollection   extends EntityBase implements IView
{
    private static final String myTableName = "Book";

    private Vector<Book> books;
    // GUI Components

    // constructor for this class
    //----------------------------------------------------------

    public BookCollection(){
        super(myTableName);
        books = new Vector<Book>();
    }

    public BookCollection( Book book) throws
            Exception
    {
        super(myTableName);

        if (book == null)
        {
            new Event(Event.getLeafLevelClassName(this), "<init>",
                    "Missing book information", Event.FATAL);
            throw new Exception
                    ("UNEXPECTED ERROR: BookCollection.<init>: book information is null");
        }

        String bookId = (String)book.getState("bannerId");

        if (bookId == null)
        {
            new Event(Event.getLeafLevelClassName(this), "<init>",
                    "Data corrupted: Book has no id in database", Event.FATAL);
            throw new Exception
                    ("UNEXPECTED ERROR: BookCollection.<init>: Data corrupted: Worker has no id in repository");
        }

        String query = "SELECT * FROM " + myTableName + " WHERE (bannerId = " + bookId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            books = new Vector<Book>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

                Book book1 = new Book(nextBookData);

                if (book1 != null)
                {
                    addBook(book1);
                }
            }

        }
        else
        {
            throw new InvalidPrimaryKeyException("No bannerId for book : "
                    + bookId + ". Title : " + book.getState("title"));
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
            books = new Vector<Book>();

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);
                System.out.println(nextPatronData);

                Book book = new Book(nextPatronData);

                if (book != null)
                {
                    addBook(book);
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
    private void addBook(Book a)
    {
        //accounts.add(a);
        int index = findIndexToAdd(a);
        books.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    protected void insertBook(Book a)
    {
        //accounts.add(a);
        int index = findIndexToAdd(a);
        books.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Book a)
    {
        //users.add(u);
        int low=0;
        int high = books.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Book midSession = books.elementAt(middle);

            int result = Book.compare(a,midSession);

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
        if (key.equals("Books")) {
            return books;
        }
        else
        if (key.equals("BookList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
    public Book retrieve(String accountNumber)
    {
        Book retValue = null;
        for (int cnt = 0; cnt < books.size(); cnt++)
        {
            Book nextBook= books.elementAt(cnt);
            String nextBookID = (String)nextBook.getState("barcode");
            if (nextBookID.equals(accountNumber) == true)
            {
                retValue = nextBook;
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
