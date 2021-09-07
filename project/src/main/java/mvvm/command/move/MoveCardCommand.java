package mvvm.command.move;

import direction.Direction;
import model.Card;
import model.Column;
import model.PositionState;


public class MoveCardCommand extends MoveCommand<Card, Column> {

    private PositionState<Card, Column> save;


    //   CONSTRUCTOR

    public MoveCardCommand(Card card, Direction direction) {
        super(card, direction);
    }


    //   METHODS

    @Override
    public void execute() {
        super.execute();
        Card card = (Card) getMovable();
        switch(getDirection()) {
            case LEFT:
                save(card);
                card.moveLeft();
                break;
            case RIGHT:
                save(card);
                card.moveRight();
                break;
        }
    }

    @Override
    public void undo() {
        super.undo();
        Card card = (Card) getMovable();
        switch(getDirection()) {
            case LEFT:
            case RIGHT:
                card.delete();
                card.restore(save);
                break;
        }
    }

    private void save(Card card) {
        save = card.savePosition();
    }


    //  TO STRING

    @Override
    public String toString() {
        String str = "Déplacement de la ";
        switch (getDirection()) {
            case UP:
            case DOWN:
                str += getMovable() + " " + getDirection() + " de la " + getMovable().getContainer();
                break;

            case LEFT:
                Column next = ((Card)getMovable()).getNextColumn();
                str += getMovable() + " de la " + next + " à la " + getMovable().getContainer();
                break;

            case RIGHT:
                Column previous = ((Card)getMovable()).getPreviousColumn();
                str += getMovable() + " de la " + previous + " à la " + getMovable().getContainer();
                break;
        }
        return str;
    }

}
