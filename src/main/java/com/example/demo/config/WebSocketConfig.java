package com.example.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.example.demo.Constants;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport implements WebSocketMessageBrokerConfigurer {

	@Value("${websocket.relay.host}")
	private String relayHost;

	@Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
	
	@Value("${websocket.relay.port}")
	private String relayPort;

	@Value("${websocket.relay.system.login}")
	private String relaySystemLogin;

	@Value("${websocket.relay.system.passcode}")
	private String relaySystemPasscode;

	@Value("${websocket.relay.client.login}")
	private String relayClientLogin;
	
	@Value("${websocket.relay.client.passcode}")
	private String relayClientPassCode;
	
	
	@Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return super.configureMessageConverters(messageConverters);
    }
    
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
        
    }
    
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // configure websocket transport for performance
        registry.setSendTimeLimit(15 * 1000).setSendBufferSizeLimit(512 * 1024).setMessageSizeLimit(64 * 1024);
        WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);       
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(Constants.STOMP_END_POINT);
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	// RABBITMQ broker
    	config.enableStompBrokerRelay(Constants.STOMP_RABBIT_AMQ, Constants.STOMP_RABBIT_EXCHANGE, Constants.STOMP_RABBIT_QUEUE, Constants.STOMP_RABBIT_TOPIC)
    	.setRelayHost(relayHost)
    	.setRelayPort(Integer.parseInt(relayPort))
    	.setSystemLogin(relaySystemLogin)
    	.setSystemPasscode(relaySystemPasscode)
    	.setClientLogin(relayClientLogin)
    	.setVirtualHost(virtualHost)
    	.setClientPasscode(relayClientPassCode);
    	
        config.setApplicationDestinationPrefixes(Constants.APPLICATION_PREFIX);
        config.setUserDestinationPrefix(Constants.USER_PREFIX);
        
    }

	
}