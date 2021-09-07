package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.repository.RepositoryFactory;


public class Trello {

    private static ObservableList<Board> getAll() {
        return FXCollections.observableList(RepositoryFactory.getRepositoryBoard().getAll());
    }

    public static ObservableList<Board> getAllBoards() {
        return FXCollections.unmodifiableObservableList(getAll());
    }

}
