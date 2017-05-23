package com.example.nightingale.qwalk.Model;

import android.graphics.Path;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class OptionQuestion extends Question implements Parcelable {

    private ArrayList<String> options = new ArrayList<>();


    public OptionQuestion(String title, ArrayList<String> options, int correctAnswer, double latitude, double longitude/*Image image, Position position*/ ) {
        this.questionTitle = title;
        for(String option : options){
            this.options.add(option);
        }
        this.correctAnswer = correctAnswer;
        location = new QLocation(latitude, longitude);
        this.upperBounds=3;
        this.lowerBounds=0;
    }

    public String getOption(int index) {
        if (index<options.size()&&index>-1){
            return options.get(index);
        }
        return "option does not exist";
    }

    public String[] getOptions(){
        String[] options = new String[this.options.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = this.options.get(i);
        }
        return options;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof OptionQuestion){
            OptionQuestion other = (OptionQuestion) o;

            return other.getOption(1).equals(getOption(1)) &&
                    other.getOption(2).equals(getOption(2)) &&
                    other.getOption(3).equals(getOption(3)) &&
                    other.getOption(4).equals(getOption(4)) &&
                    other.getCorrectAnswer() == getCorrectAnswer() &&
                    other.getLocation().equals(getLocation()) &&
                    other.getQuestionTitle().equals(getQuestionTitle());
        }
        return false;
    }


    public static boolean validateOptions(String[] options){
        int count = 0;
        for (int i = 0; i < options.length; i++) {
            if (!options[i].equals("")) {
                count++;
            }
        }
        return count >= 2;
    }

    public int getNumberOfOptions() {
        int numberOfOptions = 0;

        for (int i = 0; i < options.size() ; i++) {
            if (!options.get(i).equals("")) {
                numberOfOptions++;
            }
        }
        return numberOfOptions;
    }


    protected OptionQuestion(Parcel in) {
        questionTitle = in.readString();
        correctAnswer = in.readInt();
        location = (QLocation) in.readValue(QLocation.class.getClassLoader());
        if (in.readByte() == 0x01) {
            options = new ArrayList<String>();
            in.readList(options, String.class.getClassLoader());
        } else {
            options = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionTitle);
        dest.writeInt(correctAnswer);
        dest.writeValue(location);
        if (options == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(options);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OptionQuestion> CREATOR = new Parcelable.Creator<OptionQuestion>() {
        @Override
        public OptionQuestion createFromParcel(Parcel in) {
            return new OptionQuestion(in);
        }

        @Override
        public OptionQuestion[] newArray(int size) {
            return new OptionQuestion[size];
        }
    };
}