package cs446.notebank;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;



/**
 * Created by aaronlovejan on 6/17/16.
 */
public class SearchResultView extends Activity {

    private static DataRequest search_result = new DataRequest();


    private class GetData extends AsyncTask<Void ,Void, Void>{
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SearchResultView.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            search_result.getRequest("http://notebank.click/documents");
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult);

        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        String course_id = getIntent().getStringExtra("course_id");
        String course_name = getIntent().getStringExtra("course_name");
        String course_prof = getIntent().getStringExtra("course_prof");

        GetData gd = new GetData();
        gd.execute();
        SystemClock.sleep(2000);

        JSONArray jsonArray = search_result.search_result;

        int num = jsonArray.length();

        Log.d("info",Integer.toString(num));
        // TODO: 7/21/16 should filter the json result

        try {
            for (int i = 0; i < num; i++) {

                JSONObject temp= jsonArray.getJSONObject(i);

                String data_id = temp.getString("data_id");
                Log.d("info",data_id);
                TextView tv = new TextView(this);
                tv.setText("Name + Format + " + data_id);
                ll.addView(tv);
                Button b = new Button(this);
                b.setText("Preview");
                ll.addView(b);
                final int value=Integer.parseInt(data_id);

                final Context context = this;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PdfViewActivity.class);
                        intent.putExtra("data_id",value);
                        startActivity(intent);
                    }
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        }//end try-catch

        this.setContentView(sv);


    }



}
