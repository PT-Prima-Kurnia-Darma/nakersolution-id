package com.nakersolutionid.nakersolutionid.data.local

import com.nakersolutionid.nakersolutionid.data.local.dao.InspectionDao
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionCheckItem
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionEntity
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionFinding
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionTestResult
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class LocalDataSource(private val inspectionDao: InspectionDao) {
    suspend fun insertInspection(
        inspectionEntity: InspectionEntity,
        checkItems: List<InspectionCheckItem>,
        findings: List<InspectionFinding>,
        testResults: List<InspectionTestResult>
    ) = inspectionDao.insertInspectionWithDetails(
        inspectionEntity,
        checkItems,
        findings,
        testResults
    )

    fun getInspection(id: Long): Flow<InspectionWithDetails> = inspectionDao.getInspectionWithDetails(id)
    fun getAllInspectionsWithDetails(): Flow<List<InspectionWithDetails>> = inspectionDao.getAllInspectionsWithDetails()
}