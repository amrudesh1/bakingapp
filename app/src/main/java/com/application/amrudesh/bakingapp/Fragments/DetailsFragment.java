package com.application.amrudesh.bakingapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment implements View.OnClickListener {
    private JSONObject list,StepsJsonList;
    private JSONArray StepsArray;
    FragmentListerner listerner;
    RequestQueue queue;
    List<Steps> stepsList;
    @BindView(R.id.playerView)
    SimpleExoPlayer exoPlayer;
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


    int index,arrayPosition;
    boolean tablet;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this,view);
        queue = Volley.newRequestQueue(getActivity());

        Bundle bundle;
        if (savedInstanceState == null)
        {
            bundle = getArguments();
            index = bundle.getInt("index");
            arrayPosition =bundle.getInt("current");
            tablet = bundle.getBoolean("tablet");

            Log.i("TAG_ARRAY_VALUE",String.valueOf(arrayPosition));
            Log.i("TAG_POS_DETAIL",String.valueOf(index));
            Log.i("TAG_POS_TAB",String.valueOf(tablet));
        }

        f1.setOnClickListener(this);
        f2.setOnClickListener(this);
        return view;

    }



    private List<Steps> getSteps(final int index)
    {
        JsonArrayRequest stepsRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i =index;i<=index;i++)
                        {
                            try {
                                list = response.getJSONObject(i);
                                StepsArray = new JSONArray(list.getString("steps"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j =0;j <StepsArray.length();j++)
                        {
                            try {
                                StepsJsonList = StepsArray.getJSONObject(j);
                                Steps steps = new Steps();
                                steps.setDescription(StepsJsonList.getString("description"));
                                steps.setShortdescription(StepsJsonList.getString("shortDescription"));
                                steps.setVideoURL(StepsJsonList.getString("videoURL"));
                                steps.setThumbnailURL(StepsJsonList.getString("thumbnailURL"));
                                stepsList.add(steps);


                                Log.i("Steps",steps.getDescription());
                                Log.i("Steps",steps.getShortdescription());



                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
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

    }

}
