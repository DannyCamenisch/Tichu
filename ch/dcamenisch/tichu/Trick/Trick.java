package ch.dcamenisch.tichu.Trick;

import java.util.LinkedList;
import java.util.List;

public class Trick {
    List<CardSet> trick;
    
    public Trick() {
        trick = new LinkedList<CardSet>();
    }

    public void addCards(CardSet cs) {
        trick.add(cs);
    }

    public int getPoints() {
        int counter = 0;
        
        for(CardSet cs : trick) {
            counter += cs.getPoints();
        }

        return counter;
    }

}
