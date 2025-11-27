package use_case.studyDeck;

import java.util.ArrayList;

import entity.DeckManagement.StudyCard;
import entity.DeckManagement.StudyDeck;

public interface StudyDeckInputBoundary {
    void execute(StudyDeckInputData inputData, StudyDeckAction action);

    void createNewDeck(String title, String description);

    void addCardToCurrentDeck(String question, ArrayList<String> answers, int correctAnswerIndex);

    void removeCardFromCurrentDeck(int cardIndex);

    void updateCardInCurrentDeck(int cardIndex, String question, ArrayList<String> answers, int correctAnswerIndex);

    void saveCurrentDeck();

    void loadDeck(String deckName);

    void unloadDeck();

    StudyDeck getCurrentDeck();

//    StudyCard generateCardWithAI(String topic, String sourceText);
//
//    StudyCard generateAndAddCardWithAI(String topic, String sourceText);
//
//    ArrayList<StudyCard> generateAndAddMultipleCardsWithAI(String topic, String sourceText, int numberOfCards);

}
