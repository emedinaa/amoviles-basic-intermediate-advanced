package com.kotlin.samples.kotlinapp.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.samples.kotlinapp.DashboardListener

import com.kotlin.samples.kotlinapp.R
import com.kotlin.samples.kotlinapp.adapters.CategoryAdapter
import com.kotlin.samples.kotlinapp.model.Category
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: DashboardListener? = null

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DashboardListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement DashboardListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewCategories.layoutManager= GridLayoutManager(activity,2)

        val categories:MutableList<Category> = mutableListOf()
        categories.add(Category(1,"Ensaladas","Las mejores ensaladas de la casa",0))
        categories.add(Category(2,"SaladBar","Ahora tu eres el Chef Diviertete!",0))
        categories.add(Category(2,"Wraps","Llenos de sabor y frescura, Deliciosos!",0))
        categories.add(Category(2,"Plato del día","Platos saludables con abundante ensalada.",0))
        context?.let {
            recyclerViewCategories.adapter= CategoryAdapter(it,categories)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
