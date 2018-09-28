package Blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;
    Deck() {
        deck = new ArrayList<Card>();
        for(int i = 0; i < 4; i++) {
            for(int j = 1; j < 14; j++) {
                deck.add(new Card(i, j));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card draw() {
        return deck.remove(0);
    }
}
