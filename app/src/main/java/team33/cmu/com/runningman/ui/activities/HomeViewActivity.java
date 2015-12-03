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
import team33.cmu.com.runningman.dbLayout.LoginDBManager;
import team33.cmu.com.runningman.dbLayout.RunnerLadderDBMananger;

public class HomeViewActivity extends AppCompatActivity {

    private ListView myRunList;
    private ListView topRunnersList;
    private ArrayAdapter<String> topRunnersArrayAdapter;
    private String username;
    private RunnerLadderDBMananger runnerLadderDBMananger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);
        myRunList = (ListView) findViewById(R.id.myRunsList);
        List<String> myRuns = new ArrayList<>();

        //populate list from DB in final version
        myRuns.add("Run1 \t\t 11/03/2015");
        myRuns.add("Run2 \t\t 11/02/2015");
        myRuns.add("Run3 \t\t 11/01/2015");
        myRuns.add("Run4 \t\t 10/11/2015");
        myRuns.add("Run5 \t\t 10/10/2015");
        myRuns.add("Run6 \t\t 10/09/2015");
        myRuns.add("Run7 \t\t 10/08/2015");
        myRuns.add("Run8 \t\t 10/07/2015");
        myRuns.add("Run9 \t\t 10/06/2015");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> myRunArrayAdapter = new ArrayAdapter<>(
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
                intent.setClass(HomeViewActivity.this, SummaryActivity.class); //set to Hongyi's activity
                intent.putExtra("id", "123");
                startActivity(intent);
            }
        });


        topRunnersList = (ListView) findViewById(R.id.topRunnersList);

        List<String> topRunners = new ArrayList<>();

        //populate list from DB in final version
        topRunners.add("Tom\t\t10 miles");
        topRunners.add("Bob\t\t9 miles");
        topRunners.add("Jack\t\t8 miles");

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

//                 Start RunningActivity.class
                Intent myIntent = new Intent(HomeViewActivity.this,
                        RunningActivity.class);
                startActivity(myIntent);
            }
        });

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
                runnerLadderDBMananger.addMilesToUser("tom",12.2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "";
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            List<String> topRunners = new LinkedList<>();
            topRunners.add("MM\t\t8 miles");
            topRunnersArrayAdapter.clear();
            topRunnersArrayAdapter.addAll(topRunners);
            topRunnersArrayAdapter.notifyDataSetChanged();
        }
    }

}
