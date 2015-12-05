package team33.cmu.com.runningman.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import team33.cmu.com.runningman.R;
import team33.cmu.com.runningman.dbLayout.RunnerLadderDBMananger;
import team33.cmu.com.runningman.dbLayout.SummaryDBManager;
import team33.cmu.com.runningman.entities.LadderEntry;
import team33.cmu.com.runningman.entities.Summary;
import team33.cmu.com.runningman.entities.User;

public class HomeViewActivity extends AppCompatActivity {

    private ListView myRunList;
    private ListView topRunnersList;
    private ArrayAdapter<String> topRunnersArrayAdapter;
    private ArrayAdapter<String> myRunArrayAdapter;
    private List<LadderEntry> ladderEntries;
    private List<Summary> summaries;
    private String username = User.getUser().getName();
    private RunnerLadderDBMananger runnerLadderDBMananger;
    private SummaryDBManager summaryDBManager;
    private static int RUN_LADDER_NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);
        myRunList = (ListView) findViewById(R.id.myRunsList);
        List<String> myRuns = new ArrayList<>();

        //TODO: get user name from Hailun's intent

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        myRunArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                myRuns);

        myRunList.setAdapter(myRunArrayAdapter);
        myRunList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                String value = adapter.getItemAtPosition(position).toString();
                Log.i("[jump]", value);
                Intent intent = new Intent();
                intent.setClass(HomeViewActivity.this, SummaryActivity.class);
                Summary selectedSummary = summaries.get(position);
                intent.putExtra("id", selectedSummary.getId());
                startActivity(intent);
            }
        });


        topRunnersList = (ListView) findViewById(R.id.topRunnersList);

        List<String> topRunners = new ArrayList<>();

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        topRunnersArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                topRunners);

        topRunnersList.setAdapter(topRunnersArrayAdapter);

        Button button = (Button) findViewById(R.id.startRun);
        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Log.i("[jump]", "start Run!");
                Intent myIntent = new Intent(HomeViewActivity.this,
                        RunningActivity.class);
                startActivity(myIntent);
            }
        });

        new LoadSummaryTask().execute();
        new RunLadderTask().execute();
    }


    private class RunLadderTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                runnerLadderDBMananger = new RunnerLadderDBMananger();
                ladderEntries = runnerLadderDBMananger.getTopUsers(RUN_LADDER_NUM);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "";
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            List<String> topRunners = new LinkedList<>();
            for (LadderEntry ladderEntry : ladderEntries) {
                topRunners.add(ladderEntry.getUserName() + "\t\t" + ladderEntry.getDistance() + " miles");
            }

            topRunnersArrayAdapter.clear();
            topRunnersArrayAdapter.addAll(topRunners);
            topRunnersArrayAdapter.notifyDataSetChanged();
        }
    }

    private class LoadSummaryTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                summaryDBManager = new SummaryDBManager();
                summaries = summaryDBManager.getSummariesByUsername(username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "";
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            List<String> myRuns = new LinkedList<>();
            for (Summary summary : summaries) {
                myRuns.add(summary.getName() + "\t" + summary.getStartDate() + "\t"+ summary.getDistance() + " miles");
            }

            myRunArrayAdapter.clear();
            myRunArrayAdapter.addAll(myRuns);
            myRunArrayAdapter.notifyDataSetChanged();
        }
    }

}
