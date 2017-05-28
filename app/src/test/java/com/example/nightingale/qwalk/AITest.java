package com.example.nightingale.qwalk;

import com.example.nightingale.qwalk.Model.Actor.AI;
import com.example.nightingale.qwalk.Model.QLocation;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.Quiz.QuizDifficulty;
import com.example.nightingale.qwalk.Model.StandardQuizzes;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Elina Olsson on 2017-05-22.
 */

public class AITest {
    AI ai;
    Quiz quiz;
    QLocation location = new QLocation(11, 58);

    @Before
    public void create() {
        quiz = StandardQuizzes.getTestQuiz();
    }

    /**
     * Tests if the AI generates at least x percent of correct answers where x stands for the difficulty number (can be 0-100).
     */

    @Test
    public void testAnswers() {
        int[] correctAnswers = quiz.getCorrectAnswers();
        int numberOfQuestions = quiz.getCorrectAnswers().length;
        int numberOfAIs = 100;
        double diffucultyPercent = 0.0;
        int numberOfCorrectAnswers = 0;

        for (int i = 0; i < numberOfAIs; i++) {
            ai = new AI(quiz.getCorrectAnswers(), false, quiz.getLowerBounds(), quiz.getUpperBounds(), QuizDifficulty.HARD, quiz.getLocations(), location);
            int[] monkeyAnswers = ai.getAnswers();
            diffucultyPercent = ai.getDifficulty() / 100;

            for (int j = 0; j < correctAnswers.length; j++) {
                if (monkeyAnswers[j] == correctAnswers[j]) {
                    numberOfCorrectAnswers++;
                }
            }
        }
        System.out.println("Count: " + numberOfCorrectAnswers);
        System.out.println("Frågor med rätt svar: " + diffucultyPercent * numberOfQuestions * numberOfAIs);
        //assertTrue(Math.round(numberOfCorrectAnswers/numberOfAIs) == diffucultyPercent * numberOfQuestions * numberOfAIs);

        assertTrue(numberOfCorrectAnswers > diffucultyPercent * numberOfQuestions * numberOfAIs);

    }
}




    /*
    @Before
    public void createFakeQuiz() {
        quiz = StandardQuizzes.getChalmersQuiz();
        ai = new AI(quiz.getCorrectAnswers(), false, quiz.getLowerBounds(), quiz.getUpperBounds(), QuizDifficulty.HARD, quiz.getLocations(), location);
    }

    @Test
    public void scoreTest() {
        ai.getAnswers();
        quiz.getCorrectAnswers();

        int score = 0;

        for (int i = 0; i < ai.getAnswers().length; i++) {
            score++;
        }

        assertEquals("Is score correct?", score, ai.getScore());
    }
}*/