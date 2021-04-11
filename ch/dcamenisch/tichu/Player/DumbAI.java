package ch.dcamenisch.tichu.Player;

import java.util.ArrayList;

import ch.dcamenisch.tichu.Card.Card;

public class DumbAI extends StandardPlayer {

    /**
     * instatiate a new player object with a given name
     * @param name of the player
     */
    public DumbAI(String name) {
        super("[Dumb AI] - " + name);
    }

    @Override
    public ArrayList<Card> playCard() {
        // TODO: implement
        return null;
    }

    @Override
    public void selectCardsToExchange() {
        // TODO: implement
        
    }

    @Override
    public void exchangeCards(Player left, Player mate, Player right) {
        // TODO: implement
        
    }
}
