package cs446.notebank;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;


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


        // real info should come from model
        for(int i = 0; i < 10; i++) {
            TextView tv = new TextView(this);
            tv.setText("Name + Format");
            ll.addView(tv);
            Button b = new Button(this);
            b.setText("Preview");
            ll.addView(b);
        }
        this.setContentView(sv);


    }

}
