package ch.dcamenisch.tichu.Trick;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import ch.dcamenisch.tichu.Card.Card;
import ch.dcamenisch.tichu.Card.CardRank;
import ch.dcamenisch.tichu.Card.CardSuit;

public class CardSetGenerator {
	/* Constants */
	private static final int straight = 5;
	private static final Card phoenix = new Card(CardSuit.SPECIAL, CardRank.PHOENIX);


    public static List<CardSet> generateCardSet(List<Card> originalCardList) {
		List<Card> cardList = new ArrayList<>(originalCardList); // destroy reference to originalCardList
		List<CardSet> cardSetList = new ArrayList<>();

		if(cardList.contains(phoenix)) {
			handlePhoenix(cardList, cardSetList);
		} else {
			generate(cardList, cardSetList);
		}

		return cardSetList;
	}


    private static void generate(List<Card> cardList, List<CardSet> cardSetList) {
		Map<CardSuit, List<Card>> cardListMapBySuit = new HashMap<>();
		Map<CardRank, List<Card>> cardListMapByRank = new HashMap<>();

		// create list of all cards sorted by either rank or suit
		for (Card c : cardList) {
			if (!cardListMapBySuit.containsKey(c.suit)) {
				cardListMapBySuit.put(c.suit, new ArrayList<Card>());
			}
			cardListMapBySuit.get(c.suit).add(c);

			if (!cardListMapByRank.containsKey(c.rank)) {
				cardListMapByRank.put(c.rank, new ArrayList<Card>());
			}
			cardListMapByRank.get(c.rank).add(c);
		}

		cardListMapBySuit.remove(CardSuit.DUMMY);

		// create all single cards
		for (Card c : cardList) {
			List<Card> card = new ArrayList<>();
			card.add(c);
			CardSet singleCardSet = new CardSet(CardSetType.SINGLE_CARD, card);
			cardSetList.add(singleCardSet);
		}

		// create all pairs, tripple and 4-bombs
		for (Entry<CardRank, List<Card>> e : cardListMapByRank.entrySet()) {
			List<Card> cards = e.getValue();

			if (cards.size() >= 4) {
				CardSet fourCard = new CardSet(CardSetType.FOUR_BOMB, cards);
				cardSetList.add(fourCard);
			}

			if (cards.size() >= 3) {
				Set<CardSuit> usedSuits = new HashSet<>();
				Set<CardSet> cs = new TreeSet<>();

				for (Card c1 : cards) {
					if(usedSuits.contains(c1.suit)) continue;
					
					for (Card c2 : cards) {
						if(usedSuits.contains(c2.suit)) continue;
						if(c1.equals(c2)) continue;
						
						for (Card c3 : cards) {
							if(usedSuits.contains(c3.suit)) continue;
							if(c1.equals(c3)) continue;
							if(c2.equals(c3)) continue;

							List<Card> tempCardList = new ArrayList<>();
							tempCardList.add(c1);
							tempCardList.add(c2);
							tempCardList.add(c3);

							CardSet triple = new CardSet(CardSetType.TRIPLE, tempCardList);
							cs.add(triple);
							usedSuits.add(c1.suit);
						}
					}
				}

				cardSetList.addAll(cs);
			}

			if (cards.size() >= 2) {
				Set<CardSuit> usedSuits = new HashSet<>();

				for (Card c1 : cards) {
					if(usedSuits.contains(c1.suit)) continue;

					for (Card c2 : cards) {
						if(usedSuits.contains(c2.suit)) continue;
						if(c1.equals(c2)) continue;

						List<Card> tempCardList = new ArrayList<>();
						tempCardList.add(c1);
						tempCardList.add(c2);

						CardSet pair = new CardSet(CardSetType.PAIR, tempCardList);
						cardSetList.add(pair);
						usedSuits.add(c1.suit);
					}
				}
			}
		}

		// create multiple pairs and full house by combining tripple and pairs
		Map<CardSetType, List<CardSet>> cardSetMapByPattern = new HashMap<>();
		for (CardSet cardSet : cardSetList) {
			if (!cardSetMapByPattern.containsKey(cardSet.type)) {
				cardSetMapByPattern.put(cardSet.type, new ArrayList<CardSet>());
			}
			cardSetMapByPattern.get(cardSet.type).add(cardSet);
		}

		// full house
		if (cardSetMapByPattern.containsKey(CardSetType.TRIPLE) && cardSetMapByPattern.containsKey(CardSetType.PAIR)) {
			for (CardSet tripleCardSet : cardSetMapByPattern.get(CardSetType.TRIPLE)) {
				for (CardSet pairCardSet : cardSetMapByPattern.get(CardSetType.PAIR)) {
					if (tripleCardSet.cards.get(0).rank.equals(pairCardSet.cards.get(0).rank)) continue;

					List<Card> fullHouseCardList = new ArrayList<>();
					fullHouseCardList.addAll(tripleCardSet.cards);
					fullHouseCardList.addAll(pairCardSet.cards);

					if (dummyCount(fullHouseCardList) <= 1) {
						CardSet fullHouse = new CardSet(CardSetType.FULL_HOUSE, fullHouseCardList);
						cardSetList.add(fullHouse);
					}
				}
			}
		}

		// multiple pairs
		if (cardSetMapByPattern.containsKey(CardSetType.PAIR)) {
			List<CardSet> pairCardSetList = cardSetMapByPattern.get(CardSetType.PAIR);
			
			if (pairCardSetList.size() > 1) {
				for (CardSet cs1 : pairCardSetList) {
					int c1Rank = cs1.cards.get(0).rank.getPower();

					List<Card> tempCardList = new ArrayList<>();
					tempCardList.addAll(cs1.cards);

					for (CardSet cs2 : pairCardSetList) {
						int c2Rank = cs2.cards.get(0).rank.getPower();

						if (c2Rank == c1Rank + 1) {
							c1Rank = c2Rank;
							tempCardList.addAll(cs2.cards);

							if (dummyCount(tempCardList) <= 1) {
								CardSet multiPairCardSet = new CardSet(CardSetType.MULTIPLE_PAIR, tempCardList);
								tempCardList = new ArrayList<>(tempCardList); // destroy reference to old tempCardList
								cardSetList.add(multiPairCardSet);
							}
						}
					}
				}
			}
		}

		// create straights and straight-bombs
		for (Entry<CardSuit, List<Card>> e : cardListMapBySuit.entrySet()) {
			List<Card> sameSuitCardList = e.getValue();
			if (sameSuitCardList.size() >= straight && !sameSuitCardList.get(0).suit.equals(CardSuit.SPECIAL)) {
				checkStraight(sameSuitCardList, CardSetType.STRAIGHT_BOMB, cardSetList);
			}
		}
		checkStraight(cardList, CardSetType.STRAIGHT, cardSetList);
	}


