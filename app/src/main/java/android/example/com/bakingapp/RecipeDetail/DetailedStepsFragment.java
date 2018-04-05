package android.example.com.bakingapp.RecipeDetail;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.data.Steps;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

/**
 * Created by Abbie on 20/03/2018.
 */

public class DetailedStepsFragment extends Fragment{
    private  Steps step;

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer exoPlayer;

    private long exoCurrentPosition = 0;
    private boolean playerStopped = false;
    private long playerStopPosition;

    String videoURL;
    String thumbnailURL;

    public DetailedStepsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detailed_step_fragment, container, false);

        TextView description = rootView.findViewById(R.id.detailed_textview);
        ImageView thumbnailImage = rootView.findViewById(R.id.image_thumbnail);
        TextView stepidTextview = rootView.findViewById(R.id.step_id_textview);

        simpleExoPlayerView = rootView.findViewById(R.id.video_view);

        if (this.getArguments() != null) {
            step = this.getArguments().getParcelable("steps");
            description.setText(step.getDescription());
        }

        stepidTextview.setText(String.valueOf("Step " + step.getSteps_id()));

        videoURL = step.getVideoURL();
        thumbnailURL = step.getThumbnailURL();

        if (!TextUtils.isEmpty(thumbnailURL)) {
            thumbnailImage.setVisibility(View.GONE);

        } else {
            thumbnailImage.setVisibility(View.VISIBLE);

            if(!thumbnailURL.equals("")) {
                Picasso.get()
                        .load(thumbnailURL)
                        .error(R.drawable.noimage)
                        .into(thumbnailImage);
            } else {
                Picasso.get()
                        .load(R.drawable.noimage)
                        .into(thumbnailImage);
            }
        }

        if (!TextUtils.isEmpty(videoURL)) {
            initializePlayer(Uri.parse(videoURL));
            thumbnailImage.setVisibility(View.GONE);
        } else {
            assert simpleExoPlayerView != null;
            simpleExoPlayerView.setVisibility(View.GONE);
            releasePlayer();
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(videoURL))
            initializePlayer(Uri.parse(videoURL));
    }

    @Override
    public void onStop() {
        super.onStop();
        if(exoPlayer != null) {
            playerStopPosition = exoPlayer.getCurrentPosition();
            playerStopped = true;
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    private void initializePlayer(Uri mediaUri){

        if(exoPlayer == null){

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            simpleExoPlayerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getContext(), String.valueOf(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getContext(),userAgent),
                    new DefaultExtractorsFactory(),null,null);
            exoPlayer.prepare(mediaSource);


            if (exoCurrentPosition != 0 && !playerStopped){
                exoPlayer.seekTo(exoCurrentPosition);
            } else {
                exoPlayer.seekTo(playerStopPosition);
            }
        }
    }

    private void releasePlayer(){
        if(exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}