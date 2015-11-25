package com.qait.tests;

import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import com.qait.automation.utils.YamlReader;

public class ReinstateFactoryClass {

	
    @Factory(dataProvider = "yamldataprovider")
    public Object[] factoryMethod(Map<String, Object> usereInfoMap) {
        return new Object[]{new ACS_Reinstate_Member_EWEB_Test(usereInfoMap)};
    }

    @DataProvider(name = "yamldataprovider", parallel = true)
    public Object[][] dataprovider() {
    YamlReader.setYamlFilePath();
        int userMapSize = YamlReader.getYamlValues("reinstateUsers").size();
        Object[][] returnObj = new Object[userMapSize][1];
        for (int i = 1; i <= userMapSize; i++) {
            Map<String, Object> userInfo = YamlReader.getYamlValues("reinstateUsers.user" + i);
            returnObj[i - 1][0] = userInfo;
        }
        return returnObj;
    }
}
