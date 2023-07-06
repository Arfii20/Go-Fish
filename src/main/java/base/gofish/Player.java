package base.gofish;

import base.gofish.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
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

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public void clearCardFromHand(int val) {
        for (Card card: cards) {
            if (card.getValue() == val) {
                this.removeCard(card);
            }
        }
    }

    public int hasCard(Card cardIn) {
        int counter = 0;
        for (Card card: this.cards) {
            if (card.getValue() == cardIn.getValue()) {
                counter++;
            }
        }
        return counter;
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
}
