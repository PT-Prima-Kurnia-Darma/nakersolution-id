package com.nakersolutionid.nakersolutionid.data.local

import com.nakersolutionid.nakersolutionid.data.local.dao.ReportDao
import com.nakersolutionid.nakersolutionid.data.local.entity.ReportEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val reportDao: ReportDao) {
    suspend fun insertReport(report: ReportEntity) = reportDao.insertReport(report)
    suspend fun insertAllReports(reports: List<ReportEntity>) = reportDao.insertAllReports(reports)
    suspend fun getReportById(id: String): ReportEntity? = reportDao.getReportById(id)
    fun getAllReports(): Flow<List<ReportEntity>> = reportDao.getAllReports()
    suspend fun deleteReportById(id: String): Int = reportDao.deleteReportById(id)
    suspend fun deleteAllReports() = reportDao.deleteAllReports()
}