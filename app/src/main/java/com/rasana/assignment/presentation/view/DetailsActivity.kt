package com.rasana.assignment.presentation.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.util.Linkify
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rasana.assignment.R
import com.rasana.assignment.application.AssignmentApplication.Companion.applicationComponent
import com.rasana.assignment.presentation.model.DisplayLaunches
import com.rasana.assignment.presentation.model.DisplayRow
import com.rasana.assignment.presentation.presenter.DetailsPresenter
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), DetailsPresenter.DisplayResults {

    @Inject
    lateinit var presenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        applicationComponent.inject(this)

        presenter.inject(this)
        presenter.onIntentReceived(intent.extras?.get("details") as DisplayLaunches)
        presenter.onCreate()
    }

    override fun showDetails(details: DisplayLaunches){
        detailsText.text = details.details
        rocketName.text = getString(R.string.rocket_name, details.rocketName)
        rocketType.text = getString(R.string.rocket_type, details.rocketType)
        wikipediaLink.text = details.wikipediaLink
        Linkify.addLinks(wikipediaLink, Linkify.WEB_URLS);
    }

    override fun showLoading() {
        loadingIndicator.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingIndicator.visibility = View.GONE
    }
}