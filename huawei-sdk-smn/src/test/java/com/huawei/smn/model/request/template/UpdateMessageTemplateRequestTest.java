package com.huawei.smn.model.request.template;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.smn.model.AuthenticationBean;

import junit.framework.TestCase;

public class UpdateMessageTemplateRequestTest extends TestCase{
	private static Logger logger = LoggerFactory.getLogger(UpdateMessageTemplateRequestTest.class);
	UpdateMessageTemplateRequest updateMessageTemplateRequest;
	static AuthenticationBean authenticationBean;
	final static String projectId = "cffe4fc4c9a54219b60dbaf7b586e132";

	@Before
	public void setUp() {
		updateMessageTemplateRequest = mock(UpdateMessageTemplateRequest.class);
		authenticationBean = mock(AuthenticationBean.class);
		when(authenticationBean.getProjectId()).thenReturn(projectId);
	}

	public void testGetRequestUrl() throws Exception {
		logger.info("starting test publish");
		String messageTemplateId = "53013641b66d4ce4888b27f68e64bea3";
		when(updateMessageTemplateRequest.getAuthenticationBean()).thenReturn(authenticationBean);
		when(updateMessageTemplateRequest.getMessageTemplateId()).thenReturn(messageTemplateId);
		when(updateMessageTemplateRequest.getContent()).thenReturn("content");
		when(updateMessageTemplateRequest.getRequestUrl()).thenCallRealMethod();
		String requestURL = "https://smn.cn-north-1.myhwclouds.com/v2/cffe4fc4c9a54219b60dbaf7b586e132/notifications/message_template/53013641b66d4ce4888b27f68e64bea3";
		Assert.assertEquals(requestURL, updateMessageTemplateRequest.getRequestUrl());

	}

	public void testGetRequestParameterMap() throws Exception {
		when(updateMessageTemplateRequest.getContent()).thenReturn("content");
		when(updateMessageTemplateRequest.getRequestParameterMap()).thenCallRealMethod();
		Assert.assertNotNull(updateMessageTemplateRequest.getRequestParameterMap().get("content"));
	}

}
