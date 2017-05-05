package androi30_b.my_mp3_app;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import static android.R.attr.path;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener, MusicPlayer.OnCompletionListener {
    private ListView lvMusic;
    private TextView tvTitle, tvArtist, tvTimeProcess, tvTimeTotal;
    private SeekBar sbProcess;
    private ImageView ivShuffle ,ivPrevious , ivPlay, ivRepeat, ivNext;
    private ArrayList<String> paths; // lưu tất cả đường dẫn file nhạc
    private int timeProcess;
    private int timeTotal;
    private PlayListAdapter adapter;
    private MusicPlayer musicPlayer;
    private boolean isRunning;
    private int UPDATE_TIME = 1;
    private int timeCurrent;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ
        initViews();
        //set sự kiện cho các view
        initListeners();
        initComponents();
    }

    private void initComponents() {
        initList();
        adapter = new PlayListAdapter(App.getContext(), paths);
        lvMusic.setAdapter(adapter);
        musicPlayer = new MusicPlayer();
        musicPlayer.setOnCompletion(this);
    }

    private void initList() {
        paths = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        File file = new File(path);
        File[] files = file.listFiles(); // lấy tất cả các file trong thư mục lưu bài hát
        for (int i = 0; i< files.length; i++){
            String s = files[i].getName();
            if(s.endsWith(".mp3")){
                paths.add(files[i].getAbsolutePath());
            }
        }
    }

    private void initListeners() {
        lvMusic.setOnItemClickListener(this);
        ivShuffle.setOnClickListener(this);
        ivPrevious.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivRepeat.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        sbProcess.setOnSeekBarChangeListener(this);
    }

    private void initViews() {
        lvMusic = (ListView) findViewById(R.id.lv_play_list);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvArtist = (TextView) findViewById(R.id.tv_artist);
        tvTimeProcess = (TextView) findViewById(R.id.tv_time_process);
        tvTimeTotal = (TextView) findViewById(R.id.tv_time_total);
        sbProcess = (SeekBar) findViewById(R.id.sb_process);
        ivPrevious = (ImageView) findViewById(R.id.iv_previous);
        ivShuffle = (ImageView) findViewById(R.id.iv_shuffle);
        //ivPrevious = (ImageView) findViewById(R.id.iv_);
        ivPlay = (ImageView) findViewById(R.id.iv_play);
        ivRepeat = (ImageView) findViewById(R.id.iv_repeat);
        ivNext = (ImageView) findViewById(R.id.iv_next);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == UPDATE_TIME){
                timeCurrent = musicPlayer.getTimeCurrent();
                tvTimeProcess.setText(getTimeFormat(timeCurrent));
                sbProcess.setProgress(timeCurrent);
            }
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        String path = paths.get(position);
        playMusic(path);
    }
    public void playMusic(String path){

        if(musicPlayer.getState() == MusicPlayer.PLAYER_PLAY){
            musicPlayer.stop();
        }
        musicPlayer.setUp(path);
        musicPlayer.play();
        ivPlay.setImageResource(R.drawable.pause);
        //setup tên bài hát vf tên ca sĩ
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(paths.get(position));
        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        tvArtist.setText(artist);
        tvTitle.setText(title);
        isRunning = true;

        //set up time total
        tvTimeTotal.setText(getTimeFormat(musicPlayer.getTimeTotal()));
        //process time //set up seekbar
        sbProcess.setMax(musicPlayer.getTimeTotal());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning){
                    Message message = new Message();
                    message.what = UPDATE_TIME;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    private String getTimeFormat(long time){
        String tm = "";
        int s;
        int m;
        int h;
        //giây
        s = (int) time % 60;
        m = (int ) ((time - s)/60);
        if(m >=60){
            h = m/60;
            m = m % 60;
            if (h > 0){
                if (h < 10){
                    tm += "0" + h + ":";

                }else {
                    tm += h + ":";
                }
            }
        }
        if (m < 10){
            tm += "0" + m + ":";
        }else {
            tm += m +":";
        }
        if(s < 10){
            tm += "0" + s + ":";
        }else {
            tm += s +"";
        }
        return tm;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_next:
                nextMusic();
                break;
            case R.id.iv_play:
                if (musicPlayer.getState() == MusicPlayer.PLAYER_PLAY){
                    ivPlay.setImageResource(R.drawable.play);
                    musicPlayer.pause();
                }else {
                    ivPlay.setImageResource(R.drawable.pause);
                    musicPlayer.play();
                }
                break;
            case R.id.iv_previous:
                previousMusic();
                break;
            default:
                break;
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(timeCurrent != progress && timeCurrent !=0){
            musicPlayer.seek(sbProcess.getProgress() * 1000);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onEndMusic() {
        //khi ket thuc bai hat no se vo day
      nextMusic();

    }

    private void nextMusic() {
        position++;
        if (position >= paths.size()){
            position = 0;
        }
        String path = paths.get(position);
        playMusic(path);
    }
    private void previousMusic() {
        position--;
        if (position < 0){
            position = paths.size() - 1;
        }
        String path = paths.get(position);
        playMusic(path);

    }

}
