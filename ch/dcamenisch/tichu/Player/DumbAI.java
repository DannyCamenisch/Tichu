package ch.dcamenisch.tichu.Player;

import java.util.ArrayList;
import java.util.List;

import ch.dcamenisch.tichu.Card.Card;
import ch.dcamenisch.tichu.Card.CardRank;
import ch.dcamenisch.tichu.Card.CardSuit;

public class DumbAI implements Player {
    private String name;
    private List<Card> cards;
    private List<Card> stack;
    private int score;

    private List<Card> exchange;

    /**
     * instatiate a new player object with a given name
     * @param name of the player
     */
    public DumbAI(String name) {
        this.name = name;
        this.cards = new ArrayList<Card>();
        this.stack = new ArrayList<Card>();
        this.score = 0;

        this.exchange = new ArrayList<Card>();
    }

    /**
     * receive cards dealt
     */
    public void receiveCards(List<Card> list) {
        cards.addAll(list);
        cards.sort(null);
    }

    public void receiveCard(Card card) {
        cards.add(card);
        cards.sort(null);
    }

    public void printCards() {
        System.out.println("Player " + name);
        System.out.println("---------------");
        for(Card c : cards) {
            System.out.println(c);
        }
        System.out.println("---------------");
    }

    public int getScore() {
        return score;
    }

    public void newRound() {
        stack = new ArrayList<Card>();
    }

    public ArrayList<Card> playCard() {
        return new ArrayList<Card>(cards);
    }

    public void receiveStack(List<Card> stack) {
        this.stack.addAll(stack);
    }

    public void endRound() {
        for(Card c : stack) {
            score += c.getPoints();
        }
    }

    @Override
    public void selectCardsToExchange() {
        Card lowEven = cards.get(0);
        Card lowOdd = cards.get(1);
        Card best = cards.get(cards.size() - 1);

        exchange.add(lowEven);
        exchange.add(lowOdd);
        exchange.add(best);
    }

    @Override
    public void exchangeCards(Player left, Player mate, Player right) {
        right.receiveCard(exchange.get(1));
        left.receiveCard(exchange.get(2));
        mate.receiveCard(exchange.get(3));
    }

    @Override
    public boolean hasCard(CardRank rank, CardSuit suit) {
        for(Card c : cards) {
            if(c.rank == rank && c.suit == suit) return true;
        }
        
        return false;
    }
}
