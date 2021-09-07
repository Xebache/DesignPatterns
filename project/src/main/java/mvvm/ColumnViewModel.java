package mvvm;

import mvvm.command.*;
import model.Card;
import direction.Direction;
import javafx.beans.property.*;
import model.Column;
import mvvm.command.add.AddCardCommand;
import mvvm.command.delete.DeleteCardCommand;
import mvvm.command.move.MoveColumnCommand;


public class ColumnViewModel {

    private final IntegerProperty
            selectedCard = new SimpleIntegerProperty();

    private final ObjectProperty<Direction>
            direction = new SimpleObjectProperty<>();

    private final LabelEditableViewModel labelEditableViewModel;

    private final BooleanProperty selected = new SimpleBooleanProperty();

    private final Column column;

    private final CommandManager
            commandManager = CommandManager.getInstance();


    //  CONSTRUCTOR

    public ColumnViewModel(Column column) {
        this.column = column;
        this.labelEditableViewModel = new LabelEditableViewModel(column);
        configListeners();
    }


    //   LISTENERS

    private void configListeners() {
        addMoveListener();
        selectedListener();
    }

    private void addMoveListener() {
        direction.addListener((obj, oldVal, direction) ->
                commandManager.execute(new MoveColumnCommand(column, direction))
        );

    }

    private void selectedListener() {
        selected.addListener((obs, oldVal, newVal) -> {
            column.setSelected(newVal);
            //boardHasNoSelectedColumns.bind(column.getContainer().hasNoSelectedColumnsProperty());
        });
    }


    //   PROPERTIES

    public ListProperty<Card> cardsListProperty() {
        return new SimpleListProperty<>(column.getCards());
    }

    public StringProperty columnTitleProperty() {
        return labelEditableViewModel.itemTitleProperty();
    }

    public BooleanProperty btRightDisabledProperty() {
        return column.isLastProperty();
    }

    public BooleanProperty btLeftDisabledProperty() {
        return column.isFirstProperty();
    }

    //   BINDINGS

    public void selectedColumnBinding(ReadOnlyIntegerProperty integerProperty) {
        selectedCard.bind(integerProperty);
    }

    public void directionBinding(ObjectProperty<Direction> direction) {
        this.direction.bind(direction);
    }

    public void focusedTitleBinding(ReadOnlyBooleanProperty readOnlyBooleanProperty) {
        labelEditableViewModel.focusedTitleBinding(readOnlyBooleanProperty);
    }


    //   ADD & DELETE

    public void addCard() {
        commandManager.execute(new AddCardCommand(column));
    }

    public void delete() {
        Card card = getCard();
        if(card != null) {
            commandManager.execute(new DeleteCardCommand(card));
        }
    }

    private Card getCard() {
        int index = selectedCard.get();
        return index == -1 ? null : cardsListProperty().get(index);
    }

}