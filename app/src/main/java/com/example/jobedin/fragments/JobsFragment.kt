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
import com.example.jobedin.data.remote.api.NotificationApi
import com.example.jobedin.repository.LinkedInRepository
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.android.synthetic.main.fragment_network.*
import kotlinx.android.synthetic.main.fragment_network.rlLayout
import javax.inject.Inject


class JobsFragment : Fragment() {

    @Inject
    lateinit var notificationApi: NotificationApi

   var repository=LinkedInRepository(notificationApi)
    private var jobArrayList=ArrayList<JobModel>()
lateinit var jobAdapter:JobAdapter

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
        repository.getJobData().let {
            
        }
        for (i in 0..99) {
            if (i % 2 == 0) {
                jobArrayList!!.add(
                    JobModel("https://media-exp3.licdn.com/dms/image/C4D0BAQG_oY7LkqBPBA/" +
                            "company-logo_100_100/0/1622604168326?e=1632355200&v=beta&t=6CXWa4Vdco" +
                            "QjPBYK1VEFUMaY4xl_-Gx_ojD-Jn7E1bY","Android Develope" +
                            "r","ShareChat","Delhi","2 weeks ago"))
            } else if (i % 2 == 1) {
                jobArrayList!!.add(
                    JobModel(
                       "https://media-exp3.licdn.com/dms/image/C560BAQEAvxNRkegJiQ/company-" +
                               "logo_100_100/0/1519895803576?e=1632355200&v=beta&t=HKIEGSd1zDjdXVh5H" +
                               "-uVYvN-XDUN-IeBt5fKS4ZnZy0","Mern Stack Developer",
                        "Rev sales","Banglore","1 hour ago"
                    )
                )
            }
        }
    }
}