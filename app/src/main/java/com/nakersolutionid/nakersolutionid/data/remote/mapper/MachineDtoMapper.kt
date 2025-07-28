package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineAmpereMeasurement
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineConclusionAndRecommendation
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineEnvironmentalMeasurement
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineFoundationAnalysis
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineMeasurementPoint
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineMeasurementPoints
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineStatusResultDetail
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineTestingAndMeasurement
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineVisualInspection
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import com.nakersolutionid.nakersolutionid.features.bap.ptp.toBapRequest
import com.nakersolutionid.nakersolutionid.features.bap.ptp.toPtpBAPCreationReport

// =================================================================================================
// Domain Model -> DTO Request
// =================================================================================================

fun InspectionWithDetailsDomain.toMachineReportRequest(): MachineReportRequest {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String, defaultValue: String = ""): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: defaultValue
    }

    fun getStatusResultDetail(category: String, itemName: String): MachineStatusResultDetail {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return MachineStatusResultDetail(
            status = item?.status ?: false,
            result = item?.result ?: ""
        )
    }

    fun getMeasurementPoint(category: String, pointName: String): MachineMeasurementPoint {
        val resultItem = getCheckItemValue(category, "${pointName}_result")
        val analysisItem = getCheckItemValue(category, "${pointName}_analysis")
        return MachineMeasurementPoint(
            result = resultItem.toDoubleOrNull() ?: 0.0,
            status = analysisItem
        )
    }

    val generalData = MachineGeneralData(
        companyName = inspection.ownerName ?: "",
        companyLocation = inspection.ownerAddress ?: "",
        userInCharge = getCheckItemValue("general_data", "userInCharge"),
        userAddressInCharge = inspection.addressUsageLocation ?: "",
        subcontractorPersonInCharge = getCheckItemValue("general_data", "subcontractorPersonInCharge"),
        unitLocation = inspection.usageLocation ?: "",
        equipmentType = inspection.equipmentType,
        brandType = inspection.manufacturer?.brandOrType ?: "",
        serialNumberUnitNumber = inspection.serialNumber ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        locationAndYearOfManufacture = inspection.manufacturer?.year ?: "",
        technicalDataDieselMotorPowerRpm = getCheckItemValue("general_data", "motorPower"),
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        pjk3SkpNo = getCheckItemValue("general_data", "pjk3SkpNo"),
        ak3SkpNo = getCheckItemValue("general_data", "ak3SkpNo"),
        usagePermitNumber = inspection.permitNumber ?: "",
        operatorName = getCheckItemValue("general_data", "operatorName"),
        equipmentHistory = getCheckItemValue("general_data", "equipmentHistory")
    )

    val technicalData = MachineTechnicalData(
        machineSpecification = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineSpecification(
            brandType = getCheckItemValue("technical_data", "type"),
            technicalDataMaxFeederSpeed = getCheckItemValue("technical_data", "maxFeederSpeed"),
            technicalDataMaxPlateWidth = getCheckItemValue("technical_data", "maxPlateWidth"),
            technicalDataPlateThickness = getCheckItemValue("technical_data", "plateThickness"),
            technicalDataMaxPlateWeight = getCheckItemValue("technical_data", "maxPlateWeight"),
            technicalDataMaxInnerCoilDiameter = getCheckItemValue("technical_data", "maxInnerCoilDiameter"),
            technicalDataMaxOuterCoilDiameter = getCheckItemValue("technical_data", "maxOuterCoilDiameter"),
            technicalDataDriveMotor = getCheckItemValue("technical_data", "driveMotor"),
            technicalDataDieselMotorPowerRpm = getCheckItemValue("technical_data", "motorPowerKw").toIntOrNull() ?: 0,
            serialNumberUnitNumber = getCheckItemValue("technical_data", "brandAndSerial"),
            locationAndYearOfManufacture = getCheckItemValue("technical_data", "locationAndYear"),
            technicalDataMachineWeight = getCheckItemValue("technical_data", "machineDim_weightKg").toIntOrNull() ?: 0,
            technicalDataOverallDimension = getCheckItemValue("technical_data", "machineDim_overall")
        ),
        foundationDimension = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineFoundationDimension(
            technicalDataFoundationDim = getCheckItemValue("technical_data", "foundationDim_dim"),
            technicalDataFoundationDistance = getCheckItemValue("technical_data", "foundationDim_distance"),
            technicalDataVibrationDamperType = getCheckItemValue("technical_data", "foundationDim_vibrationDamper"),
            technicalDataFoundationWeight1 = getCheckItemValue("technical_data", "foundationDim_weight1").toIntOrNull() ?: 0,
            technicalDataFoundationWeight2 = getCheckItemValue("technical_data", "foundationDim_weight2").toIntOrNull() ?: 0
        )
    )

    val visualInspection = MachineVisualInspection(
        foundation = getStatusResultDetail("visual_inspection", "foundationCondition"),
        foundationBearing = getStatusResultDetail("visual_inspection", "foundationBearingCondition"),
        machineFrame = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineFrame(
            mainFrame = getStatusResultDetail("visual_inspection", "mainFrameCondition"),
            braceFrame = getStatusResultDetail("visual_inspection", "braceFrameCondition")
        ),
        roller = getStatusResultDetail("visual_inspection", "rollerCondition"),
        controlPanel = getStatusResultDetail("visual_inspection", "controlPanelCondition"),
        display = getStatusResultDetail("visual_inspection", "displayCondition"),
        operationButtons = getStatusResultDetail("visual_inspection", "operationButtonsCondition"),
        electricalComponents = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineElectricalComponentsVisual(
            measurements = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineElectricalMeasurements(
                electricVoltage = getCheckItemValue("visual_inspection", "electricalVoltage").toIntOrNull() ?: 0,
                electricPhase = getCheckItemValue("visual_inspection", "electricalPhase").toIntOrNull() ?: 0,
                electricFrequency = getCheckItemValue("visual_inspection", "electricalFrequency").toIntOrNull() ?: 0,
                electricAmper = getCheckItemValue("visual_inspection", "electricalCurrent").toIntOrNull() ?: 0
            ),
            voltage = getStatusResultDetail("visual_inspection", "electricalVoltage"),
            power = getStatusResultDetail("visual_inspection", "electricalPower"),
            phase = getStatusResultDetail("visual_inspection", "electricalPhase"),
            frequency = getStatusResultDetail("visual_inspection", "electricalFrequency"),
            current = getStatusResultDetail("visual_inspection", "electricalCurrent"),
            electricalPanel = getStatusResultDetail("visual_inspection", "electricalPanel"),
            conductor = getStatusResultDetail("visual_inspection", "electricalConductor"),
            insulation = getStatusResultDetail("visual_inspection", "electricalInsulation")
        ),
        safetyDevices = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineSafetyDevicesVisual(
            limitSwitchUp = getStatusResultDetail("visual_inspection", "safetyLimitSwitchUp"),
            limitSwitchDown = getStatusResultDetail("visual_inspection", "safetyLimitSwitchDown"),
            grounding = getStatusResultDetail("visual_inspection", "safetyGrounding"),
            safetyGuard = getStatusResultDetail("visual_inspection", "safetyGuard"),
            stampLock = getStatusResultDetail("visual_inspection", "safetyStampLock"),
            pressureIndicator = getStatusResultDetail("visual_inspection", "safetyPressureIndicator"),
            emergencyStop = getStatusResultDetail("visual_inspection", "safetyEmergencyStop"),
            handSensor = getStatusResultDetail("visual_inspection", "safetyHandSensor")
        ),
        hydraulic = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineHydraulicVisual(
            pump = getStatusResultDetail("visual_inspection", "hydraulicPumpCondition"),
            hose = getStatusResultDetail("visual_inspection", "hydraulicHoseCondition")
        )
    )

    val testingAndMeasurement = MachineTestingAndMeasurement(
        safetyDeviceTest = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineSafetyDeviceTest(
            grounding = getStatusResultDetail("testing", "func_safetyGrounding"),
            safetyGuard = getStatusResultDetail("testing", "func_safetyGuard"),
            roller = getStatusResultDetail("testing", "func_safetyRoller"),
            emergencyStop = getStatusResultDetail("testing", "func_safetyEmergencyStop")
        ),
        speedTest = getStatusResultDetail("testing", "func_speedTest"),
        functionTest = getStatusResultDetail("testing", "func_functionTest"),
        weldJointTest = getStatusResultDetail("testing", "func_weldJointTest"),
        vibrationTest = getStatusResultDetail("testing", "func_vibrationTest"),
        lightingTest = getStatusResultDetail("testing", "func_lightingTest"),
        noiseTest = getStatusResultDetail("testing", "func_noiseTest")
    )

    val electricalPanelComponents = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineElectricalPanelComponents(
        ka = getCheckItemValue("testing", "elec_panel_ka"),
        voltage = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineVoltageMeasurement(
            rs = getCheckItemValue("testing", "elec_panel_voltRS").toIntOrNull() ?: 0,
            rt = getCheckItemValue("testing", "elec_panel_voltRT").toIntOrNull() ?: 0,
            st = getCheckItemValue("testing", "elec_panel_voltST").toIntOrNull() ?: 0,
            rn = getCheckItemValue("testing", "elec_panel_voltRN").toIntOrNull() ?: 0,
            rg = getCheckItemValue("testing", "elec_panel_voltRG").toDoubleOrNull() ?: 0.0,
            ng = getCheckItemValue("testing", "elec_panel_voltNG").toDoubleOrNull() ?: 0.0
        ),
        powerInfo = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachinePowerInfo(
            frequency = getCheckItemValue("testing", "elec_power_freq").toDoubleOrNull() ?: 0.0,
            cosQ = getCheckItemValue("testing", "elec_power_cosQ").toDoubleOrNull() ?: 0.0,
            ampere = MachineAmpereMeasurement(
                r = getCheckItemValue("testing", "elec_power_ampR").toIntOrNull() ?: 0,
                s = getCheckItemValue("testing", "elec_power_ampS").toIntOrNull() ?: 0,
                t = getCheckItemValue("testing", "elec_power_ampT").toIntOrNull() ?: 0
            ),
            result = getCheckItemValue("testing", "elec_power_remarks")
        )
    )

    val conclusionAndRecommendation = MachineConclusionAndRecommendation(
        conclusion = this.findings.filter { it.type == FindingType.FINDING }.joinToString("\n") { it.description },
        recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }
    )

    val administration = com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineAdministration(
        inspectionDate = inspection.reportDate ?: ""
    )

    val foundationAnalysis = MachineFoundationAnalysis(
        actualWeight = getCheckItemValue("foundation_analysis", "machineWeight_actual").toDoubleOrNull() ?: 0.0,
        additionalMeterials = getCheckItemValue("foundation_analysis", "machineWeight_additional").toIntOrNull() ?: 0,
        totalWeight = getCheckItemValue("foundation_analysis", "machineWeight_total").toDoubleOrNull() ?: 0.0,
        minimumFoundationWeight = getCheckItemValue("foundation_analysis", "minWeight_calculation").toDoubleOrNull() ?: 0.0,
        totalMinimumFoundationWeight = getCheckItemValue("foundation_analysis", "minWeight_result").toDoubleOrNull() ?: 0.0,
        foundationWeight = 0.0, // Data not present in domain model
        heightFoundation = getCheckItemValue("foundation_analysis", "height_result").toDoubleOrNull() ?: 0.0,
        foundationAnalysisResult = getCheckItemValue("foundation_analysis", "summary")
    )

    val environmentalMeasurement = MachineEnvironmentalMeasurement(
        noise = MachineMeasurementPoints(
            pointA = getMeasurementPoint("noise_measurement", "pointA"),
            pointB = getMeasurementPoint("noise_measurement", "pointB"),
            pointC = getMeasurementPoint("noise_measurement", "pointC"),
            pointD = getMeasurementPoint("noise_measurement", "pointD")
        ),
        lighting = MachineMeasurementPoints(
            pointA = getMeasurementPoint("lighting_measurement", "pointA"),
            pointB = getMeasurementPoint("lighting_measurement", "pointB"),
            pointC = getMeasurementPoint("lighting_measurement", "pointC"),
            pointD = getMeasurementPoint("lighting_measurement", "pointD")
        )
    )

    return MachineReportRequest(
        examinationType = inspection.examinationType,
        extraId = inspection.id,
        inspectionType = inspection.inspectionType.name,
        createdAt = inspection.createdAt ?: "",
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        testingAndMeasurement = testingAndMeasurement,
        electricalPanelComponents = electricalPanelComponents,
        conclusionAndRecommendation = conclusionAndRecommendation,
        administration = administration,
        foundationAnalysis = foundationAnalysis,
        environmentalMeasurement = environmentalMeasurement
    )
}

