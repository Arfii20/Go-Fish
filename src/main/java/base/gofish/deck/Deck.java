package base.gofish.deck;


import java.io.Serial;
import java.io.Serializable;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Deck implements Serializable {
    private List<Card> deck = new ArrayList<Card>();
    @Serial
    private static final long serialVersionUID = 5;


    public Deck(){
        int count;
        for (Suit suit: Suit.values()) {
            count = 2;
            for (Name name: Name.values()) {
                deck.add(new Card(suit, name, count++));
            }
        }
    }

    public Card draw(){
        Card lastCard = null;
        if (!deck.isEmpty()) {
            lastCard = deck.remove(deck.size() - 1);
        }
        return lastCard;
    }

    public void remove(Card card){
        deck.remove(card);
    }

    public void shuffle(Random random){
        Collections.shuffle(deck, random);
    }

    public int size(){
        return deck.size();
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }
}
