package com.hello.seoulnuri.view.course;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hello.seoulnuri.R;
import com.hello.seoulnuri.model.course.CourseMapData;
import com.hello.seoulnuri.view.info.tour.InfoTourDetailActivity;

import java.util.ArrayList;

public class CourseMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Double> latLang; // 경복궁 위도 경도
    private TextView course_item_map_title;
    private TextView course_item_map_addr;
    private RatingBar course_item_map_rating_star;
    private TextView course_item_map_rating_txt;
    private ImageView show_tour_info_btn;
    private ImageView course_item_map_img;
    private CourseMapData courseMapData;
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        course_item_map_title = (TextView)findViewById(R.id.course_item_map_title);
        course_item_map_addr = (TextView)findViewById(R.id.course_item_map_addr);
        course_item_map_rating_txt = (TextView)findViewById(R.id.course_item_map_rating_txt);
        course_item_map_rating_star = (RatingBar) findViewById(R.id.course_item_map_rating_star);
        show_tour_info_btn = (ImageView) findViewById(R.id.show_tour_info_btn);
        course_item_map_img = (ImageView) findViewById(R.id.course_item_map_img);

        Intent intent = getIntent();
//        String course_item_title = intent.getStringExtra("course_item_title");
//        String course_item_addr = intent.getStringExtra("course_item_addr");
        String course_star_count = intent.getStringExtra("course_star_count");
        float course_star = intent.getFloatExtra("course_star",0);

        courseMapData = (CourseMapData) intent.getSerializableExtra("course_map_data");
        Log.v("1111courseMapData", courseMapData.toString());

        if(courseMapData.getDetailData().get(0).getImg() != ""){
            Glide.with(getApplicationContext())
                    .load(courseMapData.getDetailData().get(0).getImg())
                    .into(course_item_map_img);
        }

        course_item_map_title.setText(courseMapData.getDetailData().get(0).getTitle());
        course_item_map_addr.setText(courseMapData.getDetailData().get(0).getAddr());
        course_item_map_rating_txt.setText(course_star_count);
        course_item_map_rating_star.setRating(course_star);

//        pos = 0;
//        for (int i =0; i < courseMapData.getDetailData().size(); i++ ) {
//            if (course_item_map_title.getText().toString().equals(courseMapData.getDetailData().get(i).getTitle())) {
//                pos = i;
//            }
//        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        latLang = intent.getDoubleArrayExtra("latLang_list");

        ArrayList<LatLng> locations = new ArrayList<>();
        // Add a marker in Sydney and move the camera
        for (int i = 0; i < courseMapData.getDetailData().size(); i++) {
            locations.add(new LatLng(courseMapData.getDetailData().get(i).getLat(), courseMapData.getDetailData().get(i).getLang()));
        }

        int j = 0;
        for(LatLng location : locations){
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(courseMapData.getDetailData().get(j).getTitle())
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_spot_select))));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            j++;
        }

        mMap.setOnMarkerClickListener(this);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        pos = 0;
        for (int i =0; i < courseMapData.getDetailData().size(); i++ ) {
            if (marker.getTitle().equals(courseMapData.getDetailData().get(i).getTitle())) {
                pos = i;
            }
        }
        if(courseMapData.getDetailData().get(pos).getImg() != ""){
            Glide.with(getApplicationContext())
                    .load(courseMapData.getDetailData().get(pos).getImg())
                    .into(course_item_map_img);
        }

        course_item_map_title.setText(courseMapData.getDetailData().get(pos).getTitle());
        course_item_map_addr.setText(courseMapData.getDetailData().get(pos).getAddr());
        show_tour_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseMapActivity.this, InfoTourDetailActivity.class);
                intent.putExtra("index", courseMapData.getDetailData().get(pos).getTour_idx()); // 일단 6 -창덕궁
                startActivity(intent);
            }
        });
        return false;
    }
}
