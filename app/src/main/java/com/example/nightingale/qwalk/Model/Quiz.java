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

    private boolean questionTimer = false, quizTimer = false, hiddenQuestions = false, inOrder = true, withBot = false;

    public void setSetting(QuizSetting setting, boolean enabled) {
        switch (setting) {
            case QUESTION_TIMER:
                questionTimer = enabled;
                break;
            case WITH_BOT:
                withBot = enabled;
                break;
            case IN_ORDER:
                inOrder = enabled;
                break;
            case IS_HIDDEN:
                hiddenQuestions = enabled;
                break;
            case QUIZ_TIMER:
                quizTimer = enabled;
                break;
        }
    }

    public boolean getSetting(QuizSetting setting) {
        switch (setting) {
            case QUESTION_TIMER:
                return questionTimer;
            case WITH_BOT:
                return withBot;
            case IN_ORDER:
                return inOrder;
            case IS_HIDDEN:
                return hiddenQuestions;
            case QUIZ_TIMER:
                return quizTimer;
        }
        return false;
    }

    private List<Question> questions = new ArrayList<>();

    public Quiz(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Question get(int index) {
        return questions.get(index);
    }

    public int size() {
        return questions.size();
    }

    protected Quiz(Parcel in) {
        title = in.readString();
        description = in.readString();
        if (in.readByte() == 0x01) {
            questions = new ArrayList<Question>();
            in.readList(questions, OptionQuestion.class.getClassLoader());
        } else {
            questions = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        if (questions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(questions);
        }
    }

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