package mvvm;

import javafx.beans.property.*;
import model.Item;
import mvvm.command.CommandManager;
import mvvm.command.edit.EditCommand;


public class LabelEditableViewModel {

    private final BooleanProperty
            focusedTitle = new SimpleBooleanProperty();

    private final StringProperty
            itemTitle = new SimpleStringProperty("");

    private final Item item;

    private final CommandManager
            commandManager = CommandManager.getInstance();


    //   CONSTRUCTOR

    public LabelEditableViewModel(Item item) {
        this.item = item;
        this.itemTitle.bindBidirectional(item.titleProperty());
        addTitleListener();
    }


    //   PROPERTIES

    public StringProperty itemTitleProperty() {
        return itemTitle;
    }

    public void focusedTitleBinding(ReadOnlyBooleanProperty readOnlyBooleanProperty) {
        focusedTitle.bind(readOnlyBooleanProperty);
    }


    //   EDIT COMMAND

    private void addTitleListener() {
         focusedTitle.addListener((obs, oldVal, newVal) -> {
            if(newVal) {
                itemTitle.addListener((o, oV, nV) -> commandManager.execute(new EditCommand(item, oV)));
            }
        });
    }

}
