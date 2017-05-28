package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Presenter.DetailedResults.DetailedResultsPresenter;
import com.example.nightingale.qwalk.Presenter.DetailedResults.IDetailedResults;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import static com.example.nightingale.qwalk.R.id.questions;

/**
 * Created by PiaLocal on 2017-05-26.
 */

public class DetailedResultsActivity extends AppCompatActivity implements IDetailedResults {
    private DetailedResultsPresenter presenter;
    private ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedresults);

        resultList=(ListView) findViewById(R.id.resultList);

        Intent i = getIntent();
        int[] answers = i.getIntArrayExtra("answers");
        Quiz quiz = i.getParcelableExtra("quiz");

        Question[] questions= quiz.getQuestions();
        String[] questionTitles=new String[quiz.size()];
        String[] answerTitles=new String[quiz.size()];
        for(int j=0; j<quiz.size();j++){
            questionTitles[j]=questions[j].getQuestionTitle();
            if(questions[j] instanceof OptionQuestion) {
                answerTitles[j] = ((OptionQuestion) questions[j]).getOption(answers[j]);
            }
        }

        loadList(questionTitles, answerTitles);

        presenter = new DetailedResultsPresenter(this, quiz, answers);
    }

    public void onBackPressed(View view) {
        finish();
    }

    public void loadList(String[] questions, String[] answers) {

        //CustomAdapter mAdapter = new CustomAdapter(this, R.layout.listitem, new ArrayList<>(Arrays.asList(questions)));
        //resultList.setAdapter(mAdapter);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_list_item_1, android.R.id.text1, questions);

        //resultList.setAdapter(adapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, questions);

        resultList.setAdapter(adapter);

        MenuActivity.setListViewHeightBasedOnItems(resultList);

    }

    @Override
    public void setListColors(boolean[] isCorrect){
        for(int i=0;i<isCorrect.length;i++) {
            if (isCorrect[i]){
                resultList.getChildAt(i).setBackgroundColor(Color.parseColor("green"));
            }else{
                resultList.getChildAt(i).setBackgroundColor(Color.parseColor("red"));
            }
        }
    }
}
