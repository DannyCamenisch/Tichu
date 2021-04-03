package ch.dcamenisch.tichu;

import java.util.ArrayList;

import ch.dcamenisch.tichu.Card.Card;
import ch.dcamenisch.tichu.Card.Deck;

public class Game {
    private static final int MAX_SCORE = 1000;

    private Players players;
    private Deck deck;
    private ArrayList<Card> sharedStack;
    private ArrayList<Card> topCards;

    /**
     * Instantiate a new Game Object with a new deck of 56 cards and 4 players
     */
    public Game() {
        deck = new Deck();
        players = new Players(4);
    }

    /**
     * Function that starts the game and runs until a team reaches the MAX_SCORE
     */
    public void startGame() {
        System.out.println("[GAME] - Starting a new Game");
        
        playRound();
    }

    /**
     * Prints the current state of the game to the console
     */
    public void printGame() {
        for(Player p : players.getPlayers()) {
            p.printCards();
        }
    }

    /**
     * Function to start a new round in a running game
     */
    private void playRound() {
        deck.dealCards(players.getPlayers());
        players.determineStartingPlayer();

        sharedStack = new ArrayList<Card>();
        topCards = new ArrayList<Card>();

        players.getCurrentPlayer().printCards();
    }
}
