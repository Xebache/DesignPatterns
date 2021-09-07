package mvvm.command.delete;

import model.Card;
import model.Column;


public class DeleteCardCommand extends DeleteCommand<Card, Column> {

    public DeleteCardCommand(Card card) {
        super(card);
    }
}
