package base.gofish;

import base.gofish.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player>{
    private String name;
    private List<Card> cards;
    private int points;

    public Player(String name){
        this.name = name;
        this.cards = new ArrayList<>();
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addToHand(Card card) {
        this.cards.add(card);
        if (cardCount(card.getValue()) == 4) {
            points++;
            this.clearCardFromHand(card.getValue());
        }
    }

    public void addToHand(List<Card> cards) {
        this.cards.addAll(cards);
        if (this.cardCount(cards.get(0).getValue()) == 4) {
            points++;
            this.clearCardFromHand(cards.get(0).getValue());
        }
    }

    public List<Card> clearCardFromHand(int val) {
        List<Card> tempCards = new ArrayList<>();
        for (Card card: cards) {
            if (card.getValue() == val) {
                tempCards.add(card);
                this.cards.remove(card);
            }
        }
        return tempCards;
    }

    public boolean hasCard(Card cardIn) {
        for (Card card: this.cards) {
            if (card.getValue() == cardIn.getValue()) {
                return true;
            }
        }
        return false;
    }

    public int cardCount(int val) {
        int count = 0;
        for (Card card: cards) {
            if (card.getValue() == val) {
                count++;
            }
        }
        return count;
    }

    public boolean cardFinished() {
        return (cards.size() == 0);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Player playerIn) {
        return Integer.compare(playerIn.getPoints(), this.getPoints());
    }
}
