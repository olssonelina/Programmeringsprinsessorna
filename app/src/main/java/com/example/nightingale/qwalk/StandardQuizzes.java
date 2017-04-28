package com.example.nightingale.qwalk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-04-28.
 */

public final class StandardQuizzes {

    //Hide the constructor
    private StandardQuizzes(){}

    public static List<Question> getChalmersQuiz(){

        List<Question> quiz = new ArrayList<>();

        quiz.add(new Question("Vilken sektion har sin sektionslokal här?", "Data", "Informationsteknik", "Elektro", "Maskin", 1));
        quiz.get(0).setLocation(57.688290, 11.979162);

        quiz.add(new Question("Vad var syftet med denna byggnaden från början?", "Att stänga in elever som fuskade", "Att klättra i", "Det är en så kallad Schrödingers Cage", "Att göra experiment i", 3));
        quiz.get(1).setLocation(57.687449, 11.980544);

        quiz.add(new Question("Vad är denna pizzerian känd för?", "Att göra fyrkantiga pizzor", "Att vara Sveriges bästa två år i rad", "Att det är IT-studenternas favorit", "Det har varit ett kattcafé", 2));
        quiz.get(2).setLocation(57.687837, 11.982194);

        return quiz;
    }
}
