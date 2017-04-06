package Qwalk;

/**
 * Created by Kraft on 2017-03-31.
 */
public abstract class Question {
    private String questionText;
    protected int answerIndex;


    protected Question(String questionText, int answerIndex){
        this.questionText = questionText;
        this.answerIndex = answerIndex;
    }

    public abstract boolean guess(int guessNumber);

    public String getQuestionText(){
        return questionText;
    }
}
