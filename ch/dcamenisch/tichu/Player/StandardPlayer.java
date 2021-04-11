package ch.dcamenisch.tichu.Player;

import java.util.ArrayList;
import java.util.List;

import ch.dcamenisch.tichu.Card.Card;
import ch.dcamenisch.tichu.Card.CardRank;
import ch.dcamenisch.tichu.Card.CardSuit;
import ch.dcamenisch.tichu.Trick.Trick;

public abstract class StandardPlayer implements Player {
    protected String name;
    protected List<Card> cards;
    protected List<Trick> tricks;
    protected int score;

    /**
     * instatiate a new player object with a given name
     * @param name of the player
     */
    public StandardPlayer(String name) {
        this.name = name;
        this.cards = new ArrayList<Card>();
        this.score = 0;
    }

    @Override
    public void receiveCards(List<Card> list) {
        cards.addAll(list);
        cards.sort(null);
    }

    @Override
    public void receiveCard(Card card) {
        cards.add(card);
        cards.sort(null);
    }

    @Override
    public void receiveTrick(Trick trick) {
        tricks.add(trick);
    }
    
    @Override
    public void printCards() {
        System.out.println("Player " + name);
        System.out.println("---------------");
        for(Card c : cards) {
            System.out.println(c);
        }
        System.out.println("---------------");
    }

    @Override
    public boolean hasCard(CardRank rank, CardSuit suit) {
        for(Card c : cards) {
            if(c.rank == rank && c.suit == suit) return true;
        }
        
        return false;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void newRound() {
        // TODO: implement

        cards = new ArrayList<Card>();
        tricks = new ArrayList<Trick>();
    }
    
    @Override
    public void endRound() {
        // TODO: implement

        for(Trick t : tricks) {
            score += t.getPoints();
        }
    }

}
