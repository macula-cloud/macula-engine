package org.macula.cloud.sdk.configure;

import org.macula.cloud.sdk.event.CloudEventStreamChannel;
import org.macula.cloud.sdk.event.CloudKafkaMessagePull;
import org.macula.cloud.sdk.event.CloudKafkaMessagePush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.config.BindingBeansRegistrar;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;

@Configuration
@ConditionalOnProperty(prefix = "macula.cloud", name = "event.kafka", havingValue = "true", matchIfMissing = false)
@EnableBinding(CloudEventStreamChannel.class)
@AutoConfigureAfter(BindingBeansRegistrar.class)
@AutoConfigureBefore(BindingServiceConfiguration.class)
public class CloudEventKafkaConfigure {

	@Bean
	@Autowired
	public CloudKafkaMessagePull cloudKafkaMessagePull(ApplicationEventMulticaster applicationEventMulticaster) {
		return new CloudKafkaMessagePull(applicationEventMulticaster);
	}

	@Bean
	@Autowired
	public CloudKafkaMessagePush cloudKafkaMessagePush() {
		return new CloudKafkaMessagePush();
	}

}
