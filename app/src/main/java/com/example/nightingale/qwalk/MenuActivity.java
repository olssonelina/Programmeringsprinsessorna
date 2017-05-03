package com.example.nightingale.qwalk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraft on 2017-04-28.
 */

public class MenuActivity extends AppCompatActivity {

    public static final int CREATE_QUIZ_CODE = 37;

    private ListView listView;

    private List<Quiz> quizzes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); //ändra namnet till rätt xml-fil
        loadQuizzes();
        loadList();
    }

    private void loadQuizzes() {
        quizzes.add(StandardQuizzes.getChalmersQuiz());
        quizzes.add(StandardQuizzes.getAdressQuiz());
        //TODO Kevin, här kanske du kan lägga till från databasen ?. Eventuellt fler standardquizzes med
    }

    private void loadList() {
        listView = (ListView) findViewById(R.id.list);
        String[] values = new String[quizzes.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = quizzes.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                play(position);
            }
        });
    }

    private void play(Quiz quiz) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("quiz", quiz);
        startActivity(intent);
    }

    private void play(int index) {
        play(quizzes.get(index));
    }

    public void createButtonPressed(View view) {
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivityForResult(intent, CREATE_QUIZ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_QUIZ_CODE) {

            Quiz newQuiz = (Quiz)data.getParcelableExtra("quiz");
            quizzes.add(newQuiz);
            loadList();

            //TODO ladda upp nya quizzen här!
        }
    }
}
