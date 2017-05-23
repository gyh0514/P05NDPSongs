package android.myapplicationdev.com.p05_ndpsongs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    Button btn5stars;
    ListView lv;
    SongAdapter aa;
    ArrayList<Song> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = (ListView)findViewById(R.id.lv);
        btn5stars = (Button)findViewById(R.id.btn5stars);
        //al = new ArrayList<String>();


        final DBHelper dbh = new DBHelper(SecondActivity.this);
        al = dbh.getAllSongs();
        

        aa = new SongAdapter(this, R.layout.row, al);
        lv.setAdapter(aa);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Intent i = new Intent(SecondActivity.this,
                        EditActivity.class);
                al = new ArrayList<Song>();
                al.addAll(dbh.getAllSongs());
                Song data1 = al.get(position);


//                String data = al.get(position);
                //String id = data.split(",")[0].split(":")[1];
                //String title = data.split(",")[1].trim();
                //String title =
               // String title = data.

                //Song target = new Song(title, singers, year, star);
                i.putExtra("data", data1);
                startActivityForResult(i, 9);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            //btnRetrieve.performClick();
        }
    }
}