	private static void handlePhoenix(List<Card> cardList, List<CardSet> cardSetList) {
		Set<Card> dummyCardList = new HashSet<>();
		Set<CardRank> dummyRanks = new HashSet<>();

		for (CardRank rank : CardRank.values()) {
			if (!rank.equals(CardRank.DOG)
					&& !rank.equals(CardRank.DRAGON)
					&& !rank.equals(CardRank.MAHJONG)
					&& !rank.equals(CardRank.PHOENIX)) {
				dummyRanks.add(rank);
			}
		}

		for (CardRank rank : dummyRanks) {
			Card dummy = new Card(CardSuit.DUMMY, rank);
			dummyCardList.add(dummy);
		}

		cardList.addAll(dummyCardList);
		cardList.remove(phoenix);

		generate(cardList, cardSetList);
	}

	// count the amount of dummy cards in a list of cards
	private static int dummyCount(List<Card> cardList) {
		int total = 0;
		for (Card cd : cardList) {
			if (cd.suit.equals(CardSuit.DUMMY)) {
				total++;
			}
		}
		return total;
	}

	// check if a list of cards contains a straight and add the straight to the cardSetList
	public static void checkStraight(List<Card> chkCardList, CardSetType cardSetPattern, List<CardSet> cardSetList) {
		for (Card c1 : chkCardList) {
			int straightCnt = 0; 
			int c1Rank = c1.rank.getPower();

			List<Card> tempCardList = new ArrayList<>();
			tempCardList.add(c1);

			for (Card c2 : chkCardList) {
				int c2Rank = c2.rank.getPower();

				if (c2Rank == c1Rank + 1) {
					straightCnt++;
					c1Rank = c2Rank; 
					tempCardList.add(c2);

					if (straightCnt >= straight - 1 && dummyCount(tempCardList) <= 1) {
						CardSet straightCardSet = new CardSet(cardSetPattern, tempCardList);
						tempCardList = new ArrayList<>(tempCardList); // destroy reference to old tempCardList
						cardSetList.add(straightCardSet);
					}
				}
			}
		}
	}
}