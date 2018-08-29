package com.hello.seoulnuri.planner

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hello.seoulnuri.model.PlannerListData
import com.hello.seoulnuri.planner.adapter.PlannerAdapter
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.fragment_planner.*
import kotlinx.android.synthetic.main.fragment_planner.view.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PlannerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PlannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlannerFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            planner_edit_btn -> {
                if (number == 0) {
                    number = 1
                    if (flag == false) {
                        planner_edit_btn.text = "완료"
                        flag = true
                    }
                } else {
                    number = 0
                    planner_edit_btn.text = "편집"
                    flag = false
                }
                plannerAdapter.change(number)
                plannerAdapter.notifyDataSetChanged()
            }
            planner_plus_btn -> {
                startActivity(Intent(context, PlannerStartActivity::class.java))
            }
            v!! -> {
                var index = planner_rv.getChildAdapterPosition(v)
                Toast.makeText(context, index.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var number: Int = 0
    lateinit var items: ArrayList<PlannerListData>
    lateinit var plannerAdapter: PlannerAdapter


    private var mListener: OnFragmentInteractionListener? = null
    lateinit var pContext: Context
    var flag: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            mParam1 = getArguments()!!.getString(ARG_PARAM1)
            mParam2 = getArguments()!!.getString(ARG_PARAM2)
        }
    }

    fun init(v: View) {
        v.planner_edit_btn.setOnClickListener(this)
        v.planner_plus_btn.setOnClickListener(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_planner, container, false)
        items = ArrayList()
        items.add(PlannerListData("8월15일", "경복궁"))
        items.add(PlannerListData("8월20일", "광화문"))
        items.add(PlannerListData("8월25일", "창경궁"))
        plannerAdapter = PlannerAdapter(items, pContext)
        plannerAdapter.setOnItemClickListener(this)
        view.planner_rv.layoutManager = LinearLayoutManager(this.pContext)
        view.planner_rv.adapter = plannerAdapter

        init(view)

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
        pContext = context!!
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlannerFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(/*String param1, String param2*/): PlannerFragment {
            val fragment = PlannerFragment()
            val args = Bundle()
            //        args.putString(ARG_PARAM1, param1);
            //        args.putString(ARG_PARAM2, param2);
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
