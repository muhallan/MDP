package com.teamvan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamvan.curriculumvitae.R
import com.teamvan.data.Project
import kotlinx.android.synthetic.main.fragment_projects.*
import kotlinx.android.synthetic.main.projects_list_item.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [ProjectsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projects = arrayListOf(
            Project("Agkika Supplies ", "2019", "Java-based desktop software used to track purchases, sales, customers, inventory, and accounts of agricultural tools", "Java, SQLite"),
            Project("InVacSIM", "2019", "Vaccination tracker app which tracks vaccinations and sends reminders via SMS.", "Java, Android, MySQL, SQLite"),
            Project("Hillsong Lyrics", "2014", "Android app which displays all lyrics of songs by Hillsong music band.", "Java, Android, SQLite"),
            Project("Lecrae Discography, MIU", "2022", "Designed and implemented both backend and frontend of a MEAN stack web app which records and displays the albums and songs of artist Lecrae Moore.", "Node, Express, Angular, MongoDB"),
            Project("Web Shop, MIU", "2022", "With a group of 4, developed a system comprised of 4 microservices which allow customers to add products to cart and checkout orders. Used Spring Cloud tools to architect a scalable and resilient system.", "Java, Spring (Cloud, Boot), Kafka, MongoDB, CQRS, Elasticsearch, Logstash, Kibana"),
            Project("FinCo Framework, MIU", "2022", "In a team of 3, designed and developed 2 connected applications for Bank and Credit Card. Identified common features and grouped into a single customizable framework which manages customers, accounts, and payments.", "Java Swing, JSON, Design Patterns (Strategy, Command, Abstract Factory, Role)"),
            Project("Library Management System, MIU", "2021", "Worked as a group of 3 to design and implement a system for libraries which supports login, adding members, managing books and book copies, and book checkout", "JavaFX, XML")
        )
        projectsRV.layoutManager = LinearLayoutManager(context)
        val adapter = MyAdapter(projects)
        projectsRV.adapter = adapter
    }

    inner class MyAdapter(private val projects: ArrayList<Project>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.projects_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.projectTitleTv.text = "${projects[position].name} (${projects[position].year})"
            holder.itemView.projectDescriptionTv.text = projects[position].description
            holder.itemView.technologiesTv.text = projects[position].technologies
        }

        override fun getItemCount(): Int {
            return projects.size
        }

    }
}
