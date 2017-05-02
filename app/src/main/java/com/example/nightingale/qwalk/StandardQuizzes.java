package com.example.nightingale.qwalk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-04-28.
 */

public final class StandardQuizzes {

    //Hide the constructor
    private StandardQuizzes(){}

    public static Quiz getChalmersQuiz(){

        Quiz q = new Quiz("Chalmersquiz","Trivia om Chalmers och dess campus!");

        List<Question> questions = new ArrayList<>();

        questions.add(new Question("Vilken sektion har sin sektionslokal här?", "Data", "Informationsteknik", "Elektro", "Maskin", 1));
        questions.get(0).setLocation(57.688290, 11.979162);

        questions.add(new Question("Vad var syftet med denna byggnaden från början?", "Att stänga in elever som fuskade", "Att klättra i", "Det är en så kallad Schrödingers Cage", "Att göra experiment i", 3));
        questions.get(1).setLocation(57.687449, 11.980544);

        questions.add(new Question("Vad är denna pizzerian känd för?", "Att göra fyrkantiga pizzor", "Att vara Sveriges bästa två år i rad", "Att det är IT-studenternas favorit", "Det har varit ett kattcafé", 2));
        questions.get(2).setLocation(57.687837, 11.982194);

        q.setQuestions(questions);

        return q;
    }
}
