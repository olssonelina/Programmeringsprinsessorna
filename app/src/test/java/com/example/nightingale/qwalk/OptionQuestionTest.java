package com.example.nightingale.qwalk;

import com.example.nightingale.qwalk.Model.Question.OptionQuestion;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class OptionQuestionTest {

    @Test
    public void hasMoreThanTwoOptions() throws Exception {
        String[] celebrities = {"Brad Pitt", "Michael Jackson", "The King", "Beyoncé"};
        String[] years = {"1932", "1995", "2003"};
        String[] names = {"elina"};
        String[] blank = {""};

        boolean fourOptions = OptionQuestion.validateOptions(celebrities);
        boolean threeOptions = OptionQuestion.validateOptions(years);
        boolean oneOptions = OptionQuestion.validateOptions(names);
        boolean noOptions = OptionQuestion.validateOptions(blank);

        assertTrue("Is enough options?", fourOptions);
        assertTrue("Is enough options?", threeOptions);
        assertFalse("Is not enough options?", oneOptions);
        assertFalse("Is no options?", noOptions);
    }
}