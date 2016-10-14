/**
 * Copyright 2016, RadiantBlue Technologies, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package piazza.commons.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
//old import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import util.PiazzaLogger;

@Configuration
@EnableAutoConfiguration
public class NativeElasticsearchTemplateConfiguration {
	@Value("${elasticsearch.clustername}")
	private String clustername;

	// move to CF VCAP geoint serv. platform
//	@Value("${elasticsearch.hostname}")
//	private String hostname;
	@Value("${vcap.services.pz-elasticsearch.credentials.hostname}")
	private String cfhostname;

	@Value("${elasticsearch.port}")
	private Integer port;

	@Bean
	public Client client() throws UnknownHostException {
//		TransportClient client = TransportClient.builder().build()
//		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), port));
		
		Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
		
		TransportClient transportClient = TransportClient.builder().settings(settings).build();
		
		transportClient.addTransportAddress(new InetSocketTransportAddress(
											InetAddress.getByName(cfhostname), port));

		return transportClient;
	}

	/*
	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
*/	
	/*
	 * need local bean "wiring to JobCommons content (PiazzaLogger),
	 * strategy to scan JC package fails because this project doesn't use other "util" 
	 * members (e.g. UUIDgen)
	 */
	@Bean
	public PiazzaLogger logger() {
		return new PiazzaLogger();
	}

/*	@Bean
	public NativeElasticsearchTemplate template(Client client, ObjectMapper mapper) {
		return new NativeElasticsearchTemplate(client, mapper);
	}
	*/
}
