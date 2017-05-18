package com.example.nightingale.qwalk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Elina Olsson on 2017-05-15.
 */

public class AI extends Actor implements Parcelable {

    Quiz quiz;
    List<Question> questions = quiz.getQuestions();
    ArrayList<Integer> correctAnswers = quiz.getCorrectAnswers();
    ArrayList<Integer> monkeyAnswers = new ArrayList<>();
    int score;

    public AI(Quiz quiz, int level) {
        super(0);
        this.quiz = quiz;
    }


    public void setScore(ArrayList<Integer> correctAnswers, ArrayList<Integer> monkeyAnswers) {
        int score = 0;
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i) == monkeyAnswers.get(i)) {
                score++;
            }
        }
    }

    public int getAnswer(Question question) {
        int index = quiz.getQuestionIndex(question);
        return monkeyAnswers.get(index);
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfQuestions() {
        return monkeyAnswers.size();
    }


    public ArrayList<Integer> setAnswers(int level) {
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (level > randomInt()) {
                monkeyAnswers.add(correctAnswers.get(i));
            } else {
                if (questions.get(i) instanceof OptionQuestion) {
                    monkeyAnswers.add(randomAnswer(((OptionQuestion) questions.get(i)).getNumberOfOptions()));
                }
            }
        }
        return monkeyAnswers;
    }

    private int randomInt() {
        Random rand = new Random();
        return rand.nextInt(100);
    }

    private int randomAnswer(int numberOfOptions) {
        Random rand = new Random();
        return rand.nextInt(numberOfOptions);
    }



    protected AI(Parcel in) {
        quiz = (Quiz) in.readValue(Quiz.class.getClassLoader());
        if (in.readByte() == 0x01) {
            questions = new ArrayList<Question>();
            in.readList(questions, Question.class.getClassLoader());
        } else {
            questions = null;
        }
        if (in.readByte() == 0x01) {
            correctAnswers = new ArrayList<Integer>();
            in.readList(correctAnswers, Integer.class.getClassLoader());
        } else {
            correctAnswers = null;
        }
        if (in.readByte() == 0x01) {
            monkeyAnswers = new ArrayList<Integer>();
            in.readList(monkeyAnswers, Integer.class.getClassLoader());
        } else {
            monkeyAnswers = null;
        }
        score = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quiz);
        if (questions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(questions);
        }
        if (correctAnswers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(correctAnswers);
        }
        if (monkeyAnswers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(monkeyAnswers);
        }
        dest.writeInt(score);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AI> CREATOR = new Parcelable.Creator<AI>() {
        @Override
        public AI createFromParcel(Parcel in) {
            return new AI(in);
        }

        @Override
        public AI[] newArray(int size) {
            return new AI[size];
        }
    };
}