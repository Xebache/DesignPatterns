package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public abstract class Item {

    private int id;
    private final StringProperty title = new SimpleStringProperty();


    //   CONSTRUCTOR

    public Item(String title) {
        this.title.set(title);
    }


    //   GETTER / SETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }


    //   PROPERTY

    public StringProperty titleProperty() {
        return title;
    }


    //   TOSTRING

    @Override
    public String toString() {
        return getTitle();
    }


    //   UPDATE TITLE

    public void updateTitle(String title) {
        setTitle(title);
    }


    //   MEMENTO

    public TitleState saveTitle(String oldTitle) {
        return new TitleState(oldTitle);
    }

    public TitleState restore(TitleState save) {
        TitleState newSave = saveTitle(getTitle());
        updateTitle(save.getOldTitle());
        return newSave;
    }


}
