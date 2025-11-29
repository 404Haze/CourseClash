package use_case.studyDeck;

import entity.DeckManagement.StudyCard;
import entity.DeckManagement.StudyDeck;

import java.util.ArrayList;
import java.util.List;

public class StudyDeckInteractor implements StudyDeckInputBoundary {
    private final StudyDeckDataAccessInterface studyDeckDataAccessObject;
    private final StudyDeckOutputBoundary studyDeckOutputBoundary;
    private StudyDeck currentDeck;

    public StudyDeckInteractor(StudyDeckDataAccessInterface studyDeckDataAccessObject,
            StudyDeckOutputBoundary studyDeckOutputBoundary) {
        this.studyDeckDataAccessObject = studyDeckDataAccessObject;
        this.studyDeckOutputBoundary = studyDeckOutputBoundary;
    }

    @Override
    public void execute(StudyDeckInputData inputData, StudyDeckAction action) {
        switch (action) {
            case ADD_DECK -> {
                studyDeckDataAccessObject.saveDeck(inputData.getStudyDeck());
            }
            case REMOVE_DECK -> {
                studyDeckDataAccessObject.deleteDeck(inputData.getStudyDeck().getTitle());
            }
            case EDIT_DECK -> {
                studyDeckDataAccessObject.updateDeck(inputData.getStudyDeck());
            }
            case LOAD_ALL -> {
                break;
            }
        }
        List<StudyDeck> decks = studyDeckDataAccessObject.getAllDecks();
        studyDeckOutputBoundary.prepareView(new StudyDeckOutputData(decks));
    }

    // Create a new empty deck with the specified title and description.
    // @param title The title for the new deck.
    // @param description The description for the new deck.
    public void createNewDeck(String title, String description) {
        // Create a new empty deck list
        ArrayList<StudyCard> deckList = new ArrayList<>();
        // Create a new StudyDeck with a unique ID (using timestamp)
        StudyDeck newDeck = new StudyDeck(title, description, deckList, (int) System.currentTimeMillis());
        // Set this as the current deck
        this.currentDeck = newDeck;
    }

    // Add a new StudyCard to the current deck using CardCreator.
    // @param question The question text for the new card.
    // @param answers The list of answer options for the new card.
    // @param correctAnswerIndex The index of the correct answer in the answers
    // list.
    public void addCardToCurrentDeck(String question, ArrayList<String> answers, int correctAnswerIndex) {
        if (currentDeck != null) {
            // Use CardCreator to create the new card
            StudyCard newCard = new StudyCard(question, answers, correctAnswerIndex);
            // Create a new deck with the added card (since deck is immutable)
            ArrayList<StudyCard> newCards = new ArrayList<>(currentDeck.getDeck());
            newCards.add(newCard);
            // Update the current deck with the new list
            this.currentDeck = new StudyDeck(currentDeck.getTitle(), currentDeck.getDescription(), newCards,
                    currentDeck.getId());
        }
        // If currentDeck is null, do nothing (no deck to add to)
    }

    // Remove a StudyCard from the current deck by index.
    // @param cardIndex The index of the card to remove.
    public void removeCardFromCurrentDeck(int cardIndex) {
        if (currentDeck != null && cardIndex >= 0 && cardIndex < currentDeck.getDeck().size()) {
            // Create a new deck without the card at the specified index
            ArrayList<StudyCard> newCards = new ArrayList<>(currentDeck.getDeck());
            newCards.remove(cardIndex);
            // Update the current deck with the new list
            this.currentDeck = new StudyDeck(currentDeck.getTitle(), currentDeck.getDescription(), newCards,
                    currentDeck.getId());
        }
        // If currentDeck is null or index is invalid, do nothing
    }

    // Update an existing StudyCard in the current deck by index.
    // @param cardIndex The index of the card to update.
    // @param question The new question text for the card.
    // @param answers The new list of answer options for the card.
    // @param correctAnswerIndex The new index of the correct answer in the answers
    // list.
    public void updateCardInCurrentDeck(int cardIndex, String question, ArrayList<String> answers, int correctAnswerIndex) {
        if (currentDeck != null && cardIndex >= 0 && cardIndex < currentDeck.getDeck().size()) {
            // Create the updated card using CardCreator
            StudyCard updatedCard = new StudyCard(question, answers, correctAnswerIndex);
            // Create a new deck with the updated card
            ArrayList<StudyCard> newCards = new ArrayList<>(currentDeck.getDeck());
            newCards.set(cardIndex, updatedCard);
            // Update the current deck with the new list
            this.currentDeck = new StudyDeck(currentDeck.getTitle(), currentDeck.getDescription(), newCards,
                    currentDeck.getId());
        }
        // If currentDeck is null or index is invalid, do nothing
    }

    // Save the current deck to local storage via LocalDeckManager.
    public void saveCurrentDeck() {
        if (currentDeck != null) {
            studyDeckDataAccessObject.saveDeck(currentDeck);
        }
        // If currentDeck is null, do nothing (no deck to save)
    }

    // Load an existing deck from local storage by name.
    // @param deckName The name of the deck to load.
    public void loadDeck(String deckName) {
        StudyDeck loadedDeck = studyDeckDataAccessObject.getDeck(deckName);
        if (loadedDeck != null) {
            List<StudyCard> cards = loadedDeck.getDeck();
            cards.removeIf(card -> card.getQuestion().contains("Error: Question not found"));
            this.currentDeck = new StudyDeck(
                loadedDeck.getTitle(), loadedDeck.getDescription(),
                new ArrayList<>(cards), loadedDeck.getId());
        }
        // If deck doesn't exist, currentDeck remains unchanged
    }
    
