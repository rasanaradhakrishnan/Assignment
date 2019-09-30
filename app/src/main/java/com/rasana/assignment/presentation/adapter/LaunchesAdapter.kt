package com.rasana.assignment.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rasana.assignment.R
import com.rasana.assignment.presentation.model.DisplayLaunches
import com.rasana.assignment.presentation.model.DisplayRow
import kotlinx.android.synthetic.main.item_launch.view.*
import kotlinx.android.synthetic.main.item_launch_header.view.*

/**
 * Created by Rasana.
 */
class LaunchesAdapter(context: Context, private val onDisplayItemClickListener: OnDisplayItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_PAYEE = 1
    }

    var allLaunches: List<DisplayRow> = emptyList()

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = allLaunches[position]
        if (row is DisplayRow.Header) {
            bindPayeeHeader(holder, row.headerTitle)
        } else if (row is DisplayRow.DisplayItem) {
            holder.itemView.setOnClickListener(null)
            bindPayeeRow(holder, row.displayItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_PAYEE) {
            LaunchViewHolder(layoutInflater.inflate(R.layout.item_launch, parent, false))
        } else {
            LaunchHeaderHolder(layoutInflater.inflate(R.layout.item_launch_header, parent, false))
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return allLaunches.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (allLaunches[position]) {
            is DisplayRow.Header -> VIEW_TYPE_HEADER
            is DisplayRow.DisplayItem -> VIEW_TYPE_PAYEE
        }
    }

    private fun bindPayeeHeader(holder: RecyclerView.ViewHolder, headerTitle: String) {
        holder.itemView.rowSectionName.text = headerTitle
    }

    private fun bindPayeeRow(holder: RecyclerView.ViewHolder, display: DisplayLaunches) {
        holder.itemView.run {
            primaryText.text = display.missionName
            if (display.details == null) {
                secondaryText.visibility = View.GONE
            } else {
                secondaryText.visibility = View.VISIBLE
                secondaryText.text = display.details
            }
            identifierText.text = display.launchDate

            setOnClickListener {
                onDisplayItemClickListener.onDisplayItemClicked(display)
            }
        }
    }

    //region View Holder
    class LaunchViewHolder(payeeView: View) : RecyclerView.ViewHolder(payeeView)

    class LaunchHeaderHolder(headerView: View) : RecyclerView.ViewHolder(headerView)
    //endregion

    interface OnDisplayItemClickListener {
        fun onDisplayItemClicked(displayItem: DisplayLaunches)
    }
}