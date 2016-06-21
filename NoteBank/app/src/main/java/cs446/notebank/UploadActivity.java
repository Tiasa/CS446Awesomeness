package cs446.notebank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by aaronlovejan on 6/10/16.OO
 */
public class UploadActivity extends Activity {
    
    private Spinner sp_course_name,sp_course_id,sp_course_prof;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        addItemsOnSpinner();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        // TODO: 6/17/16 The course info should query from server 
        // TODO: 6/17/16 And the course info should match each other
        String list_CN[] = {"CS","Math","SE"};
        String list_CID[] = {"100","200","300"};
        String list_CProf[] = {"Prof A","Prof B"};
        sp_course_name = (Spinner) findViewById(R.id.upload_coursename_spinner);
        sp_course_id = (Spinner) findViewById(R.id.upload_courseid_spinner);
        sp_course_prof = (Spinner) findViewById(R.id.upload_courseprof_spinner);

        // generate all drop-down list
        ArrayAdapter<String>  dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_CN);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_course_name.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_CID);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_course_id.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_CProf);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_course_prof.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        // TODO: 6/17/16 may not need listener for each spinner 
        sp_course_name = (Spinner) findViewById(R.id.upload_coursename_spinner);
        sp_course_name.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        sp_course_name = (Spinner) findViewById(R.id.upload_coursename_spinner);
        sp_course_id = (Spinner) findViewById(R.id.upload_courseid_spinner);
        btnSubmit = (Button) findViewById(R.id.note_upload);
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
