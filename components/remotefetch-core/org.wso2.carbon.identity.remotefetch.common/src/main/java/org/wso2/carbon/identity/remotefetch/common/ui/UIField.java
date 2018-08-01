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

package org.wso2.carbon.identity.remotefetch.common.ui;

import java.util.Arrays;
import java.util.List;

public class UIField {
    public enum FIELD_TYPES {
        TEXT_BOX,
    }
    private String id;
    private FIELD_TYPES type;
    private String displayName;
    private String helpText;
    private String validationRegex;
    private List<String> defaultValues;
    private boolean isMandatory;
    private boolean isSensitive;
    private boolean isMultiValue;

    public UIField(String id, FIELD_TYPES type, String displayName, String helpText, String validationRegex,
                   List<String> defaultValues, boolean isMandatory, boolean isSensitive, boolean isMultiValue) {

        this.id = id;
        this.type = type;
        this.displayName = displayName;
        this.helpText = helpText;
        this.validationRegex = validationRegex;
        this.defaultValues = defaultValues;
        this.isMandatory = isMandatory;
        this.isSensitive = isSensitive;
        this.isMultiValue = isMultiValue;
    }

    public UIField(String id, FIELD_TYPES type, String displayName, String helpText, String validationRegex,
                   String defaultValue, boolean isMandatory, boolean isSensitive, boolean isMultiValue) {

        this.id = id;
        this.type = type;
        this.displayName = displayName;
        this.helpText = helpText;
        this.validationRegex = validationRegex;
        String[] value = {defaultValue};
        this.defaultValues = Arrays.asList(value);
        this.isMandatory = isMandatory;
        this.isSensitive = isSensitive;
        this.isMultiValue = isMultiValue;
    }

    public String getId() {

        return id;
    }

    public FIELD_TYPES getType() {

        return type;
    }

    public String getDisplayName() {

        return displayName;
    }

    public String getHelpText() {

        return helpText;
    }

    public String getValidationRegex() {

        return validationRegex;
    }

    public List<String> getDefaultValues() {

        return defaultValues;
    }

    public boolean isMandatory() {

        return isMandatory;
    }

    public boolean isSensitive() {

        return isSensitive;
    }

    public boolean isMultiValue() {

        return isMultiValue;
    }

    @Override
    public String toString() {

        return "UIField{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", displayName='" + displayName + '\'' +
                ", helpText='" + helpText + '\'' +
                ", validationRegex='" + validationRegex + '\'' +
                ", defaultValues=" + defaultValues +
                ", isMandatory=" + isMandatory +
                ", isSensitive=" + isSensitive +
                ", isMultiValue=" + isMultiValue +
                '}';
    }
}
