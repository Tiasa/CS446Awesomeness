package cs446.notebank;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;


/**
 * Created by aaronlovejan on 6/17/16.
 */
public class SearchResultView extends Activity {

    private static DataRequest search_result = new DataRequest();

    public static final int progress_bar_type = 0;
    private ProgressDialog pDialog;

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

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

    private class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/2011.kml");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            dismissDialog(progress_bar_type);
        }

        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
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

                final String data_id = temp.getString("data_id");
                String user_id = temp.getString("user_id");
                String created_at = temp.getString("created_at");
                Log.d("info",data_id);
                TextView tv = new TextView(this);
                tv.setText("User id " + user_id + " created at " + created_at);
                ll.addView(tv);
                Button b = new Button(this);
                b.setText("Preview");
                ll.addView(b);
                Button db = new Button(this);
                db.setText("Download");
                ll.addView(db);

                final int value=Integer.parseInt(data_id);

                db.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fileURL = "http://notebank.click/data/" + data_id;

                        try {
                            File sdCard = Environment.getExternalStorageDirectory();
                            File folder = new File(sdCard.getAbsolutePath() + "/dir");
                            folder.mkdirs();
                            File file = new File(folder, "Read.pdf");


                            FileOutputStream f = new FileOutputStream(file);
                            URL u = new URL(fileURL);
                            HttpURLConnection c = (HttpURLConnection) u.openConnection();
                            c.setRequestMethod("GET");
                            c.setDoOutput(true);
                            c.connect();

                            InputStream in = c.getInputStream();

                            byte[] buffer = new byte[1024];
                            int len1 = 0;
                            while ((len1 = in.read(buffer)) > 0) {
                                f.write(buffer, 0, len1);
                            }
                            f.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

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
