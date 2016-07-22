package cs446.notebank;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class UploadChoiceActivity extends AppCompatActivity {
    Button picture;
    NumberPicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_choice);
        addListenerOnButton();
    }
    public void addListenerOnButton() {
//        picture = (Button) findViewById(R.id.picture_button);
//        picker = (NumberPicker) findViewById(R.id.numberPicker);
//        picture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int REQUEST_IMAGE_CAPTURE = picker.getValue();
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                }
//            }
//        });
    }
}
