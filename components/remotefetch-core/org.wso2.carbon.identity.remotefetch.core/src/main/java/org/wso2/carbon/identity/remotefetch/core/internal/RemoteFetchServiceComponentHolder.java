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
import org.wso2.carbon.identity.remotefetch.common.RemoteFetchComponentRegistery;
import org.wso2.carbon.identity.remotefetch.core.RemoteFetchCore;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RemoteFetchServiceComponentHolder {
    private static RemoteFetchServiceComponentHolder instance = new RemoteFetchServiceComponentHolder();
    private RemoteFetchComponentRegistery remoteFetchComponentRegistery;

    public static RemoteFetchServiceComponentHolder getInstance() {

        return instance;
    }

    public RemoteFetchComponentRegistery getRemoteFetchComponentRegistery() {

        return remoteFetchComponentRegistery;
    }

    public void setRemoteFetchComponentRegistery(RemoteFetchComponentRegistery remoteFetchComponentRegistery) {

        this.remoteFetchComponentRegistery = remoteFetchComponentRegistery;
    }
}
