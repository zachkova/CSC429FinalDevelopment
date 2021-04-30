package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;


public class StudentBorrowerTableModel{

    private final SimpleStringProperty bannerId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty contactPhone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty borrowerStatus;
    private final SimpleStringProperty dateOfLastestBorrowerStatus;
    private final SimpleStringProperty dateOfRegistration;
    private final SimpleStringProperty notes;
    private final SimpleStringProperty status;


    //----------------------------------------------------------------------------
    public StudentBorrowerTableModel(Vector<String> bookData)
    {
        bannerId =  new SimpleStringProperty(bookData.elementAt(0));
        firstName =  new SimpleStringProperty(bookData.elementAt(1));
        lastName =  new SimpleStringProperty(bookData.elementAt(2));
        contactPhone =  new SimpleStringProperty(bookData.elementAt(3));
        email =  new SimpleStringProperty(bookData.elementAt(4));
        borrowerStatus =  new SimpleStringProperty(bookData.elementAt(5));
        dateOfLastestBorrowerStatus =  new SimpleStringProperty(bookData.elementAt(6));
        dateOfRegistration =  new SimpleStringProperty(bookData.elementAt(7));
        notes =  new SimpleStringProperty(bookData.elementAt(8));
        status =  new SimpleStringProperty(bookData.elementAt(9));
    }

    //----------------------------------------------------------------------------
    public String getBannerId() {return bannerId.get(); }

    //----------------------------------------------------------------------------
    public void setBannerId(String number) { bannerId.set(number); }

    //----------------------------------------------------------------------------
    public String getFirstName() {
        return firstName.get();
    }

    //----------------------------------------------------------------------------
    public void setFirstName(String name) {
        firstName.set(name);
    }

    //----------------------------------------------------------------------------
    public String getLastName() {
        return lastName.get();
    }

    //----------------------------------------------------------------------------
    public void setLastName(String name)
    {
        lastName.set(name);
    }

    //----------------------------------------------------------------------------
    public String getContactPhone()
    {
        return contactPhone.get();
    }

    //----------------------------------------------------------------------------
    public void setContactPhone(String phone)
    {
        contactPhone.set(phone);
    }

    //----------------------------------------------------------------------------
    public String getEmail()
    {
        return email.get();
    }

    //----------------------------------------------------------------------------
    public void setEmail(String em)
    {
        email.set(em);
    }
    //----------------------------------------------------------------------------
    public String getBorrowerStatus()
    {
        return borrowerStatus.get();
    }

    //----------------------------------------------------------------------------
    public void setBorrowerStatus(String cred)
    {
        borrowerStatus.set(cred);
    }

    //----------------------------------------------------------------------------
    public String getDateOfLatestBorrowerStatus()
    {
        return dateOfLastestBorrowerStatus.get();
    }

    //----------------------------------------------------------------------------
    public void setDateOfLatestBorrowerStatus(String dob)
    {
        dateOfLastestBorrowerStatus.set(dob);
    }

    //----------------------------------------------------------------------------
    public String getDateOfRegistration() {
        return dateOfRegistration.get();
    }

    //----------------------------------------------------------------------------
    public void setDateOfRegistration(String hire)
    {
        dateOfRegistration.set(hire);
    }

    public String getNotes() {
        return notes.get();
    }

    //----------------------------------------------------------------------------
    public void setNotes(String stat)
    {
        notes.set(stat);
    }

    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String stat)
    {
        status.set(stat);
    }
}