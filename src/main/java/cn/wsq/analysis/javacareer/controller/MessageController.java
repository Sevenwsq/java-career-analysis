package cn.wsq.analysis.javacareer.controller;

import cn.wsq.analysis.javacareer.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Map;
//https://blog.csdn.net/liyongzhi1992/article/details/81221103
@Controller
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * 接受消息并回复
     * MessageMapping("/message")：类似与@RequestMapping，客户端请求服务器的URL，前提是双方端点已经打开
     * SendTo("/topic/message"):作用跟convertAndSend类似，广播发给与该通道相连的客户端
     */
    @MessageMapping("/message")
    @SendTo("/topic/message") //@SendTo注解表示当服务器有消息需要推送的时候，会对订阅了@SendTo中路径的浏览器发送消息。群发
    public Message message(@RequestBody Map<String, String> map) {
        String hello = map.get("hello");
        System.out.println(hello);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 6 && hour < 11) {
            return new Message("早上好!");
        } else if (hour == 12){
            return new Message("中午好!");
        } else if (hour >= 13 && hour <= 18){
            return new Message("下午好!");
        } else {
            return new Message("晚上好!");
        }
    }

    /**
     * 推送消息
     */
    @RequestMapping("/send")
    @ResponseBody
    public String send(){
        /*
        参数1：destination：客户端(client)订阅的地址
        参数2：有效载荷，即推送的消息
         */
        simpMessagingTemplate.convertAndSend("/topic/message", new Message("这是一条消息!"));
        return "ok";
    }


}