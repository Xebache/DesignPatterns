package view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import direction.Direction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Card;
import model.Column;
import mvvm.ColumnViewModel;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;


public class ColumnView extends VBox {

    private static final Color BACKGROUND_COLOR = Color.web("#C0D6DF");
    private static final int CARD_CELL_HEIGHT = 98;

    private final HBox
            hbHeader = new HBox();

    private final Button
            btLeft = new Button(),
            btRight = new Button(),
            btAdd = new Button();

    private final EditableLabel
            elTitle = new EditableLabel();

    private final VBox vbAdd = new VBox();

    private final Region
            region = new Region();

    private final ListView<Card>
            lvCards = new ListView<>();

    private final ObjectProperty<Direction>
            direction = new SimpleObjectProperty<>();

    private final ColumnViewModel columnViewModel;


    //  CONSTRUCTORS

    public ColumnView(ColumnViewModel columnViewModel) {
        this.columnViewModel = columnViewModel;
        buildGraphicComponents();
        configBindings();
        configActions();
    }

    ColumnView(Column column) {
        this(new ColumnViewModel(column));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONFIG GRAPHIC COMPONENTS

    private void buildGraphicComponents() {
        makeComponentsHierarchy();
        configStyles();
        customizeLvCards();
    }

    private void makeComponentsHierarchy() {
        hbHeader.getChildren().addAll(btLeft, elTitle, btRight);
        vbAdd.getChildren().add(btAdd);
        getChildren().addAll(hbHeader, lvCards, region, vbAdd);
    }

    private void configStyles() {
        configStyleVBox();
        configStyleButtonsMove();
        configStyleButtonAdd();
        configStyleEditableLabel();
        configStyleListView();
        configStyleRegion();
    }

    //  BACKGROUNDS

    private Background background() {
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
        return new Background(backgroundFill);
    }

    private void backgroundBtn(Button btn) {
        int leftRadii = btn == btLeft ? 15 : 0;
        int rightRadii = leftRadii == 15 ? 0 : 15;
        CornerRadii corners = new CornerRadii(leftRadii, rightRadii, 0, 0, true);
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, corners, Insets.EMPTY);
        btn.setBackground(new Background(backgroundFill));
    }

    //  VBOX

    private void configStyleVBox() {
        CornerRadii corners = new CornerRadii(10);
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, corners, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);
        setMaxWidth(240);
        setMinWidth(240);
        setPadding(new Insets(0, 0, 10, 0));
        prefHeightProperty().bind(lvCards.prefHeightProperty());
        VBox.setVgrow(region, Priority.ALWAYS);
        VBox.setVgrow(lvCards, Priority.SOMETIMES);
    }

    //  BUTTONS

    private void configStyleButtonsMove() {
        Button[] buttons = {btLeft, btRight};
        String[] imgName = {"left.png", "right.png"};

        IntStream.range(0, 2).forEach(idx -> {
            var btn = buttons[idx];

            Image image = new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("/icons/" + imgName[idx])),
                    20, 20, true, false);
            btn.setGraphic(new ImageView(image));
            btn.setPrefSize(30, 30);
            btn.setPadding(new Insets(10, 0, 0, 0));
            backgroundBtn(btn);
        });
    }

    private void configStyleButtonAdd() {
        btAdd.setPrefSize(250, 30);
        btAdd.setBackground(background());
    }

    //  EDITABLE LABEL

    private void configStyleEditableLabel() {
        elTitle.textField.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
        elTitle.textField.setAlignment(Pos.CENTER);
        elTitle.label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
        elTitle.label.setTextFill(Color.WHITE);
        elTitle.label.setMinWidth(175);
        elTitle.setPrefHeight(40);
        elTitle.setPadding(new Insets(3, 0, 0, 0));
        elTitle.setAlignment(Pos.CENTER);
        HBox.setHgrow(elTitle, Priority.ALWAYS);
    }

    //   REGION

    private void configStyleRegion() {
        region.setBackground(background());
    }

    //   LISTVIEW

    private void configStyleListView() {
        lvCards.setStyle("-fx-background-insets: 0; -fx-padding: 0;");
        lvCards.setPrefHeight(580);
    }

    private void customizeLvCards() {
        lvCards.setCellFactory(columnView -> new ListCell<>(){
            @Override
            protected void updateItem(Card card, boolean empty) {
                super.updateItem(card, empty);
                CardView cardView = null;
                if (card != null) {
                    cardView = new CardView(card);
                }
                setBackground(background());
                setGraphic(cardView);
            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //   BINDINGS

    private void configBindings() {
        configDataBindings();
        configViewModelBindings();
        configDisableBindings();
    }

    private void configDataBindings() {
        elTitle.label.textProperty().bindBidirectional(columnViewModel.columnTitleProperty());
        lvCards.itemsProperty().bind(columnViewModel.cardsListProperty());
        lvCards.prefHeightProperty().bind(Bindings.size(lvCards.getItems()).multiply(CARD_CELL_HEIGHT));
    }

    private void configViewModelBindings() {
        columnViewModel.directionBinding(direction);
        columnViewModel.selectedColumnBinding(lvCards.getSelectionModel().selectedIndexProperty());
        columnViewModel.focusedTitleBinding(elTitle.textField.focusedProperty());
    }

    private void configDisableBindings() {
        btRight.disableProperty().bind(columnViewModel.btRightDisabledProperty());
        btLeft.disableProperty().bind(columnViewModel.btLeftDisabledProperty());
    }


    //   ACTIONS

    private void configActions() {
        configEventsHandling();
        configContextMenu();
        configMouseEvents();
    }

    private void configEventsHandling() {
        btRight.setOnAction(e -> direction.set(Direction.DOWN));
        btLeft.setOnAction(e -> direction.set(Direction.UP));
    }

    private void configMouseEvents() {
        region.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                columnViewModel.addCard();
            }
        });

        btAdd.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                columnViewModel.addCard();
            }
        });

    }

    private void configContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Supprimer");
        delete.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Supprimer " + selectedCard().getTitle() + " ?");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.isPresent() && action.get() == ButtonType.OK) {
                columnViewModel.delete();
            }
        });
        contextMenu.getItems().add(delete);
        lvCards.setContextMenu(contextMenu);
    }

    private Card selectedCard() {
        return lvCards.getSelectionModel().getSelectedItem();
    }

}