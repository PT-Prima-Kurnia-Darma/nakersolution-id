package com.nakersolutionid.nakersolutionid.features.report.ptp.machine

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import kotlinx.collections.immutable.toImmutableList

/**
 * Maps the ProductionMachineUiState to an InspectionWithDetailsDomain model without using reflection.
 * All UI state data is flattened into InspectionDomain, InspectionFindingDomain, or InspectionCheckItemDomain.
 */
fun ProductionMachineUiState.toInspectionWithDetailsDomain(
    currentTime: String
): InspectionWithDetailsDomain {
    val report = this.inspectionReport
    val generalData = report.generalData
    val technicalData = report.technicalData
    val visualInspection = report.visualInspection
    val testing = report.testingAndMeasurement
    val foundation = report.foundationAnalysis
    val noise = report.noiseMeasurement
    val lighting = report.lightingMeasurement
    val conclusion = report.conclusion

    // Hardcoded values as requested
    val inspectionId = 0L
    val documentType = DocumentType.LAPORAN
    val inspectionType = InspectionType.PTP
    val subInspectionType = SubInspectionType.Machine

    // 1. Map main and general data to InspectionDomain
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = report.extraId,
        moreExtraId = report.moreExtraId,
        documentType = documentType,
        inspectionType = inspectionType,
        subInspectionType = subInspectionType,
        equipmentType = report.equipmentType,
        examinationType = report.examinationType,
        ownerName = generalData.ownerName,
        ownerAddress = generalData.ownerAddress,
        usageLocation = generalData.unitLocation,
        addressUsageLocation = generalData.userAddressInCharge,
        driveType = generalData.driveType,
        serialNumber = generalData.serialNumberUnitNumber,
        permitNumber = generalData.usagePermitNumber,
        capacity = "", // Not available in Machine UI State
        manufacturer = ManufacturerDomain(
            name = generalData.manufacturer,
            brandOrType = generalData.brandType,
            year = generalData.locationAndYearOfManufacture
        ),
        createdAt = currentTime,
        speed = "",
        floorServed = "",
        reportDate = "",
        nextInspectionDate = "",
        inspectorName = "",
        status = "",
        isSynced = false
    )

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

    fun addConditionResultCheckItem(category: String, itemName: String, value: ProductionMachineConditionResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.isGood,
                result = value.remarks
            )
        )
    }

    fun addTestResultCheckItem(category: String, itemName: String, value: ProductionMachineTestResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.isMet,
                result = value.remarks
            )
        )
    }

    // 2. Map remaining General Data to CheckItems
    addCheckItem("general_data", "userInCharge", generalData.userInCharge)
    addCheckItem("general_data", "subcontractorPersonInCharge", generalData.subcontractorPersonInCharge)
    addCheckItem("general_data", "motorPower", generalData.motorPower)
    addCheckItem("general_data", "intendedUse", generalData.intendedUse)
    addCheckItem("general_data", "pjk3SkpNo", generalData.pjk3SkpNo)
    addCheckItem("general_data", "ak3SkpNo", generalData.ak3SkpNo)
    addCheckItem("general_data", "operatorName", generalData.operatorName)
    addCheckItem("general_data", "equipmentHistory", generalData.equipmentHistory)

    // 3. Map all Technical Data to CheckItems
    val techCategory = "technical_data"
    addCheckItem(techCategory, "type", technicalData.type)
    addCheckItem(techCategory, "maxFeederSpeed", technicalData.maxFeederSpeed)
    addCheckItem(techCategory, "maxPlateWidth", technicalData.maxPlateWidth)
    addCheckItem(techCategory, "plateThickness", technicalData.plateThickness)
    addCheckItem(techCategory, "maxPlateWeight", technicalData.maxPlateWeight)
    addCheckItem(techCategory, "maxInnerCoilDiameter", technicalData.maxInnerCoilDiameter)
    addCheckItem(techCategory, "maxOuterCoilDiameter", technicalData.maxOuterCoilDiameter)
    addCheckItem(techCategory, "driveMotor", technicalData.driveMotor)
    addCheckItem(techCategory, "motorPowerKw", technicalData.motorPowerKw)
    addCheckItem(techCategory, "brandAndSerial", technicalData.brandAndSerial)
    addCheckItem(techCategory, "locationAndYear", technicalData.locationAndYear)
    addCheckItem(techCategory, "machineDim_weightKg", technicalData.machineDimensions.weightKg)
    addCheckItem(techCategory, "machineDim_overall", technicalData.machineDimensions.overallDimension)
    addCheckItem(techCategory, "foundationDim_dim", technicalData.foundationDimensions.dim)
    addCheckItem(techCategory, "foundationDim_distance", technicalData.foundationDimensions.distance)
    addCheckItem(techCategory, "foundationDim_vibrationDamper", technicalData.foundationDimensions.vibrationDamperType)
    addCheckItem(techCategory, "foundationDim_weight1", technicalData.foundationDimensions.weightKg1)
    addCheckItem(techCategory, "foundationDim_weight2", technicalData.foundationDimensions.weightKg2)

    // 4. Map all Visual Inspection to CheckItems
    val visualCategory = "visual_inspection"
    addConditionResultCheckItem(visualCategory, "foundationCondition", visualInspection.foundationCondition)
    addConditionResultCheckItem(visualCategory, "foundationBearingCondition", visualInspection.foundationBearingCondition)
    addConditionResultCheckItem(visualCategory, "mainFrameCondition", visualInspection.mainFrameCondition)
    addConditionResultCheckItem(visualCategory, "braceFrameCondition", visualInspection.braceFrameCondition)
    addConditionResultCheckItem(visualCategory, "rollerCondition", visualInspection.rollerCondition)
    addConditionResultCheckItem(visualCategory, "controlPanelCondition", visualInspection.controlPanelCondition)
    addConditionResultCheckItem(visualCategory, "displayCondition", visualInspection.displayCondition)
    addConditionResultCheckItem(visualCategory, "operationButtonsCondition", visualInspection.operationButtonsCondition)
    addConditionResultCheckItem(visualCategory, "electricalVoltage", visualInspection.electricalVoltage)
    addConditionResultCheckItem(visualCategory, "electricalPower", visualInspection.electricalPower)
    addConditionResultCheckItem(visualCategory, "electricalPhase", visualInspection.electricalPhase)
    addConditionResultCheckItem(visualCategory, "electricalFrequency", visualInspection.electricalFrequency)
    addConditionResultCheckItem(visualCategory, "electricalCurrent", visualInspection.electricalCurrent)
    addConditionResultCheckItem(visualCategory, "electricalPanel", visualInspection.electricalPanel)
    addConditionResultCheckItem(visualCategory, "electricalConductor", visualInspection.electricalConductor)
    addConditionResultCheckItem(visualCategory, "electricalInsulation", visualInspection.electricalInsulation)
    addConditionResultCheckItem(visualCategory, "safetyLimitSwitchUp", visualInspection.safetyLimitSwitchUp)
    addConditionResultCheckItem(visualCategory, "safetyLimitSwitchDown", visualInspection.safetyLimitSwitchDown)
    addConditionResultCheckItem(visualCategory, "safetyGrounding", visualInspection.safetyGrounding)
    addConditionResultCheckItem(visualCategory, "safetyGuard", visualInspection.safetyGuard)
    addConditionResultCheckItem(visualCategory, "safetyStampLock", visualInspection.safetyStampLock)
    addConditionResultCheckItem(visualCategory, "safetyPressureIndicator", visualInspection.safetyPressureIndicator)
    addConditionResultCheckItem(visualCategory, "safetyEmergencyStop", visualInspection.safetyEmergencyStop)
    addConditionResultCheckItem(visualCategory, "safetyHandSensor", visualInspection.safetyHandSensor)
    addConditionResultCheckItem(visualCategory, "hydraulicPumpCondition", visualInspection.hydraulicPumpCondition)
    addConditionResultCheckItem(visualCategory, "hydraulicHoseCondition", visualInspection.hydraulicHoseCondition)

    // 5. Map Testing and Measurement to CheckItems
    val testCategory = "testing"
    testing.functionalTests.let { func ->
        addTestResultCheckItem(testCategory, "func_safetyGrounding", func.safetyGrounding)
        addTestResultCheckItem(testCategory, "func_safetyGuard", func.safetyGuard)
        addTestResultCheckItem(testCategory, "func_safetyRoller", func.safetyRoller)
        addTestResultCheckItem(testCategory, "func_safetyEmergencyStop", func.safetyEmergencyStop)
        addTestResultCheckItem(testCategory, "func_speedTest", func.speedTest)
        addTestResultCheckItem(testCategory, "func_functionTest", func.functionTest)
        addTestResultCheckItem(testCategory, "func_weldJointTest", func.weldJointTest)
        addTestResultCheckItem(testCategory, "func_vibrationTest", func.vibrationTest)
        addTestResultCheckItem(testCategory, "func_lightingTest", func.lightingTest)
        addTestResultCheckItem(testCategory, "func_noiseTest", func.noiseTest)
    }
    testing.electricalMeasurements.let { elec ->
        addCheckItem(testCategory, "elec_panel_ka", elec.panelControlDrawing.ka)
        addCheckItem(testCategory, "elec_panel_voltRS", elec.panelControlDrawing.voltageRS)
        addCheckItem(testCategory, "elec_panel_voltRT", elec.panelControlDrawing.voltageRT)
        addCheckItem(testCategory, "elec_panel_voltST", elec.panelControlDrawing.voltageST)
        addCheckItem(testCategory, "elec_panel_voltRN", elec.panelControlDrawing.voltageRN)
        addCheckItem(testCategory, "elec_panel_voltRG", elec.panelControlDrawing.voltageRG)
        addCheckItem(testCategory, "elec_panel_voltNG", elec.panelControlDrawing.voltageNG)
        addCheckItem(testCategory, "elec_power_freq", elec.powerInfo.frequency)
        addCheckItem(testCategory, "elec_power_cosQ", elec.powerInfo.cosQ)
        addCheckItem(testCategory, "elec_power_ampR", elec.powerInfo.ampereR)
        addCheckItem(testCategory, "elec_power_ampS", elec.powerInfo.ampereS)
        addCheckItem(testCategory, "elec_power_ampT", elec.powerInfo.ampereT)
        addCheckItem(testCategory, "elec_power_remarks", elec.powerInfo.remarks)
    }

    // 6. Map Foundation Analysis to CheckItems
    val foundCategory = "foundation_analysis"
    addCheckItem(foundCategory, "machineWeight_actual", foundation.machineWeight.actualTon)
    addCheckItem(foundCategory, "machineWeight_additional", foundation.machineWeight.additionalMaterialTon)
    addCheckItem(foundCategory, "machineWeight_total", foundation.machineWeight.totalTon)
    addCheckItem(foundCategory, "minWeight_calculation", foundation.minFoundationWeight.calculation)
    addCheckItem(foundCategory, "minWeight_result", foundation.minFoundationWeight.resultTon)
    addCheckItem(foundCategory, "height_formula", foundation.foundationHeight.formula)
    addCheckItem(foundCategory, "height_calculation", foundation.foundationHeight.calculation)
    addCheckItem(foundCategory, "height_result", foundation.foundationHeight.resultMeter)
    addCheckItem(foundCategory, "summary", foundation.summary)

    // 7. Map Noise and Lighting to CheckItems
    val noiseCategory = "noise_measurement"
    addCheckItem(noiseCategory, "pointA_result", noise.pointA.result)
    addCheckItem(noiseCategory, "pointA_analysis", noise.pointA.analysis)
    addCheckItem(noiseCategory, "pointB_result", noise.pointB.result)
    addCheckItem(noiseCategory, "pointB_analysis", noise.pointB.analysis)
    addCheckItem(noiseCategory, "pointC_result", noise.pointC.result)
    addCheckItem(noiseCategory, "pointC_analysis", noise.pointC.analysis)
    addCheckItem(noiseCategory, "pointD_result", noise.pointD.result)
    addCheckItem(noiseCategory, "pointD_analysis", noise.pointD.analysis)

    val lightingCategory = "lighting_measurement"
    addCheckItem(lightingCategory, "pointA_result", lighting.pointA.result)
    addCheckItem(lightingCategory, "pointA_analysis", lighting.pointA.analysis)
    addCheckItem(lightingCategory, "pointB_result", lighting.pointB.result)
    addCheckItem(lightingCategory, "pointB_analysis", lighting.pointB.analysis)
    addCheckItem(lightingCategory, "pointC_result", lighting.pointC.result)
    addCheckItem(lightingCategory, "pointC_analysis", lighting.pointC.analysis)
    addCheckItem(lightingCategory, "pointD_result", lighting.pointD.result)
    addCheckItem(lightingCategory, "pointD_analysis", lighting.pointD.analysis)


    // 8. Map Conclusion to Findings
    val findings = mutableListOf<InspectionFindingDomain>()
    conclusion.summary.forEach {
        findings.add(
            InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING)
        )
    }
    conclusion.requirements.forEach {
        // Asumsi "Persyaratan" sama dengan "Rekomendasi"
        findings.add(
            InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION)
        )
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList()
    )
}

