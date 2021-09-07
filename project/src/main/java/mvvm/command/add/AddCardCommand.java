package mvvm.command.add;

import model.*;


public class AddCardCommand extends AddCommand<Card, Column> {

    public AddCardCommand(Column column) {
        super(column);
    }

    @Override
    public String toString() {
        return super.toString() + "d'une carte";
    }

}
