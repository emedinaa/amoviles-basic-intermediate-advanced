package com.kotlin.samples.kotlinapp.fragments


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kotlin.samples.kotlinapp.R
import com.kotlin.samples.kotlinapp.listeners.ColorListener
import kotlinx.android.synthetic.main.fragment_box.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BoxFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BoxFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener:ColorListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_box, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ColorListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement ColorListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun paintColor(position:Int){
        var color:Int?=null
        when(position){
            0 -> color= Color.parseColor("#CC2EFA")
            1 -> color= Color.parseColor("#FE2E2E")
            2 -> color= Color.parseColor("#F7FE2E")
        }
        color?.let {
            flayBox.setBackgroundColor(it)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BoxFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                BoxFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
