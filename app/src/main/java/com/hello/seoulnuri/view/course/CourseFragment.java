package com.hello.seoulnuri.view.course;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hello.seoulnuri.R;
import com.hello.seoulnuri.model.CourseItem;
import com.hello.seoulnuri.model.course.CourseStarData;
import com.hello.seoulnuri.model.course.CourseStarResponse;
import com.hello.seoulnuri.network.ApplicationController;
import com.hello.seoulnuri.network.NetworkService;
import com.hello.seoulnuri.utils.SharedPreference;
import com.hello.seoulnuri.view.course.adapter.CourseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private NetworkService networkService;
    ArrayList<CourseItem> courseList;
    private RecyclerView rv;
    private LinearLayoutManager mLinearLayoutManager;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(/*String param1, String param2*/) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_course, container, false);
        courseList = new ArrayList<CourseItem>();

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        Networking();

        //레이아웃매니저
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 2);

        rv = (RecyclerView) view.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);


        return view;
    }
    public void Networking(){
        Call<CourseStarResponse> requestDetail = networkService.getCourseStar();
        requestDetail.enqueue(new Callback<CourseStarResponse>() {
            @Override
            public void onResponse(Call<CourseStarResponse> call, Response<CourseStarResponse> response) {
                if(response.isSuccessful()) {
                    Map<String, Map<String, Double>> courseStarData;
                    double[] c_data_array = new double[8];

                    courseStarData = response.body().getData();
                    int i = 0;

                    for (Map.Entry<String, Map<String, Double>> entry : courseStarData.entrySet()) {

                        String key = entry.getKey();
                        Log.v("courseFragment", key);
                        Log.v("mapValue", "value = " + entry.getValue());

                        for (String mapkey : entry.getValue().keySet()){
                            Log.v("mapvalue" ,"key:"+mapkey+",value:"+entry.getValue().get(mapkey));
                            c_data_array[i] = entry.getValue().get(mapkey);
                            i++;
                        }
                    }

                    for (int j = 0; j < c_data_array.length; j++) {
                        Log.v("c_data_array", "value = " + c_data_array[j]);
                    }

                    CourseItem[] item = new CourseItem[4];
                    item[0]=new CourseItem(R.drawable.card_graphic_course_1, R.drawable.course_eye, "시각장애인 여행 추천", c_data_array[0], c_data_array[1]);
                    item[1]=new CourseItem(R.drawable.card_graphic_course_2, R.drawable.course_card_wheel, "지체장애인 여행 추천 ", c_data_array[4], c_data_array[5]);
                    item[2]=new CourseItem(R.drawable.card_graphic_course_3, R.drawable.course_card_ear, "청각장애인 여행 추천", c_data_array[2], c_data_array[3]);
                    item[3]=new CourseItem(R.drawable.card_graphic_course_4, R.drawable.course_card_elder, "노약자 여행 추천",c_data_array[6], c_data_array[7]);

                    for(int j=0;j<4;j++) courseList.add(item[j]);

                    //course adapter 연결
                    CourseAdapter adapter = new CourseAdapter(getActivity(),courseList);
                    Log.e("onCreate[courseList]", "" + courseList.size());
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CourseStarResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
