package cs446.notebank;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by aaronlovejan on 6/10/16.OO
 */
public class UploadActivity extends Activity {
    //data is stored in the internal arraylist of data_model
    //if you want to pull out information simple do this:
    //              data_model.prof => this return a List<String> with prof
    private static DataRequest data_model = new DataRequest();

    private Spinner sp_course_name,sp_course_id,sp_course_prof;
    Button btnSubmit;

    //Async task class to get json by making HTTP call
    //HAVE to create this class because we cant make HTTP request on main thread
    //any change to the request, URL are done in the: doInBackGround function
    private class GetData extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(UploadActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance

            data_model.getRequest("http://notebank.click/courses");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        //create the subclass


        addItemsOnSpinner();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();


    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        // TODO: GET IS DONE - NEED TO DO POST
        GetData gd = new GetData();
        gd.execute();
        SystemClock.sleep(2000);
        //Reading information to the spinner
        ArrayList<String> list_CN = data_model.course_name;
        ArrayList<String> list_CT = data_model.term;
        ArrayList<String> list_CProf = data_model.prof;


        sp_course_name = (Spinner) findViewById(R.id.course_name);
        sp_course_id = (Spinner) findViewById(R.id.term);
        sp_course_prof = (Spinner) findViewById(R.id.course_prof);

        // generate all drop-down list
        ArrayAdapter<String>  dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                list_CN);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_course_name.setAdapter(dataAdapter);



        dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                list_CT);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_course_id.setAdapter(dataAdapter);



        dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                list_CProf);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_course_prof.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        // TODO: 6/17/16 may not need listener for each spinner
        sp_course_name = (Spinner) findViewById(R.id.course_name);
        sp_course_name.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        sp_course_name = (Spinner) findViewById(R.id.course_name);
        sp_course_id = (Spinner) findViewById(R.id.term);
        btnSubmit = (Button) findViewById(R.id.course_search);
        final Context context = this;

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: 6/17/16 need search
                Toast.makeText(UploadActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : " + String.valueOf(sp_course_name.getSelectedItem()) +
                                "\nSpinner 2 : " + String.valueOf(sp_course_id.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, UploadChoiceActivity.class);
                startActivity(intent);
            }
        });
    }

}
