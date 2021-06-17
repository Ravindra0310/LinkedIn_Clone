package com.example.jobedin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobedin.R
import com.example.jobedin.RecyclerViewComponant.JobRecycelerView.JobAdapter
import com.example.jobedin.RecyclerViewComponant.JobRecycelerView.JobModel
import com.example.jobedin.RecyclerViewComponant.Personality
import com.example.jobedin.RecyclerViewComponant.PersonalityAdepter
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.android.synthetic.main.fragment_network.*
import kotlinx.android.synthetic.main.fragment_network.rlLayout


class JobsFragment : Fragment() {
    private var jobArrayList:ArrayList<JobModel>?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildData()
        setRecycleData()
    }


    private fun setRecycleData() {
        val linearLayoutManager = LinearLayoutManager(context)
        rlLayout1.setLayoutManager(linearLayoutManager)
        val personalityAdepter = JobAdapter(jobArrayList);
        rlLayout1.setAdapter(personalityAdepter)
    }

    private fun buildData() {
        jobArrayList = ArrayList()

        for (i in 0..99) {
            if (i % 2 == 0) {
                jobArrayList!!.add(
                    JobModel(R.drawable.bgimage,"Android Developer","ShareChat","Delhi","2 weeks ago"))
            } else if (i % 2 == 1) {
                jobArrayList!!.add(
                    JobModel(
                        R.drawable.bglloyed,"Mern Stack Developer","Rev sales","Banglore","1 hour ago"
                    )
                )
            }
        }
    }
}