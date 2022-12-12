package com.teamvan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_work.*

import com.teamvan.curriculumvitae.R

/**
 * A simple [Fragment] subclass.
 * Use the [WorkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkFragment : Fragment() {

    private var fragmentCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCount = 3
        val myAdapter = MyAdapter(requireActivity())
        viewpager_work.adapter = myAdapter

        TabLayoutMediator(tabs_work, viewpager_work){ tab, position ->
            when(position){
                0 -> {
                    tab.text="Employment History"
                }
                1 -> {
                    tab.text="Core Competences"
                }
                2 -> {
                    tab.text = "Projects"
                }
            }
        }.attach()
        setHasOptionsMenu(true)
    }


    inner class MyAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> EmploymentHistoryFragment()
                1 -> CoreCompetencesFragment()
                else -> ProjectsFragment()
            }
        }

        override fun getItemCount(): Int {
            return fragmentCount
        }

    }

}