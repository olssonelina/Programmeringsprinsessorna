package com.example.nightingale.qwalk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elina Olsson on 2017-04-28.
 */

public class Quiz implements Parcelable {
    private String title;
    private String description;
    private int quizID;
    private QuizDifficulty difficulty = QuizDifficulty.MEDIUM;
    private List<Question> questions = new ArrayList<>();
    private boolean questionTimer = false, quizTimer = true, hiddenQuestions = false, inOrder = true, withBot = true;

    /**
     * Creates a new Qwalk quiz
     *
     * @param title       the title pf the quiz
     * @param description the decription if this quiz
     * @param quizID      the ID used to identify this quiz
     */
    public Quiz(String title, String description, int quizID, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.quizID = quizID;
        this.questions = questions;
    }

    /**
     * Returns the difficulty of the quiz
     *
     * @return returns easy, medium or hard
     */
    public QuizDifficulty getDifficulty() {
        return difficulty;
    }

    /**
     * @return returns the quiz id used in a database
     */
    public int getQuizID() {
        return quizID;
    }

    /**
     * Sets the difficulty of a quiz
     *
     * @param difficulty easy, medium or hard
     */
    public void setDifficulty(QuizDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Changes the settings of the quiz
     *
     * @param setting Which setting to change
     * @param enabled Set to true to enable, false to disable
     */
    public void setSetting(QuizSetting setting, boolean enabled) {
        switch (setting) {
            case HAS_QUESTION_TIMER:
                questionTimer = enabled;
                break;
            case WITH_AI:
                withBot = enabled;
                break;
            case QUESTIONS_IN_ORDER:
                inOrder = enabled;
                break;
            case QUESTIONS_ARE_HIDDEN:
                hiddenQuestions = enabled;
                break;
            case HAS_QUIZ_TIMER:
                quizTimer = enabled;
                break;
        }
    }

    /**
     * @param setting Setting to check enabled status of
     * @return Returns true if given setting is enabled
     */
    public boolean getSetting(QuizSetting setting) {
        switch (setting) {
            case HAS_QUESTION_TIMER:
                return questionTimer;
            case WITH_AI:
                return withBot;
            case QUESTIONS_IN_ORDER:
                return inOrder;
            case QUESTIONS_ARE_HIDDEN:
                return hiddenQuestions;
            case HAS_QUIZ_TIMER:
                return quizTimer;
        }
        return false;
    }

    /**
     * @return Returns the title of the quiz
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Returns the description of the quiz
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Returns the questions of this quiz
     */
    public Question[] getQuestions() {
        Question[] qArr = new Question[questions.size()];
        for (int i = 0; i < qArr.length; i++) {
            qArr[i] = questions.get(i);
        }
        return qArr;
    }

    /**
     * Returns a specific question
     *
     * @param index index of the specif question
     * @return returns the question at the given index
     */
    public Question get(int index) {
        return questions.get(index);
    }

    /**
     * @return Returns the number of questions in this quiz
     */
    public int size() {
        return questions.size();
    }

    /**
     * @return Returns an array of all the correct answers
     */
    public int[] getCorrectAnswers() {
        int[] correctAnswers = new int[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            correctAnswers[i] = questions.get(i).getCorrectAnswer();
        }
        return correctAnswers;
    }

    /**
     * @return returns an array of the lowest index or answer option available to each question
     */
    public int[] getLowerBounds() {
        int[] lowerBounds = new int[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            lowerBounds[i] = questions.get(i).getLowerBounds();
        }
        return lowerBounds;
    }

    /**
     * @return returns an array of the highest index or answer option available to each question
     */
    public int[] getUpperBounds() {
        int[] upperBounds = new int[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            upperBounds[i] = questions.get(i).getUpperBounds();
        }
        return upperBounds;
    }

    public QLocation[] getLocations() {
        QLocation[] locations = new QLocation[questions.size()];
        for (int i = 0; i < locations.length; i++) {
            locations[i] = questions.get(i).getLocation();
        }
        return locations;
    }

    /**
     * Figures ut the index of a given question
     *
     * @param q the given question to figure out index of
     * @return Returns the index of the question in the question list
     */
    public int getQuestionIndex(Question q) {
        for (int i = 0; i < questions.size(); i++) {
            if (q.equals(questions.get(i))) {
                return i;
            }
        }
        throw new IllegalArgumentException("No such question in list!");
    }

    protected Quiz(Parcel in) {
        withBot = in.readByte() != 0;
        questionTimer = in.readByte() != 0;
        quizTimer = in.readByte() != 0;
        inOrder = in.readByte() != 0;
        hiddenQuestions = in.readByte() != 0;
        quizID = in.readInt();
        title = in.readString();
        description = in.readString();
        difficulty = (QuizDifficulty) in.readSerializable();
        questionTimer = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            questions = new ArrayList<Question>();
            in.readList(questions, Question.class.getClassLoader());
        } else {
            questions = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (withBot ? 1 : 0));
        dest.writeByte((byte) (questionTimer ? 1 : 0));
        dest.writeByte((byte) (quizTimer ? 1 : 0));
        dest.writeByte((byte) (inOrder ? 1 : 0));
        dest.writeByte((byte) (hiddenQuestions ? 1 : 0));
        dest.writeInt(quizID);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeSerializable(difficulty);
        dest.writeByte((byte) (questionTimer ? 0x01 : 0x00));
        if (questions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(questions);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };
}