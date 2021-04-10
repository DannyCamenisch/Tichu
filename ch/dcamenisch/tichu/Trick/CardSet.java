package ch.dcamenisch.tichu.Trick;

import java.util.List;

import ch.dcamenisch.tichu.Card.Card;

public class CardSet implements Comparable<CardSet> {
    public CardSetType type;
    public List<Card> cards;
    
    public CardSet(CardSetType type, List<Card> cards) {
        this.cards = cards;
        this.type = type;
    }

    public CardSet(List<Card> cards) throws CardSetException {
        makeCardSet(cards);
    }

    // TODO: in case of a straight with a phoenix at start/end, selecte the straight of higher "power"
    private void makeCardSet(List<Card> cards) throws CardSetException {
        List<CardSet> cardSets = CardSetGenerator.generateCardSet(cards);

        for (CardSet cs : cardSets) {
			if (cs.cards.size() == cards.size()) {
				this.type = cs.type;
				this.cards = cs.cards;
				return;
			}
		}

		throw new CardSetException("These cards are not a valid play!");
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
            cardSet += cardSet.length() == 0 ? c.toString() : ", " + c.toString();
        }

        return cardSet;
    }

    /**
     * compares two cardsets and returns 1 if this card set is stronger, 0 if they are equal and -1
     * if this cardset is weaker
     */
    @Override
    public int compareTo(CardSet o) {
        if(this.type.getPower() < o.type.getPower()) return -1;
        else if(this.type.getPower() > o.type.getPower()) return 1;
        else if(!this.type.equals(o.type)) return 1;
        else {
            int thisCardPower = this.cards.get(0).rank.getPower();
            int otherCardPower = o.cards.get(0).rank.getPower();

            if(thisCardPower < otherCardPower) return -1;
            else if(thisCardPower > otherCardPower) return 1;
            else return 0;
        }
    }
}