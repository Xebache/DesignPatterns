package mvvm.command.edit;

import model.Item;
import model.TitleState;
import mvvm.command.Command;


public class EditCommand implements Command {

    private final Item item;
    private final String oldTitle;
    private TitleState save;


    //   CONSTRUCTOR

    public EditCommand(Item item, String oldTitle) {
        this.item = item;
        this.oldTitle = oldTitle;
    }


    //   METHODS

    @Override
    public void execute() {
        if(save != null) {
            undo();
        }
        else {
            save = item.saveTitle(oldTitle);
        }
    }

    @Override
    public void undo() {
        save = item.restore(save);
    }


    //   TO STRING

    @Override
    public String toString() {
        return "Edition du titre ";
    }
}