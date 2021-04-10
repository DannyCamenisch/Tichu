package ch.dcamenisch.tichu.Card;

public class Card implements Comparable<Card>{
    public final CardSuit suit;
    public final CardRank rank;

    public Card (CardSuit suit, CardRank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getPoints() {
        return rank.getPoints();
    }

    @Override
    public String toString() {
        return suit + "_" + rank;
    }

    @Override
    public int compareTo(Card card) {
        return this.rank.getPower() - card.rank.getPower();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card) {
            Card c = (Card) obj;
            return this.suit.equals(c.suit) && this.rank.equals(c.rank);
        } else {
            return false;
        }
    }
}

