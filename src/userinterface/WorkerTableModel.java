package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;


public class WorkerTableModel{

    private final SimpleStringProperty bannerId;
    private final SimpleStringProperty password;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty contactPhone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty credentials;
    private final SimpleStringProperty dateOfLastestCredentialsStatus;
    private final SimpleStringProperty dateOfHire;
    private final SimpleStringProperty status;


    //----------------------------------------------------------------------------
    public WorkerTableModel(Vector<String> bookData)
    {
        bannerId =  new SimpleStringProperty(bookData.elementAt(0));
        password =  new SimpleStringProperty(bookData.elementAt(1));
        firstName =  new SimpleStringProperty(bookData.elementAt(2));
        lastName =  new SimpleStringProperty(bookData.elementAt(3));
        contactPhone =  new SimpleStringProperty(bookData.elementAt(4));
        email =  new SimpleStringProperty(bookData.elementAt(5));
        credentials =  new SimpleStringProperty(bookData.elementAt(6));
        dateOfLastestCredentialsStatus =  new SimpleStringProperty(bookData.elementAt(7));
        dateOfHire =  new SimpleStringProperty(bookData.elementAt(8));
        status =  new SimpleStringProperty(bookData.elementAt(9));
        System.out.println("It is hitting here and I want you to see this: "+bannerId + " " + firstName + " " + password);
    }

    //----------------------------------------------------------------------------
    public String getBannerId() {return bannerId.get(); }

    //----------------------------------------------------------------------------
    public void setBannerId(String number) { bannerId.set(number); }

    //----------------------------------------------------------------------------
    public String getPassword() { return password.get(); }

    //----------------------------------------------------------------------------
    public void setPassword(String pass) { password.set(pass); }

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
    public String getCredentials()
    {
        return credentials.get();
    }

    //----------------------------------------------------------------------------
    public void setCredentials(String cred)
    {
        credentials.set(cred);
    }

    //----------------------------------------------------------------------------
    public String getDateOfLatestCredentialsStatus()
    {
        return dateOfLastestCredentialsStatus.get();
    }

    //----------------------------------------------------------------------------
    public void setDateOfLatestCredentialsStatus(String dob)
    {
        dateOfLastestCredentialsStatus.set(dob);
    }

    //----------------------------------------------------------------------------
    public String getDateOfHire() {
        return dateOfHire.get();
    }

    //----------------------------------------------------------------------------
    public void setDateOfHire(String hire)
    {
        dateOfHire.set(hire);
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