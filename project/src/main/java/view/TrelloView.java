package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Board;
import mvvm.BoardViewModel;
import mvvm.MenuViewModel;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;


public class TrelloView extends VBox {

    private final Region
            rTop = new Region(),
            rBottom = new Region(),
            rLeft = new Region(),
            rRight = new Region();

    private final BorderPane
            bpBoard = new BorderPane();

    private final MenuView menuView = MenuView.getInstance();


    //  CONSTRUCTOR

    public TrelloView(Stage primaryStage) {
        Scene scene = new Scene(this);
        primaryStage.setTitle("Trello");
        exitAlertConfig(primaryStage);
        primaryStage.setScene(scene);
        build();
        selectedBoardListener();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONFIG GRAPHIC COMPONENTS

    public void build() {
        makeComponentsHierarchy();
        configStyles();
    }

    private void makeComponentsHierarchy() {
        bpBoard.setTop(rTop);
        bpBoard.setBottom(rBottom);
        bpBoard.setLeft(rLeft);
        bpBoard.setRight(rRight);
        this.getChildren().addAll(menuView, bpBoard);
    }

    private void configStyles() {
        configStylesVBox();
        configStylesRegions();
    }

    private void configStylesVBox() {
        setBackground(background());
        setPrefSize(1000, 750);
        setVgrow(this.bpBoard, Priority.ALWAYS);
    }

    private Background background() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/wallpaper.png")), 1000, 1000, true, false
        );
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        return new Background(backgroundImage);
    }

    private void configStylesRegions() {
        rTop.setMinHeight(30);
        rBottom.setMinHeight(30);
        rLeft.setMinWidth(30);
        rRight.setMinWidth(30);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //   ACTIONS OPEN & CLOSE

    private void exitAlertConfig(Stage primaryStage) {
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e-> {
            e.consume();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Enregistrer et quitter");
            alert.setHeaderText(null);
            alert.setContentText("Vous êtes sur le point de quitter l'application. Voulez-vous enregistrer votre travail ?");

            ButtonType save = new ButtonType("Enregistrer et quitter");
            ButtonType quit = new ButtonType("Quitter sans enregistrer");
            ButtonType cancel = new ButtonType("Annuler");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(save, quit, cancel);

            Optional<ButtonType> option = alert.showAndWait();
            if(option.isPresent()) {
                if (option.get() == save) {
                    MenuViewModel.saveAllBoards();
                }
                else if(option.get() == quit) {
                    System.exit(0);
                }
                else
                    alert.close();
            }
        });
    }

    private void selectedBoardListener() {
        menuView.selectedBoardProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                int idBoard = menuView.selectedBoardProperty().get().getId();
                if(oldVal != null) {
                    Board board = BoardViewModel.getBoard();
                    MenuViewModel.saveBoard(board);
                }
                try {
                    bpBoard.setCenter(new BoardView(idBoard));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                menuView.columnsProperty().set(BoardViewModel.getBoard().getColumns());
            }
            else {
                bpBoard.setCenter(null);
            }
        });
    }
}