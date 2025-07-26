package com.nakersolutionid.nakersolutionid.data.remote.mapper

/**
 * Single Source of Truth for all mapping keys used in PUBT feature.
 * Using these constants across all mappers (UI <-> Domain <-> DTO)
 * ensures data consistency and prevents data loss due to typos or inconsistencies.
 */
object PubtMappingKeys {

    const val SEPARATOR = "#"

    object BAP {
        object Category {
            const val TECHNICAL_DATA = "DATA TEKNIK"
            const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
            const val VISUAL_SAFETY_VALVE = "$VISUAL_INSPECTION - Katup Pengaman"
            const val VISUAL_APAR = "$VISUAL_INSPECTION - APAR"
            const val TESTING = "PENGUJIAN"
        }

        object ItemName {
            const val FOUNDATION_CONDITION = "Kondisi Pondasi Baik"
            const val WHEEL_CONDITION = "Kondisi Roda Baik"
            const val PIPE_CONDITION = "Kondisi Pipa Baik"
            const val SAFETY_VALVE_INSTALLED = "Terpasang"
            const val SAFETY_VALVE_CONDITION = "Kondisi Baik"
            const val APAR_AVAILABLE = "Tersedia"
            const val APAR_CONDITION = "Kondisi Baik"
            const val NDT_FULFILLED = "Pengujian NDT Terpenuhi"
            const val THICKNESS_COMPLY = "Pengujian Ketebalan Sesuai"
            const val PNEUMATIC_CONDITION = "Kondisi Pengujian Pneumatik Baik"
            const val HYDRO_TEST_FULFILLED = "Pengujian Hidrostatik Terpenuhi"
            const val SAFETY_VALVE_TEST_CONDITION = "Kondisi Pengujian Katup Pengaman Baik"
        }

        object TestName {
            const val MANUFACTURE_COUNTRY = "Negara Pembuat"
            const val FUEL_TYPE = "Jenis Bahan Bakar"
            const val PRESSURE_VESSEL_CONTENT = "Isi Bejana Tekan"
            const val DESIGN_PRESSURE = "Tekanan Desain (Kg/Cm2)"
            const val MAX_WORKING_PRESSURE = "Tekanan Kerja Maks (Kg/Cm2)"
            const val MATERIAL_TYPE = "Jenis Bahan"
            const val SAFETY_VALVE_TYPE = "Jenis Katup Pengaman"
            const val VOLUME_LITERS = "Volume (Liter)"
        }
    }

    object Report {
        object Category {
            const val GENERAL_DATA = "general_data"
            const val TECHNICAL_DATA = "technical_data"
            const val VISUAL_INSPECTION = "visual_inspection"
            const val MATERIAL_THICKNESS = "material_thickness"
            const val THICKNESS_SETUP = "thickness_setup"
            const val MEASUREMENT_TOP_HEAD = "measurement_top_head"
            const val MEASUREMENT_SHELL = "measurement_shell"
            const val MEASUREMENT_BUTTON_HEAD = "measurement_button_head"
            const val NDT_TESTS = "ndt_tests"
            const val HYDROTEST = "hydrotest"
            const val APPENDAGES = "appendages"
            const val SAFETY_VALVE_TEST = "safety_valve_test"
        }

        object ItemName {
            // General Data
            const val USER = "user"
            const val OPERATOR_NAME = "operatorName"
            const val DESIGN_PRESSURE_KGCM2 = "designPressureKgCm2"
            const val MAX_ALLOWABLE_WORKING_PRESSURE_KGCM2 = "maxAllowableWorkingPressureKgCm2"
            const val STEAM_TEMPERATURE = "steamTemperature"
            const val OPERATING_PRESSURE_KGCM2 = "operatingPressureKgCm2"
            const val FUEL_TYPE = "fuelType"
            const val INTENDED_USE = "intendedUse"
            const val OPERATOR_CERTIFICATE = "operatorCertificate"
            const val EQUIPMENT_HISTORY = "equipmentHistory"

