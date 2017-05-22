package com.example.nightingale.qwalk;

import com.example.nightingale.qwalk.Model.AI;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.StandardQuizzes;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class AITest {
    AI ai;
    Quiz q;

    @Before
    public void createFakeQuiz() {
        q = StandardQuizzes.getChalmersQuiz();
        ai = new AI(q.getCorrectAnswers(), false, q.getLowerBounds(), q.getUpperBounds(), 100);
    }

    @Test
    public void scoreTest() {
        ai.getAnswers();
        q.getCorrectAnswers();

        int score = 0;

        for (int i = 0; i < ai.getAnswers().size(); i++) {
            score++;
        }

        assertEquals("Is score correct?", score, ai.getScore());
    }
}