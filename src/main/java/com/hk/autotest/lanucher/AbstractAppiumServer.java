package com.hk.autotest.lanucher;

import com.hk.autotest.exception.CapException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class AbstractAppiumServer implements AppiumServer  {
	   private static final HttpClient httpClient = HttpClients.createDefault();
	    private static final String STATUS_PATH = "/wd/hub/status";
	    private static final String PATH = "/wd/hub";

	    protected Process process;
	    protected String ip = "localhost";
	    protected int appiumPort = 4723;

	    @Override
	    public boolean isRunning() {
	        try {
	            URI uri = new URIBuilder().setScheme("http").setHost(ip)
	                    .setPort(appiumPort).setPath(STATUS_PATH).build();

	            HttpResponse response;
	            HttpGet httpget = new HttpGet(uri);
	            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
	            httpget.setConfig(requestConfig);
	            response = httpClient.execute(httpget);
	            HttpEntity entity = response.getEntity();
	            String rs = EntityUtils.toString(entity);
	            JsonElement json = new JsonParser().parse(rs);
	            int status = json.getAsJsonObject().get("status").getAsInt();
	            return status == 0;

	        } catch (Exception e) {
	            // logger.warn("isRunning", e);
	            return false;
	        }

	    }

	    /**
	     * depends start method
	     *
	     * @return
	     */
	    @Override
	    public URL getURL() {
	        URI uri;
	        try {
	            uri = new URIBuilder().setScheme("http").setHost(ip)
	                    .setPort(appiumPort).setPath(PATH).build();
	            return uri.toURL();
	        } catch (URISyntaxException | MalformedURLException e) {
	            throw new CapException("getURL", e);
	        }

	    }
}
