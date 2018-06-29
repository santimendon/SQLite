package com.android.smendon.sqlite.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.smendon.sqlite.R;
import com.android.smendon.sqlite.adapters.MovieAdapter;
import com.android.smendon.sqlite.database.DBRenderer;
import com.android.smendon.sqlite.models.MovieRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanket Mendon on 27-06-2018.
 */

public class MainActivity extends AppCompatActivity {
    private TextView txtNoData;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private List<MovieRecord> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewComponents();
        new RetrieveDataTask(MainActivity.this).execute();
    }

    private void initViewComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("IMDB");
        toolbar.setLogo(getDrawable(R.mipmap.ic_launcher));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_movie_list);
        txtNoData = (TextView) findViewById(R.id.txt_no_data);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MovieAdapter(movieList);
        mRecyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(MainActivity.this, AddRecordActivity.class));
            }
        });
    }

    private void setAppropriateView(boolean isEmpty) {
        if (isEmpty) {
            txtNoData.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            txtNoData.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private class RetrieveDataTask extends AsyncTask<Void, Void, Void> {
        private Context mContext;
        private ProgressDialog mDialog;

        public RetrieveDataTask(Context ctx) {
            this.mContext = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("Fetching data");
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBRenderer mRenderer = new DBRenderer(mContext);
            movieList = mRenderer.getAllRecords();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            if (movieList != null && movieList.size() > 0) {
                setAppropriateView(false);
                mAdapter = new MovieAdapter(movieList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                setAppropriateView(true);
            }
        }
    }
}
