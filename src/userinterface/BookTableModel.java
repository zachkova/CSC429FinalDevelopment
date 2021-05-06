package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;


public class BookTableModel{

    private final SimpleStringProperty barcode;
    private final SimpleStringProperty title;
    private final SimpleStringProperty discipline;
    private final SimpleStringProperty author1;
    private final SimpleStringProperty author2;
    private final SimpleStringProperty author3;
    private final SimpleStringProperty author4;
    private final SimpleStringProperty publisher;
    private final SimpleStringProperty yearOfPublication;
    private final SimpleStringProperty isbn;
    private final SimpleStringProperty quality;
    private final SimpleStringProperty suggestedPrice;
    private final SimpleStringProperty notes;
    private final SimpleStringProperty status;


    //----------------------------------------------------------------------------
    public BookTableModel(Vector<String> bookData)
    {
        barcode =  new SimpleStringProperty(bookData.elementAt(0));
        title =  new SimpleStringProperty(bookData.elementAt(1));
        discipline =  new SimpleStringProperty(bookData.elementAt(2));
        author1 =  new SimpleStringProperty(bookData.elementAt(3));
        author2 =  new SimpleStringProperty(bookData.elementAt(4));
        author3 =  new SimpleStringProperty(bookData.elementAt(5));
        author4 =  new SimpleStringProperty(bookData.elementAt(6));
        publisher =  new SimpleStringProperty(bookData.elementAt(7));
        yearOfPublication =  new SimpleStringProperty(bookData.elementAt(8));
        isbn =  new SimpleStringProperty(bookData.elementAt(9));
        quality =  new SimpleStringProperty(bookData.elementAt(10));
        suggestedPrice =  new SimpleStringProperty(bookData.elementAt(11));
        notes =  new SimpleStringProperty(bookData.elementAt(12));
        status =  new SimpleStringProperty(bookData.elementAt(13));

        System.out.println(quality + " " + suggestedPrice + " " + notes + " " + status);
    }


    //----------------------------------------------------------------------------
    public String getBarcode() {return barcode.get(); }

    //----------------------------------------------------------------------------
    public void setBarcode(String number) { barcode.set(number); }

    //----------------------------------------------------------------------------
    public String getTitle() {
        return title.get();
    }

    //----------------------------------------------------------------------------
    public void setTitle(String name) {
        title.set(name);
    }

    //----------------------------------------------------------------------------
    public String getDiscipline() {
        return discipline.get();
    }

    //----------------------------------------------------------------------------
    public void setDiscipline(String name)
    {
        discipline.set(name);
    }

    //----------------------------------------------------------------------------
    public String getAuthor1()
    {
        return author1.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor1(String phone)
    {
        author1.set(phone);
    }

    //----------------------------------------------------------------------------
    public String getAuthor2()
    {
        return author2.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor2(String em)
    {
        author2.set(em);
    }
    //----------------------------------------------------------------------------
    public String getAuthor3()
    {
        return author3.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor3(String cred)
    {
        author3.set(cred);
    }

    //----------------------------------------------------------------------------
    public String getAuthor4()
    {
        return author4.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor4(String dob)
    {
        author4.set(dob);
    }

    //----------------------------------------------------------------------------
    public String getPublisher() {
        return publisher.get();
    }

    //----------------------------------------------------------------------------
    public void setPublisher(String hire)
    {
        publisher.set(hire);
    }

    public String getYearOfPublication() {
        return yearOfPublication.get();
    }

    //----------------------------------------------------------------------------
    public void setYearOfPublication(String stat)
    {
        yearOfPublication.set(stat);
    }

    public String getIsbn() {
        return isbn.get();
    }

    //----------------------------------------------------------------------------
    public void setIsbn(String stat)
    {

        isbn.set(stat);
    }

    public String getQuality() {
        return quality.get();
    }

    //----------------------------------------------------------------------------
    public void setQuality(String stat)
    {
        quality.set(stat);
    }

    public String getSuggestedPrice() {
        return suggestedPrice.get();
    }

    //----------------------------------------------------------------------------
    public void setSuggestedPrice(String stat)
    {
        suggestedPrice.set(stat);
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