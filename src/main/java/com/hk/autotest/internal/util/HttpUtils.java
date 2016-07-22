package com.hk.autotest.internal.util;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLDecoder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hk.autotest.exception.CapException;
import com.hk.autotest.exception.AppDownloadException;
import com.hk.autotest.lanucher.Environment;

public class HttpUtils {


	private static final Logger logger = LoggerFactory
			.getLogger(HttpUtils.class);

	public static String download(URI uri, File desDir, String user,
			String password) {

		HttpClient httpSvnClient;

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				user, password);
		provider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
				credentials);

		httpSvnClient = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider).build();

		long contentLength = 0;
		try {
			HttpHead httpHead = new HttpHead(uri);
			HttpResponse meta;
			meta = httpSvnClient.execute(httpHead);
			String lengthHeader = meta.getFirstHeader(
					HttpHeaders.CONTENT_LENGTH).getValue();
			contentLength = Long.parseLong(lengthHeader);
		} catch (Exception e1) {
			throw new AppDownloadException(uri.toString(), e1);
		}

		HttpGet httpGet = new HttpGet(uri);
		try {
			HttpResponse response = httpSvnClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			int status = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK != status) {
				throw new CapException(EntityUtils.toString(entity));
			}

			InputStream stream = entity.getContent();

			String fileName = FilenameUtils.getName(uri.getRawPath());
			File destFile = new File(desDir, fileName);

			FileUtils.copyInputStreamToFile(stream, destFile);
			long realLength = destFile.length();
			if (realLength != contentLength) {
				throw new AppDownloadException(
						"App Package damaged by bad network:" + uri);
			}
			return destFile.getAbsolutePath();

		} catch (Exception e) {
			throw new AppDownloadException(uri.toString(), e);

		}

	}

	public static String download(URI uri, File desDir)
			throws MalformedURLException {
		String user = Environment.getSvnUser();
		String password = Environment.getSvnPassword();
		return download(uri, desDir, user, password);
	}

	public static String downloadApp(String app) {
		try {
			app = URLDecoder.decode(app, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.warn("http svn decode", e);
		}
		logger.info("try download app package {}", app);
		URI uri;
		try {
			uri = new URI(app);
			app = download(uri, Environment.assertsHome());
			logger.info("app package has been download to {}", app);
		} catch (Exception e1) {
			throw new CapException("file download", e1);
		}
		return app;
	}
}
