package com.teamvan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamvan.curriculumvitae.R
import com.teamvan.data.Competency
import kotlinx.android.synthetic.main.core_compentencies_list_item.view.*
import kotlinx.android.synthetic.main.fragment_core_competences.*

/**
 * A simple [Fragment] subclass.
 * Use the [CoreCompetencesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoreCompetencesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_core_competences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val competencies = arrayListOf(
            Competency("Languages", "Python, Java, Golang, PHP, Ruby"),
            Competency("Web", "JavaScript, TypeScript, HTML5, CSS3, Material UI, WordPress, Drupal, Bootstrap, jQuery, XML"),
            Competency("Web Services", "REST, Consul"),
            Competency("Web/App Servers", "Tomcat, Gunicorn, Tornado, Werkzeug"),
            Competency("Frameworks", "Spring (Cloud, Boot), Flask, Angular, React, Django"),
            Competency("Databases", "MySQL, PostgreSQL, SQLite, DynamoDB, Redis"),
            Competency("Design Patterns", "Command, Template, Visitor, Iterator, Strategy, State, Mediator, Observer, Singleton, Composite, Factory, Decorator, Proxy, Builder, Façade, Abstract Factory"),
            Competency("SDLC", "TDD, Scrum, Agile, Kanban"),
            Competency("Tools", "Git, GitHub, GitLab, Bitbucket, Kibana, Amplitude, Salesforce, JIRA, Confluence, Trello, Airtable, ZenHub, Pivotal Tracker, Slack, MS Teams, G Suite, Swagger, VS Code, PyCharm, GoLand, RubyMine, Eclipse, NetBeans, IntelliJ, Maven, Gradle, Android Studio, RabbitMQ, Docker"),
            Competency("Platforms", "Mac OS, Windows, Linux, Android, Vagrant, AWS"),
            Competency("Big Data", "Kafka, Elasticsearch")
        )

        competenciesRV.layoutManager = LinearLayoutManager(context)
        val adapter = MyAdapter(competencies)
        competenciesRV.adapter = adapter
    }

        inner class MyAdapter(private val competencies: ArrayList<Competency>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

            inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.core_compentencies_list_item, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.itemView.titleTv.text = competencies[position].title
                holder.itemView.detailsTv.text = competencies[position].details
            }

            override fun getItemCount(): Int {
                return competencies.size
            }

        }

}