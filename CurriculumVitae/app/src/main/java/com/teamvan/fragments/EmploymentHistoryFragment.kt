package com.teamvan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamvan.curriculumvitae.R
import com.teamvan.data.Employment
import kotlinx.android.synthetic.main.employment_history_list_item.view.*
import kotlinx.android.synthetic.main.fragment_employment_history.*


/**
 * A simple [Fragment] subclass.
 * Use the [EmploymentHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmploymentHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val employmentHistory = arrayListOf(
            Employment(0, "SEESAW LEARNING", "San Francisco, California", "August 2020", "To date", "SOFTWARE ENGINEER", "Performed full stack software development using Python, Angular, and React.",
                arrayListOf(
                    "Designed new React models and added new backend APIs to create school directory, speeding student registration to classes.",
                    "Contributed to new CSV rostering workflow, allowing school admins to work quickly and accurately.",
                    "Maintained roster object sync which allows the support team to quickly sync in data from third party systems.",
                    "Provided support to Salesforce integration by fixing bugs and modifications, allowing data team to collect accurate information and updates."
                ),
                "Python, Angular, React, Tornado, DynamoDB, Elasticsearch, AWS SQS, S3, TDD, Docker, APIs, Git",
                "seesaw.jpeg"
            ),
            Employment(1, "SLING TV", "American Fork, Utah", "June 2019", "July 2020", "SOFTWARE ENGINEER", "Developed and maintained microservices in Python and Go as part of Content Management team.",
                arrayListOf(
                    "Made key contributions to Collection Service using Go, Elasticsearch, and Kafka to pass queries and build informational screens, ribbons, and tiles.",
                    "Containerized Skynet microservice using Docker, preparing it for Kubernetes.",
                    "Modified database models and wrote SQLAlchemy queries to support new functionality in Program Service.",
                    "Added new endpoints to Promotion Service API, supporting new business requests."
                ),
                "Python, Flask, Go, PostgreSQL, Kafka, APIs, RabbitMQ, SQLAlchemy, Angular, Docker, Kubernetes, Elasticsearch, Consul, Jenkins, Git, TDD",
                "sling.jpeg"
            ),
            Employment(2, "NIRA", "San Francisco, California", "April 2018", "May 2019", "SOFTWARE ENGINEER", "With the backend team, helped develop over 15 integrations of online cloud services with Python.",
                arrayListOf(
                    "Translated models and API endpoints to Python from Ruby-based Naas client.",
                    "Developed integrations which sync data with Nira from online storage services such as OneDrive, Dropbox, Evernote, Box, Asana, Confluence, Airtable, SharePoint, and others.",
                    "Implemented different authentication methods for multiple integrated APIs."
                ),
                "Python, Flask, MySQL, PostgreSQL, Elasticsearch, Algolia, Ruby, APIs, Chalice, AWS, AWS-Lambda, OAuth, Redis, DynamoDB, Git",
                "nira.jpeg"
            ),
            Employment(3, "ANDELA", "Kampala, Uganda", "August 2017", "October 2021", "SOFTWARE ENGINEER", "Technical recruitment and training center, gathering talented technologists from across Africa.",
                arrayListOf(
                    "Engaged in community initiatives such as TeenCode and Women-in-Tech to teach software programming concepts and share computer science knowledge with youth.",
                    "Held internal leadership roles such as Senate member and Cohort representative.",
                    "Contributed to internal Andela products to manage operations and support programs, such as Andela Resource Tracking, The Collective Andela Learning Map, and Bootcamp."
                ),
                "Python, Flask, Android, Javascript, Go, Angular, Postgres, TDD, Django, PostgreSQL, MySQL, TravisCI, Heroku, CircleCI, Jenkins, Git, SQLAlchemy, Coveralls, AWS, Redis, JQuery, HTML, CSS",
                "andela.jpeg"
            ),
            Employment(4, "2AMBALE", "Kampala, Uganda", "November 2016", "August 2017", "SOFTWARE ENGINEER", "Online clothing store with both Android and web e-commerce platforms.",
                arrayListOf(
                    "Developed complete 2ambale Android e-commerce app, as used by over 1,500 users.",
                    "Implemented internal order management and delivery handling Android application.",
                    "Integrated mobile money payments and real-time notifications",
                    "Provided continued maintenance of Android app after leaving the company."
                ),
                "Java, Android, XML, PHP, AWS, Firebase, MySQL, SQLite",
                "twambale.png"
            )
        )

        empHistoryRV.layoutManager = LinearLayoutManager(context)
        val adapter = MyAdapter(employmentHistory)
        empHistoryRV.adapter = adapter
    }

    inner class MyAdapter(val employmentHistory: ArrayList<Employment>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.employment_history_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.jobTitleTv.text = employmentHistory[position].jobTitle
            holder.itemView.companyNameTv.text = employmentHistory[position].company
            holder.itemView.datesTv.text = "${employmentHistory[position].startDate} - ${employmentHistory[position].endDate}"
            holder.itemView.locationTv.text = employmentHistory[position].location
            holder.itemView.technologiesTv.text = employmentHistory[position].technologies
            holder.itemView.taglineTV.text = employmentHistory[position].tagLine

            var description = "<ul>"
            employmentHistory[position].descriptionItems.forEach {
                description += "<li> &nbsp;&nbsp;${it}</li>"
            }
            description += "</ul>"
            val spannedText = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.itemView.descriptionTv.text = spannedText

            val imageName = employmentHistory[position].logo.split(".")[0]
            val imageUri = "@drawable/${imageName}"
            val imageResource = resources.getIdentifier(imageUri, null, requireActivity().packageName)
            val res = resources.getDrawable(imageResource)
            holder.itemView.companyImageIV.setImageDrawable(res)
        }

        override fun getItemCount(): Int {
            return employmentHistory.size
        }

    }

}