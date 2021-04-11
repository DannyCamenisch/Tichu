import java.util.ArrayList;
import java.util.List;

import ch.dcamenisch.tichu.Game;
import ch.dcamenisch.tichu.Card.Card;
import ch.dcamenisch.tichu.Card.CardRank;
import ch.dcamenisch.tichu.Trick.CardSet;
import ch.dcamenisch.tichu.Trick.CardSetGenerator;
import ch.dcamenisch.tichu.Card.CardSuit;

public class Main {
    public static void main(String[] args) {
        //Game game = new Game();
        //game.startGame();

        
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(CardSuit.JADE, CardRank.FIVE));
        cards.add(new Card(CardSuit.JADE, CardRank.SIX));
        cards.add(new Card(CardSuit.SWORD, CardRank.FIVE));
        cards.add(new Card(CardSuit.SWORD, CardRank.SIX));
        cards.add(new Card(CardSuit.SWORD, CardRank.SEVEN));
        cards.add(new Card(CardSuit.JADE, CardRank.SEVEN));
        cards.add(new Card(CardSuit.SPECIAL, CardRank.PHOENIX));

        List<CardSet> cardSets = CardSetGenerator.generateCardSet(cards);

        for(int i = 0; i < cardSets.size(); i++) {
            System.out.println("Card Set: " + i);
            System.out.println(cardSets.get(i).toString());
        }
        
    }
}