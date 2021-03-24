package ch.dcamenisch.tichu.Card;

import java.util.ArrayList;
import java.util.Collections;

import ch.dcamenisch.tichu.Player;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        makeCards();
    }

    private void makeCards() {
        deck = new ArrayList<Card>();

        for(CardRank r : CardRank.values()) {
            if (r == CardRank.DRAGON || r == CardRank.PHOENIX || r == CardRank.DOG || r == CardRank.MAHJONG) {
                deck.add(new Card(CardSuit.SPECIAL, r));
                continue;
            }

            for(CardSuit s : CardSuit.values()) {
                if(s == CardSuit.SPECIAL) continue;
                deck.add(new Card(s, r));
            }
        }
    }

    /**
     * deals each player an equal amount of random cards from the deck
     */
    public void dealCards(Player[] players) {
        Collections.shuffle(deck);

        System.out.println("[GAME] - Dealing Cards...");

        int i = 0;
        int p = 0;
        while(i < deck.size()) {
            players[p].receiveCard(deck.get(i));

            p = (p + 1) % players.length;
            i++;
        }
    }

}