package com.reign.reigntest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.reign.reigntest.R
import com.reign.reigntest.model.Report
import kotlinx.android.synthetic.main.item_new.view.*

class ReportListAdapter (var reports : ArrayList<Report>, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ReportListAdapter.ReportViewHolder>() {
   private val deletedKeys :ArrayList<String> = arrayListOf();
    fun updateReports(report: List<Report>) {
        reports.clear()
        reports.addAll(report.filter { report: Report -> !deletedKeys.contains(report.id) })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ReportViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_new, parent, false)
    )

    override fun getItemCount() = reports.size

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(reports[position], itemClickListener)
    }
    fun removeAt(i:Int) : Report{
        val report:Report = reports.get(i);
        this.deletedKeys.add(report.id);
        reports.removeAt(i);
        notifyItemRemoved(i);
        return report
    }

    class ReportViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val title = view.title
        private val author = view.author
        private val createdAt= view.created_at

        fun bind(report: Report, clickListener: OnItemClickListener)
        {
            title.text = report.title?: report.storyTitle
            author.text = report.author
            createdAt.text = report.createdAt

            itemView.setOnClickListener{
                clickListener.onItemClicked(report)
            }

        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(report: Report)
}