package com.example.jobedin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobedin.R


import com.example.jobedin.RecyclerViewComponant.Personality
import com.example.jobedin.RecyclerViewComponant.PersonalityAdepter



import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_network.*





class NetworkFragment : Fragment() {
    private var personalities: ArrayList<Personality>? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_network, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          buildData()
        setRecycleData()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NetworkFragment().apply {

            }
    }

    private fun setRecycleData() {
        val linearLayoutManager = GridLayoutManager(context, 2)
        rlLayout.setLayoutManager(linearLayoutManager)
        val personalityAdepter = PersonalityAdepter(personalities)
        rlLayout.setAdapter(personalityAdepter)
    }

    private fun buildData() {

        for (i in 0..99) {
            if (i % 2 == 0) {
                personalities!!.add(
                    Personality(
                        R.drawable.bgimage,
                        R.drawable.nrupul,
                        "Nrupul Dev",
                        "Software Developer at Masai school",
                        "10 years+ experience"
                    )
                )
            } else if (i % 2 == 1) {
                personalities!!.add(
                    Personality(
                        R.drawable.bglloyed,
                       R.drawable.lloyed,
                        "Lloyed Decosta",
                        "Android Faculty at Masai school",
                        "5 years+ experience"
                    )
                )
            }
        }
    }
}