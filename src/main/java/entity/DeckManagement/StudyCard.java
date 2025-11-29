// Entity for Study Cards
// Archie
package entity.DeckManagement;

import java.util.ArrayList;

public class StudyCard {
    private final String question;
    private final ArrayList<String> answers;
    private final int solutionId;

    // Defensive copying from Clean Architecture
    public StudyCard(String question, ArrayList<String> answers,  int solutionId) {
        this.question = question;
        this.answers = new ArrayList<>(answers);
        this.solutionId = solutionId;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return new ArrayList<>(answers);
    }

    public int getSolutionId() {
        return solutionId;
    }
}
