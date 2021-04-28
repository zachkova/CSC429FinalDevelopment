package model;

import event.Event;
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

import java.util.Hashtable;
import java.util.Properties;

public class CheckInTransaction implements IView, IModel, ISlideShow {

    private ModelRegistry myRegistry;
    private Hashtable<String, Scene> myViews;
    private Stage myStage;
    private Properties dependencies;

    protected CheckInTransaction(){
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();
        myRegistry = new ModelRegistry("CheckInBookTransaction");

        setDependencies();
    }


    @Override
    public void updateState(String key, Object value) {

    }

    @Override
    public Object getState(String key) {
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
        if(key.equals("doYourJob")){
            createAndShowCheckOutBookView();
        }
        myRegistry.updateSubscribers(key, this);
    }

    private void createAndShowCheckOutBookView()
    {
        Scene currentScene = null;

        // create our initial view
        View newView = ViewFactory.createView("CheckInBook", this); // USE VIEW FACTORY
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
}
