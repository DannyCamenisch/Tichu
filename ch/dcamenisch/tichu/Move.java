package ch.dcamenisch.tichu;

import java.util.ArrayList;

public class Move {
    private Type type;
    private ArrayList<Card> cards;
    
    public Move(Type type, ArrayList<Card> cards) {
        this.type = type;
        this.cards = cards;
    }

    public int getValue() {
        int res = 0;
        for(Card c : cards) {
            res += c.cardValue();
        }
        return res;
    }

    public boolean validNextMove(Move move) {
        //TODO: implement validNextMove function

        return false;
    }

    public enum Type {
        SINGLE_CARD,
        PAIR,
        MULTIPLE_PAIR,
        TRIPPLE,
        FULLHOUSE,
        STREET,
        BOMB
    }
}
