package com.example.nightingale.qwalk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nightingale.qwalk.Model.Question.OptionQuestion;
import com.example.nightingale.qwalk.Presenter.CreateQuiz.ICreateQuiz;
import com.example.nightingale.qwalk.Model.Question.Question;
import com.example.nightingale.qwalk.Model.Quiz.Quiz;
import com.example.nightingale.qwalk.Model.Question.Tiebreaker;
import com.example.nightingale.qwalk.Presenter.CreateQuiz.CreateQuizPresenter;
import com.example.nightingale.qwalk.R;

import java.util.ArrayList;

/**
 * Created by Kraft on 2017-04-27.
 */

public class CreateQuizActivity extends AppCompatActivity implements ICreateQuiz, AdapterView.OnItemClickListener {

    private CreateQuizPresenter presenter;

    private EditText quizTitle;
    private EditText quizDescription;
    private ListView questionList;

    private final static int OPTIONQUESTION_CODE = 7;
    private final static int TIEBREAKER_CODE = 22;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createquiz);

        quizTitle = (EditText) findViewById(R.id.quizTitleField);
        quizDescription = (EditText) findViewById(R.id.descriptionField);
        questionList = (ListView) findViewById(R.id.questionList);
        questionList.setOnItemClickListener(this);

        try {
            Quiz editQuiz = getIntent().getParcelableExtra("quiz");
            presenter = new CreateQuizPresenter(this, editQuiz);
        } catch (NullPointerException e) {
            presenter = new CreateQuizPresenter(this);
        }

    }

    @Override
    public final void fillFields(Quiz editQuiz) {
        quizTitle.setText(editQuiz.getTitle());
        quizDescription.setText(editQuiz.getDescription());
    }

    @Override
    public final void setListItems(String[] questionTitles) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, questionTitles);
        questionList.setAdapter(adapter);
        setListViewHeightBasedOnItems(questionList);
    }

    @Override
    public final void close() {
        finish();
    }

    @Override
    public final void closeWithResult() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        returnIntent.putExtra("update", true);
        finish();
    }

    @Override
    public final void openCreateOptionQuestion(int questionCounter) {
        Intent intent = new Intent(this, CreateOptionQuestionActivity.class);
        intent.putExtra("questionCounter", questionCounter);
        startActivityForResult(intent, OPTIONQUESTION_CODE);
    }

    @Override
    public final void openCreateOptionQuestion(OptionQuestion question, int questionCounter) {
        Intent intent = new Intent(this, CreateOptionQuestionActivity.class);
        intent.putExtra("question", question);
        intent.putExtra("questionCounter", questionCounter);
        startActivityForResult(intent, OPTIONQUESTION_CODE);
    }

    @Override
    public final void openCreateTiebreakerQuestion() {
        Intent intent = new Intent(this, CreateTiebreakerActivity.class);
        startActivityForResult(intent, TIEBREAKER_CODE);
    }

    @Override
    public final void openCreateTiebreakerQuestion(Tiebreaker tiebreaker) {
        Intent intent = new Intent(this, CreateTiebreakerActivity.class);
        intent.putExtra("question", tiebreaker);
        startActivityForResult(intent, TIEBREAKER_CODE);
    }

    @Override
    public final String getDescription() {
        return quizDescription.getText().toString();
    }

    @Override
    public final String getQuestionTitle() {
        return quizTitle.getText().toString();
    }

    @Override
    public final void sendError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public final void onBackPressed(View view) {
        presenter.onBackPressed();
    }

    @Override
    public final void onBackPressed() {
        presenter.onBackPressed();
    }

    public final void addQuestionButtonClicked(View view) {
        presenter.addOptionQuestionButtonPressed();
    }

    public final void addTiebreaker(View view) {
        presenter.addTiebreakerQuestionButtonPressed();
    }

    public final void createQuiz(View view) {
        presenter.saveQuizButtonPressed();
    }


    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPTIONQUESTION_CODE) {
            try {
                presenter.addQuestions((ArrayList<Question>) data.getSerializableExtra("questions"));
            } catch (NullPointerException e) {

            }
        }
        if (requestCode == TIEBREAKER_CODE) {
            try {
                presenter.setTiebreaker((Tiebreaker) data.getParcelableExtra("tiebreaker"));
            } catch (NullPointerException e) {
             }
        }
    }

    private static boolean setListViewHeightBasedOnItems(ListView listView) {

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
    public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.questionPressed(position);
    }
}
