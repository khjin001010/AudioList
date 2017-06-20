
package khjin001010.kr.hs.emirim.audiolist;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Button butPlay, butStop, butPause;
    TextView textMusic;
    ProgressBar progress;
    String[] musics={"gyoga","chch","cute","star","simsul"};
    int[] musicResIds = {R.raw.gyoga, R.raw.chch,R.raw.cute,R.raw.star,R.raw.simsul};
    int selectedMusicId;
    MediaPlayer mediaplay;
    boolean select_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list_music);
        butPlay = (Button)findViewById(R.id.but_play);
        butStop = (Button)findViewById(R.id.but_stop);
        textMusic = (TextView)findViewById(R.id.text_music);
        progress = (ProgressBar)findViewById(R.id.progress_music);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, musics);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setItemChecked(0, true);
        selectedMusicId = musicResIds[0];
        mediaplay = MediaPlayer.create(this,selectedMusicId);
        butPause = (Button)findViewById(R.id.but_suspend);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mediaplay.stop();
                selectedMusicId = musicResIds[position];
                progress.setVisibility(View.INVISIBLE);
            }
        });

        butPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select_pause==false) {
                    mediaplay = MediaPlayer.create(MainActivity.this, selectedMusicId);
                }
                select_pause = false;
                mediaplay.start();
                progress.setVisibility(View.VISIBLE);
            }
        });

        butStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaplay.stop();
                progress.setVisibility(View.INVISIBLE);
                select_pause = false;
            }
        });

        butPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaplay.pause();
                select_pause = true;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaplay.stop();
    }
}
