package com.example.nightingale.qwalk.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nightingale.qwalk.InterfaceView.ICreateQuiz;
import com.example.nightingale.qwalk.Model.Database.DatabaseHandler;
import com.example.nightingale.qwalk.Model.Question;
import com.example.nightingale.qwalk.Model.Quiz;
import com.example.nightingale.qwalk.Model.Tiebreaker;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;

/**
 * Created by Kraft on 2017-04-27.
 */

public class CreateQuizActivity extends AppCompatActivity implements ICreateQuiz, AdapterView.OnItemClickListener {

    ArrayList<Question> questions = new ArrayList<>();

    private EditText quizTitle;
    private EditText quizDescription;
    private Tiebreaker tiebreaker;
    private int oldQuestions;

    public final static int OPTIONQUESTION_CODE = 7;
    public final static int TIEBREAKER_CODE = 22;
    private final static int IS_EDITING = 1;
    private final static int IS_NOT_EDITING = 0;
    private int mode = IS_NOT_EDITING;
    Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquiz);

        quizTitle = (EditText) findViewById(R.id.quizTitleField);
        quizDescription = (EditText) findViewById(R.id.descriptionField);

        try {
            quiz = getIntent().getParcelableExtra("quiz");
            mode = IS_EDITING;
            setAllFields(quiz);
            loadList();
        } catch (NullPointerException e) {
        }

    }

    public void addQuestionButtonClicked(View view) {
        Intent intent = new Intent(this, CreateOptionQuestionActivity.class);
        startActivityForResult(intent, OPTIONQUESTION_CODE);
    }

    public void addTiebreaker(View view) {
        Intent intent = new Intent(this, CreateTiebreakerActivity.class);
        startActivityForResult(intent, TIEBREAKER_CODE);
    }

    public void createQuiz(View view) {
        if (!isQuizComplete()) {
            sendErrorMsg();
        } else {
            try {
                saveQuiz();
            } catch (Exception e) {

            }
        }
    }

    public boolean isQuizComplete() {
        if (quizTitle.getText().toString().equals("") || quizDescription.getText().toString().equals("") || questions.size() == 0) {
            return false;
        } else if (questions.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void sendErrorMsg() {
        String msg;
        if (quizTitle.getText().toString().equals("")) {
            msg = getResources().getString(R.string.set_title_ex);
        } else if (quizDescription.getText().toString().equals("")) {
            msg = getResources().getString(R.string.set_description_ex);
        } else if (questions.size() == 0 && tiebreaker == null) {
            msg = getResources().getString(R.string.add_questions_ex);
        } else {
            msg = getResources().getString(R.string.error);
        }
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
        toast.show();
    }

    public void saveQuiz() throws InterruptedException {


        questions.add(tiebreaker);


        DatabaseHandler.saveQuiz(quizTitle.getText().toString(), quizDescription.getText().toString(), questions, quiz);


    }

    public void saveQuizComplete(String msg) {

        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 160);
        toast.show();

        Intent returnIntent = new Intent();
        setResult(GetPositionActivity.RESULT_OK, returnIntent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPTIONQUESTION_CODE) {
            try {
                questions.addAll((ArrayList<Question>) data.getSerializableExtra("questions"));
                loadList();
            } catch (NullPointerException e) {
            }

        }

        if (requestCode == TIEBREAKER_CODE) {
            try {
                tiebreaker = (Tiebreaker) data.getParcelableExtra("tiebreaker");
                loadList();
            } catch (NullPointerException e) {
            }

        }
    }

    private void loadList() {
        ListView questionList = (ListView) findViewById(R.id.questionList);
        boolean hasTiebreaker = tiebreaker != null;
        String[] values = hasTiebreaker ? new String[questions.size() + 1] : new String[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            values[i] = questions.get(i).getQuestionTitle();
        }

        if (hasTiebreaker) {
            values[values.length - 1] = tiebreaker.getQuestionTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        questionList.setAdapter(adapter);

        setListViewHeightBasedOnItems(questionList);
        questionList.setOnItemClickListener(this);
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }

    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Question q = questions.get(position); //Question pressed is an optionquestion
            questions.remove(q);
            Intent intent = new Intent(this, CreateOptionQuestionActivity.class);
            intent.putExtra("question", q);
            startActivityForResult(intent, OPTIONQUESTION_CODE);
        } catch (IndexOutOfBoundsException e) { //Question pressed is the tiebreaker
            Intent intent = new Intent(this, CreateTiebreakerActivity.class);
            intent.putExtra("question", tiebreaker);
            startActivityForResult(intent, TIEBREAKER_CODE);
        }
    }

    private void setAllFields(Quiz quiz) {
        quizTitle.setText(quiz.getTitle());
        quizDescription.setText(quiz.getDescription());
        for (int i = 0; i < quiz.size() - 1; i++) {
            questions.add(quiz.get(i));
        }
        Question lastQuestion = quiz.get(quiz.size() - 1);
        if (lastQuestion instanceof Tiebreaker) {
            tiebreaker = (Tiebreaker) lastQuestion;
            oldQuestions = quiz.size() - 2;
        } else {
            questions.add(lastQuestion);
            oldQuestions = quiz.size() - 1;
        }
    }
}
