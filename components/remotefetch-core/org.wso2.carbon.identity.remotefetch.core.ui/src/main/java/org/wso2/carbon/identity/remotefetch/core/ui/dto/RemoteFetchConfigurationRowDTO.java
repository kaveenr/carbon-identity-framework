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

package org.wso2.carbon.identity.remotefetch.core.ui.dto;

public class RemoteFetchConfigurationRowDTO {

    private int id;
    private boolean isEnabled;
    private String repositoryType;
    private String actionListnerType;
    private String configarationDeployerType;
    private String userName;

    public RemoteFetchConfigurationRowDTO(int id, boolean isEnabled, String repositoryType,
                                          String actionListnerType, String configarationDeployerType, String userName) {

        this.id = id;
        this.isEnabled = isEnabled;
        this.repositoryType = repositoryType;
        this.actionListnerType = actionListnerType;
        this.configarationDeployerType = configarationDeployerType;
        this.userName = userName;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public boolean getIsEnabled() {

        return isEnabled;
    }

    public void setEnabled(boolean enabled) {

        isEnabled = enabled;
    }

    public String getRepositoryType() {

        return repositoryType;
    }

    public void setRepositoryType(String repositoryType) {

        this.repositoryType = repositoryType;
    }

    public String getActionListnerType() {

        return actionListnerType;
    }

    public void setActionListnerType(String actionListnerType) {

        this.actionListnerType = actionListnerType;
    }

    public String getConfigarationDeployerType() {

        return configarationDeployerType;
    }

    public void setConfigarationDeployerType(String configarationDeployerType) {

        this.configarationDeployerType = configarationDeployerType;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    @Override
    public String toString() {

        return "RemoteFetchConfigurationRowDTO{" +
                "id=" + id +
                ", isEnabled=" + isEnabled +
                ", repositoryType='" + repositoryType + '\'' +
                ", actionListnerType='" + actionListnerType + '\'' +
                ", configarationDeployerType='" + configarationDeployerType + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
