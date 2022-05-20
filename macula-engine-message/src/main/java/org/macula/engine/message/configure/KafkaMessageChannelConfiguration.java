package org.macula.engine.message.configure;

import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.commons.event.BroadcastMessage;
import org.macula.engine.commons.event.MaculaLocaleEvent;
import org.macula.engine.message.channel.KafkaMessageChannelExchange;
import org.macula.engine.message.channel.MessageChannelConstants;
import org.macula.engine.message.event.ApplicationMessageMulticaster;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.config.BindingBeansRegistrar;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnClass(StreamBridge.class)
@ConditionalOnProperty(prefix = "macula.message", name = "channel.kafka", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(BindingBeansRegistrar.class)
@AutoConfigureBefore(BindingServiceConfiguration.class)
public class KafkaMessageChannelConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine Kafka Message Channel] Auto Configure.");
	}

	@Bean
	ApplicationMessageMulticaster applicationEventMulticaster() {
		log.debug("[Macula] |- Bean [ApplicationMessageMulticaster] Auto Configure.");
		return new ApplicationMessageMulticaster();
	}

	@Bean
	public KafkaMessageChannelExchange kafkaMessageChannelExchange(StreamBridge streamBridge) {
		log.debug("[Macula] |- Bean [KafkaMessageChannelExchange] Auto Configure.");
		return new KafkaMessageChannelExchange(streamBridge, MessageChannelConstants.MACULA_EVENT_PRODUCER_CHANNEL);
	}

	@Bean
	public Consumer<BroadcastMessage<?>> messageChannelMaculaEventConsumer() {
		log.debug("[Macula] |- Bean [Consumer<BroadcastMessage<?>>] Auto Configure.");
		return (message) -> {
			if (log.isTraceEnabled()) {
				log.trace("[Macula] |- Message |- Receive [{}] -> from [{}] payload [{}]", message.getClass(),
						message.getSourceApplication(), message.getPayload());
			}
			applicationEventMulticaster().multicastEvent(MaculaLocaleEvent.of(message));
		};
	}
}