    // unload deck
    public void unloadDeck() {
        this.currentDeck = null;
    }

    // Delete an existing deck from local storage by name.
    // @param deckName The name of the deck to delete.
    public void deleteDeck(String deckName) {
        studyDeckDataAccessObject.deleteDeck(deckName);
        // If the deleted deck was the current deck, clear currentDeck
        if (currentDeck != null && currentDeck.getTitle().equals(deckName)) {
            this.currentDeck = null;
        }
    }

    // Rename the current deck and update it in local storage.
    // @param newDeckName The new name for the current deck.
    public void renameCurrentDeck(String newDeckName) {
        if (currentDeck != null) {
            // Create a new deck with the updated title
            StudyDeck newDeck = new StudyDeck(newDeckName, currentDeck.getDescription(),
                    new ArrayList<>(currentDeck.getDeck()), currentDeck.getId());
            // Update the current deck reference
            this.currentDeck = newDeck;
            // Save the renamed deck to storage
            studyDeckDataAccessObject.updateDeck(newDeck);
        }
        // If currentDeck is null, do nothing
    }

    // Switch to working with an existing deck by name.
    // @param deckName The name of the deck to switch to.
    public void switchToDeck(String deckName) {
        StudyDeck loadedDeck = studyDeckDataAccessObject.getDeck(deckName);
        if (loadedDeck != null) {
            this.currentDeck = loadedDeck;
        }
        // If deck doesn't exist, currentDeck remains unchanged
    }

    // Get the current deck being managed.
    // @return The current StudyDeck object.
    public StudyDeck getCurrentDeck() {
        return currentDeck;
    }

    // Get a list of all deck names from local storage.
    // @return A list of deck names available in local storage.
    public ArrayList<String> getAllDeckNames() {
        List<StudyDeck> allDecks = studyDeckDataAccessObject.getAllDecks();
        ArrayList<String> deckNames = new ArrayList<>();
        for (StudyDeck deck : allDecks) {
            deckNames.add(deck.getTitle());
        }
        return deckNames;
    }

//     SCRAPPED: Generate a new StudyCard using AI with the specified topic and source
//     text.
//     The generated card is returned but NOT automatically added to the current
//     deck.
//     @param topic The topic for the generated card.
//     @param sourceText The source text to base the card on.
//     @return The generated StudyCard object, or null if generation fails.
//    public StudyCard generateCardWithAI(String topic, String sourceText) {
//        // Use CardGenerator to generate the card via AI
//        return new CardGenerator("").generateCard(topic, sourceText);
//    }
//
//     SCRAPPED: Generate a new StudyCard using AI with the specified topic and source
//     text,
//     and automatically add it to the current deck.
//     @param topic The topic for the generated card.
//     @param sourceText The source text to base the card on.
//     @return The generated StudyCard object, or null if generation fails.
//    public StudyCard generateAndAddCardWithAI(String topic, String sourceText) {
//        if (currentDeck != null) {
//            // Use CardGenerator to generate the card via AI
//            StudyCard generatedCard = new CardGenerator("").generateCard(topic, sourceText);
//
//            if (generatedCard != null) {
//                // Add the generated card to the current deck
//                ArrayList<StudyCard> newCards = new ArrayList<>(currentDeck.getDeck());
//                newCards.add(generatedCard);
//                // Update the current deck with the new list
//                this.currentDeck = new StudyDeck(currentDeck.getTitle(), currentDeck.getDescription(), newCards,
//                        currentDeck.getId());
//                return generatedCard;
//            }
//        }
//        // If currentDeck is null or generation fails, return null
//        return null;
//    }
//
//     SCRAPPED: Generate multiple StudyCards using AI with the specified topic and
//     source text,
//     and automatically add them to the current deck.
//     @param topic The topic for the generated cards.
//     @param sourceText The source text to base the cards on.
//     @param numberOfCards The number of cards to generate.
//     @return A list of generated StudyCard objects, or empty list if generation
//     fails.
//    public ArrayList<StudyCard> generateAndAddMultipleCardsWithAI(String topic, String sourceText, int numberOfCards) {
//        ArrayList<StudyCard> generatedCards = new ArrayList<>();
//
//        if (currentDeck != null) {
//            // Generate multiple cards using CardGenerator
//            for (int i = 0; i < numberOfCards; i++) {
//                StudyCard generatedCard = new CardGenerator("").generateCard(topic, sourceText);
//                if (generatedCard != null) {
//                    generatedCards.add(generatedCard);
//                }
//            }
//
//            if (!generatedCards.isEmpty()) {
//                // Add all generated cards to the current deck
//                ArrayList<StudyCard> newCards = new ArrayList<>(currentDeck.getDeck());
//                newCards.addAll(generatedCards);
//                // Update the current deck with the new list
//                this.currentDeck = new StudyDeck(currentDeck.getTitle(), currentDeck.getDescription(), newCards,
//                        currentDeck.getId());
//            }
//        }
//        // If currentDeck is null or generation fails, return empty list
//        return generatedCards;
//    }

}
