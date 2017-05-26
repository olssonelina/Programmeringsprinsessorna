package com.example.nightingale.qwalk.Model;

import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Question.Tiebreaker;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.nightingale.qwalk.Model.Quiz.QuizSetting.*;

/**
 * Created by Kraft on 2017-04-28.
 */

public final class StandardQuizzes {

    private StandardQuizzes() {
    }

    /**
     * @return returns a standard quiz about chalmers
     */
    public static Quiz getChalmersQuiz() {
        List<Question> questions = new ArrayList<>();

        questions.add(new OptionQuestion("Vilken sektion har sin sektionslokal här?", new ArrayList<String>(Arrays.asList(new String[]{"Data", "Informationsteknik", "Elektro", "Maskin"})), 1, 57.688290, 11.979162, -1));
        questions.add(new OptionQuestion("Vad var syftet med denna byggnaden från början?", new ArrayList<String>(Arrays.asList(new String[]{"Att stänga in elever som fuskade", "Att klättra i", "Det är en så kallad Schrödingers Cage", "Att göra experiment i"})), 3, 57.687449, 11.980544, -1));
        questions.add(new OptionQuestion("Vad är denna pizzerian känd för?", new ArrayList<String>(Arrays.asList(new String[]{"Att göra fyrkantiga pizzor", "Att vara Sveriges bästa två år i rad", "Att det är IT-studenternas favorit", "Det har varit ett kattcafé"})), 2, 57.687837, 11.982194, -1));

        return new Quiz("Chalmersquiz", "Trivia om Chalmers och dess campus!", -1, questions);
    }

    /**
     * @return returns a standard quiz about the developers
     */
    public static Quiz getAdressQuiz() {
        List<Question> questions = new ArrayList<>();

        questions.add(new OptionQuestion("Vem bor så här nära Chalmers?", new ArrayList<String>(Arrays.asList(new String[]{"Katten", "Pil", "Nightinggale", "Elit"})), 1, 57.689280, 11.972306, -1));
        questions.add(new OptionQuestion("Vem kan bo här?", new ArrayList<String>(Arrays.asList(new String[]{"Pil", "Katten", "Nightinggale", "Elit"})), 2, 57.742081, 11.969506, -1));
        questions.add(new OptionQuestion("Vem bor inneboende här?", new ArrayList<String>(Arrays.asList(new String[]{"Pil", "Nightinggale", "Elit", "Katten"})), 3, 57.735626, 12.116774, -1));
        questions.add(new OptionQuestion("Vem orkar pendla från Kungsbacka?", new ArrayList<String>(Arrays.asList(new String[]{"Elit", "Pil", "Nightinggale", "Katten"})), 0, 57.543822, 12.103735, -1));

        Quiz q = new Quiz("Gissa huset!", "Besök skaparna av appen och gissa vem som bor var!", -2, questions);
        q.setSetting(QUESTIONS_IN_ORDER, false);
        return q;
    }

    /**
     * @return returns a standard quiz
     */
    public static Quiz getMachineStudyRoomsQuiz() {

        List<Question> questions = new ArrayList<>();

        questions.add(new Tiebreaker("Hur gammal är byggnaden?", 45, 57.688447, 11.978703, 20, 60, -1));

        return new Quiz("M-grupprummen", "Najs grupprum", -3, questions);
    }
}
