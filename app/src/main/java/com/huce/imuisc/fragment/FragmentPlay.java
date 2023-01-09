package com.huce.imuisc.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huce.imuisc.R;
import com.huce.imuisc.api.ApiService;
import com.huce.imuisc.api.response.SongInfo;
import com.huce.imuisc.api.response.Streaming;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPlay extends Fragment {

    private ImageView ivFastRewindMusic, ivPlayPauseMusic, ivFastForwardMusic, imageSingle;
    private TextView tvCurrentTime, tvDurationTime,textViewSongName, textViewArtistName;
    private SeekBar sbPlayMusic;
    private static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    androidx.appcompat.widget.Toolbar toolbar;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play, container, false);

        toolbar = view.findViewById(R.id.toolbarplay);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(v -> getActivity().finish());


        ivFastRewindMusic = view.findViewById(R.id.imageButtonPreview);
        ivPlayPauseMusic = view.findViewById(R.id.imageButtonPlayPause);
        ivFastForwardMusic = view.findViewById(R.id.imageButtonNext);
        tvCurrentTime = view.findViewById(R.id.textViewRunTime);
        tvDurationTime = view.findViewById(R.id.textViewTimeTotal);
        textViewSongName = view.findViewById(R.id.textViewSongName);
        textViewArtistName = view.findViewById(R.id.textViewArtistName);
        imageSingle = view.findViewById(R.id.imgsingle);
        sbPlayMusic = view.findViewById(R.id.seekBartime);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();

        sbPlayMusic.setMax(100);
        ivFastRewindMusic.setOnClickListener(v -> {
            int currentPosition = mediaPlayer.getCurrentPosition();
            if (mediaPlayer.isPlaying() && currentPosition > 5000) {
                currentPosition -= 5000;
                mediaPlayer.seekTo(currentPosition);
            }
        });
        ivPlayPauseMusic.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                handler.removeCallbacks(runnable);
                mediaPlayer.pause();
                ivPlayPauseMusic.setImageResource(R.drawable.nutpause);
            } else {
                mediaPlayer.start();
                ivPlayPauseMusic.setImageResource(R.drawable.nutplay);
                updateSeekBar();
            }
        });
        ivFastForwardMusic.setOnClickListener(v -> {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            if (mediaPlayer.isPlaying() && duration != currentPosition) {
                currentPosition += 5000;
                mediaPlayer.seekTo(currentPosition);
            }
        });
        Intent intent =((AppCompatActivity)getActivity()).getIntent();
        String id = intent.getStringExtra("id");
        if (id == null) {
            textViewSongName.setText("Chưa chọn bài hát !");
            textViewArtistName.setText("Chưa chọn bài hát!");
        }else {
            getSong(id);
            getStreaming(id);
            mediaPlayer.start();
        }
//        getSong(id);
//        getStreaming(id);
//        mediaPlayer.start();


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
            mediaPlayer.reset();
//            getSong(id);
//            getStreaming(id);
//            mediaPlayer.start();
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
                }else {
                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SongInfo> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareMediaPlayer(String url) {
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            tvDurationTime.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
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
}