package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.repository.RepositoryFactory;
import java.util.List;


public class Board extends Container<Column> {

    //   CONSTRUCTOR

    public Board(String title) {
        super(title);
    }

    public Board() {
        this("Nouveau tableau");
        hasNoSelectedColumnsProperty().addListener((obs, oldVal, newval) -> System.out.println(newval));
    }


    //   GETTER

    public static Board getById(int idBoard) {
        return RepositoryFactory.getRepositoryBoard().get(idBoard);
    }

    public ObservableList<Column> getColumns() {
        return FXCollections.unmodifiableObservableList(this.getMovables());
    }

    public List<Column> getSelectedColumns() {
        return getColumns().filtered(Column::getIsSelected);
    }

    public BooleanProperty hasNoSelectedColumnsProperty() {
        return new SimpleBooleanProperty(getSelectedColumns().isEmpty());
    }

    //   SETTER

    public void setColumns(List<Column> columns) {
        setMovables(columns);
    }


    //   ADD

    @Override
    public void addNew() {
        Column column = new Column(this);
        RepositoryFactory.getRepositoryColumn().insert(column);
    }


    //   EDIT

    @Override
    public void updateTitle(String title) {
        super.updateTitle(title);
        RepositoryFactory.getRepositoryBoard().update(this);
    }

    @Override
    public TitleState saveTitle(String oldTitle) {
        RepositoryFactory.getRepositoryBoard().update(this);
        return super.saveTitle(oldTitle);
    }

}