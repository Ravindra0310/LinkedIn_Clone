package com.example.jobedin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobedin.R
import com.example.jobedin.RecyclerViewComponant.NotificationRecyclerView.NotificationAdapter
import com.example.jobedin.RecyclerViewComponant.NotificationRecyclerView.NotificationModel
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.android.synthetic.main.fragment_notification.*


class NotificationFragment : Fragment() {
    private var notificationArrayList:ArrayList<NotificationModel>?= ArrayList();
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildData()
        setRecycleData()
    }
    private fun setRecycleData() {
        val linearLayoutManager = LinearLayoutManager(context)
        rlLayout2.setLayoutManager(linearLayoutManager)
        val personalityAdepter = NotificationAdapter(notificationArrayList);
        rlLayout2.setAdapter(personalityAdepter)
    }

    private fun buildData() {
        notificationArrayList = ArrayList()

        for (i in 0..99) {
            if (i % 2 == 0) {
                notificationArrayList!!.add(
                    NotificationModel(R.drawable.bgimage,"GeeksforGeeks was live: Wondering how it feels like working at your dream workplace like Facebook? What preparation strategies you need? To give you all an insider's view, we have Mr. Abhinav Sangal, Software Engineer at Facebook. Mr. Sangal is an alumnus from Georgia Institute of Technology possessing 5+ years of experience as a Software Engineer. To connect with Abhinav on LinkedIn, click here: https://lnkd.in/e_TfjCs Download our Android app with newer & better features: https://bit.ly/3y8fSpC To watch our previous & upcoming interview experiences of other organizations, bookmark this link: https://lnkd.in/e6zwDx5 Follow us on our social media handles to stay updated! Instagram: https://lnkd.in/dBUBs6H Twitter: https://lnkd.in/d8rhR98\u200B\u200B\u200B #Facebook #MachineLearning #GeeksforGeeksGeeksforGeeks was live: Wondering how it feels like working at your dream workplace like Facebook? What preparation strategies you need? To give you all an insider's view, we have Mr. Abhinav Sangal, Software Engineer at Facebook. Mr. Sangal is an alumnus from Georgia Institute of Technology possessing 5+ years of experience as a Software Engineer. To connect with Abhinav on LinkedIn, click here: https://lnkd.in/e_TfjCs Download our Android app with newer & better features: https://bit.ly/3y8fSpC To watch our previous & upcoming interview experiences of other organizations, bookmark this link: https://lnkd.in/e6zwDx5 Follow us on our social media handles to stay updated! Instagram: https://lnkd.in/dBUBs6H Twitter: https://lnkd.in/d8rhR98\u200B\u200B\u200B #Facebook #MachineLearning #GeeksforGeeks\n" +
                            "2h\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n","1 d")
                )
            } else if (i % 2 == 1) {
                notificationArrayList!!.add(
                    NotificationModel(
                        R.drawable.bglloyed,"Mern Stack Developer","2 d"
                    )
                )
            }
        }
    }
}