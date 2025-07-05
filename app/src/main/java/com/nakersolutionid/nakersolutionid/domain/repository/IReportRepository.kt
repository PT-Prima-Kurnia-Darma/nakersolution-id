package com.nakersolutionid.nakersolutionid.domain.repository

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.entity.ReportEntity
import com.nakersolutionid.nakersolutionid.domain.model.Report
import kotlinx.coroutines.flow.Flow

interface IReportRepository {
    fun sendReport(request: Report): Flow<Resource<String>>
    fun getAllReports(): Flow<List<Report>>
}