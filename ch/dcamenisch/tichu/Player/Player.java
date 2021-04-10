package ch.dcamenisch.tichu.Player;

import java.util.ArrayList;
import java.util.List;

import ch.dcamenisch.tichu.Card.Card;
import ch.dcamenisch.tichu.Card.CardRank;
import ch.dcamenisch.tichu.Card.CardSuit;

public interface Player {

    public void receiveCards(List<Card> list);
    public void receiveCard(Card card);

    public void receiveStack(List<Card> stack);

    public void printCards();
    public boolean hasCard(CardRank rank, CardSuit suit);

    public int getScore();

    public ArrayList<Card> playCard();

    public void newRound();
    public void endRound();

    public void selectCardsToExchange();
    public void exchangeCards(Player left, Player mate, Player right);
}
