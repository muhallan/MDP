package com.teamvan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamvan.curriculumvitae.R
import com.teamvan.data.School
import kotlinx.android.synthetic.main.education_list_item.view.*
import kotlinx.android.synthetic.main.fragment_education.*

/**
 * A simple [Fragment] subclass.
 * Use the [EducationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EducationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_education, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val schools = arrayListOf(
            School("Maharishi International University", "Master of Science in Computer Science", "Oct 2021", "Aug 2023", "miu.jpeg", "Fairfield, Iowa"),
            School("Makerere University", "Bachelor of Science in Software Engineering", "Aug 2012", "May 2016", "makerere.jpeg", "Kampala, Uganda"),
            School("Ntare School", "High School", "Feb 2016", "Nov 2011", "ntare.jpeg", "Mbarara, Uganda")
        )
        educationRV.layoutManager = LinearLayoutManager(context)
        val adapter = MyAdapter(schools)
        educationRV.adapter = adapter
    }

    inner class MyAdapter(private val schools: ArrayList<School>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.education_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.nameTv.text = schools[position].name
            holder.itemView.majorTv.text = schools[position].major
            holder.itemView.datesTv.text = "${schools[position].startDate} - ${schools[position].endDate}"

            val imageName = schools[position].image.split(".")[0]
            val imageUri = "@drawable/${imageName}"
            val imageResource = resources.getIdentifier(imageUri, null, requireActivity().packageName)
            val res = resources.getDrawable(imageResource)
            holder.itemView.schoolImageIV.setImageDrawable(res)
        }

        override fun getItemCount(): Int {
            return schools.size
        }

    }
}
