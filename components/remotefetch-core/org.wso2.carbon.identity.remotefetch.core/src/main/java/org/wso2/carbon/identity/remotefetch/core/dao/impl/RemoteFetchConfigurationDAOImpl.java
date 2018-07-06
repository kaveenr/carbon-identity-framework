/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.remotefetch.core.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.remotefetch.common.exceptions.RemoteFetchCoreException;
import org.wso2.carbon.identity.remotefetch.core.RemoteFetchConfiguration;
import org.wso2.carbon.identity.remotefetch.core.dao.RemoteFetchConfigurationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONObject;

/**
 * this class accesses IDN_RF_CONFIG table to store/update and delete Remote Fetch configurations.
 */
public class RemoteFetchConfigurationDAOImpl implements RemoteFetchConfigurationDAO {

    public static final String CREATE_CONFIG = "INSERT IDN_RF_CONFIG (TENANT_ID, REPO_CONNECTOR_TYPE, ACTION_LISTENER_TYPE, " +
            "CONFIG_DEPLOYER_TYPE, ATTRIBUTES_JSON) VALUES (?,?,?,?,?)";

    private Log log = LogFactory.getLog(RemoteFetchConfigurationDAOImpl.class);

    public RemoteFetchConfigurationDAOImpl() {

    }

    /**
     * @param configuration
     * @param tenantId
     * @return
     * @throws RemoteFetchCoreException
     */
    @Override
    public int createRemoteFetchConfiguration(RemoteFetchConfiguration configuration, int tenantId) throws RemoteFetchCoreException {
        Connection connection = IdentityDatabaseUtil.getDBConnection();
        PreparedStatement addStmnt = null;
        ResultSet result = null;
        try {
            addStmnt = connection.prepareStatement(RemoteFetchConfigurationDAOImpl.CREATE_CONFIG);
            addStmnt.setInt(1,tenantId);
            addStmnt.setString(2,configuration.getRepositoryConnectorType());
            addStmnt.setString(3,configuration.getActionListenerType());
            addStmnt.setString(4,configuration.getConfgiurationDeployerType());
            //Encode object attributes to JSON
            JSONObject attributesBundle = new JSONObject();
            attributesBundle.put("repositoryConnectorAttributes",configuration.getRepositoryConnectorAttributes());
            attributesBundle.put("actionListenerAttributes",configuration.getActionListenerAttributes());
            attributesBundle.put("confgiurationDeployerAttributes",configuration.getConfgiurationDeployerAttributes());
            attributesBundle.put("deploymentDetails",configuration.getDeploymentDetails());

            addStmnt.setString(5,attributesBundle.toString(4));
            addStmnt.execute();

            int configId = -1;
            result = addStmnt.getGeneratedKeys();

            // TODO if no ID SELECT and return id

            if (!connection.getAutoCommit()) {
                connection.commit();
            }

            return configId;

        } catch (SQLException e){
            throw new RemoteFetchCoreException("Error creating new object",e);
        } finally {
            IdentityDatabaseUtil.closeResultSet(result);
            IdentityDatabaseUtil.closeStatement(addStmnt);
            IdentityDatabaseUtil.closeConnection(connection);

        }

    }

    /**
     * @param configurationId
     * @return
     * @throws RemoteFetchCoreException
     */
    @Override
    public RemoteFetchConfiguration getRemoteFetchConfiguration(int configurationId) throws RemoteFetchCoreException {

        return null;
    }

    /**
     * @param configuration
     * @param tenantId
     * @throws RemoteFetchCoreException
     */
    @Override
    public void updateRemoteFetchConfiguration(RemoteFetchConfiguration configuration, int tenantId) throws RemoteFetchCoreException {

    }

    /**
     * @param configurationId
     * @throws RemoteFetchCoreException
     */
    @Override
    public void deleteRemoteFetchConfiguration(int configurationId) throws RemoteFetchCoreException {

    }

    /**
     * @param tenantId
     * @return
     * @throws RemoteFetchCoreException
     */
    @Override
    public List<RemoteFetchConfiguration> getRemoteFetchConfigurationsByTenantId(int tenantId) throws RemoteFetchCoreException {

        return null;
    }
}
