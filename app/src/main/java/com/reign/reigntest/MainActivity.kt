package com.reign.reigntest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.reign.reigntest.viewmodel.ListViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reign.reigntest.model.Report
import com.reign.reigntest.view.OnItemClickListener
import com.reign.reigntest.view.ReportListAdapter
import com.reign.reigntest.viewmodel.DetailReportActivity
import com.reign.reigntest.viewmodel.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {
    lateinit var viewModel: ListViewModel
    private val reportAdapter = ReportListAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        reports_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reportAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        swipeRefreshLayout.setOnRefreshListener {
            observeViewModel();
            swipeRefreshLayout.isRefreshing = false
        }

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                reportAdapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(reports_list)


        observeViewModel()


    }

    private fun observeViewModel() {
        viewModel.reports.observe(this, Observer {reports ->
            reports?.let {
                if (reports.isNotEmpty()) {
                    reports_list.visibility = View.VISIBLE
                    reportAdapter.updateReports(it)
                    list_error.visibility = View.GONE
                } else {
                    reports_list.visibility = View.GONE
                    list_error.visibility = View.VISIBLE
                }
            }
        })

        viewModel.reportLoadError.observe(this, Observer { isError ->
            list_error.visibility = if(isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    list_error.visibility = View.GONE
                    reports_list.visibility = View.GONE
                }
            }
        })
    }

    override fun onItemClicked(report: Report) {
        Log.d("TAG", report.id);
        val intent = Intent(this, DetailReportActivity::class.java).apply {
            putExtra("report", report);
        }
        startActivity(intent)

    }


}