/**
 * REFACTORED: Now uses the centralized BAP logic from PtpBAPMapper.
 */
fun InspectionWithDetailsDomain.toMachineBapRequest(): MachineBapRequest {
    val inspection = this.inspection
    val ptpBAPReport = this.toPtpBAPCreationReport().report
    return ptpBAPReport.toBapRequest(
        laporanId = inspection.extraId,
        createdAt = inspection.createdAt ?: "",
        extraId = inspection.id
    )
}


// =================================================================================================
// DTO Response -> Domain Model
// =================================================================================================
// ... (rest of the file remains the same, no changes needed here)
fun MachineReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId
    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false,
                result = value ?: ""
            )
        )
    }

    fun addStatusResultCheckItem(category: String, itemName: String, value: MachineStatusResultDetail) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.status ?: false,
                result = value.result
            )
        )
    }

    fun addMeasurementPointCheckItems(category: String, pointName: String, value: MachineMeasurementPoint) {
        addCheckItem(category, "${pointName}_result", value.result.toString())
        addCheckItem(category, "${pointName}_analysis", value.status)
    }

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id,
        moreExtraId = "",
        documentType = DocumentType.valueOf(this.documentType),
        inspectionType = InspectionType.valueOf(this.inspectionType),
        subInspectionType = SubInspectionType.valueOf(this.subInspectionType),
        equipmentType = this.generalData.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.userAddressInCharge,
        driveType = "",
        serialNumber = this.generalData.serialNumberUnitNumber,
        permitNumber = this.generalData.usagePermitNumber,
        capacity = "",
        manufacturer = ManufacturerDomain(
            name = this.generalData.manufacturer,
            brandOrType = this.generalData.brandType,
            year = this.generalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.administration.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    addCheckItem("general_data", "userInCharge", this.generalData.userInCharge)
    addCheckItem("general_data", "subcontractorPersonInCharge", this.generalData.subcontractorPersonInCharge)
    addCheckItem("general_data", "motorPower", this.generalData.technicalDataDieselMotorPowerRpm)
    addCheckItem("general_data", "intendedUse", this.generalData.intendedUse)
    addCheckItem("general_data", "pjk3SkpNo", this.generalData.pjk3SkpNo)
    addCheckItem("general_data", "ak3SkpNo", this.generalData.ak3SkpNo)
    addCheckItem("general_data", "operatorName", this.generalData.operatorName)
    addCheckItem("general_data", "equipmentHistory", this.generalData.equipmentHistory)

    val techCategory = "technical_data"
    this.technicalData.machineSpecification.let { spec ->
        addCheckItem(techCategory, "type", spec.brandType)
        addCheckItem(techCategory, "maxFeederSpeed", spec.technicalDataMaxFeederSpeed)
        addCheckItem(techCategory, "maxPlateWidth", spec.technicalDataMaxPlateWidth)
        addCheckItem(techCategory, "plateThickness", spec.technicalDataPlateThickness)
        addCheckItem(techCategory, "maxPlateWeight", spec.technicalDataMaxPlateWeight)
        addCheckItem(techCategory, "maxInnerCoilDiameter", spec.technicalDataMaxInnerCoilDiameter)
        addCheckItem(techCategory, "maxOuterCoilDiameter", spec.technicalDataMaxOuterCoilDiameter)
        addCheckItem(techCategory, "driveMotor", spec.technicalDataDriveMotor)
        addCheckItem(techCategory, "motorPowerKw", spec.technicalDataDieselMotorPowerRpm.toString())
        addCheckItem(techCategory, "brandAndSerial", spec.serialNumberUnitNumber)
        addCheckItem(techCategory, "locationAndYear", spec.locationAndYearOfManufacture)
        addCheckItem(techCategory, "machineDim_weightKg", spec.technicalDataMachineWeight.toString())
        addCheckItem(techCategory, "machineDim_overall", spec.technicalDataOverallDimension)
    }
    this.technicalData.foundationDimension.let { found ->
        addCheckItem(techCategory, "foundationDim_dim", found.technicalDataFoundationDim)
        addCheckItem(techCategory, "foundationDim_distance", found.technicalDataFoundationDistance)
        addCheckItem(techCategory, "foundationDim_vibrationDamper", found.technicalDataVibrationDamperType)
        addCheckItem(techCategory, "foundationDim_weight1", found.technicalDataFoundationWeight1.toString())
        addCheckItem(techCategory, "foundationDim_weight2", found.technicalDataFoundationWeight2.toString())
    }

    val visualCategory = "visual_inspection"
    addStatusResultCheckItem(visualCategory, "foundationCondition", this.visualInspection.foundation)
    addStatusResultCheckItem(visualCategory, "foundationBearingCondition", this.visualInspection.foundationBearing)
    addStatusResultCheckItem(visualCategory, "mainFrameCondition", this.visualInspection.machineFrame.mainFrame)
    addStatusResultCheckItem(visualCategory, "braceFrameCondition", this.visualInspection.machineFrame.braceFrame)
    addStatusResultCheckItem(visualCategory, "rollerCondition", this.visualInspection.roller)
    addStatusResultCheckItem(visualCategory, "controlPanelCondition", this.visualInspection.controlPanel)
    addStatusResultCheckItem(visualCategory, "displayCondition", this.visualInspection.display)
    addStatusResultCheckItem(visualCategory, "operationButtonsCondition", this.visualInspection.operationButtons)
    this.visualInspection.electricalComponents.let { elec ->
        addStatusResultCheckItem(visualCategory, "electricalVoltage", elec.voltage)
        addStatusResultCheckItem(visualCategory, "electricalPower", elec.power)
        addStatusResultCheckItem(visualCategory, "electricalPhase", elec.phase)
        addStatusResultCheckItem(visualCategory, "electricalFrequency", elec.frequency)
        addStatusResultCheckItem(visualCategory, "electricalCurrent", elec.current)
        addStatusResultCheckItem(visualCategory, "electricalPanel", elec.electricalPanel)
        addStatusResultCheckItem(visualCategory, "electricalConductor", elec.conductor)
        addStatusResultCheckItem(visualCategory, "electricalInsulation", elec.insulation)
    }
    this.visualInspection.safetyDevices.let { safe ->
        addStatusResultCheckItem(visualCategory, "safetyLimitSwitchUp", safe.limitSwitchUp)
        addStatusResultCheckItem(visualCategory, "safetyLimitSwitchDown", safe.limitSwitchDown)
        addStatusResultCheckItem(visualCategory, "safetyGrounding", safe.grounding)
        addStatusResultCheckItem(visualCategory, "safetyGuard", safe.safetyGuard)
        addStatusResultCheckItem(visualCategory, "safetyStampLock", safe.stampLock)
        addStatusResultCheckItem(visualCategory, "safetyPressureIndicator", safe.pressureIndicator)
        addStatusResultCheckItem(visualCategory, "safetyEmergencyStop", safe.emergencyStop)
        addStatusResultCheckItem(visualCategory, "safetyHandSensor", safe.handSensor)
    }
    addStatusResultCheckItem(visualCategory, "hydraulicPumpCondition", this.visualInspection.hydraulic.pump)
    addStatusResultCheckItem(visualCategory, "hydraulicHoseCondition", this.visualInspection.hydraulic.hose)

    val testCategory = "testing"
    this.testingAndMeasurement.safetyDeviceTest.let { safeTest ->
        addStatusResultCheckItem(testCategory, "func_safetyGrounding", safeTest.grounding)
        addStatusResultCheckItem(testCategory, "func_safetyGuard", safeTest.safetyGuard)
        addStatusResultCheckItem(testCategory, "func_safetyRoller", safeTest.roller)
        addStatusResultCheckItem(testCategory, "func_safetyEmergencyStop", safeTest.emergencyStop)
    }
    addStatusResultCheckItem(testCategory, "func_speedTest", this.testingAndMeasurement.speedTest)
    addStatusResultCheckItem(testCategory, "func_functionTest", this.testingAndMeasurement.functionTest)
    addStatusResultCheckItem(testCategory, "func_weldJointTest", this.testingAndMeasurement.weldJointTest)
    addStatusResultCheckItem(testCategory, "func_vibrationTest", this.testingAndMeasurement.vibrationTest)
    addStatusResultCheckItem(testCategory, "func_lightingTest", this.testingAndMeasurement.lightingTest)
    addStatusResultCheckItem(testCategory, "func_noiseTest", this.testingAndMeasurement.noiseTest)

    this.electricalPanelComponents.let { elec ->
        addCheckItem(testCategory, "elec_panel_ka", elec.ka)
        addCheckItem(testCategory, "elec_panel_voltRS", elec.voltage.rs.toString())
        addCheckItem(testCategory, "elec_panel_voltRT", elec.voltage.rt.toString())
        addCheckItem(testCategory, "elec_panel_voltST", elec.voltage.st.toString())
        addCheckItem(testCategory, "elec_panel_voltRN", elec.voltage.rn.toString())
        addCheckItem(testCategory, "elec_panel_voltRG", elec.voltage.rg.toString())
        addCheckItem(testCategory, "elec_panel_voltNG", elec.voltage.ng.toString())
        addCheckItem(testCategory, "elec_power_freq", elec.powerInfo.frequency.toString())
        addCheckItem(testCategory, "elec_power_cosQ", elec.powerInfo.cosQ.toString())
        addCheckItem(testCategory, "elec_power_ampR", elec.powerInfo.ampere.r.toString())
        addCheckItem(testCategory, "elec_power_ampS", elec.powerInfo.ampere.s.toString())
        addCheckItem(testCategory, "elec_power_ampT", elec.powerInfo.ampere.t.toString())
        addCheckItem(testCategory, "elec_power_remarks", elec.powerInfo.result)
    }

    val foundCategory = "foundation_analysis"
    this.foundationAnalysis.let { found ->
        addCheckItem(foundCategory, "machineWeight_actual", found.actualWeight.toString())
        addCheckItem(foundCategory, "machineWeight_additional", found.additionalMeterials.toString())
        addCheckItem(foundCategory, "machineWeight_total", found.totalWeight.toString())
        addCheckItem(foundCategory, "minWeight_calculation", found.minimumFoundationWeight.toString())
        addCheckItem(foundCategory, "minWeight_result", found.totalMinimumFoundationWeight.toString())
        addCheckItem(foundCategory, "height_result", found.heightFoundation.toString())
        addCheckItem(foundCategory, "summary", found.foundationAnalysisResult)
    }

    addMeasurementPointCheckItems("noise_measurement", "pointA", this.environmentalMeasurement.noise.pointA)
    addMeasurementPointCheckItems("noise_measurement", "pointB", this.environmentalMeasurement.noise.pointB)
    addMeasurementPointCheckItems("noise_measurement", "pointC", this.environmentalMeasurement.noise.pointC)
    addMeasurementPointCheckItems("noise_measurement", "pointD", this.environmentalMeasurement.noise.pointD)
    addMeasurementPointCheckItems("lighting_measurement", "pointA", this.environmentalMeasurement.lighting.pointA)
    addMeasurementPointCheckItems("lighting_measurement", "pointB", this.environmentalMeasurement.lighting.pointB)
    addMeasurementPointCheckItems("lighting_measurement", "pointC", this.environmentalMeasurement.lighting.pointC)
    addMeasurementPointCheckItems("lighting_measurement", "pointD", this.environmentalMeasurement.lighting.pointD)

    val findings = mutableListOf<InspectionFindingDomain>()
    this.conclusionAndRecommendation.conclusion.split("\n").filter { it.isNotBlank() }.forEach {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING))
    }
    this.conclusionAndRecommendation.recommendations.split("\n").filter { it.isNotBlank() }.forEach {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList()
    )
}

