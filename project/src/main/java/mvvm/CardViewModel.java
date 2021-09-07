package mvvm;

import mvvm.command.CommandManager;
import mvvm.command.move.MoveCardCommand;
import direction.Direction;
import model.*;
import javafx.beans.property.*;


public class CardViewModel {

    private final ObjectProperty<Direction>
            direction = new SimpleObjectProperty<>(null);

    private final Card card;

    private final LabelEditableViewModel labelEditableViewModel;

    private final CommandManager
            commandManager = CommandManager.getInstance();


    //  CONSTRUCTOR

    public CardViewModel(Card card) {
        this.card = card;
        this.labelEditableViewModel = new LabelEditableViewModel(card);
        configListeners();
    }


    //   LISTENERS

    private void configListeners() {
        addMoveListener();
    }

    private void addMoveListener() {
        direction.addListener((obj, oldVal, direction) ->
            commandManager.execute(new MoveCardCommand(card, direction))
        );
    }


    //   BINDINGS

    public void bindMoveDirection(ObjectProperty<Direction> direction) {
        this.direction.bind(direction);
    }

    public void focusedTitleBinding(ReadOnlyBooleanProperty readOnlyBooleanProperty) {
        labelEditableViewModel.focusedTitleBinding(readOnlyBooleanProperty);
    }


    //  PROPERTIES

    public StringProperty cardTitleProperty() {
        return labelEditableViewModel.itemTitleProperty();
    }

    public BooleanProperty btRightDisabledProperty() {
        return (card.getContainer()).isLastProperty();
    }

    public BooleanProperty btLeftDisabledProperty() {
        return (card.getContainer()).isFirstProperty();
    }

    public BooleanProperty btUpDisabledProperty() {
        return card.isFirstProperty();
    }

    public BooleanProperty btDownDisabledProperty() {
        return card.isLastProperty();
    }

}