/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.huawei.smn.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.smn.common.utils.HttpUtil;
import com.huawei.smn.model.SmnRequest;
import com.huawei.smn.service.SmsService;

/**
 * SMS service implemented
 * 
 * @author huangqiong
 *
 * @date 2017年8月2日
 *
 * @version 0.1
 */
public class SmsServiceImpl implements SmsService {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

    /**
     * send sms directly
     * 
     * @param smnRequest
     * @return
     * @throws RuntimeException
     */
    @Override
    public Map<String, Object> smsPublish(SmnRequest smnRequest) throws RuntimeException {
        try {
            Map<String, String> requestHeader = smnRequest.getRequestHeaderMap();
            Map<String, Object> requestParam = smnRequest.getRequestParameterMap();
            Map<String, Object> responseMap = HttpUtil.post(requestHeader, requestParam, smnRequest.getRequestUrl());
            return responseMap;
        } catch (RuntimeException e) {
            LOGGER.error("Failed to send sms.", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Failed to send sms.", e);
            throw new RuntimeException("Failed to send sms.", e);
        }
    }

}
