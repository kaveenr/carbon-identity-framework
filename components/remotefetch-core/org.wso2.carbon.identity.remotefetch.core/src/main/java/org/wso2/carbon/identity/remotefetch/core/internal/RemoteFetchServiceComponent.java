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

package org.wso2.carbon.identity.remotefetch.core.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.wso2.carbon.identity.remotefetch.core.RemoteFetchCore;

@Component(
        name = "identity.application.remotefetch.component",
        immediate = true
)
public class RemoteFetchServiceComponent {
    private static Log log = LogFactory.getLog(RemoteFetchServiceComponent.class);
    RemoteFetchCore core = new RemoteFetchCore();
    Thread remoteFetchCoreThread;

    @Activate
    protected void activate(ComponentContext context) {
        try {
            this.remoteFetchCoreThread = new Thread(this.core);
            log.info("**********################************########");
            remoteFetchCoreThread.start();
            if (log.isDebugEnabled()) {
                log.debug("Identity RemoteFetchServiceComponent bundle is activated");
            }
        } catch (Exception e) {
            log.error("Error while activating RemoteFetchServiceComponent bundle", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

        try {
            this.remoteFetchCoreThread.join();
        } catch (InterruptedException e) {
            log.error("Error stopping main RemoteFetchCore thread",e);
        }
        if (log.isDebugEnabled()) {
            log.debug("Identity RemoteFetchServiceComponent bundle is deactivated");
        }
    }
}
