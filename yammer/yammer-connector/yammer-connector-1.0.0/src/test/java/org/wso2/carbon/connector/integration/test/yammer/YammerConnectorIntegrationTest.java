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

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.util.HashMap;
import java.util.Map;


public class YammerConnectorIntegrationTest extends ConnectorIntegrationTestBase {

    protected static final String CONNECTOR_NAME = "yammer-connector-1.0.0";
    private String validAuthorization;
    private String invalidAuthorization;


    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> parametersMap = new HashMap<String, String>();

    @BeforeTest(alwaysRun = true)
    protected void init() throws Exception {
        super.init(CONNECTOR_NAME);

        esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
        esbRequestHeadersMap.put("Content-Type", "application/json");

        apiRequestHeadersMap.put("Authorization","Bearer " + connectorProperties.getProperty("token"));
        apiRequestHeadersMap.put("Accept-Charset", "UTF-8");
        apiRequestHeadersMap.put("Content-Type", "application/json");
    }


    @Override
    protected void cleanup() {
        axis2Client.destroy();
    }

    private String addCredentials(String jsonString) {
        return String.format(
                jsonString,
                connectorProperties.getProperty("token"),
                connectorProperties.getProperty("Apiurl"));
    }


    /*Mandatory Param Test Cases */


    @Test(priority = 1, groups = {"wso2.esb"}, description = "yammer {getFollowing} integration test with mandatory parameters")
    public void testYammerGetFollowingWithMandatory() throws Exception {

        esbRequestHeadersMap.put("Action", "urn:getFollowing");
        String apiEndPoint =  connectorProperties.getProperty("Apiurl") + "/v1/messages/following.json";

        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,"TokenAndUrl.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getBody().get("threaded_extended").toString(), apiRestResponse.getBody().get("threaded_extended").toString());
    }

    @Test(priority = 1, groups = {"wso2.esb"}, description = "yammer {getMessages} integration test with mandatory parameters")
    public void testYammerGetMessagesWithMandatory() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getMessages");
        String apiEndPoint =  connectorProperties.getProperty("Apiurl") + "/v1/messages.json";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,"TokenAndUrl.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getBody().get("threaded_extended").toString(), apiRestResponse.getBody().get("threaded_extended").toString());
    }


    /*Optional Param Test Cases */


    @Test(priority = 1, groups = {"wso2.esb"}, description = "yammer {getMessages} integration test with optional parameters")
    public void testYammerGetMessagesWithOptional() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getMessages");
        String apiEndPoint =  connectorProperties.getProperty("Apiurl") + "/v1/messages.json";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,"GetMessagesOptional.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getBody().get("threaded_extended").toString(), apiRestResponse.getBody().get("threaded_extended").toString());

    }


    /*Negative Param Test Cases*/

    @Test(priority = 3, groups = {"wso2.esb"}, description = "yammer {getMessages} integration test with negative parameters")
    public void testYammerGetMessagesWithNegative() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getMessages");
        String apiEndPoint =  connectorProperties.getProperty("Apiurl") + "/v1/messages.json?older_than=418ssdsd830394";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,"GetMessagesNegative.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode() , apiRestResponse.getHttpStatusCode());
    }


}

