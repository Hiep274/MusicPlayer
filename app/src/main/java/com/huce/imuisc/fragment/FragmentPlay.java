package com.huce.imuisc.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.huce.imuisc.R;
import com.huce.imuisc.adapter.LyricAdapter;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.SongInfo;
import com.huce.imuisc.api.response.Streaming;
import com.huce.imuisc.model.Lyric;
import com.huce.imuisc.model.Song;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;


public class FragmentPlay extends Fragment {

    private ImageView ivFastRewindMusic, ivPlayPauseMusic, ivFastForwardMusic, imageSingle,imageButtonPre,imageButtonNe;
    private TextView tvCurrentTime, tvDurationTime,textViewSongName, textViewArtistName, textviewlyric;
    private SeekBar sbPlayMusic;
    private static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    ObjectAnimator objectAnimator;
    LyricAdapter lyricAdapter;
    androidx.appcompat.widget.Toolbar toolbarplay;
    View view;
    RecyclerView recyclerViewLyric;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String id ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("data",getActivity().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        view = inflater.inflate(R.layout.fragment_play, container, false);
        textviewlyric = view.findViewById(R.id.textviewlyric);
        toolbarplay = view.findViewById(R.id.toolbarlyric);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarplay);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplay.setTitleTextColor(Color.WHITE);
        toolbarplay.setNavigationOnClickListener(v -> getActivity().finish());
        ivFastRewindMusic = view.findViewById(R.id.imageButtonPreview);
        ivPlayPauseMusic = view.findViewById(R.id.imageButtonPlayPause);
        ivFastForwardMusic = view.findViewById(R.id.imageButtonNext);
        tvCurrentTime = view.findViewById(R.id.textViewRunTime);
        tvDurationTime = view.findViewById(R.id.textViewTimeTotal);
        textViewSongName = view.findViewById(R.id.textViewSongName);
        textViewArtistName = view.findViewById(R.id.textViewArtistName);
        sbPlayMusic = view.findViewById(R.id.seekBartime);
        imageSingle = view.findViewById(R.id.imgsingle);
        imageButtonNe = view.findViewById(R.id.imageButtonNe);
        imageButtonPre = view.findViewById(R.id.imageButtonPre);
        recyclerViewLyric = view.findViewById(R.id.recyclerviewlyric);

        objectAnimator = ObjectAnimator.ofFloat(imageSingle, "rotation", 0f, 360f);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();

        Intent intent =((AppCompatActivity)getActivity()).getIntent();
        if(intent.getStringExtra("id") != null) {
            id = intent.getStringExtra("id");
            editor.putString("lastPlayId",id);
            editor.apply();
        }else {
            id = sharedPreferences.getString("lastPlayId", "");
        }
        if (id == null) {
            textViewSongName.setText("Chưa chọn bài hát !");
            textViewArtistName.setText("Chưa chọn bài hát!");
        }else {
            getSong(id);
            getStreaming(id);
            getLyric(id);
        }
        imageButtonNe.setOnClickListener(v -> {
            if(objectAnimator.isRunning()){
                objectAnimator.end();
            }
            mediaPlayer.reset();
            id = sharedPreferences.getString("lastPlayId", "");
            getNextSong(id);
            sbPlayMusic.setProgress(0);
            mediaPlayer.start();
            ivPlayPauseMusic.setImageResource(R.drawable.nutpause);
            updateSeekBar();
        });

        imageButtonPre.setOnClickListener(v -> {
            if (objectAnimator.isRunning()){
                objectAnimator.end();
            }
            mediaPlayer.reset();
            id = sharedPreferences.getString("lastPlayId", "");
            getPrevSong(id);
            sbPlayMusic.setProgress(0);
            mediaPlayer.start();
            ivPlayPauseMusic.setImageResource(R.drawable.nutpause);
            updateSeekBar();
        });

        sbPlayMusic.setMax(100);

