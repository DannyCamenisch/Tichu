package ch.dcamenisch.tichu.Card;

import java.util.ArrayList;
import java.util.Collections;

import ch.dcamenisch.tichu.Player.Player;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        generateCards();
    }

    /**
     * generates each card of the deck, DUMMY suit gets ignored
     */
    private void generateCards() {
        deck = new ArrayList<Card>();

        for(CardRank r : CardRank.values()) {
            if (r == CardRank.DRAGON || r == CardRank.PHOENIX || r == CardRank.DOG || r == CardRank.MAHJONG) {
                deck.add(new Card(CardSuit.SPECIAL, r));
                continue;
            }

            for(CardSuit s : CardSuit.values()) {
                if(s == CardSuit.SPECIAL || s == CardSuit.DUMMY) continue;
                deck.add(new Card(s, r));
            }
        }

        assert deck.size() == 56 : "[ERROR] - deck size is wrong"; // deck should now consist of 56 cards
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