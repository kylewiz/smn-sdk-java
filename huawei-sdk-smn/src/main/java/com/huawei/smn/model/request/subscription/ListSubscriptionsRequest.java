package com.huawei.smn.model.request.subscription;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.smn.common.SmnConstants;
import com.huawei.smn.model.AbstractSmnRequest;

/**
 * Query subscribtion's list
 * 
 * @author huangqiong
 *
 */
public class ListSubscriptionsRequest extends AbstractSmnRequest {
    private static Logger logger = LoggerFactory.getLogger(ListSubscriptionsRequest.class);

    /**
     * paging list's starting page,default 0
     */
    private int offset = 0;
    /**
     * max returned items for a request,default 100
     */
    private int limit = 100;
    /**
     * message access point
     */
    private String endpoint;

    @Override
    public String getRequestUrl() throws RuntimeException {
        if (StringUtils.isBlank(getAuthenticationBean().getProjectId())) {
            logger.error("project id is null");
            throw new RuntimeException();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(SmnConstants.SMN_HOST_NAME).append(SmnConstants.URL_DELIMITER).append(SmnConstants.V2_VERSION)
                .append(SmnConstants.URL_DELIMITER).append(getAuthenticationBean().getProjectId())
                .append(SmnConstants.URL_DELIMITER).append(SmnConstants.SMN_NOTIFICATIONS)
                .append(SmnConstants.URL_DELIMITER).append(SmnConstants.SMN_SUBSCRIPTIONS);

        if (getOffset() > 0) {
            sb.append("?offset=" + getOffset());
        } else {
            sb.append("?offset=" + "0");
        }
        if (getLimit() > 0 && getLimit() < 100) {
            sb.append("&limit=" + getLimit());
        } else {
            sb.append("&limit=").append("100");
        }
        if (StringUtils.isNoneBlank(getEndpoint())) {
            sb.append("&endpoint=").append(getEndpoint());
        }
        logger.info("Request url is: " + sb.toString());
        return sb.toString();
    }

    @Override
    public Map<String, Object> getRequestParameterMap() {
        Map<String, Object> requestParameterMap = new HashMap<String, Object>();
        return requestParameterMap;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset
     *            the offset to set
     */
    public void setOffset(int offset) {
        if (offset > 0) {
            this.offset = offset;
        }
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(int limit) {
        if (100 > limit && limit > 0) {
            this.limit = limit;
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
