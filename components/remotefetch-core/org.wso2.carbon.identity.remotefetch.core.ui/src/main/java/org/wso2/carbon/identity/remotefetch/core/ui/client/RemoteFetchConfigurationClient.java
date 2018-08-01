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

package org.wso2.carbon.identity.remotefetch.core.ui.client;

import com.google.gson.Gson;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.remotefetch.common.RemoteFetchConfiguration;
import org.wso2.carbon.identity.remotefetch.common.exceptions.RemoteFetchCoreException;
import org.wso2.carbon.identity.remotefetch.common.ValidationReport;
import org.wso2.carbon.identity.remotefetch.core.ui.dto.RemoteFetchConfigurationRowDTO;
import org.wso2.carbon.identity.remotefetch.core.ui.internal.RemotefetchCoreUIComponentDataHolder;

import java.util.List;
import java.util.stream.Collectors;

public class RemoteFetchConfigurationClient {

    public static List<RemoteFetchConfigurationRowDTO> getConfigurations() throws RemoteFetchCoreException {

        int tenant_id = CarbonContext.getThreadLocalCarbonContext().getTenantId();

        List<RemoteFetchConfiguration> fetchConfigurations = RemotefetchCoreUIComponentDataHolder
                .getInstance().getRemoteFetchConfigurationService().getRemoteFetchConfigurationList(tenant_id);

        return fetchConfigurations.stream().map((fetchConfiguration ->
                RemoteFetchConfigurationClient.fetchConfigurationToDTO(fetchConfiguration)
        )).collect(Collectors.toList());
    }

    public static RemoteFetchConfigurationRowDTO fetchConfigurationToDTO(RemoteFetchConfiguration fetchConfiguration) {

        String repositoryManager = RemotefetchCoreUIComponentDataHolder.getInstance().getComponentRegistry().
                getRepositoryManagerComponent(fetchConfiguration.getRepositoryManagerType()).getName();

        String actionListener = RemotefetchCoreUIComponentDataHolder.getInstance().getComponentRegistry().
                getActionListenerComponent(fetchConfiguration.getActionListenerType()).getName();

        String configurationDeployer = RemotefetchCoreUIComponentDataHolder.getInstance().getComponentRegistry().
                getConfigDeployerComponent(fetchConfiguration.getConfigurationDeployerType()).getName();

        return new RemoteFetchConfigurationRowDTO(
                fetchConfiguration.getRemoteFetchConfigurationId(),
                fetchConfiguration.isEnabled(),
                repositoryManager,
                actionListener,
                configurationDeployer,
                fetchConfiguration.getUserName()
        );
    }

    public static RemoteFetchConfiguration getRemoteFetchConfiguration(int id) throws RemoteFetchCoreException{
        return RemotefetchCoreUIComponentDataHolder.getInstance().getRemoteFetchConfigurationService()
                .getRemoteFetchConfiguration(id);
    }

    public static ValidationReport addFetchConfiguration(String jsonObject, String currentUser)
            throws RemoteFetchCoreException {

        RemoteFetchConfiguration fetchConfiguration =
                RemoteFetchConfigurationClient.parseJsonToConfiguration(jsonObject);

        fetchConfiguration.setUserName(currentUser);

        return RemotefetchCoreUIComponentDataHolder.getInstance()
                .getRemoteFetchConfigurationService()
                .addRemoteFetchConfiguration(fetchConfiguration);
    }

    public static ValidationReport updateFetchConfiguration(String jsonObject, String currentUser)
            throws RemoteFetchCoreException {

        RemoteFetchConfiguration fetchConfiguration =
                RemoteFetchConfigurationClient.parseJsonToConfiguration(jsonObject);

        fetchConfiguration.setUserName(currentUser);

        return RemotefetchCoreUIComponentDataHolder.getInstance()
                .getRemoteFetchConfigurationService()
                .updateRemoteFetchConfiguration(fetchConfiguration);
    }

    public static void deleteRemoteFetchComponent(int id) throws RemoteFetchCoreException {
        RemotefetchCoreUIComponentDataHolder.getInstance().getRemoteFetchConfigurationService()
                .deleteRemoteFetchConfiguration(id);
    }

    private static RemoteFetchConfiguration parseJsonToConfiguration(String jsonObject){
        Gson gson = new Gson();
        RemoteFetchConfiguration fetchConfiguration = gson.fromJson(jsonObject, RemoteFetchConfiguration.class);
        fetchConfiguration.setTenantId(CarbonContext.getThreadLocalCarbonContext().getTenantId());
        return fetchConfiguration;
    }


}
