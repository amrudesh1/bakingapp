package com.application.amrudesh.bakingapp.Fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.application.amrudesh.bakingapp.Data.Steps;
import com.application.amrudesh.bakingapp.R;
import com.application.amrudesh.bakingapp.Util.Constants;
import com.application.amrudesh.bakingapp.Util.FragmentListerner;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment implements View.OnClickListener {
    private JSONObject list, StepsJsonList;
    private JSONArray StepsArray;
    View view;
    FragmentListerner listerner;
    RequestQueue queue;
    List<Steps> stepsList;
    SimpleExoPlayer exoPlayer;
    @BindView(R.id.playerView)
    PlayerView playerView;
    @BindView(R.id.f1)
    FrameLayout frameLayout1;
    @BindView(R.id.f2)
    FrameLayout frameLayout2;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.next_button)
    FloatingActionButton f1;
    @BindView(R.id.back_button)
    FloatingActionButton f2;
    @BindView(R.id.root)
    LinearLayout layout;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.current)
    TextView current;

    long playerPosition;
    int index, arrayPosition;
    boolean tablet;
    private String url;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this, view);
        stepsList = new ArrayList<>();

        queue = Volley.newRequestQueue(getActivity());
        Bundle bundle;
        bundle = getArguments();
        if (savedInstanceState == null) {

            index = bundle.getInt("index");
            arrayPosition = bundle.getInt("current");
            tablet = bundle.getBoolean("tablet");

            Log.i("TAG_ARRAY_VALUE", String.valueOf(arrayPosition));
            Log.i("TAG_POS_DETAIL", String.valueOf(index));
            Log.i("TAG_POS_TAB", String.valueOf(tablet));
        } else {
            index = savedInstanceState.getInt("index");
            arrayPosition = savedInstanceState.getInt("current");
            tablet = savedInstanceState.getBoolean("tablet");
            playerPosition = savedInstanceState.getLong("position");
            url = savedInstanceState.getString("url");

        }

        stepsList = getSteps(arrayPosition, index);
        f1.setOnClickListener(this);
        f2.setOnClickListener(this);


        Log.i("PLAYER",String.valueOf(playerPosition));
        return view;

    }

    private void showUI() {
        if (index <= 0) {
            f2.hide();
        } else {
            f2.show(); }
        if (index >=stepsList.size()-1) {
            f1.hide(); } else {
            f1.show(); }

    }

    private void ListTheSteps(List<Steps> stepsList, int list_pos) {

        releasePlayer();
        if (stepsList.get(list_pos).getVideoURL().isEmpty() && stepsList.get(list_pos).getThumbnailURL().isEmpty())
        {
            playerView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            empty.setText("No videos or Thumbnails to Display");

        }
        else if(!stepsList.get(list_pos).getVideoURL().isEmpty())
        {
            url = stepsList.get(list_pos).getVideoURL();
            empty.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
            initilisePlayer(url);
        }
        else
        {
            String IMG_URL= stepsList.get(list_pos).getThumbnailURL();
            empty.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);

            Picasso.get()
                    .load(IMG_URL)
                    .placeholder(R.drawable.no_image_found)
                    .into(imageView);
        }
    hideSystemInterface();
        description.setText(stepsList.get(list_pos).getDescription());
        current.setText((list_pos+1)+"/"+stepsList.size());




    }

    private List<Steps> getSteps(final int index, final int click_loc) {
        stepsList.clear();
        JsonArrayRequest stepsRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = index; i <= index; i++) {
                            try {
                                list = response.getJSONObject(i);
                                StepsArray = new JSONArray(list.getString("steps"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j = 0; j < StepsArray.length(); j++) {
                            try {
                                StepsJsonList = StepsArray.getJSONObject(j);
                                Steps steps = new Steps();
                                steps.setDescription(StepsJsonList.getString("description"));
                                steps.setShortdescription(StepsJsonList.getString("shortDescription"));
                                steps.setVideoURL(StepsJsonList.getString("videoURL"));
                                steps.setThumbnailURL(StepsJsonList.getString("thumbnailURL"));
                                stepsList.add(steps);
                                Log.i("Steps", steps.getDescription());
                                Log.i("Steps", steps.getShortdescription());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        ListTheSteps(stepsList, click_loc);
                        showUI();
                        Log.i("Steps",String.valueOf(stepsList.size()));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });
        queue.add(stepsRequest);
        return stepsList;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next_button:
                index++;
                getSteps(arrayPosition, index);
                break;
            case R.id.back_button:
                index--;
                getSteps(arrayPosition, index);
                break;
        }
    }


    private void initilisePlayer(String url) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        playerView.setPlayer(exoPlayer);
        exoPlayer.setPlayWhenReady(true);
        DataSource.Factory datasourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "Videoplayer"));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(datasourceFactory)
                .createMediaSource(Uri.parse(url));
        if (playerPosition != C.TIME_UNSET) {
            exoPlayer.seekTo(playerPosition);
        }
        exoPlayer.prepare(mediaSource,true, false);


    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }

    }
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (tablet) {
            return;
        }



    }

    private void hideSystemInterface() {
        if(tablet)
        {
            return;
        }
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        empty.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }
    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
//        playerPosition =  exoPlayer.getCurrentPosition();
        Log.i("PLAYER","ONPAUSE METHOD CALLED");

    }
    @Override
    public void onStop() {
        super.onStop();
        {
            releasePlayer();
        }
    }
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("position",playerPosition);
        outState.putInt("index",index);
        outState.putInt("current",arrayPosition);
        outState.putBoolean("tablet",tablet);
        outState.putString("url",url);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (url != null)
        {
            initilisePlayer(url);
        }
    }
}