            // Technical Data - Shell
            const val SHELL_NUMBER_OF_ROUNDS = "shell_numberOfRounds"
            const val SHELL_CONNECTION_METHOD = "shell_connectionMethod"
            const val SHELL_MATERIAL = "shell_material"
            const val SHELL_PIPE_DIAMETER_MM = "shell_pipeDiameterMm"
            const val SHELL_THICKNESS_MM = "shell_thicknessMm"
            const val SHELL_BODY_LENGTH_MM = "shell_bodyLengthMm"
            const val HEADS_TYPE = "heads_type" // From GeneralMapper
            const val HEADS_TOP_DIAMETER_MM = "heads_topDiameterMm"
            const val HEADS_TOP_THICKNESS_MM = "heads_topThicknessMm"
            const val HEADS_REAR_DIAMETER_MM = "heads_rearDiameterMm"
            const val HEADS_REAR_THICKNESS_MM = "heads_rearThicknessMm"
            const val TUBE_PLATE_FRONT_DIM1_MM = "tubePlate_frontDim1Mm"
            const val TUBE_PLATE_FRONT_DIM2_MM = "tubePlate_frontDim2Mm"
            const val TUBE_PLATE_REAR_DIM1_MM = "tubePlate_rearDim1Mm"
            const val TUBE_PLATE_REAR_DIM2_MM = "tubePlate_rearDim2Mm"

            // Technical Data - Furnace
            const val FURNACE_TYPE = "furnace_type"
            const val FURNACE_MATERIAL = "furnace_material"
            const val FURNACE_OUTER_DIAMETER_MM = "furnace_outerDiameterMm"
            const val FURNACE_INNER_DIAMETER_MM = "furnace_innerDiameterMm"
            const val FURNACE_THICKNESS_MM = "furnace_thicknessMm"

            // Technical Data - Water Tubes
            const val TUBES_FIRST_DIAMETER = "tubes_first_diameter"
            const val TUBES_FIRST_THICKNESS = "tubes_first_thickness"
            const val TUBES_FIRST_LENGTH = "tubes_first_length"
            const val TUBES_FIRST_QUANTITY = "tubes_first_quantity"
            const val TUBES_SECOND_DIAMETER = "tubes_second_diameter"
            const val TUBES_SECOND_THICKNESS = "tubes_second_thickness"
            const val TUBES_SECOND_LENGTH = "tubes_second_length"
            const val TUBES_SECOND_QUANTITY = "tubes_second_quantity"
            const val TUBES_STAY_DIAMETER = "tubes_stay_diameter"
            const val TUBES_STAY_THICKNESS = "tubes_stay_thickness"
            const val TUBES_STAY_LENGTH = "tubes_stay_length"
            const val TUBES_STAY_QUANTITY = "tubes_stay_quantity"
            const val TUBES_MATERIAL_DIAMETER = "tubes_material_diameter"
            const val TUBES_MATERIAL_THICKNESS = "tubes_material_thickness"
            const val TUBES_MATERIAL_LENGTH = "tubes_material_length"
            const val TUBES_MATERIAL_QUANTITY = "tubes_material_quantity"
            const val TUBES_CONNECTION_METHOD = "tubes_connectionMethod"

            // Visual Inspection - Steam Equipment
            const val STEAM_EQUIPMENT_SHELL_DRUM = "steamEquipmentShellDrum"
            const val STEAM_EQUIPMENT_BOUILEUR = "steamEquipmentBouileur"
            const val STEAM_EQUIPMENT_FURNACE = "steamEquipmentFurnace"
            const val STEAM_EQUIPMENT_FIRE_CHAMBER = "steamEquipmentFireChamber"
            const val STEAM_EQUIPMENT_REFRACTORY = "steamEquipmentRefractory"
            const val STEAM_EQUIPMENT_COMBUSTION_CHAMBER = "steamEquipmentCombustionChamber"
            const val STEAM_EQUIPMENT_FIRE_TUBES = "steamEquipmentFireTubes"
            const val STEAM_EQUIPMENT_SUPER_HEATER = "steamEquipmentSuperHeater"
            const val STEAM_EQUIPMENT_REHEATER = "steamEquipmentReheater"
            const val STEAM_EQUIPMENT_ECONOMIZER = "steamEquipmentEconomizer"

