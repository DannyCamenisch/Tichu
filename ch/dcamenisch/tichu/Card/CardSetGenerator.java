package ch.dcamenisch.tichu.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CardSetGenerator {

	private static Card phoenix = new Card(CardSuit.SPECIAL, CardRank.PHOENIX);

    public static List<CardSet> generateCardSet(List<Card> originalCardList) {

		List<Card> cardList = new ArrayList<>(originalCardList);

		List<CardSet> cardSetList = new ArrayList<>();

		generate(cardList, cardSetList);

		handlePhoenix(cardList, cardSetList);


		return cardSetList;
	}

    private static void generate(List<Card> cardList, List<CardSet> cardSetList) {

		Map<CardSuit, List<Card>> cardListMapBySuit = new HashMap<>();
		Map<CardRank, List<Card>> cardListMapByRank = new HashMap<>();

		for (Card c : cardList) {
			if (!cardListMapBySuit.containsKey(c.suit)) {
				cardListMapBySuit.put(c.suit, new ArrayList<Card>());
			}
			cardListMapBySuit.get(c.suit).add(c);

			// 숫자별 정리
			if (!cardListMapByRank.containsKey(c.rank)) {
				cardListMapByRank.put(c.rank, new ArrayList<Card>());
			}
			cardListMapByRank.get(c.rank).add(c);
		}

		//cardListMapBySuit.remove(CardSuit.PHOENIX);

		final int straight = 5;

		for (Entry<CardSuit, List<Card>> e : cardListMapBySuit.entrySet()) {
			List<Card> sameSuitCardList = e.getValue();
			if (sameSuitCardList.size() >= straight) {
				checkStraight(sameSuitCardList, straight,
						CardSetType.STRAIGHT_FLUSH, cardSetList);
			}
		}


		for (Entry<CardRank, List<Card>> e : cardListMapByRank.entrySet()) {
			List<Card> cards = e.getValue();

			if (cards.size() == 4) {
				if (cards.contains(phoenix)) {
					continue;
				}

				CardSet fourCard = new CardSet(CardSetType.FOUR_CARDS, cards);
				cardSetList.add(fourCard);
			}

			if (cards.size() == 3) {
				CardSet triple = new CardSet(CardSetType.TRIPLE, cards);
				cardSetList.add(triple);
			}

			if (cards.size() == 2) {


				CardSet pair = new CardSet(CardSetType.PAIR, cards);
				cardSetList.add(pair);
			}
		}

		Map<CardSetType, List<CardSet>> cardSetMapByPattern = new HashMap<>();
		for (CardSet cardSet : cardSetList) {
			if (!cardSetMapByPattern.containsKey(cardSet.type)) {
				cardSetMapByPattern.put(cardSet.type, new ArrayList<CardSet>());
			}
			cardSetMapByPattern.get(cardSet.type).add(cardSet);
		}

		if (cardSetMapByPattern.containsKey(CardSetType.TRIPLE) && cardSetMapByPattern.containsKey(CardSetType.PAIR)) {
			for (CardSet tripleCardSet : cardSetMapByPattern.get(CardSetType.TRIPLE)) {
				for (CardSet pairCardSet : cardSetMapByPattern.get(CardSetType.PAIR)) {
					List<Card> fullHouseCardList = new ArrayList<>();
					fullHouseCardList.addAll(tripleCardSet.cards);
					fullHouseCardList.addAll(pairCardSet.cards);

					if (phoenixCount(fullHouseCardList) <= 1) {
						CardSet fullHouse = new CardSet(CardSetType.FULL_HOUSE, fullHouseCardList);
						cardSetList.add(fullHouse);
					}

				}
			}

		}


		if (cardSetMapByPattern.containsKey(CardSetType.PAIR)) {
			List<CardSet> pairCardSetList = cardSetMapByPattern
					.get(CardSetType.PAIR);
			if (cardSetMapByPattern.get(CardSetType.PAIR).size() > 1) {

				for (CardSet cs1 : pairCardSetList) {
					int straightCnt = 0;
					int c1Rank = cs1.cards.get(0).rank.power;

					List<Card> tempCardList = new ArrayList<>();
					tempCardList.addAll(cs1.cards);

					for (CardSet cs2 : pairCardSetList) {
						int c2Rank = cs2.cards.get(0).rank.power;

						if (c1Rank != c2Rank) {

							if (c2Rank == c1Rank + 1) {
								straightCnt++;
								c1Rank = c2Rank;
								tempCardList.addAll(cs2.cards);
							} else {
								break;
							}
						}
					}

					if (straightCnt > 0) {
						if (phoenixCount(tempCardList) <= 1) {
							CardSet stairCardSet = new CardSet(
                                CardSetType.MULTIPLE_PAIR, tempCardList);
							cardSetList.add(stairCardSet);
						}
					}
				}

			}
		}


		checkStraight(cardList, straight, CardSetType.STRAIGHT, cardSetList);


		for (Card c : cardList) {
			List<Card> card = new ArrayList<>();
			card.add(c);
			CardSet singleCardSet = new CardSet(CardSetType.SINGLE_CARD,
					card);
			cardSetList.add(singleCardSet);

		}
	}


	private static void handlePhoenix(List<Card> cardList, List<CardSet> cardSetList) {
		// TODO 봉황로직은 추후 정리할 필요가 있다. 대충 만들어서 돌긴 하는데 이유를 모른다..
		// 봉황이 있으면?! 더미카드를 만들어보자!
		Set<Card> dummyCardList = new HashSet<>();
		boolean hasPhoenix = false;
		for (Card c : cardList) {
			if (c.rank.equals(CardRank.PHOENIX)) {
				hasPhoenix = true;
			}
		}

		Set<CardRank> dummyRanks = new HashSet<>();
		if (hasPhoenix) {
			for (Card c : cardList) {
				if (!c.rank.equals(CardRank.DOG)
						&& !c.rank.equals(CardRank.DRAGON)
						&& !c.rank.equals(CardRank.MAHJONG)
						&& !c.rank.equals(CardRank.PHOENIX)) {
					dummyRanks.add(c.rank);
				}
			}
		}

		for (CardRank rank : dummyRanks) {
			Card dummy = new Card(CardSuit.SPECIAL, rank);
			dummyCardList.add(dummy);
		}

		cardList.addAll(dummyCardList);

		generate(cardList, cardSetList);
	}


	private static int phoenixCount(List<Card> cardList) {
		int total = 0;
		for (Card cd : cardList) {
			if (cd.rank.equals(CardRank.PHOENIX)) {
				total++;
			}
		}
		return total;
	}


	public static void checkStraight(List<Card> chkCardList, int straight, CardSetType cardSetPattern, List<CardSet> cardSetList) {

		if (cardSetPattern.equals(CardSetType.STRAIGHT_FLUSH)) {
			chkCardList.remove(phoenix);
		}

		for (Card c1 : chkCardList) {
			int straightCnt = 0; 
			int c1Rank = c1.rank.power;
			List<Card> tempCardList = new ArrayList<>();
			tempCardList.add(c1);

			for (Card c2 : chkCardList) {
				int c2Rank = c2.rank.power;

				if (c1Rank != c2Rank) {
					if (c2Rank == c1Rank + 1) {
						straightCnt++;
						c1Rank = c2Rank; 
						tempCardList.add(c2);
					} else {
						break;
					}
				}
			}


			if (straightCnt >= straight - 1) {
				if (phoenixCount(tempCardList) <= 1) {
					CardSet straightCardSet = new CardSet(cardSetPattern, tempCardList);
					cardSetList.add(straightCardSet);
				}

			}
		}
	}


	public static Map<CardSetType, List<CardSet>> getCardSetByPattern(List<CardSet> cardSetList) {

		Map<CardSetType, List<CardSet>> cardSetMapByPattern = new HashMap<>();

        for (CardSet cardSet : cardSetList) {
			if (!cardSetMapByPattern.containsKey(cardSet.type)) {
				cardSetMapByPattern.put(cardSet.type,
						new ArrayList<CardSet>());
			}
			cardSetMapByPattern.get(cardSet.type).add(cardSet);
		}

		return cardSetMapByPattern;
	}

}





/*
package ch.dcamenisch.tichu.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardSetGenerator {
    private final static int straight = 5;

    public static List<CardSet> generateCardSet(List<Card> originalCardList) {
        List<Card> cardList = new ArrayList<Card>(originalCardList);
        List<CardSet> cardSetList = new ArrayList<CardSet>();

        generate(cardList, cardSetList);
        
        return cardSetList;
    }

    private static void generate(List<Card> cardList, List<CardSet> cardSetList) {
        Map<CardSuit, List<Card>> cardListMapBySuit = new HashMap<>();
        Map<CardRank, List<Card>> cardListMapByRank = new HashMap<>();
        
        // fill maps with cards
        for(Card c : cardList) {
            if(!cardListMapBySuit.containsKey(c.suit)) {
                cardListMapBySuit.put(c.suit, new ArrayList<Card>());
            }
            cardListMapBySuit.get(c.suit).add(c);

            if(!cardListMapByRank.containsKey(c.rank)) {
                cardListMapByRank.put(c.rank, new ArrayList<Card>());
            }
            cardListMapByRank.get(c.rank).add(c);
        }

        
        


    }




}
*/