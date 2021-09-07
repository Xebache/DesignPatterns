package view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import model.Board;
import model.Column;
import mvvm.BoardViewModel;
import mvvm.MenuViewModel;


public class MenuView extends MenuBar {

    private static MenuView menuView;

    private final Menu
            mFile = new Menu("Fichier"),
            mEdit = new Menu("Edition"),
            mOpenBoard = new Menu("Ouvrir un tableau..."),
            mNewCard = new Menu("Nouvelle carte");

    private final MenuItem
            miNewBoard = new MenuItem("Nouveau tableau"),
            miCloseBoard = new MenuItem("Fermer le tableau"),
            miNewColumn = new MenuItem("Nouvelle colonne"),
            miExit = new MenuItem("Quitter"),
            miUndo = new MenuItem(),
            miRedo = new MenuItem();

    private final SeparatorMenuItem
            sep1 = new SeparatorMenuItem(),
            sep2 = new SeparatorMenuItem();

    private final SimpleListProperty<Board>
            boards = new SimpleListProperty<>();

    private final SimpleListProperty<Column>
            columns = new SimpleListProperty<>();

    private final ObjectProperty<Board>
            selectedBoard = new SimpleObjectProperty<>();

    private final MenuViewModel
            menuViewModel = new MenuViewModel();


    //  CONSTRUCTOR

    private MenuView() {
        configData();
        menusListener();
        buildGraphicComponents();
        configActions();
    }

    public static MenuView getInstance() {
        if(menuView == null) {
            menuView = new MenuView();
        }
        return menuView;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void buildGraphicComponents() {
        makeComponentsHierarchy();
        configAccelerators();
    }

    private void makeComponentsHierarchy() {
        mOpenBoardMenuItemsConfig();
        mFile.getItems().addAll(miNewBoard, mOpenBoard, miCloseBoard, sep1, miExit);
        mEdit.getItems().addAll(miUndo, miRedo, sep2, miNewColumn, mNewCard);
        this.getMenus().addAll(mFile, mEdit);
    }

    //  CONFIG GRAPHIC COMPONENTS

    private void configAccelerators() {
        miUndo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        miRedo.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
    }

    //   CONFIG MENU ITEMS

    private void mOpenBoardMenuItemsConfig() {
        mOpenBoard.getItems().clear();
        for(Board board: boards) {
            MenuItem miBoard = new MenuItem(board.getTitle());
            miBoard.setOnAction(event -> selectedBoard.set(board));
            mOpenBoard.getItems().add(miBoard);
        }
    }

    private void configAddCardMenuItems(Board board) {
        mNewCard.getItems().clear();
        for(Column column : board.getColumns()) {
            MenuItem miColumn = new MenuItem("Colonne " + (column.getPosition() + 1));
            miColumn.setOnAction(event ->
                    menuViewModel.addCard(column)
            );
            mNewCard.getItems().add(miColumn);
        }
    }


    private void menusListener() {
        columns.addListener((obs, oldVal, newVal) -> {
            if (newVal != null && BoardViewModel.getBoard() != null) {
                configAddCardMenuItems(BoardViewModel.getBoard());
            }
        });

        boards.addListener((obs, oldVal, newVal) -> {
            if (newVal != null)
                mOpenBoardMenuItemsConfig();
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //   PROPERTY

    public ObjectProperty<Board> selectedBoardProperty() {
        return selectedBoard;
    }

    public SimpleListProperty<Column> columnsProperty() {
        return columns;
    }


    //   CONFIG ACTIONS

    private void configActions() {
        configMenuItemsActions();
        configMenuItemsBindings();
        configViewModelBinding();
        configMenuItemsDisableBindings();
    }

    private void configMenuItemsActions() {

        miNewBoard.setOnAction(event -> {
            menuViewModel.newBoard();
            selectedBoard.set(boards.get(boards.size() - 1));
        });

        miCloseBoard.setOnAction(event -> {
            Board board = BoardViewModel.getBoard();
            MenuViewModel.saveBoard(board);
            selectedBoard.set(null);
        });

        miExit.setOnAction(event -> MenuViewModel.saveAllBoards());

        miNewColumn.setOnAction(event ->
                menuViewModel.addColumn()
        );

        miRedo.setOnAction(event ->
                menuViewModel.redo()
        );

        miUndo.setOnAction(event ->
                menuViewModel.undo()
        );
    }

    private void configViewModelBinding() {
        menuViewModel.selectedBoardProperty().bind(selectedBoardProperty());
    }

    private void configMenuItemsBindings() {
        miRedo.textProperty().bind(menuViewModel.redoCommandTextProperty());
        miUndo.textProperty().bind(menuViewModel.undoCommandTextProperty());
    }

    private void configMenuItemsDisableBindings() {
        miRedo.disableProperty().bind(menuViewModel.imRedoDisabledProperty());
        miUndo.disableProperty().bind(menuViewModel.imUndoDisabledProperty());
        miNewColumn.disableProperty().bind((menuViewModel.miNewColumnDisabledProperty()));
        mNewCard.disableProperty().bind(menuViewModel.mNewCardDisabledProperty());
    }

    private void configData() {
        boards.bind(menuViewModel.boardsProperty());
        menuViewModel.columnsProperty().bind(columns);
    }

}