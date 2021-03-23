package ch.dcamenisch.tichu;

import java.util.ArrayList;
import java.util.List;

public interface Player {

    public void receiveCards(List<Card> list);
    public void receiveCard(Card card);

    public void receiveStack(List<Card> stack);

    public void printCards();
    public boolean hasCard(Card.Value value, Card.Color color);

    public int getScore();

    public ArrayList<Card> playCard();

    public void newRound();
    public void endRound();

    public void selectCardsToExchange();
    public void exchangeCards(Player left, Player mate, Player right);
}
