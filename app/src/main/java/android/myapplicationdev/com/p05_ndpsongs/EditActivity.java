package android.myapplicationdev.com.p05_ndpsongs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditActivity extends AppCompatActivity {

    Button btnDelete,btnEdit,btnCancel;
    Song data;
    EditText editTitle,editSinger,editYear;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        id = (TextView) findViewById(R.id.id);
        btnEdit = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        editSinger = (EditText) findViewById(R.id.edtSingers);
        editTitle = (EditText) findViewById(R.id.edtTitle);
        editYear = (EditText) findViewById(R.id.edtYear);


        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        id.setText(data.getId()+"");
        editSinger.setText(data.getSingers());
        editTitle.setText(data.getTitle());
        editYear.setText(data.getYear()+"");



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteSong(data.getId());
                dbh.close();
                Intent i = new Intent();
                i.putExtra("grade", 9);
                // Set result to RESULT_OK to indicate normal response and pass in the intent containing the data
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the RadioGroup object
                RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup1);
                // Get the Id of the selected radio button in the RadioGroup
                int selectedButtonId = rg.getCheckedRadioButtonId();
                // Get the radio button object from the Id we had gotten above
                RadioButton rb = (RadioButton) findViewById(selectedButtonId);

                String stars = rb.getText().toString();
                int star = Integer.parseInt(stars);

                String years = editYear.getText().toString();
                int year = Integer.parseInt(years);


                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setSingers(editSinger.getText().toString());
                data.setTitle(editTitle.getText().toString());
                data.setStars(star);
                data.setYear(year);
                dbh.updateSong(data);
                dbh.close();
                Intent i = new Intent();
                i.putExtra("grade", 9);
                // Set result to RESULT_OK to indicate normal response and pass in the intent containing the data
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }
}
