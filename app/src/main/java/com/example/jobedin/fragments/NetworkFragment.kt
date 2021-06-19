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
    private var personalities: ArrayList<Personality>? = arrayListOf()


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

        for (i in 0..30) {
            if (i == 0 || i==6) {
                personalities!!.add(
                    Personality(
                        "https://media-exp3.licdn.com/dms/image/C5103AQGBIueSWAkHkw/profile-displayphoto-shrink_200_200/0/1564266187183?e=1629331200&v=beta&t=BWT4hhN4Y-gbFzwBV7PBO9dA0ZCQLC0lc4hNMY_IpIM",
                        R.drawable.bgimage,
                        "Nrupul Dev",
                        "Software Developer at Masai school",
                        "10 years+ experience"
                    )
                )
            } else if (i == 2|| i==7) {
                personalities!!.add(
                    Personality(
                        "https://media-exp3.licdn.com/dms/image/C5603AQEF98ZKu6x-bA/profile-displayphoto-shrink_200_200/0/1591957593685?e=1629331200&v=beta&t=D8AyVAkeQ-NyctpfkIovWz1Fed8skhG3APb8ZyFRQVk",
                        R.drawable.bglloyed,
                        "Lloyed Decosta",
                        "Android Faculty at Masai school",
                        "5 years+ experience"
                    )
                )
            } else if (i == 3|| i==8) {
                personalities!!.add(
                    Personality(
                        "https://media-exp3.licdn.com/dms/image/C5103AQG_90kKOlYgeA/profile-displayphoto-shrink_200_200/0/1555267885323?e=1629331200&v=beta&t=oRka_1gdfE28nb7SdiKmS3S51-jC7iTot1lw7NfIIlY",
                        R.drawable.bgimage,
                        "Prateek Shukla",
                        "Hiring for leadership positions in product",
                        ""
                    )
                )
            } else if (i == 4|| i==9) {
                personalities!!.add(
                    Personality(
                        "https://media-exp3.licdn.com/dms/image/C4D03AQEX7WFY5iPhrg/profile-displayphoto-shrink_200_200/0/1622368093283?e=1629331200&v=beta&t=g9V6kdmVjkJ9w-tce7E-WqE2Dm9B94EvSFwNX4DxDzw",
                        R.drawable.bglloyed,
                        "Varun Wani",
                        "earning Android Development at Masai School",
                        "Fresher"
                    )
                )
            } else if (i == 4|| i==10) {
                personalities!!.add(
                    Personality(
                        "https://media-exp3.licdn.com/dms/image/D5635AQEPJe1LXxlQ7w/profile-framedphoto-shrink_200_200/0/1622826040150?e=1624183200&v=beta&t=jeI58nYA0bYY7-WksHzUvatZeOosDtbDwiqhsHLx1lo",
                        R.drawable.bgimage,
                        "Karthik Manoharan",
                        "earning Android Development at Masai School",
                        "Fresher"
                    )
                )
            } else if (i == 5|| i==11) {
                personalities!!.add(
                    Personality(
                        "https://media-exp3.licdn.com/dms/image/C5103AQHRClLCAaXbCA/profile-displayphoto-shrink_200_200/0/1561083392670?e=1629331200&v=beta&t=X8q9lXqHLDZZaHhtQwReMB-unBQ85p9M76IVuAxozME",
                        R.drawable.bgimage,
                        "Shivaraj Patil ",
                        "Building Masai School",
                        "5 years+ experience"
                    )
                )
            }
        }
    }
}
