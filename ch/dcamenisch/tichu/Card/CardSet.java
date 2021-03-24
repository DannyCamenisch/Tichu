package ch.dcamenisch.tichu.Card;

import java.util.List;

public class CardSet implements Comparable<CardSet> {
    public CardSetType type;
    public List<Card> cards;
    
    public CardSet(List<Card> cards) {
        makeCardSet(cards);
    }

    private void makeCardSet(List<Card> cards) {

        // TODO: implement 

        this.type = null;
        this.cards = null;
    }

    public int getPoints() {
        int res = 0;
        for(Card c : cards) {
            res += c.getPoints();
        }
        return res;
    }

    @Override
    public String toString() {
        String cardSet = "";

        for(Card c : cards) {
            cardSet = cardSet.concat(", ").concat(c.toString());
        }

        return cardSet;
    }

    @Override
    public int compareTo(CardSet o) {
        if(this.type.power < o.type.power) return -1;
        else if(this.type.power > o.type.power) return 1;
        else if(!this.type.equals(o.type)) return 1;
        else {
            int thisCardPower = this.cards.get(0).rank.power;
            int otherCardPower = o.cards.get(0).rank.power;

            if(thisCardPower < otherCardPower) return -1;
            else if(thisCardPower > otherCardPower) return 1;
            else return 0;
        }
    }
}
