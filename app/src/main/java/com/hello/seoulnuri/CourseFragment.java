package com.hello.seoulnuri;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;


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

    ArrayList<CourseItem> courseList;
    private RecyclerView rv;
    private LinearLayoutManager mLinearLayoutManager;
//    private RecyclerView.LayoutManager layoutManager;

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

        CourseItem[] item=new CourseItem[4];
        item[0]=new CourseItem(R.drawable.card_graphic_course_1, R.drawable.course_eye, "시각장애인 여행 추천");
        item[1]=new CourseItem(R.drawable.card_graphic_course_2, R.drawable.course_card_wheel, "지체장애인 여행 추천 ");
        item[2]=new CourseItem(R.drawable.card_graphic_course_3, R.drawable.course_card_ear, "청각장애인 여행 추천");
        item[3]=new CourseItem(R.drawable.card_graphic_course_4, R.drawable.course_card_elder, "노약자 여행 추천");

        for(int i=0;i<4;i++) courseList.add(item[i]);

        //레이아웃매니저
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 2);

        rv = (RecyclerView) view.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);

        //course adapter 연결
        CourseAdapter adapter = new CourseAdapter(getActivity(),courseList);
        Log.e("onCreate[courseList]", "" + courseList.size());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
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
