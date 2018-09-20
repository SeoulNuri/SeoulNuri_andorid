package com.hello.seoulnuri.view.mypage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.hello.seoulnuri.R;
import com.hello.seoulnuri.network.ApplicationController;
import com.hello.seoulnuri.network.NetworkService;
import com.hello.seoulnuri.utils.Init;
import com.hello.seoulnuri.utils.SharedPreference;
import com.hello.seoulnuri.view.login.LoginCategoryActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MypageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MypageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageFragment extends Fragment implements View.OnClickListener, Init{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MypageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * // @param param1 Parameter 1.
     * // @param param2 Parameter 2.
     *
     * @return A new instance of fragment MypageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageFragment newInstance(/*String param1, String param2*/) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private NetworkService networkService;
    private Button btnTour;
    private Button btnCourse;
    private ImageButton mypageSettingButton;
    private ScrollView tourScrollView;
    private ScrollView courseScrollView;
    private TabLayout mypageTab;

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


        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        //btnTour = (Button) view.findViewById(R.id.myTourButton);
        //btnCourse = (Button) view.findViewById(R.id.myCourseButton);
        mypageSettingButton = (ImageButton) view.findViewById(R.id.mypageSettingButton);
        mypageTab = view.findViewById(R.id.mypageTab);
        mypageTab.addTab(mypageTab.newTab().setText("관광지"));
        mypageTab.addTab(mypageTab.newTab().setText("여행정보"));
        mypageTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //tourScrollView = (ScrollView) view.findViewById(R.id.myTourScrollView);
        //courseScrollView = (ScrollView) view.findViewById(R.id.myCourseScrollView);
        mypageTab.setTabTextColors(
                ContextCompat.getColor(getContext(), R.color.unselected_text_color), // 선택되지 않은 텍스트 컬러
                ContextCompat.getColor(getContext(), R.color.selected_text_color) // 선택된 텍스트 컬러
        );

        init(); // 초기화 함수 호출


        /*
        있으면 GridView로 없으면 ImageView
         */

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mypageSettingButton:
                startActivity(new Intent(getContext(), ChangeTypeActivity.class));
                break;
        }
    }

    @Override
    public void init() {
        mypageSettingButton.setOnClickListener(this);
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