            // Visual Inspection - Boiler Details
            const val BOILER_DETAILS_GRATE = "boilerDetailsGrate"
            const val BOILER_DETAILS_BURNER = "boilerDetailsBurner"
            const val BOILER_DETAILS_FDF = "boilerDetailsFdf"
            const val BOILER_DETAILS_IDF = "boilerDetailsIdf"
            const val BOILER_DETAILS_AIR_HEATER = "boilerDetailsAirHeater"
            const val BOILER_DETAILS_AIR_DUCT = "boilerDetailsAirDuct"
            const val BOILER_DETAILS_GAS_DUCT = "boilerDetailsGasDuct"
            const val BOILER_DETAILS_ASH_CATCHER = "boilerDetailsAshCatcher"
            const val BOILER_DETAILS_CHIMNEY = "boilerDetailsChimney"
            const val BOILER_DETAILS_STAIRS = "boilerDetailsStairs"
            const val BOILER_DETAILS_INSULATION = "boilerDetailsInsulation"

            // Visual Inspection - Safety Devices
            const val SAFETY_VALVE_RING = "safetyValveRing"
            const val SAFETY_VALVE_PIPE = "safetyValvePipe"
            const val SAFETY_VALVE_EXHAUST = "safetyValveExhaust"
            const val PRESSURE_GAUGE_MARK = "pressureGaugeMark"
            const val PRESSURE_GAUGE_SIPHON = "pressureGaugeSiphon"
            const val PRESSURE_GAUGE_COCK = "pressureGaugeCock"
            const val GAUGE_GLASS_TRY_COCKS = "gaugeGlassTryCocks"
            const val GAUGE_GLASS_BLOWDOWN = "gaugeGlassBlowdown"
            const val WATER_LEVEL_LOWEST_MARK = "waterLevelLowestMark"
            const val WATER_LEVEL_POSITION = "waterLevelPosition"
            const val FEEDWATER_PUMP = "feedwaterPump"
            const val FEEDWATER_CAPACITY = "feedwaterCapacity"
            const val FEEDWATER_MOTOR = "feedwaterMotor"
            const val FEEDWATER_CHECK_VALVE = "feedwaterCheckValve"
            const val CONTROL_BLACKS_FLUIT = "controlBlacksFluit"
            const val CONTROL_FUSIBLE_PLUG = "controlFusiblePlug"
            const val CONTROL_WATER_LEVEL = "controlWaterLevel"
            const val CONTROL_STEAM_PRESSURE = "controlSteamPressure"
            const val BLOWDOWN_DESC = "blowdownDesc"
            const val BLOWDOWN_MATERIAL = "blowdownMaterial"
            const val MANHOLE_MANHOLE = "manholeManhole"
            const val MANHOLE_INSPECTION_HOLE = "manholeInspectionHole"
            const val ID_MARK_NAMEPLATE = "idMarkNameplate"
            const val ID_MARK_DATA_MATCH = "idMarkDataMatch"
            const val ID_MARK_FORM_9_STAMP = "idMarkForm9Stamp"

            // Material Thickness
            const val THICKNESS_SHELL_THICKNESS_MM = "shellThicknessMm"
            const val THICKNESS_SHELL_DIAMETER_MM = "shellDiameterMm"
            const val THICKNESS_HEADER_DIAMETER_MM = "headerDiameterMm"
            const val THICKNESS_HEADER_THICKNESS_MM = "headerThicknessMm"
            const val THICKNESS_HEADER_LENGTH_MM = "headerLengthMm"
            const val THICKNESS_FURNACE1_DIAMETER_MM = "furnace1DiameterMm"
            const val THICKNESS_FURNACE1_THICKNESS_MM = "furnace1ThicknessMm"
            const val THICKNESS_FURNACE1_LENGTH_MM = "furnace1LengthMm"
            const val THICKNESS_FURNACE2_DIAMETER_MM = "furnace2DiameterMm"
            const val THICKNESS_FURNACE2_THICKNESS_MM = "furnace2ThicknessMm"
            const val THICKNESS_FURNACE2_LENGTH_MM = "furnace2LengthMm"

