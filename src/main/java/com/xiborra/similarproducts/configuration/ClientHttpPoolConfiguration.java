package com.xiborra.similarproducts.configuration;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiborra.similarproducts.constants.HttpConnectionPoolConstants;

@Configuration
public class ClientHttpPoolConfiguration {

	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
		result.setDefaultMaxPerRoute(HttpConnectionPoolConstants.MAX_PER_ROUTE);
		result.setMaxTotal(HttpConnectionPoolConstants.MAX_TOTAL);
		return result;
	}

	@Bean
	public RequestConfig requestConfig() {
		return RequestConfig.custom().setConnectTimeout(HttpConnectionPoolConstants.CONNECTION_TIMEOUT)
				.setSocketTimeout(HttpConnectionPoolConstants.SOCKET_TIMEOUT).build();
	}

	@Bean
	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
			RequestConfig requestConfig) {
		return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager)
				.setDefaultRequestConfig(requestConfig).build();
	}
	
}
