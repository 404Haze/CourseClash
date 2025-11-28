// Core entity representing a collection of study cards
// Archie
package entity.DeckManagement;

import entity.DeckManagement.StudyCard;
import java.util.ArrayList;
import java.util.List;

public class StudyDeck {
    private final String title;
    private final String description;
    private final ArrayList<StudyCard> deck;
    private final int id;

    public StudyDeck(String title, String description, ArrayList<StudyCard> deck, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.deck = new ArrayList<>(deck);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public List<StudyCard> getDeck() {
        ArrayList<StudyCard> deckCopy = new ArrayList<>();
        for (StudyCard card : this.deck) {
            deckCopy.add(card);
        }
        return deckCopy;
    }

    public int getCardCount() {
        return this.deck.size();
    }

    public boolean isEmpty(){
        return this.deck.isEmpty();
    }
}