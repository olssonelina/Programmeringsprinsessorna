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

        List<OptionQuestion> questions = new ArrayList<>();

        questions.add(new OptionQuestion("Vilken sektion har sin sektionslokal här?", "Data", "Informationsteknik", "Elektro", "Maskin", 1,57.688290, 11.979162));
        //questions.get(0).setLocation(57.688290, 11.979162);

        questions.add(new OptionQuestion("Vad var syftet med denna byggnaden från början?", "Att stänga in elever som fuskade", "Att klättra i", "Det är en så kallad Schrödingers Cage", "Att göra experiment i", 3, 57.687449, 11.980544));
        //questions.get(1).setLocation(57.687449, 11.980544);

        questions.add(new OptionQuestion("Vad är denna pizzerian känd för?", "Att göra fyrkantiga pizzor", "Att vara Sveriges bästa två år i rad", "Att det är IT-studenternas favorit", "Det har varit ett kattcafé", 2,57.687837, 11.982194));
        //questions.get(2).setLocation(57.687837, 11.982194);

        q.setQuestions(questions);

        return q;
    }

    public static Quiz getAdressQuiz(){

        Quiz q = new Quiz("Gissa huset!","Besök skaparna av appen och gissa vem som bor var!");

        List<OptionQuestion> questions = new ArrayList<>();

        questions.add(new OptionQuestion("Vem bor så här nära Chalmers?", "Katten", "Pil", "Nightinggale", "Elit", 1,57.689280, 11.972306));
        //questions.get(0).setLocation(57.689280, 11.972306);

        questions.add(new OptionQuestion("Vem kan bo här?", "Pil", "Katten", "Nightinggale", "Elit", 2,57.742081, 11.969506));
        //questions.get(1).setLocation(57.742081, 11.969506);

        questions.add(new OptionQuestion("Vem bor inneboende här?", "Pil", "Nightinggale", "Elit", "Katten", 3,57.735626, 12.116774));
        //questions.get(2).setLocation(57.735626, 12.116774);

        questions.add(new OptionQuestion("Vem orkar pendla från Kungsbacka?", "Elit", "Pil", "Nightinggale", "Katten", 0,57.543822, 12.103735));
        //questions.get(3).setLocation(57.543822, 12.103735);


        q.setQuestions(questions);

        return q;
    }
}
