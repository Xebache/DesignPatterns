package view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Board;
import model.Column;
import mvvm.BoardViewModel;
import java.sql.SQLException;
import java.util.Optional;


public class BoardView extends VBox {

    private static final Color BACKGROUND_COLOR = Color.web("#3D6A75");
    private static final Color BACKGROUND_COLUMN = Color.web("#638790");
    private static final int COLUMN_CELL_WIDTH = 273;

    private final EditableLabel
            elTitle = new EditableLabel();

    private final HBox
            hbList = new HBox();

    private final ListView<Column>
            lvColumns = new ListView<>();

    private final Region
            region = new Region();

    private final BoardViewModel boardViewModel;


    //  CONSTRUCTORS

    public BoardView(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        buildGraphicComponents();
        configBindings();
        configActions();
    }

    public BoardView(Board board) {
        this(new BoardViewModel(board));
    }

    public BoardView(int idBoard) throws SQLException {
        this(Board.getById(idBoard));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void buildGraphicComponents() {
        makeComponentsHierarchy();
        configStyles();
        customizeLvColumns();
    }

    //  CONFIG GRAPHIC COMPONENTS

    public void makeComponentsHierarchy() {
        hbList.getChildren().addAll(lvColumns, region);
        this.getChildren().addAll(elTitle, hbList);
    }

    public void configStyles() {
        configStyleVBox();
        configStyleEditableLabel();
        configStyleHBox();
        configStyleListView();
        configStyleRegion();
    }

    //  BACKGROUNDS

    private Background background() {
        CornerRadii corners = new CornerRadii(10);
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, corners, Insets.EMPTY);
        return new Background(backgroundFill);
    }

    private Background backgroundColumn() {
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLUMN, CornerRadii.EMPTY, Insets.EMPTY);
        return new Background(backgroundFill);
    }

    //  VBOX

    private void configStyleVBox() {
        setBackground(background());
        setSpacing(15);
        setPadding(new Insets(0, 20, 20, 20));
        VBox.setVgrow(hbList, Priority.ALWAYS);
    }

    //  EDITABLE LABEL

    private void configStyleEditableLabel() {
        elTitle.textField.setFont(Font.font("Arial", 17));
        elTitle.textField.setAlignment(Pos.BOTTOM_LEFT);
        elTitle.textField.minWidthProperty().bind(this.widthProperty().subtract(40));
        elTitle.setPrefHeight(50);
        elTitle.minWidthProperty().bind(this.widthProperty().subtract(40));
        elTitle.label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        elTitle.setAlignment(Pos.CENTER_LEFT);
        elTitle.label.setTextFill(Color.WHITE);
    }

    //   REGION

    private void configStyleRegion() {
        region.setBackground(backgroundColumn());
        region.setMinWidth(20);
    }

    private void configStyleHBox() {
        HBox.setHgrow(lvColumns, Priority.ALWAYS);
        HBox.setHgrow(region, Priority.SOMETIMES);
    }

    //   CONFIG LISTVIEW

    private void configStyleListView() {
        lvColumns.setOrientation(Orientation.HORIZONTAL);
        lvColumns.setStyle("-fx-background-insets: 0; -fx-padding: 0;");
        lvColumns.maxWidthProperty().bind(Bindings.size(boardViewModel.columnsListProperty()).multiply(COLUMN_CELL_WIDTH));

    }

    private void customizeLvColumns() {
        lvColumns.setCellFactory(columnView -> new ListCell<>(){
            @Override
            protected void updateItem(Column column, boolean empty) {
                super.updateItem(column, empty);
                ColumnView columnView = null;
                if (column != null) {
                    columnView = new ColumnView(column);
                }
                setBackground(backgroundColumn());
                setGraphic(columnView);

            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONFIG BINDINGS & LISTENERS

    private void configBindings() {
        elTitle.label.textProperty().bindBidirectional(boardViewModel.boardTitleProperty());
        lvColumns.itemsProperty().bindBidirectional(boardViewModel.columnsListProperty());
        boardViewModel.selectedColumnBinding(lvColumns.getSelectionModel().selectedIndexProperty());
        boardViewModel.focusedTitleBinding(elTitle.textField.focusedProperty());
    }


    // CONFIG MOUSE EVENT

    private void configActions() {
        configMouseEvents();
        configContextMenu();
    }

    private void configMouseEvents() {
        region.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                boardViewModel.addColumn();
            }
        });
    }

    private void configContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Supprimer");
        delete.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Supprimer " + selectedColumn().getTitle() + " ?");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.isPresent() && action.get() == ButtonType.OK) {
                boardViewModel.delete();
            }
        });
        contextMenu.getItems().add(delete);
        lvColumns.setContextMenu(contextMenu);
    }

    //  SELECT

    private Column selectedColumn() {
        return lvColumns.getSelectionModel().getSelectedItem();
    }

}