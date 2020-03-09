package com.reign.reigntest.model

import androidx.room.*

@Dao
interface ReportDAO {
    @Query("SELECT * FROM report")
    fun getAll(): List<Report>

    @Query("SELECT * FROM report WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Report>

    @Query("SELECT * FROM report WHERE author LIKE :first AND " +
            "id LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Report

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg report: Report)

    @Delete
    fun delete(report: Report)
}