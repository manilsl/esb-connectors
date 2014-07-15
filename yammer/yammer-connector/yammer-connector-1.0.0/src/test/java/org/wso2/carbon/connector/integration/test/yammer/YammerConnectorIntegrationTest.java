/**
 *  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.integration.test.yammer;

import org.apache.axis2.context.ConfigurationContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.api.clients.proxy.admin.ProxyServiceAdminClient;
import org.wso2.carbon.automation.api.clients.utils.AuthenticateStub;
import org.wso2.carbon.automation.utils.axis2client.ConfigurationContextProvider;

import org.wso2.carbon.esb.ESBIntegrationTest;
import org.wso2.carbon.mediation.library.stub.MediationLibraryAdminServiceStub;
import org.wso2.carbon.mediation.library.stub.upload.MediationLibraryUploaderStub;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;


import javax.activation.DataHandler;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class YammerConnectorIntegrationTest extends ConnectorIntegrationTestBase {



    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        init("yammer-connector-1.0.0");
    }

    private String addCredentials(String jsonString) {
        return String.format(
                jsonString,
                connectorProperties.getProperty("yammer_token"),
                connectorProperties.getProperty("Apiurl"));
    }


    /**
     * Oauth Positive test case for postGetAll method with mandatory parameters.
     */
    @Test(priority = 1, groups = {"wso2.esb"}, description = "yammer {getFollowing} integration test with mandatory parameters")
    public void testYammerGetFollowingWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "GetFollowing.txt";

        String rawString = getFileContent(jsonRequestFilePath);
        rawString = rawString.replace("clientId",connectorProperties.getProperty("client_id"));
        rawString = rawString.replace("clientSecret",connectorProperties.getProperty("client_secret"));
        final String jsonString = addCredentials(rawString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC = responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD = responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl") + "/v1/posts/all", "", validAuthorization);

        Assert.assertTrue(omElementC.getFirstElement().toString().equals(omElementD.getFirstElement().toString()));
    }



}

