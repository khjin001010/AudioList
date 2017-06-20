
package khjin001010.kr.hs.emirim.audiolist;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Button butPlay, butStop, butPause;
    TextView textMusic,textTime;
    ProgressBar progress;
    String[] musics={"gyoga","chch","cute","star","simsul"};
    int[] musicResIds = {R.raw.gyoga, R.raw.chch,R.raw.cute,R.raw.star,R.raw.simsul};
    int selectedMusicId;
    MediaPlayer mediaplay;
    boolean select_pause;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list_music);
        butPlay = (Button)findViewById(R.id.but_play);
        butStop = (Button)findViewById(R.id.but_stop);
        textMusic = (TextView)findViewById(R.id.text_music);
        textTime = (TextView)findViewById(R.id.textTime);
        progress = (SeekBar)findViewById(R.id.progress_music);
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
                textMusic.setText(musics[i]);
                selectedMusicId = musicResIds[position];
                progress.setVisibility(View.INVISIBLE);
                i = position;
            }
        });

        butPlay.setOnClickListener(new View.OnClickListener() {
            SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
            @Override
            public void onClick(View v) {
                textMusic.getText();
                if(select_pause==false) {
                    mediaplay = MediaPlayer.create(MainActivity.this, selectedMusicId);
                }
                select_pause = false;
                mediaplay.start();
                progress.setVisibility(View.VISIBLE);

                Thread musicThread = new Thread(){
                    @Override
                    public void run() { //run 메서드 오버라이딩
                        super.run();//지우든 안 지우든 상관 X

                        if(mediaplay==null) return;
                        progress.setMax(mediaplay.getDuration()); //최대값 설정 //getDuration : 음악의 전체 길이
                        while(mediaplay.isPlaying()){ //실행중이라면
                            progress.setProgress(mediaplay.getCurrentPosition()); //바의 위치 설정 //getCurrentPosition() : 음악이 진행되는 현재 위치
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textTime.setText("진행시간: "+timeFormat.format(mediaplay.getCurrentPosition()));
                                }
                            });
                            SystemClock.sleep(200); //0.2초 간격으로 바가 움직임
                        }
                    }
                };
                musicThread.start(); //이것을 실행해야 New born -> Runnable -> Running 이 되는 것이다.
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