fun MachineBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id, // API BAP ID
        documentType = DocumentType.valueOf(this.documentType),
        inspectionType = InspectionType.PTP,
        subInspectionType = SubInspectionType.valueOf(this.subInspectionType),
        equipmentType = "", // Not present in BAP DTO
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.userAddressInCharge,
        serialNumber = this.technicalData.serialNumberUnitNumber,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brandType,
            year = this.technicalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Perlengkapan pengaman terpasang", status = this.technicalData.areSafetyFeaturesInstalled))

    this.visualChecks.let { visual ->
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi mesin baik", status = visual.isMachineGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Indikator kelistrikan baik", status = visual.areElectricalIndicatorsGood))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "APAR tersedia", status = visual.isAparAvailable))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "APD tersedia", status = visual.isPpeAvailable))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Pembumian terpasang", status = visual.isGroundingInstalled))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi baterai baik", status = visual.isBatteryGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Ada kebocoran pelumasan", status = visual.hasLubricationLeak))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi pondasi baik", status = visual.isFoundationGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Ada kebocoran hidrolik", status = visual.hasHydraulicLeak))
    }

    this.functionalTests.let { test ->
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Penerangan memenuhi syarat", status = test.isLightingCompliant))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Tingkat kebisingan memenuhi syarat", status = test.isNoiseLevelCompliant))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Tombol darurat berfungsi", status = test.isEmergencyStopFunctional))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Mesin berfungsi baik", status = test.isMachineFunctional))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Tingkat getaran memenuhi syarat", status = test.isVibrationLevelCompliant))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Tahanan isolasi baik", status = test.isInsulationResistanceOk))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Putaran poros memenuhi syarat", status = test.isShaftRotationCompliant))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Tahanan pembumian memenuhi syarat", status = test.isGroundingResistanceCompliant))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Hasil NDT Las baik", status = test.isNdtWeldTestOk))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Tegangan antar fasa normal", status = test.isVoltageBetweenPhasesNormal))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Beban antar fasa seimbang", status = test.isPhaseLoadBalanced))
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    this.technicalData.let { tech ->
        val cat = "DATA TEKNIK"
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Keterangan Kapasitas", result = tech.capacityWorkingLoad, notes = cat))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Daya Motor Penggerak (kW)", result = tech.technicalDataDieselMotorPowerRpm, notes = cat))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Spesifikasi Khusus", result = tech.specialSpecification, notes = cat))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Keterangan Dimensi", result = tech.dimensionsDescription, notes = cat))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Putaran (RPM)", result = tech.rotationRpm, notes = cat))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Frekuensi (Hz)", result = tech.technicalDataGeneratorFrequency, notes = cat))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Arus (Ampere)", result = tech.technicalDataGeneratorCurrent, notes = cat))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Berat Mesin (Kg)", result = tech.machineWeightKg, notes = cat))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = testResults
    )
}