        ivFastRewindMusic.setOnClickListener(v -> {
            int currentPosition = mediaPlayer.getCurrentPosition();
            if (mediaPlayer.isPlaying() && currentPosition > 10000) {
                currentPosition -= 10000;
                mediaPlayer.seekTo(currentPosition);
            }
        });
        ivPlayPauseMusic.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                handler.removeCallbacks(runnable);
                mediaPlayer.pause();
                objectAnimator.pause();
                ivPlayPauseMusic.setImageResource(R.drawable.nutpause);
            } else {
                mediaPlayer.start();
                if(objectAnimator.isPaused()) {
                    objectAnimator.resume();
                }else {
                    objectAnimator.start();
                }
                ivPlayPauseMusic.setImageResource(R.drawable.nutplay);
                updateSeekBar();
            }
        });
        ivFastForwardMusic.setOnClickListener(v -> {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            if (mediaPlayer.isPlaying() && duration != currentPosition) {
                currentPosition += 10000;
                mediaPlayer.seekTo(currentPosition);
            }
        });

        sbPlayMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int playPosition = (mediaPlayer.getDuration() / 100) * progress;
                    mediaPlayer.seekTo(playPosition);
                    tvCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateSeekBar();
            }
        });

        mediaPlayer.setOnBufferingUpdateListener((mediaPlayer, percent) -> {
            sbPlayMusic.setSecondaryProgress(percent);
        });

        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            ivPlayPauseMusic.setImageResource(R.drawable.nutpause);
            sbPlayMusic.setProgress(0);
            tvCurrentTime.setText(R.string.zero_time);
            tvDurationTime.setText(R.string.zero_time);
        });
        return view;
    }

    private void getSong(String id) {
        ApiService.apiService.getInfoSong(id).enqueue(new Callback<SongInfo>() {
            @Override
            public void onResponse(@NonNull Call<SongInfo> call, @NonNull Response<SongInfo> response) {
                if (response.isSuccessful()) {
                    SongInfo songInfo = response.body();
                    textViewSongName.setText(songInfo.getTitle());
                    textViewArtistName.setText(songInfo.getArtistsNames());
                    Picasso.get().load(songInfo.getThumbnailM()).into(imageSingle);

                }
            }

            @Override
            public void onFailure(@NonNull Call<SongInfo> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareMediaPlayer(String url) {
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            tvDurationTime.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
            tvCurrentTime.setText(R.string.zero_time);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                updateSeekBar();
                long currentDuration = mediaPlayer.getCurrentPosition();
                tvCurrentTime.setText(milliSecondsToTimer(currentDuration));
            }
            handler.postDelayed(runnable, 1000);
        }
    };

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            sbPlayMusic.setProgress(mediaPlayer.getCurrentPosition() * 100 / mediaPlayer.getDuration());
            handler.postDelayed(runnable, 1000);
        }
    }

    private String milliSecondsToTimer(long milliSeconds) {
        String timer = "";
        String second = "";
        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            timer = hours + ":";
        }
        if (seconds < 10) {
            second = "0" + seconds;
        } else {
            second = "" + seconds;
        }
        timer = timer + minutes + ":" + second;
        return timer;
    }

    private void getStreaming(String id) {
        ApiService.apiService.getStreaming(id).enqueue(new Callback<Streaming>() {

            @Override
            public void onResponse(@NonNull Call<Streaming> call, @NonNull Response<Streaming> response) {
                if (response.isSuccessful()) {
                    Streaming streaming = response.body();
                    if (streaming != null) {
                        try {
                            Pattern pattern = Pattern.compile("https://");
                            if (pattern.matcher(streaming.get_320()).find()) {
                                prepareMediaPlayer(streaming.get_320());
                            } else {
                                prepareMediaPlayer(streaming.get_128());
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Streaming> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getLyric(String id) {
        ApiService.apiService.getLyric(id).enqueue(new Callback<ArrayList<Lyric>>() {

            @Override
            public void onResponse(@NonNull Call<ArrayList<Lyric>> call, @NonNull Response<ArrayList<Lyric>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Lyric> manglyric = response.body();
                    lyricAdapter = new LyricAdapter(getContext(), manglyric);
                    recyclerViewLyric.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerViewLyric.setAdapter(lyricAdapter);

                }
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Lyric>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPrevSong(String id) {
        ApiService.apiService.getRecommend(id).enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Song>> call, @NonNull Response<ArrayList<Song>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String id = response.body().get(response.body().size() - 1).getId();
                        editor.putString("lastPlayId",id);
                        editor.apply();
                        getSong(id);
                        getLyric(id);
                        getStreaming(id);
                    } else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Song>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNextSong(String id) {
        ApiService.apiService.getRecommend(id).enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Song>> call, @NonNull Response<ArrayList<Song>> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null){
                        String id = response.body().get(0).getId();
                        editor.putString("lastPlayId",id);
                        editor.apply();
                        getSong(id);
                        getLyric(id);
                        getStreaming(id);
                    }
                    else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Song>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}