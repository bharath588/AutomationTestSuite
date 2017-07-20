
package features;

import static libraries.CommonLibrary.siebelAccoutEstablishment;

public  class CamsSiebelAccountEstablishmentForAutoTests {
    public static void CamsSiebelAccountEstablishmentForAutoTestsTest() throws  Exception {

        String testScenariosFileName = "FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests";
        String testScenariosSheetName = "Test_Scenarios";
        siebelAccoutEstablishment(testScenariosFileName, testScenariosSheetName);

    }//End Of Class

}


