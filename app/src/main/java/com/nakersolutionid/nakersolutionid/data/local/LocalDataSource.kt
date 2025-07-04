package com.nakersolutionid.nakersolutionid.data.local

import com.nakersolutionid.nakersolutionid.data.local.dao.ReportDao
import com.nakersolutionid.nakersolutionid.data.local.entity.ReportEntity

class LocalDataSource(private val reportDao: ReportDao) {
    suspend fun insertReport(report: ReportEntity) = reportDao.insertReport(report)
}