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

    private List<OptionQuestion> questions = new ArrayList<>();

    public Quiz(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setQuestions(List<OptionQuestion> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<OptionQuestion> getQuestions() {
        return questions;
    }

    public OptionQuestion get(int index){
        return questions.get(index);
    }

    public int size(){
        return questions.size();
    }

    protected Quiz(Parcel in) {
        title = in.readString();
        description = in.readString();
        if (in.readByte() == 0x01) {
            questions = new ArrayList<OptionQuestion>();
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