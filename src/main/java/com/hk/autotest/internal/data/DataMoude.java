package com.hk.autotest.internal.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctrip.cap.at.client.AvatarServiceClient;
import com.ctrip.cap.at.client.RunAtServiceClient;
import com.ctrip.cap.at.client.impl.CapAvatarDataServiceClientImp;
import com.ctrip.cap.at.client.impl.CapRunAtDataServiceClientImp;
import com.ctrip.cap.at.client.mock.MockAvatarServiceClient;
import com.ctrip.cap.at.client.mock.MockRunAtServiceClient;
import com.ctrip.cap.client.property.CapClientProperties;
import com.hk.autotest.lanucher.Environment;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class DataMoude extends AbstractModule  {

	private static final Logger logger = LoggerFactory
			.getLogger(DataMoude.class);
	private Injector injector;

	private volatile boolean initialized;

	private DataMoude() {
		super();
	}

	@Override
	protected void configure() {

		if (Environment.isLab()) {
			AvatarServiceClient avatarClient = new CapAvatarDataServiceClientImp();
			RunAtServiceClient runatClient = new CapRunAtDataServiceClientImp();

			AvatarServiceClient avatarProxy = (AvatarServiceClient) SilenceTimeoutClientProxy
					.newInstance(avatarClient);

			RunAtServiceClient runAtProxy = (RunAtServiceClient) SilenceTimeoutClientProxy
					.newInstance(runatClient);

			bind(AvatarServiceClient.class).toInstance(avatarProxy);

			bind(RunAtServiceClient.class).toInstance(runAtProxy);
		} else {
			bind(AvatarServiceClient.class).to(MockAvatarServiceClient.class);
			bind(RunAtServiceClient.class).to(MockRunAtServiceClient.class);
		}

	}

	public AvatarServiceClient avatarServiceClient() {
		return injector.getInstance(AvatarServiceClient.class);
	}

	public RunAtServiceClient runAtServiceClient() {
		return injector.getInstance(RunAtServiceClient.class);
	}

	public void config(String dataUrl) {

		if (initialized) {
			logger.error("Module has been initialized, nothing happen");
			return;
		}
		CapClientProperties.capDataUrl = Environment.getDataURI();
		initialized = true;
	}

	public void construct() {
		injector = Guice.createInjector(new DataMoude());
	}

	public static class DataServiceHolder {

		private static final DataMoude dataMoude;;
		static {
			dataMoude = new DataMoude();
			dataMoude.construct();
		}

		public static void setUp(String dataUrl) {
			dataMoude.config(dataUrl);
		}

		public static AvatarServiceClient avatarServiceClient() {
			return dataMoude.avatarServiceClient();
		}

		public static RunAtServiceClient runAtServiceClient() {
			return dataMoude.runAtServiceClient();
		}
	}

}
