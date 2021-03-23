package ch.dcamenisch.tichu;

import java.util.ArrayList;
import java.util.Collections;

import ch.dcamenisch.tichu.Card.Color;
import ch.dcamenisch.tichu.Card.Value;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();

        for(Value v : Value.values()) {
            if (v == Value.DRAGON || v == Value.PHOENIX || v == Value.DOG || v == Value.MAHJONG) {
                deck.add(new Card(Color.SPECIAL, v));
                continue;
            }

            for(Color c : Color.values()) {
                if(c == Color.SPECIAL) continue;
                deck.add(new Card(c, v));
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