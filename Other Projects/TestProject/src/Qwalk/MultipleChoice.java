package Qwalk;

/**
 * Created by Kraft on 2017-03-31.
 */
public class MultipleChoice extends Question{

    private String[] alternatives;

    public MultipleChoice(String questionText, String[] alternatives, int answerIndex){
        super(questionText, answerIndex);
        this.alternatives = alternatives;
        if (alternatives.length  < 1 || 4 < alternatives.length){
            throw new IllegalArgumentException("Illegal number of alternatives");
        }
    }

    @Override
    public boolean guess(int guessNumber) {
        return guessNumber == answerIndex;
    }
}
