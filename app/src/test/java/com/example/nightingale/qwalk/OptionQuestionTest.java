package com.example.nightingale.qwalk;

import com.example.nightingale.qwalk.Model.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.StandardQuizzes;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class OptionQuestionTest {
    OptionQuestion oq1;
    OptionQuestion oq2;
    OptionQuestion oq3;
    OptionQuestion oq4;

    @Before
    public void create() {
        /*oq1 = StandardQuizzes.getChalmersQuiz().getQuestions().get(0);
        oq2 = StandardQuizzes.getChalmersQuiz().getQuestions().get(1);
        oq3 = StandardQuizzes.getChalmersQuiz().getQuestions().get(2);
        oq4 = StandardQuizzes.getChalmersQuiz().getQuestions().get(3);*/
    }

    @Test
    public void hasMoreThanTwoOptions() throws Exception {
        String[] celebrities = {"Brad Pitt", "Michael Jackson", "The King", "Beyoncé"};
        String[] years = {"1932", "1995", "2003"};
        String[] names = {"elina"};
        String[] blank = {""};

        boolean fourOptions = OptionQuestion.validateOptions(celebrities);
        boolean threeOptions = oq1.validateOptions(years);
        boolean oneOptions = oq1.validateOptions(names);
        boolean noOptions = oq1.validateOptions(blank);

        assertTrue("Is enough options?", fourOptions);
        assertTrue("Is enough options?", threeOptions);
        assertFalse("Is not enough options?", oneOptions);
        assertFalse("Is no options?", noOptions);
    }

    @Test
    public void getNumberOfOptions() throws Exception {
        /*String[] celebrities = {"Brad Pitt", "Michael Jackson", "The King", "Beyoncé"};
        String[] years = {"1932", "1995", "2003", ""};
        String[] names = {"elina", "", "", ""};
        String[] blank = {"", "", "", ""};

        int fourOptions = oq1.getNumberOfOptions();
        int threeOptions = oq1.getNumberOfOptions();
        int oneOption = oq1.getNumberOfOptions();
        int noOptions = oq1.getNumberOfOptions();

        assertEquals("Number of options", fourOptions, 4);
        assertEquals("Number of options", threeOptions, 3);
        assertEquals("Number of options", oneOption, 1);
        assertEquals("Number of options", noOptions, 0);*/
    }
}