package com.android.smendon.sqlite.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.smendon.sqlite.R;
import com.android.smendon.sqlite.database.DBRenderer;
import com.android.smendon.sqlite.models.MovieRecord;

/**
 * Created by Sanket Mendon on 27-06-2018.
 */

public class AddRecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText edtMovieTitle, edtRuntime;
    private Spinner spinnerGenre, spinnerLanguage;
    private RatingBar rBar;
    private String selectedGenre, selectedLanguage;
    private Button btnAdd;

    private final String msgErrorTitle = "Movie title cannot be left blank";
    private final String msgErrorRuntime = "Please enter valid runtime in mins";
    private final String msgInsertionSuccess = "Record added";
    private final String msgInsertionError = "Error adding record";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        initViewComponents();
    }

    private void initViewComponents() {
        getSupportActionBar().setTitle("IMDB");
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setLogo(getDrawable(R.mipmap.ic_launcher));
        edtMovieTitle = (EditText) findViewById(R.id.edt_title);
        edtRuntime = (EditText) findViewById(R.id.edt_runtime);
        spinnerGenre = (Spinner) findViewById(R.id.spinner_genre);
        spinnerLanguage = (Spinner) findViewById(R.id.spinner_language);
        rBar = (RatingBar) findViewById(R.id.rbar);
        btnAdd = (Button) findViewById(R.id.btn_add);
        spinnerGenre.setOnItemSelectedListener(this);
        spinnerLanguage.setOnItemSelectedListener(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieRecord mRecord = new MovieRecord();
                mRecord.setTitle(edtMovieTitle.getText().toString());
                mRecord.setGenre(selectedGenre);
                mRecord.setLanguage(selectedLanguage);
                mRecord.setRuntime(edtRuntime.getText().toString());
                mRecord.setRating(String.valueOf(rBar.getRating()));
                validate(mRecord);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        if (selectedSpinner.getId() == R.id.spinner_genre) {
            selectedGenre = parent.getItemAtPosition(position).toString();
        } else if (selectedSpinner.getId() == R.id.spinner_language) {
            selectedLanguage = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void validate(MovieRecord record) {
        if (record.getTitle() != null && !record.getTitle().isEmpty()) {
            if (!record.getRuntime().equals("") && Float.parseFloat(record.getRuntime()) > 0 && Float.parseFloat(record.getRuntime()) <= 500) {
                new InsertTask(AddRecordActivity.this).execute(record);
            } else {
                Toast.makeText(AddRecordActivity.this, msgErrorRuntime, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddRecordActivity.this, msgErrorTitle, Toast.LENGTH_SHORT).show();
        }
    }

    private class InsertTask extends AsyncTask<MovieRecord, Void, Long> {
        private Context mContext;
        private ProgressDialog mDialog;

        public InsertTask(Context ctx) {
            this.mContext = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("Please wait");
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected Long doInBackground(MovieRecord... movieRecords) {
            DBRenderer mRenderer = new DBRenderer(mContext);
            long result = mRenderer.addRecord(movieRecords[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            if (result > 0) {
                Toast.makeText(mContext, msgInsertionSuccess, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddRecordActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else {
                Toast.makeText(mContext, msgInsertionError, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
