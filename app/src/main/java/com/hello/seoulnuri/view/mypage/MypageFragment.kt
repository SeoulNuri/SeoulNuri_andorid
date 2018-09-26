package com.hello.seoulnuri.view.mypage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ScrollView
import com.hello.seoulnuri.R
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.Init
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.view.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_mypage.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MypageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MypageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MypageFragment : Fragment(), View.OnClickListener, Init {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private var networkService: NetworkService? = null
    private val btnTour: Button? = null
    private val btnCourse: Button? = null
    private var mypageSettingButton: ImageButton? = null
    private var mypageLogoutButton: Button? = null
    private val tourScrollView: ScrollView? = null
    private val courseScrollView: ScrollView? = null
    private var mypageTab: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }

        replaceFragment(TourDestinationFragment())



    }



    fun replaceFragment(fragment : Fragment){
        val fm = activity!!.supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.mypageFrame, fragment)
        transaction.commit()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)

        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        //btnTour = (Button) view.findViewById(R.id.myTourButton);
        //btnCourse = (Button) view.findViewById(R.id.myCourseButton);
        mypageSettingButton = view.findViewById<View>(R.id.mypageSettingButton) as ImageButton
        mypageLogoutButton = view.findViewById<View>(R.id.mypageLogoutButton) as Button

        init()

        mypageTab = view.findViewById(R.id.mypageTab)
        mypageTab!!.addTab(mypageTab!!.newTab().setText("관광지"))
        mypageTab!!.addTab(mypageTab!!.newTab().setText("여행정보"))
        mypageTab!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab!!.position){
                    0->{
                        replaceFragment(TourDestinationFragment())
                    }
                    1->{
                        replaceFragment(TourCourseFragment())
                    }

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        //tourScrollView = (ScrollView) view.findViewById(R.id.myTourScrollView);
        //courseScrollView = (ScrollView) view.findViewById(R.id.myCourseScrollView);
        mypageTab!!.setTabTextColors(
                ContextCompat.getColor(context!!, R.color.unselected_text_color), // 선택되지 않은 텍스트 컬러
                ContextCompat.getColor(context!!, R.color.selected_text_color) // 선택된 텍스트 컬러
        )

        init() // 초기화 함수 호출


        /*
        있으면 GridView로 없으면 ImageView
         */

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.mypageSettingButton -> startActivity(Intent(context, ChangeTypeActivity::class.java))
            R.id.mypageLogoutButton -> {
                Log.v("213412341", "out")
                SharedPreference.instance!!.removeData("data")
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }
    }

    override fun init() {
        mypageSettingButton!!.setOnClickListener(this)
        mypageLogoutButton!!.setOnClickListener(this)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         *
         * // @param param1 Parameter 1.
         * // @param param2 Parameter 2.
         *
         * @return A new instance of fragment MypageFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(/*String param1, String param2*/): MypageFragment {
            val fragment = MypageFragment()
            val args = Bundle()
            //        args.putString(ARG_PARAM1, param1);
            //        args.putString(ARG_PARAM2, param2);
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
