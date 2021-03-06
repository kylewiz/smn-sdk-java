package com.huawei.smn.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.smn.common.AccessPolicyConstants;
import com.huawei.smn.common.AccessPolicyType;
import com.huawei.smn.model.request.topic.CreateTopicRequest;
import com.huawei.smn.model.request.topic.DeleteTopicAttributeByNameRequest;
import com.huawei.smn.model.request.topic.DeleteTopicAttributesRequest;
import com.huawei.smn.model.request.topic.DeleteTopicRequest;
import com.huawei.smn.model.request.topic.ListTopicAttributesRequest;
import com.huawei.smn.model.request.topic.ListTopicsRequest;
import com.huawei.smn.model.request.topic.QueryTopicDetailRequest;
import com.huawei.smn.model.request.topic.UpdateTopicAttributeRequest;
import com.huawei.smn.model.request.topic.UpdateTopicRequest;
import com.huawei.smn.service.TopicService;

import junit.framework.TestCase;

public class TopicServiceImplTest extends TestCase {
    private static Logger logger = LoggerFactory.getLogger(TopicServiceImplTest.class);
    TopicService topicService = new TopicServiceImpl();

    @Test
    public void testCreateTopic() {
        CreateTopicRequest createRequest = new CreateTopicRequest();
        createRequest.setName("publishMsgWithStruct02");
        topicService.setSmnRequest(createRequest);
        Map<String, Object> res = topicService.createTopic();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("topic_urn"));
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));
    }

    @Test
    public void testDeleteTopic() {
        String topicUrn = "urn:smn:cn-north-1:cffe4fc4c9a54219b60dbaf7b586e132:publishMsgWithStruct02";
        DeleteTopicRequest deleteRequest = new DeleteTopicRequest();
        deleteRequest.setTopicUrn(topicUrn);
        TopicService topicService = new TopicServiceImpl();
        topicService.setSmnRequest(deleteRequest);
        Map<String, Object> res = topicService.deleteTopic();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));
    }

    @Test
    public void testListTopics() {
        ListTopicsRequest listTopicsRequest = new ListTopicsRequest();
        TopicService topicService = new TopicServiceImpl();
        topicService.setSmnRequest(listTopicsRequest);
        Map<String, Object> res = topicService.listTopics();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));
    }

    @Test
    public void testQueryTopicDetail() {
        String topicUrn = "urn:smn:cn-north-1:cffe4fc4c9a54219b60dbaf7b586e132:SmnApi";
        QueryTopicDetailRequest queryTopicDetailRequest = new QueryTopicDetailRequest();
        queryTopicDetailRequest.setTopicUrn(topicUrn);
        TopicService topicService = new TopicServiceImpl();
        topicService.setSmnRequest(queryTopicDetailRequest);
        Map<String, Object> res = topicService.queryTopicDetail();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("topic_urn"));
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));

    }

    @Test
    public void testListTopicAttributesRequest() {
        String topicUrn = "urn:smn:cn-north-1:cffe4fc4c9a54219b60dbaf7b586e132:createMessageTemplate";
        ListTopicAttributesRequest listTopicAttributesRequest = new ListTopicAttributesRequest();
        listTopicAttributesRequest.setTopicUrn(topicUrn);
        listTopicAttributesRequest.setAttributesName("access_policy");
        TopicService topicService = new TopicServiceImpl();
        topicService.setSmnRequest(listTopicAttributesRequest);
        Map<String, Object> res = topicService.listTopicAttributes();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("attributes"));
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));

    }

    @Test
    public void testUpdateTopicAttributeRequest() {
        String topicUrn = "urn:smn:cn-north-1:cffe4fc4c9a54219b60dbaf7b586e132:createMessageTemplate";
        UpdateTopicAttributeRequest updateTopicAttributeRequest = new UpdateTopicAttributeRequest();
        updateTopicAttributeRequest.setTopicUrn(topicUrn);
        /**
         * Topic attribute have two types currently: access_policy or
         * introduction;other value is invalid
         */
        updateTopicAttributeRequest.setAttributesName(AccessPolicyType.ACCESS_POLICY);

        /**
         * 访问策略组成:访问策略是通过Statement语句来定义的。 一个访问策略可包含一条或多条Statement语句。
         * 使用Statement语句可对其他用户或云服务授权对主题操作，包括查询主题订阅者列表、增加主题的发送消息模板、发布消息、修改主题属性、甚至删除该主题等操作。
         */
        List<LinkedHashMap<String, Object>> statements = new ArrayList<LinkedHashMap<String, Object>>();

        LinkedHashMap<String, Object> singleStatement = new LinkedHashMap<String, Object>();

        /**
         * currently effective actions incuded as following:
         * SMN:UpdateTopic,SMN:DeleteTopic,SMN:QueryTopicDetail,
         * SMN:ListTopicAttributes,SMN:UpdateTopicAttribute,
         * SMN:DeleteTopicAttributes,SMN:DeleteTopicAttributeByName,
         * SMN:ListSubscriptionsByTopic,SMN:Subscribe,SMN:Unsubscribe,SMN:Publish.
         * 
         */
        // action List
        List<String> actionList = new ArrayList<String>();
        actionList.add("SMN:UpdateTopic");
        actionList.add("SMN:DeleteTopic");
        actionList.add("SMN:QueryTopicDetail");
        actionList.add("SMN:ListTopicAttributes");
        actionList.add("SMN:UpdateTopicAttribute");
        actionList.add("SMN:DeleteTopicAttributes");
        actionList.add("SMN:DeleteTopicAttributeByName");
        actionList.add("SMN:ListSubscriptionsByTopic");
        actionList.add("SMN:Subscribe");
        actionList.add("SMN:Unsubscribe");
        actionList.add("SMN:Publish");
        singleStatement.put(AccessPolicyConstants.POLICY_ACTION, actionList);

        Map<String, List<String>> principal = new LinkedHashMap<String, List<String>>();
        List<String> cspPolicy = new ArrayList<String>();
        cspPolicy.add("*");
        principal.put(AccessPolicyConstants.POLICY_CSP, cspPolicy);
        singleStatement.put(AccessPolicyConstants.POLICY_PRINCIPAL, principal);

        String uuid = UUID.randomUUID().toString().replace("-", "");
        logger.info(uuid);
        singleStatement.put(AccessPolicyConstants.POLICY_STATEMENT_ID, uuid);
        singleStatement.put(AccessPolicyConstants.POLICY_EFFECT, AccessPolicyConstants.POLICY_EFFECT_ALLOW);
        singleStatement.put(AccessPolicyConstants.POLICY_RESOURCE,
                "urn:smn:regionId:8bad8a40e0f7462f8c1676e3f93a8183:zhaoli");

        // 策略里配置statement,可多个,这里以一个为例
        statements.add(singleStatement);

        LinkedHashMap<String, Object> acessPolicy = new LinkedHashMap<String, Object>();
        acessPolicy.put(AccessPolicyConstants.POLICY_ID, AccessPolicyConstants.DEFAULT_POLICY_ID);
        acessPolicy.put(AccessPolicyConstants.POLICY_VERSION, AccessPolicyConstants.DEFAULT_VERSION);
        acessPolicy.put(AccessPolicyConstants.POLICY_STATEMENT, statements);
        // 创建新的access policy,默认的id与version
        // 设置 acessPolicy
        updateTopicAttributeRequest.setAcessPolicy(acessPolicy);
        TopicService topicService = new TopicServiceImpl();
        topicService.setSmnRequest(updateTopicAttributeRequest);
        Map<String, Object> res = topicService.updateTopicAttribute();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));
    }

    @Test
    public void testDeleteTopicAttributeByName() {
        String topicUrn = "urn:smn:cn-north-1:cffe4fc4c9a54219b60dbaf7b586e132:createMessageTemplate";
        DeleteTopicAttributeByNameRequest deleteTopicAttributeByNameRequest = new DeleteTopicAttributeByNameRequest();
        deleteTopicAttributeByNameRequest.setTopicUrn(topicUrn);
        deleteTopicAttributeByNameRequest.setAttributesName("access_policy");
        logger.warn(deleteTopicAttributeByNameRequest.toString());
        TopicService topicService = new TopicServiceImpl();
        topicService.setSmnRequest(deleteTopicAttributeByNameRequest);
        Map<String, Object> res = topicService.deleteTopicAttributeByName();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));
    }

    @Test
    public void testDeleteTopicAttributes() {
        String topicUrn = "urn:smn:cn-north-1:cffe4fc4c9a54219b60dbaf7b586e132:createMessageTemplate";
        DeleteTopicAttributesRequest deleteTopicAttributeRequest = new DeleteTopicAttributesRequest();
        deleteTopicAttributeRequest.setTopicUrn(topicUrn);
        logger.warn(deleteTopicAttributeRequest.toString());
        TopicService topicService = new TopicServiceImpl();
        topicService.setSmnRequest(deleteTopicAttributeRequest);
        Map<String, Object> res = topicService.deleteTopicAttributes();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));
    }

    @Test
    public void testUpdateTopic() {
        String topicUrn = "urn:smn:cn-north-1:cffe4fc4c9a54219b60dbaf7b586e132:createMessageTemplate";
        UpdateTopicRequest updateTopicRequest = new UpdateTopicRequest();
        updateTopicRequest.setTopicUrn(topicUrn);
        updateTopicRequest.setDisplayName("pollyman");
        TopicService topicService = new TopicServiceImpl();
        topicService.setSmnRequest(updateTopicRequest);
        Map<String, Object> res = topicService.updateTopic();
        logger.info(res.toString());
        Assert.assertNotNull(res.get("request_id"));
        Assert.assertNotNull(res.get("status"));
    }
}
