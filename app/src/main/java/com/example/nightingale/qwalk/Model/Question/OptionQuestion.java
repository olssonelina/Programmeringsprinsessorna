package com.example.nightingale.qwalk.Model.Question;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.nightingale.qwalk.Model.QLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elina Olsson on 2017-04-24.
 */

public class OptionQuestion extends Question implements Parcelable {

    private List<String> options = new ArrayList<>();

    /**
     * Creates a new multiple-choice question for Qwalk
     *
     * @param title         the actual question
     * @param options       array with up to four options
     * @param correctAnswer the index in options of the correct answer
     * @param latitude      the latitude of the question
     * @param longitude     the longitude of the question
     * @param id            the id stored in the database for this question
     */
    public OptionQuestion(String title, List<String> options, int correctAnswer, double latitude, double longitude, int id/*Image image, Position position*/) {
        super(title, correctAnswer, new QLocation(latitude, longitude), id);
        for (String option : options) {
            this.options.add(option);
        }
    }

    /**
     * Returns a specific option
     *
     * @param index the index of the specific option
     * @return returns a specific option
     */
    public final String getOption(int index) {
        if (index < options.size() && index > -1) {
            return options.get(index);
        }
        throw new IllegalArgumentException("No such index!");
    }

    /**
     * @return returns all options
     */
    public final String[] getOptions() {
        String[] options = new String[this.options.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = this.options.get(i);
        }
        return options;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getUpperBounds() {
        int count = 0;
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals("")) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  final int getLowerBounds() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object o) {
        if (o instanceof OptionQuestion) {
            OptionQuestion other = (OptionQuestion) o;

            String[] options = getOptions();
            String[] otherOptions = other.getOptions();
            for (int i = 0; i < options.length; i++) {
                if (!options[i].equals(otherOptions[i])) {
                    return false;
                }
            }

            return options.length == otherOptions.length &&
                    other.getCorrectAnswer() == getCorrectAnswer() &&
                    other.getLocation().equals(getLocation()) &&
                    other.getQuestionTitle().equals(getQuestionTitle());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        // TODO gör en haschcode och lägg till den i equals
        return 0;
    }

    /**
     * A static method to check if the specified options are allowed in an optionquestion
     *
     * @param options options which to check
     * @return true if specified options are allowed
     */
    public static boolean validateOptions(String[] options) {
        int count = 0;
        for (int i = 0; i < options.length; i++) {
            if (!options[i].equals("")) {
                count++;
            }
        }
        return count >= 2;
    }

    /**
     * {@inheritDoc}
     */
    protected OptionQuestion(Parcel in) {
        super(in);
        if (in.readByte() == 0x01) {
            options = new ArrayList<String>();
            in.readList(options, String.class.getClassLoader());
        } else {
            options = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (options == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(options);
        }
    }

    /**
     * {@inheritDoc}
     */
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