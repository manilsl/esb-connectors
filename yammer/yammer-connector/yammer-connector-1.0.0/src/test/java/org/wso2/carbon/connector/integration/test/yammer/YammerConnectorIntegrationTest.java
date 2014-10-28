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

    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> parametersMap = new HashMap<String, String>();

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {

        init("yammer-connector-1.0.0");
        esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
        esbRequestHeadersMap.put("Content-Type", "application/json");

        apiRequestHeadersMap.put("Authorization","Bearer " + connectorProperties.getProperty("token"));
        apiRequestHeadersMap.put("Accept-Charset", "UTF-8");
        apiRequestHeadersMap.put("Content-Type", "application/json");

    }

    private String addCredentials(String jsonString) {
        return String.format(
                jsonString,
                connectorProperties.getProperty("token"),
                connectorProperties.getProperty("Apiurl"));
    }


    @Test(priority = 1, groups = {"wso2.esb"}, description = "yammer {getFollowing} integration test with mandatory parameters")
    public void testYammerGetFollowingWithMandatory() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:getFollowing");
        String apiEndPoint =  connectorProperties.getProperty("Apiurl") + "/v1/messages/following.json";

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,"TokenAndUrl.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getBody().get("threaded_extended").toString(), apiRestResponse.getBody().get("threaded_extended").toString());
    }

/*    @Test(priority = 1, groups = { "wso2.esb" }, description = "Soundcloud getUser")
    public void testSoundcloudGetUserPositive() throws Exception {

        //http://api.soundcloud.com/users/99675972.json?client_id=21fded24c32c2d9b0316971643d2f75f

        esbRequestHeadersMap.put("Action", "urn:getUser");
        String apiEndPoint =  connectorProperties.getProperty("apiUrlHttp") + "/users/" +
                connectorProperties.getProperty("userId")+
                ".json?client_id="+connectorProperties.getProperty("clientId");

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,"esb_userid_mandatory.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getBody().get("id"), apiRestResponse.getBody().get("id"));

    }*/










}

