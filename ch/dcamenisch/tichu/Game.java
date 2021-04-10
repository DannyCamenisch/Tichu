package ch.dcamenisch.tichu;

import ch.dcamenisch.tichu.Card.Deck;
import ch.dcamenisch.tichu.Player.Player;
import ch.dcamenisch.tichu.Player.PlayerManager;
import ch.dcamenisch.tichu.Trick.Trick;

public class Game {
    /* Constants */
    private static final int MAX_SCORE = 1000;


    private PlayerManager players;
    private Deck deck;
    private Trick trick;
    
    /**
     * Instantiate a new Game Object with a new deck of 56 cards and 4 players
     */
    public Game() {
        deck = new Deck();
        players = new PlayerManager(4);
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


        players.getCurrentPlayer().printCards();
    }
}
