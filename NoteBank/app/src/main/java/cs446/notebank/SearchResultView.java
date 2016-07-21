package cs446.notebank;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.view.View;
import android.content.Intent;



import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by aaronlovejan on 6/17/16.
 */
public class SearchResultView extends Activity {


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

        DataRequest search_result = new DataRequest();
        search_result.getRequest("http://notebank.click/documents");
        JSONArray search_json = search_result.search_json;
        int t = search_json.length();
        Log.d("here2",Integer.toString(t));
        // TODO: 7/21/16 should filter the json result

        try {
            for (int i = 0; i < search_json.length(); i++) {

                JSONObject temp = search_json.getJSONObject(i);
                final int data_id = temp.getInt("data_id");
                TextView tv = new TextView(this);
                tv.setText("Name + Format + " + Integer.toString(data_id));
                ll.addView(tv);
                Button b = new Button(this);
                b.setText("Preview");
                ll.addView(b);

                final Context context = this;
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PdfViewActivity.class);
                        intent.putExtra("data_id",data_id);
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
