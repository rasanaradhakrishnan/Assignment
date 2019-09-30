package com.rasana.assignment.presentation.view

import android.os.Bundle
import android.view.View
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
        adapter = LaunchesAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        applicationComponent.inject(this)

        sortByName.setOnClickListener { presenter.onClickSortByName() }

        sortByDate.setOnClickListener { presenter.onClickSortByDate() }

        presenter.inject(this)
        presenter.onCreate()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun showLaunches(allLaunches: List<DisplayRow>){
        adapter.allLaunches = allLaunches
        adapter.notifyDataSetChanged()
    }

    override fun onDisplayItemClicked(displayItem: DisplayLaunches) {
        Toast.makeText(this, R.string.navigate, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        loadingIndicator.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingIndicator.visibility = View.GONE
    }
}
