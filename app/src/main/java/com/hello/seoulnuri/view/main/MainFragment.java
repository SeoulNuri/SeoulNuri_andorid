package com.hello.seoulnuri.view.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hello.seoulnuri.R;
import com.hello.seoulnuri.model.main.MainTourResponse;
import com.hello.seoulnuri.model.main.TourData2;
import com.hello.seoulnuri.network.ApplicationController;
import com.hello.seoulnuri.network.NetworkService;
import com.hello.seoulnuri.utils.SharedPreference;
import com.hello.seoulnuri.view.info.tour.InfoTourDetailActivity;
import com.hello.seoulnuri.view.search.Search2Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(/*String param1, String param2*/) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 200){
                Log.v("yong",data.getStringExtra("result"));
            }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final ImageView imageView = (ImageView)view.findViewById(R.id.mainImageView);
        final RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        ratingBar.setRating((float) 3.5);

        final TextView addressTextView = (TextView)view.findViewById(R.id.addressTextView);
        final TextView summaryTextView = (TextView)view.findViewById(R.id.summaryTextView);
        final TextView placeTextView = (TextView)view.findViewById(R.id.placeTextView);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        final ImageButton imageButton1 = (ImageButton)view.findViewById(R.id.courseContentImageButton1);
        final ImageButton imageButton2 = (ImageButton)view.findViewById(R.id.courseContentImageButton2);
        final ImageButton imageButton3 = (ImageButton)view.findViewById(R.id.courseContentImageButton3);
        final ImageButton imageButton4 = (ImageButton)view.findViewById(R.id.courseContentImageButton4);
        final ImageButton imageButton5 = (ImageButton)view.findViewById(R.id.courseContentImageButton5);

        final TextView textViewTitle1 = (TextView)view.findViewById(R.id.courseContentTitleTextView1);
        final TextView textViewTitle2 = (TextView)view.findViewById(R.id.courseContentTitleTextView2);
        final TextView textViewTitle3 = (TextView)view.findViewById(R.id.courseContentTitleTextView3);
        final TextView textViewTitle4 = (TextView)view.findViewById(R.id.courseContentTitleTextView4);
        final TextView textViewTitle5 = (TextView)view.findViewById(R.id.courseContentTitleTextView5);

        final TextView textViewAddress1 = (TextView)view.findViewById(R.id.courseContentAddressTextView1);
        final TextView textViewAddress2 = (TextView)view.findViewById(R.id.courseContentAddressTextView2);
        final TextView textViewAddress3 = (TextView)view.findViewById(R.id.courseContentAddressTextView3);
        final TextView textViewAddress4 = (TextView)view.findViewById(R.id.courseContentAddressTextView4);
        final TextView textViewAddress5 = (TextView)view.findViewById(R.id.courseContentAddressTextView5);

        Button btnSearch = (Button)view.findViewById(R.id.mainSearchButton);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search2Activity.class);
                startActivityForResult(intent,200);
            }
        });



        final Button btnNext = (Button)view.findViewById(R.id.buttonNextArrow);

        ApplicationController controller = new ApplicationController();
        controller.buildNetwork();
        NetworkService service = controller.getNetworkService();
        SharedPreference.Companion.getInstance();


        Call<MainTourResponse> call = service.getMainInfo(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        Log.v("token : ", SharedPreference.Companion.getInstance().getPrefStringData("data"));
        call.enqueue(new Callback<MainTourResponse>() {
            @Override
            public void onResponse(Call<MainTourResponse> call, final Response<MainTourResponse> response) {
//                response.body().getData().getRand_tour().getTour_image();
                if (response.body() != null) {
                    Log.v("message in onResponse", response.body().getData().toString());
                    imageView.setImageURI(Uri.parse(response.body().getData().getRand_tour().getTour_image()));
                    ratingBar.setRating((float)response.body().getData().getRand_tour().getTour_star());
                    addressTextView.setText(response.body().getData().getRand_tour().getTour_addr());
                    summaryTextView.setText(response.body().getData().getRand_tour().getTour_info());
                    placeTextView.setText(response.body().getData().getRand_tour().getTour_name());

                    final TourData2 tourData2[] = new TourData2[5];
                    for (int i=0; i<5; i++) {
                        tourData2[i] = response.body().getData().getReco_tour().get(i);
                    }
                    imageButton1.setImageURI(Uri.parse(tourData2[0].getTour_image()));
                    imageButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), InfoTourDetailActivity.class);
                            intent.putExtra("tour_idx", tourData2[0].getTour_idx());
                            startActivity(intent);
                        }
                    });
                    imageButton2.setImageURI(Uri.parse(tourData2[1].getTour_image()));
                    imageButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), InfoTourDetailActivity.class);
                            intent.putExtra("tour_idx", tourData2[1].getTour_idx());
                            startActivity(intent);
                        }
                    });
                    imageButton3.setImageURI(Uri.parse(tourData2[2].getTour_image()));
                    imageButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), InfoTourDetailActivity.class);
                            intent.putExtra("tour_idx", tourData2[2].getTour_idx());
                            startActivity(intent);
                        }
                    });
                    imageButton4.setImageURI(Uri.parse(tourData2[3].getTour_image()));
                    imageButton4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), InfoTourDetailActivity.class);
                            intent.putExtra("tour_idx", tourData2[3].getTour_idx());
                            startActivity(intent);
                        }
                    });
                    imageButton5.setImageURI(Uri.parse(tourData2[4].getTour_image()));
                    imageButton5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), InfoTourDetailActivity.class);
                            intent.putExtra("tour_idx", tourData2[4].getTour_idx());
                            startActivity(intent);
                        }
                    });

                    textViewTitle1.setText(tourData2[0].getTour_name());
                    textViewTitle2.setText(tourData2[1].getTour_name());
                    textViewTitle3.setText(tourData2[2].getTour_name());
                    textViewTitle4.setText(tourData2[3].getTour_name());
                    textViewTitle5.setText(tourData2[4].getTour_name());

                    textViewAddress1.setText(tourData2[0].getTour_addr());
                    textViewAddress2.setText(tourData2[1].getTour_addr());
                    textViewAddress3.setText(tourData2[2].getTour_addr());
                    textViewAddress4.setText(tourData2[3].getTour_addr());
                    textViewAddress5.setText(tourData2[4].getTour_addr());

                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), InfoTourDetailActivity.class);
                            intent.putExtra("tour_idx", response.body().getData().getRand_tour().getTour_idx());
                            startActivity(intent);
                        }
                    });
                } else Log.v("message in onResponse", "is null object");
            }

            @Override
            public void onFailure(Call<MainTourResponse> call, Throwable t) {
                Log.d("Fail","haha");
            }
        });


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
