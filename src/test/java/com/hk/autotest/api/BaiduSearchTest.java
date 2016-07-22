package com.hk.autotest.api;

import static com.hk.autotest.common.Assertion.assertEquals;
import static com.hk.autotest.common.Assertion.assertTrue;

import java.net.URISyntaxException;

import org.testng.annotations.Test;

public class BaiduSearchTest extends APITest  {
	
	@Test
	public void testSearchEasy() throws URISyntaxException {
		// URI uri = new URIBuilder("http://www.baidu.com/s").addParameter("wd",
		// "simple is not easy").build();
		String uri = "http://www.baidu.com/s?wd=simple";

		APIRequest request = APIRequest.get(uri);
		APIResponse response = execute(request);

		assertEquals(response.getStatusCode(), 200);
		assertTrue(response.getBody().contains("simple"));
	}
}
