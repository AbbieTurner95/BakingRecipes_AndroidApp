package android.example.com.bakingapp;

import android.content.Context;
import android.example.com.bakingapp.data.Steps;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbie on 20/03/2018.
 */

public class DetailedStepsFragment extends Fragment{
    Steps step;

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;

    String videoURL;
    String thumbnailURL;

    public DetailedStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detailed_step_fragment, container, false);

        TextView description = rootView.findViewById(R.id.detailed_textview);
        TextView stepidTextview = rootView.findViewById(R.id.step_id_textview);
        simpleExoPlayerView = rootView.findViewById(R.id.video_view);

        if (this.getArguments() != null) {
            step = this.getArguments().getParcelable("steps");
            description.setText(step.getDescription());
        }

        stepidTextview.setText(String.valueOf("Step " + step.getSteps_id()));
        videoURL = step.getVideoURL();
        thumbnailURL = step.getThumbnailURL();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}