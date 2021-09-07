package main;

import dao.DAOContext;
import javafx.application.Application;
import javafx.stage.Stage;
import view.TrelloView;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        DAOContext.init();
        TrelloView trelloView = new TrelloView(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