            // Thickness Measurement Setup
            const val SETUP_OWNER = "owner"
            const val SETUP_INSPECTION_DATE = "inspectionDate"
            const val SETUP_PROJECT = "project"
            const val SETUP_OBJECT_TYPE = "objectType"
            const val SETUP_WORK_ORDER_NO = "workOrderNo"
            const val SETUP_EQUIPMENT_USED = "equipmentUsed"
            const val SETUP_METHOD_USED = "methodUsed"
            const val SETUP_PROBE_TYPE = "probeType"
            const val SETUP_MATERIAL_TYPE = "materialType"
            const val SETUP_PROBE_STYLE = "probeStyle"
            const val SETUP_OPERATING_TEMP = "operatingTemp"
            const val SETUP_SURFACE_CONDITION = "surfaceCondition"
            const val SETUP_WELDING_PROCESS = "weldingProcess"
            const val SETUP_LAMINATING_CHECK = "laminatingCheck"
            const val SETUP_COUPLANT_USED = "couplantUsed"

            // Measurement Results
            const val MEASUREMENT_NOMINAL_MM = "nominalMm"
            const val MEASUREMENT_POINT_1 = "point1"
            const val MEASUREMENT_POINT_2 = "point2"
            const val MEASUREMENT_POINT_3 = "point3"
            const val MEASUREMENT_MINIMUM = "minimum"
            const val MEASUREMENT_MAXIMUM = "maximum"

            // NDT Tests
            const val NDT_SHELL_METHOD = "shell_method"
            const val NDT_SHELL_LONGITUDINAL = "shell_longitudinal"
            const val NDT_SHELL_CIRCUMFERENTIAL = "shell_circumferential"
            const val NDT_FURNACE_METHOD = "furnace_method"
            const val NDT_FURNACE_LONGITUDINAL = "furnace_longitudinal"
            const val NDT_FURNACE_CIRCUMFERENTIAL = "furnace_circumferential"
            const val NDT_FIRE_TUBES_METHOD = "fireTubes_method"
            const val NDT_FIRE_TUBES_FRONT = "fireTubes_front"
            const val NDT_FIRE_TUBES_REAR = "fireTubes_rear"

            // Hydrotest
            const val HYDROTEST_TEST_PRESSURE_KGCM2 = "testPressureKgCm2"
            const val HYDROTEST_MAWP_KGCM2 = "mawpKgCm2"
            const val HYDROTEST_TEST_MEDIUM = "testMedium"
            const val HYDROTEST_TEST_DATE = "testDate"
            const val HYDROTEST_TEST_RESULT = "testResult"

            // Appendages
            const val APPENDAGE_PRESSURE_GAUGE = "pressureGauge"
            const val APPENDAGE_MANHOLE = "manHole"
            const val APPENDAGE_SAFETY_VALVE = "safetyValve"
            const val APPENDAGE_MAIN_STEAM_VALVE = "mainSteamValve"
            const val APPENDAGE_LEVEL_GLASS_INDICATOR = "levelGlassIndicator"
            const val APPENDAGE_BLOWDOWN_VALVE = "blowdownValve"
            const val APPENDAGE_FEEDWATER_STOP_VALVE = "feedwaterStopValve"
            const val APPENDAGE_FEEDWATER_INLET_VALVE = "feedwaterInletValve"
            const val APPENDAGE_STEAM_DRIER = "steamDrier"
            const val APPENDAGE_WATER_PUMP = "waterPump"
            const val APPENDAGE_CONTROL_PANEL = "controlPanel"
            const val APPENDAGE_NAMEPLATE = "nameplate"

            // Safety Valve Test
            const val VALVE_TEST_HEADER = "header"
            const val VALVE_TEST_STARTS_TO_OPEN_KGCM2 = "startsToOpenKgCm2"
            const val VALVE_TEST_VALVE_INFO = "valveInfo"
        }
    }
}
