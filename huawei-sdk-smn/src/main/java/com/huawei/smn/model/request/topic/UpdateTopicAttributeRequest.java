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
package com.huawei.smn.model.request.topic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.smn.common.SmnConstants;
import com.huawei.smn.common.utils.JsonUtil;
import com.huawei.smn.model.AbstractSmnRequest;

/**
 * Update topic's access attribute
 * 
 * @author huangqiong
 *
 */
public class UpdateTopicAttributeRequest extends AbstractSmnRequest {

    private static Logger logger = LoggerFactory.getLogger(UpdateTopicAttributeRequest.class);
    /**
     * attribute name
     */
    private String attributesName;
    /**
     * topic's unique resource identifier
     */
    private String topicUrn;
    /**
     * acess policy
     */
    private LinkedHashMap<String, Object> acessPolicy = null;

    // construct url address
    public String getRequestUrl() throws RuntimeException {
        if (Objects.isNull(getAuthenticationBean()) || StringUtils.isBlank(getAuthenticationBean().getProjectId())) {
            logger.error("project id is null");
            throw new RuntimeException();
        }
        if (StringUtils.isBlank(getTopicUrn())) {
            logger.error("getTopicUrn() is null");
            throw new RuntimeException();
        }
        if (StringUtils.isBlank(getAttributesName()) || !isValidAttributeName(getAttributesName())) {
            logger.error("Attributte name is null or is not valid");
            throw new RuntimeException();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(SmnConstants.SMN_HOST_NAME).append(SmnConstants.URL_DELIMITER).append(SmnConstants.V2_VERSION)
                .append(SmnConstants.URL_DELIMITER).append(getAuthenticationBean().getProjectId())
                .append(SmnConstants.SMN_TOPIC_URI).append(SmnConstants.URL_DELIMITER).append(getTopicUrn())
                .append("/attributes").append(SmnConstants.URL_DELIMITER).append(getAttributesName());

        logger.info("Request url is: " + sb.toString());
        return sb.toString();
    }

    @Override
    public Map<String, Object> getRequestParameterMap() {
        Map<String, Object> requestParameterMap = new HashMap<String, Object>();
        requestParameterMap.put("value", JsonUtil.getJsonStringByMap(getAcessPolicy()));
        return requestParameterMap;
    }

    private boolean isValidAttributeName(String attributeName) {
        if ("access_policy".equals(attributeName) || "introduction".equals(attributeName)) {
            return true;
        }
        return false;
    }

    /**
     * @return the attributesName
     */
    public String getAttributesName() {
        return attributesName;
    }

    /**
     * @param attributesName
     *            the attributesName to set
     */
    public void setAttributesName(String attributesName) {
        this.attributesName = attributesName;
    }

    /**
     * @return the topicUrn
     */
    public String getTopicUrn() {
        return topicUrn;
    }

    /**
     * @param topicUrn
     *            the topicUrn to set
     */
    public void setTopicUrn(String topicUrn) {
        this.topicUrn = topicUrn;
    }

    /**
     * @return the acessPolicy
     */
    public LinkedHashMap<String, Object> getAcessPolicy() {
        return acessPolicy;
    }

    /**
     * @param acessPolicy
     *            the acessPolicy to set
     */
    public void setAcessPolicy(LinkedHashMap<String, Object> acessPolicy) {
        this.acessPolicy = acessPolicy;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
