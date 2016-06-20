package cs446.notebank;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {
    private Button upload,download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_activity);
        addListenerOnButton();
    }
    public void addListenerOnButton() {
        upload = (Button) findViewById(R.id.upload_button);
        download = (Button) findViewById(R.id.download_button);
        final Context context = this;
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UploadActivity.class);
                startActivity(intent);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DownloadActivity.class);
                startActivity(intent);
            }
        });
    }
}
