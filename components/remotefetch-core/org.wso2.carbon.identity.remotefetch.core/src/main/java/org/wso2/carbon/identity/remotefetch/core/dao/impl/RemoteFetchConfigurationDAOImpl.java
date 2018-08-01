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

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.TransactionException;
import org.wso2.carbon.identity.remotefetch.common.exceptions.RemoteFetchCoreException;
import org.wso2.carbon.identity.remotefetch.common.RemoteFetchConfiguration;
import org.wso2.carbon.identity.remotefetch.core.constants.SQLConstants;
import org.wso2.carbon.identity.remotefetch.core.dao.RemoteFetchConfigurationDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.wso2.carbon.identity.remotefetch.core.util.JdbcUtils;

/**
 * this class accesses IDN_REMOTE_FETCH_CONFIG table to store/update and delete Remote Fetch configurations.
 */
public class RemoteFetchConfigurationDAOImpl implements RemoteFetchConfigurationDAO {

    /**
     * @param configuration
     * @return
     * @throws RemoteFetchCoreException
     */
    @Override
    public int createRemoteFetchConfiguration(RemoteFetchConfiguration configuration) throws RemoteFetchCoreException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            return jdbcTemplate.withTransaction(template ->
                    template.executeInsert(SQLConstants.CREATE_CONFIG,
                            preparedStatement -> this.configurationToPreparedStatement(preparedStatement, configuration)
                            , configuration, true)
            );
        } catch (TransactionException e) {
            throw new RemoteFetchCoreException("Error creating new RemoteFetchConfiguration, caused by "
                    + e.getMessage() + " for user " + configuration.getUserName(), e);
        }
    }

    /**
     * @param configurationId
     * @return
     * @throws RemoteFetchCoreException
     */
    @Override
    public RemoteFetchConfiguration getRemoteFetchConfiguration(int configurationId) throws RemoteFetchCoreException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            return jdbcTemplate.withTransaction(template ->
                    jdbcTemplate.fetchSingleRecord(SQLConstants.GET_CONFIG,
                            (resultSet, i) -> this.resultSetToConfiguration(resultSet),
                            preparedStatement -> preparedStatement.setInt(1, configurationId))
            );
        } catch (TransactionException e) {
            throw new RemoteFetchCoreException("Error reading RemoteFetchConfiguration of id " +
                    configurationId + " from database", e);
        }
    }

    /**
     * @param configuration
     * @throws RemoteFetchCoreException
     */
    @Override
    public void updateRemoteFetchConfiguration(RemoteFetchConfiguration configuration) throws RemoteFetchCoreException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            jdbcTemplate.withTransaction(template -> {
                jdbcTemplate.executeUpdate(SQLConstants.UPDATE_CONFIG,
                        preparedStatement -> {
                            this.configurationToPreparedStatement(preparedStatement, configuration);
                            preparedStatement.setInt(8,configuration.getRemoteFetchConfigurationId());

                        }

                );
                return null;
            });
        } catch (TransactionException e) {
            throw new RemoteFetchCoreException("Error updating RemoteFetchConfiguration of id "
                    + configuration.getRemoteFetchConfigurationId(), e);
        }
    }

    /**
     * @param configurationId
     * @throws RemoteFetchCoreException
     */
    @Override
    public void deleteRemoteFetchConfiguration(int configurationId) throws RemoteFetchCoreException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            jdbcTemplate.withTransaction(template -> {
                template.executeUpdate(SQLConstants.DELETE_CONFIG, preparedStatement ->
                        preparedStatement.setInt(1, configurationId)
                );
                return null;
            });
        } catch (TransactionException e) {
            throw new RemoteFetchCoreException("Error Deleting DeploymentRevision of id " + configurationId, e);
        }
    }

    /**
     * @return
     * @throws RemoteFetchCoreException
     */
    @Override
    public List<RemoteFetchConfiguration> getAllRemoteFetchConfigurations() throws RemoteFetchCoreException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            return jdbcTemplate.withTransaction(template ->
                    template.executeQuery(SQLConstants.LIST_CONFIGS,
                            ((resultSet, i) -> this.resultSetToConfiguration(resultSet)))
            );
        } catch (TransactionException e) {
            throw new RemoteFetchCoreException("Error listing RemoteFetchConfigurations from database", e);
        }
    }

    /**
     * @return
     * @throws RemoteFetchCoreException
     */
    @Override
    public List<RemoteFetchConfiguration> getAllEnabledRemoteFetchConfigurations() throws RemoteFetchCoreException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            return jdbcTemplate.withTransaction(template ->
                    template.executeQuery(SQLConstants.LIST_ENABLED_CONFIGS,
                            ((resultSet, i) -> this.resultSetToConfiguration(resultSet)))
            );
        } catch (TransactionException e) {
            throw new RemoteFetchCoreException("Error listing RemoteFetchConfigurations from database", e);
        }
    }

    /**
     * @param tenant_id
     * @return
     * @throws RemoteFetchCoreException
     */
    @Override
    public List<RemoteFetchConfiguration> getRemoteFetchConfigurationsByTenant(int tenant_id) throws RemoteFetchCoreException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            return jdbcTemplate.withTransaction(template ->
                    template.executeQuery(SQLConstants.LIST_CONFIGS_BY_TENANT,
                            ((resultSet, i) -> this.resultSetToConfiguration(resultSet)),
                            preparedStatement -> preparedStatement.setInt(1, tenant_id))
            );
        } catch (TransactionException e) {
            throw new RemoteFetchCoreException("Error listing RemoteFetchConfigurations from database", e);
        }
    }

    private Map<String, String> attributeToMap(JSONObject attributes) {

        Map<String, String> attrMap = new HashMap<>();
        attributes.keySet().forEach((Object key) -> {
            if (key.getClass() == String.class) {
                attrMap.put((String) key, attributes.getString((String) key));
            }
        });
        return attrMap;
    }

    private RemoteFetchConfiguration resultSetToConfiguration(ResultSet resultSet) throws SQLException {

        RemoteFetchConfiguration remoteFetchConfiguration = new RemoteFetchConfiguration(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getString(3).equals("1"),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7)
        );
        JSONObject attributesBundle = new JSONObject(resultSet.getString(8));
        this.mapAttributes(remoteFetchConfiguration, attributesBundle);
        return remoteFetchConfiguration;
    }

    private void configurationToPreparedStatement(PreparedStatement preparedStatement,
                                                  RemoteFetchConfiguration configuration) throws SQLException {

        preparedStatement.setInt(1, configuration.getTenantId());
        preparedStatement.setString(2, (configuration.isEnabled() ? "1" : "0"));
        preparedStatement.setString(3, configuration.getUserName());
        preparedStatement.setString(4, configuration.getRepositoryManagerType());
        preparedStatement.setString(5, configuration.getActionListenerType());
        preparedStatement.setString(6, configuration.getConfigurationDeployerType());

        //Encode object attributes to JSON
        JSONObject attributesBundle = this.makeAttributeBundle(configuration);

        preparedStatement.setString(7, attributesBundle.toString(4));
    }

    private JSONObject makeAttributeBundle(RemoteFetchConfiguration configuration) {

        JSONObject attributesBundle = new JSONObject();
        attributesBundle.put("repositoryManagerAttributes", configuration.getRepositoryManagerAttributes());
        attributesBundle.put("actionListenerAttributes", configuration.getActionListenerAttributes());
        attributesBundle.put("confgiurationDeployerAttributes", configuration.getConfigurationDeployerAttributes());
        return attributesBundle;
    }

    private void mapAttributes(RemoteFetchConfiguration remoteFetchConfiguration, JSONObject attributesBundle) {

        remoteFetchConfiguration.setRepositoryManagerAttributes(
                this.attributeToMap(attributesBundle.getJSONObject("repositoryManagerAttributes"))
        );
        remoteFetchConfiguration.setActionListenerAttributes(
                this.attributeToMap(attributesBundle.getJSONObject("actionListenerAttributes"))
        );
        remoteFetchConfiguration.setConfigurationDeployerAttributes(
                this.attributeToMap(attributesBundle.getJSONObject("confgiurationDeployerAttributes"))
        );
    }
}
