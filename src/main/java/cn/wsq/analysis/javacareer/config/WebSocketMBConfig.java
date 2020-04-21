package cn.wsq.analysis.javacareer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Silent
 * @date 2020/4/21 10:17:58
 * @description 我们可以加上定时任务，以达成定时推送广告等服务
 *STOMP协议：
 * STOMP即Simple (or Streaming) Text Orientated Messaging Protocol，简单(流)文本定向消息协议，
 * 它提供了一个可互操作的连接格式，允许STOMP客户端与任意STOMP消息代理（Broker）进行交互。
 * STOMP协议由于设计简单，易于开发客户端，因此在多种语言和多种平台上得到广泛地应用。
 *
 * STOMP协议的前身是TMP协议（一个简单的基于文本的协议），专为消息中间件设计。
 * STOMP是一个非常简单和容易实现的协议，其设计灵感源自于HTTP的简单性。
 * 尽管STOMP协议在服务器端的实现可能有一定的难度，但客户端的实现却很容易。
 * 例如，可以使用Telnet登录到任何的STOMP代理，并与STOMP代理进行交互。
 *
 */
@Configuration
@EnableWebSocketMessageBroker //表示开启使用STOMP协议来传输基于代理的消息，Broker就是代理的意思。
public class WebSocketMBConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 表示注册STOMP协议的节点(endpoint)，并指定映射的URL
     * setAllowedOrigins("*")运行所有域名连接
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hello").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理(Message Broker)
     * 消息代理将会处理前缀为“/topic”和“/hello”的消息。除此之外，发往应用程序的消息将会带有“/app”前缀。
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/hello");
        registry.setApplicationDestinationPrefixes("/app");
    }


}
