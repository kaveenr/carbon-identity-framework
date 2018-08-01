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

import org.wso2.carbon.identity.remotefetch.common.ValidationReport;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIFieldValidator {
    public static ValidationReport validate(Map<String,String> attributes, List<UIField> fields){
        ValidationReport validationReport = new ValidationReport();
        for (UIField field : fields){

            String input_value = attributes.getOrDefault(field.getId(),null);

            if (input_value == null){
                // Check if mandatory value is present
                if(field.isMandatory()){
                    validationReport.addMessage("%s is a mandatory value",field.getDisplayName());
                }
                continue;
            }else{
                if (!UIFieldValidator.doesPatternMatch(field.getValidationRegex(),input_value)){
                    validationReport.addMessage("%s does not match pattern",field.getDisplayName());
                }
            }

        }
        if(validationReport.getMessages().size() == 0){
            validationReport.setValidationStatus(ValidationReport.VALIDATION_STATUS.PASSED);
        }else{
            validationReport.setValidationStatus(ValidationReport.VALIDATION_STATUS.FAILED);
        }
        return validationReport;
    }

    private static boolean doesPatternMatch(String pattern, String value){

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(value);

        return matcher.find();
    }
}
