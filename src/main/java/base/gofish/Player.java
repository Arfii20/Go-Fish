package base.gofish;

import base.gofish.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    String name;
    List<Card> cards;

    public Player(String name){
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public void addToHand(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
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

    @Override
    public String toString() {
        return this.name;
    }
}
