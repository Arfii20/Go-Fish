package base.gofish;

import base.gofish.deck.Card;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Player implements Comparable<Player>, Serializable {
    private String name;
    private List<Card> cards;
    private int value;
    private int points;

    @Serial
    private static final long serialVersionUID = 1L;

    public Player(String name, int value){
        this.name = name;
        this.value = value;
        this.cards = new ArrayList<>();
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public List<Card> getCards() {
        Collections.sort(cards);
        return cards;
    }

    public Card getRandomCard(Random random) throws IllegalArgumentException {
        return this.cards.get(random.nextInt(0, this.cards.size()));
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
        Iterator<Card> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getValue() == val) {
                tempCards.add(card);
                iterator.remove();
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

    public int totalCards() {
        return this.cards.size();
    }

    public boolean cardFinished() {
        return (cards.size() == 0);
    }

    public int getValue(){
        return this.value;
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
