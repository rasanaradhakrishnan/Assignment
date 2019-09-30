package com.rasana.assignment.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasana.assignment.R
import com.rasana.assignment.application.AssignmentApplication.Companion.applicationComponent
import com.rasana.assignment.presentation.adapter.LaunchesAdapter
import com.rasana.assignment.presentation.model.DisplayLaunches
import com.rasana.assignment.presentation.model.DisplayRow
import com.rasana.assignment.presentation.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by Rasana.
 */
class MainActivity : AppCompatActivity(), MainPresenter.DisplayResults, LaunchesAdapter.OnDisplayItemClickListener {

    private lateinit var adapter: LaunchesAdapter

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        applicationComponent.inject(this)

        presenter.inject(this)
        presenter.onCreate()

        adapter = LaunchesAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun showLaunches(allLaunches: List<DisplayRow>){
        adapter.allLaunches = allLaunches
    }

    override fun onDisplayItemClicked(displayItem: DisplayLaunches) {
        Toast.makeText(this, R.string.navigate, Toast.LENGTH_LONG).show()
    }
}
