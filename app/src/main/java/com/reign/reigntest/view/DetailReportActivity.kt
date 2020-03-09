package com.reign.reigntest.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.reign.reigntest.R
import com.reign.reigntest.model.Report
import kotlinx.android.synthetic.main.activity_detail_report.*

class DetailReportActivity: AppCompatActivity (){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_report)

        val report: Report = intent.getParcelableExtra("report")
        report_title.text = report.storyTitle ?: report.title;

    }
}