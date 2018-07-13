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

package org.wso2.carbon.identity.remotefetch.common;

import java.io.File;
import java.util.Date;

public class FileRevision {
    private int fileRevisionId;
    private int configId;
    private File file;
    private String fileHash;
    private Date deployedDate;
    private String deploymentStatus;
    private String itemType;
    private String itemName;

    public FileRevision(int configId, File file) {

        this.configId = configId;
        this.file = file;
    }

    public int getFileRevisionId() {

        return fileRevisionId;
    }

    public void setFileRevisionId(int fileRevisionId) {

        this.fileRevisionId = fileRevisionId;
    }

    public int getConfigId() {

        return configId;
    }

    public void setConfigId(int configId) {

        this.configId = configId;
    }

    public File getFile() {

        return file;
    }

    public void setFile(File file) {

        this.file = file;
    }

    public String getFileHash() {

        return fileHash;
    }

    public void setFileHash(String fileHash) {

        this.fileHash = fileHash;
    }

    public Date getDeployedDate() {

        return deployedDate;
    }

    public void setDeployedDate(Date deployedDate) {

        this.deployedDate = deployedDate;
    }

    public String getDeploymentStatus() {

        return deploymentStatus;
    }

    public void setDeploymentStatus(String deploymentStatus) {

        this.deploymentStatus = deploymentStatus;
    }

    public String getItemType() {

        return itemType;
    }

    public void setItemType(String itemType) {

        this.itemType = itemType;
    }

    public String getItemName() {

        return itemName;
    }

    public void setItemName(String itemName) {

        this.itemName = itemName;
    }
}