fun InspectionWithDetailsDomain.toMachineUiState(): ProductionMachineUiState {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getConditionResult(category: String, itemName: String): ProductionMachineConditionResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return ProductionMachineConditionResult(
            isGood = item?.status ?: false,
            remarks = item?.result ?: ""
        )
    }

    fun getTestResult(category: String, itemName: String): ProductionMachineTestResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return ProductionMachineTestResult(
            isMet = item?.status ?: false,
            remarks = item?.result ?: ""
        )
    }

    val generalData = ProductionMachineGeneralData(
        ownerName = inspection.ownerName ?: "",
        ownerAddress = inspection.ownerAddress ?: "",
        userAddressInCharge = inspection.addressUsageLocation ?: "",
        unitLocation = inspection.usageLocation ?: "",
        driveType = inspection.driveType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        brandType = inspection.manufacturer?.brandOrType ?: "",
        locationAndYearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = inspection.serialNumber ?: "",
        usagePermitNumber = inspection.permitNumber ?: "",
        userInCharge = getCheckItemValue("general_data", "userInCharge"),
        subcontractorPersonInCharge = getCheckItemValue("general_data", "subcontractorPersonInCharge"),
        motorPower = getCheckItemValue("general_data", "motorPower"),
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        pjk3SkpNo = getCheckItemValue("general_data", "pjk3SkpNo"),
        ak3SkpNo = getCheckItemValue("general_data", "ak3SkpNo"),
        operatorName = getCheckItemValue("general_data", "operatorName"),
        equipmentHistory = getCheckItemValue("general_data", "equipmentHistory")
    )

    val techCategory = "technical_data"
    val technicalData = ProductionMachineTechnicalData(
        type = getCheckItemValue(techCategory, "type"),
        maxFeederSpeed = getCheckItemValue(techCategory, "maxFeederSpeed"),
        maxPlateWidth = getCheckItemValue(techCategory, "maxPlateWidth"),
        plateThickness = getCheckItemValue(techCategory, "plateThickness"),
        maxPlateWeight = getCheckItemValue(techCategory, "maxPlateWeight"),
        maxInnerCoilDiameter = getCheckItemValue(techCategory, "maxInnerCoilDiameter"),
        maxOuterCoilDiameter = getCheckItemValue(techCategory, "maxOuterCoilDiameter"),
        driveMotor = getCheckItemValue(techCategory, "driveMotor"),
        motorPowerKw = getCheckItemValue(techCategory, "motorPowerKw"),
        brandAndSerial = getCheckItemValue(techCategory, "brandAndSerial"),
        locationAndYear = getCheckItemValue(techCategory, "locationAndYear"),
        machineDimensions = ProductionMachineDimensions(
            weightKg = getCheckItemValue(techCategory, "machineDim_weightKg"),
            overallDimension = getCheckItemValue(techCategory, "machineDim_overall")
        ),
        foundationDimensions = ProductionMachineFoundationDimensions(
            dim = getCheckItemValue(techCategory, "foundationDim_dim"),
            distance = getCheckItemValue(techCategory, "foundationDim_distance"),
            vibrationDamperType = getCheckItemValue(techCategory, "foundationDim_vibrationDamper"),
            weightKg1 = getCheckItemValue(techCategory, "foundationDim_weight1"),
            weightKg2 = getCheckItemValue(techCategory, "foundationDim_weight2")
        )
    )

    val visualCategory = "visual_inspection"
    val visualInspection = ProductionMachineVisualInspection(
        foundationCondition = getConditionResult(visualCategory, "foundationCondition"),
        foundationBearingCondition = getConditionResult(visualCategory, "foundationBearingCondition"),
        mainFrameCondition = getConditionResult(visualCategory, "mainFrameCondition"),
        braceFrameCondition = getConditionResult(visualCategory, "braceFrameCondition"),
        rollerCondition = getConditionResult(visualCategory, "rollerCondition"),
        controlPanelCondition = getConditionResult(visualCategory, "controlPanelCondition"),
        displayCondition = getConditionResult(visualCategory, "displayCondition"),
        operationButtonsCondition = getConditionResult(visualCategory, "operationButtonsCondition"),
        electricalVoltage = getConditionResult(visualCategory, "electricalVoltage"),
        electricalPower = getConditionResult(visualCategory, "electricalPower"),
        electricalPhase = getConditionResult(visualCategory, "electricalPhase"),
        electricalFrequency = getConditionResult(visualCategory, "electricalFrequency"),
        electricalCurrent = getConditionResult(visualCategory, "electricalCurrent"),
        electricalPanel = getConditionResult(visualCategory, "electricalPanel"),
        electricalConductor = getConditionResult(visualCategory, "electricalConductor"),
        electricalInsulation = getConditionResult(visualCategory, "electricalInsulation"),
        safetyLimitSwitchUp = getConditionResult(visualCategory, "safetyLimitSwitchUp"),
        safetyLimitSwitchDown = getConditionResult(visualCategory, "safetyLimitSwitchDown"),
        safetyGrounding = getConditionResult(visualCategory, "safetyGrounding"),
        safetyGuard = getConditionResult(visualCategory, "safetyGuard"),
        safetyStampLock = getConditionResult(visualCategory, "safetyStampLock"),
        safetyPressureIndicator = getConditionResult(visualCategory, "safetyPressureIndicator"),
        safetyEmergencyStop = getConditionResult(visualCategory, "safetyEmergencyStop"),
        safetyHandSensor = getConditionResult(visualCategory, "safetyHandSensor"),
        hydraulicPumpCondition = getConditionResult(visualCategory, "hydraulicPumpCondition"),
        hydraulicHoseCondition = getConditionResult(visualCategory, "hydraulicHoseCondition")
    )

    val testCategory = "testing"
    val testingAndMeasurement = ProductionMachineTestingAndMeasurement(
        functionalTests = ProductionMachineFunctionalTests(
            safetyGrounding = getTestResult(testCategory, "func_safetyGrounding"),
            safetyGuard = getTestResult(testCategory, "func_safetyGuard"),
            safetyRoller = getTestResult(testCategory, "func_safetyRoller"),
            safetyEmergencyStop = getTestResult(testCategory, "func_safetyEmergencyStop"),
            speedTest = getTestResult(testCategory, "func_speedTest"),
            functionTest = getTestResult(testCategory, "func_functionTest"),
            weldJointTest = getTestResult(testCategory, "func_weldJointTest"),
            vibrationTest = getTestResult(testCategory, "func_vibrationTest"),
            lightingTest = getTestResult(testCategory, "func_lightingTest"),
            noiseTest = getTestResult(testCategory, "func_noiseTest")
        ),
        electricalMeasurements = ProductionMachineElectricalMeasurements(
            panelControlDrawing = ProductionMachinePanelControlDrawing(
                ka = getCheckItemValue(testCategory, "elec_panel_ka"),
                voltageRS = getCheckItemValue(testCategory, "elec_panel_voltRS"),
                voltageRT = getCheckItemValue(testCategory, "elec_panel_voltRT"),
                voltageST = getCheckItemValue(testCategory, "elec_panel_voltST"),
                voltageRN = getCheckItemValue(testCategory, "elec_panel_voltRN"),
                voltageRG = getCheckItemValue(testCategory, "elec_panel_voltRG"),
                voltageNG = getCheckItemValue(testCategory, "elec_panel_voltNG")
            ),
            powerInfo = ProductionMachinePowerInfo(
                frequency = getCheckItemValue(testCategory, "elec_power_freq"),
                cosQ = getCheckItemValue(testCategory, "elec_power_cosQ"),
                ampereR = getCheckItemValue(testCategory, "elec_power_ampR"),
                ampereS = getCheckItemValue(testCategory, "elec_power_ampS"),
                ampereT = getCheckItemValue(testCategory, "elec_power_ampT"),
                remarks = getCheckItemValue(testCategory, "elec_power_remarks")
            )
        )
    )

    val foundCategory = "foundation_analysis"
    val foundationAnalysis = ProductionMachineFoundationAnalysis(
        machineWeight = ProductionMachineWeight(
            actualTon = getCheckItemValue(foundCategory, "machineWeight_actual"),
            additionalMaterialTon = getCheckItemValue(foundCategory, "machineWeight_additional"),
            totalTon = getCheckItemValue(foundCategory, "machineWeight_total")
        ),
        minFoundationWeight = ProductionMachineMinFoundationWeight(
            calculation = getCheckItemValue(foundCategory, "minWeight_calculation"),
            resultTon = getCheckItemValue(foundCategory, "minWeight_result")
        ),
        foundationHeight = ProductionMachineFoundationHeight(
            formula = getCheckItemValue(foundCategory, "height_formula"),
            calculation = getCheckItemValue(foundCategory, "height_calculation"),
            resultMeter = getCheckItemValue(foundCategory, "height_result")
        ),
        summary = getCheckItemValue(foundCategory, "summary")
    )

    val noiseCategory = "noise_measurement"
    val noiseMeasurement = ProductionMachineNoiseMeasurement(
        pointA = ProductionMachineMeasurementPoint(
            result = getCheckItemValue(noiseCategory, "pointA_result"),
            analysis = getCheckItemValue(noiseCategory, "pointA_analysis")
        ),
        pointB = ProductionMachineMeasurementPoint(
            result = getCheckItemValue(noiseCategory, "pointB_result"),
            analysis = getCheckItemValue(noiseCategory, "pointB_analysis")
        ),
        pointC = ProductionMachineMeasurementPoint(
            result = getCheckItemValue(noiseCategory, "pointC_result"),
            analysis = getCheckItemValue(noiseCategory, "pointC_analysis")
        ),
        pointD = ProductionMachineMeasurementPoint(
            result = getCheckItemValue(noiseCategory, "pointD_result"),
            analysis = getCheckItemValue(noiseCategory, "pointD_analysis")
        )
    )

    val lightingCategory = "lighting_measurement"
    val lightingMeasurement = ProductionMachineLightingMeasurement(
        pointA = ProductionMachineMeasurementPoint(
            result = getCheckItemValue(lightingCategory, "pointA_result"),
            analysis = getCheckItemValue(lightingCategory, "pointA_analysis")
        ),
        pointB = ProductionMachineMeasurementPoint(
            result = getCheckItemValue(lightingCategory, "pointB_result"),
            analysis = getCheckItemValue(lightingCategory, "pointB_analysis")
        ),
        pointC = ProductionMachineMeasurementPoint(
            result = getCheckItemValue(lightingCategory, "pointC_result"),
            analysis = getCheckItemValue(lightingCategory, "pointC_analysis")
        ),
        pointD = ProductionMachineMeasurementPoint(
            result = getCheckItemValue(lightingCategory, "pointD_result"),
            analysis = getCheckItemValue(lightingCategory, "pointD_analysis")
        )
    )

    val summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }
    val requirements = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }
    val conclusion = ProductionMachineConclusion(
        summary = summary.toImmutableList(),
        requirements = requirements.toImmutableList()
    )

    val report = ProductionMachineInspectionReport(
        extraId = inspection.extraId,
        moreExtraId = inspection.moreExtraId,
        equipmentType = inspection.equipmentType,
        examinationType = inspection.examinationType,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        testingAndMeasurement = testingAndMeasurement,
        foundationAnalysis = foundationAnalysis,
        noiseMeasurement = noiseMeasurement,
        lightingMeasurement = lightingMeasurement,
        conclusion = conclusion
    )

    return ProductionMachineUiState(inspectionReport = report)
}