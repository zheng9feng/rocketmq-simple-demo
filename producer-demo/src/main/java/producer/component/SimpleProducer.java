package producer.component;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author m0v1
 * @date 2021年05月08日 8:42 下午
 */
@Component
public class SimpleProducer {
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 发送同步消息
     * @author m0v1
     * @date 2021/5/8 9:26 下午
     * @param destination topicName:tags
     * @param message
     */
    public void sendSyncMessage(String destination, String message) {
        SendResult sendResult = rocketMQTemplate.syncSend(destination, message);
        SendStatus sendStatus = sendResult.getSendStatus();
        System.out.println(sendStatus);
    }

    /**
     * 发送异步消息
     * @author m0v1
     * @date 2021/5/8 9:35 下午
     * @param destination topicName:tags
     * @param message
     */
    public void sendAsyncMessage(String destination,String message){
        rocketMQTemplate.asyncSend(destination,message, new SendCallback(){
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("异步消息发送成功");
                System.out.println(sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("异步消息发送异常" + throwable.getMessage());
            }
        });

       // 用于确保SendCallback回调方法被执行,否则会因进程结束而未发送消息
       try {
           TimeUnit.SECONDS.sleep(3);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }

    /**
     * 只发送请求不等待应答
     * @author m0v1
     * @date 2021/5/8 10:14 下午
     * @param destination topicName:tags
     * @param message
     */
    public void sendOneWay(String destination, String message){
        rocketMQTemplate.sendOneWay(destination,message);
    }

    /**
     * 同步发送对象消息
     * @author m0v1
     * @date 2021/5/8 10:32 下午
     * @param destination topicName:tags
     * @param message
     */
    public void sendObject(String destination, Object message){
        // 将对象转换成json串发送
        rocketMQTemplate.convertAndSend(destination,message);
    }

    /**
     * 同步延迟消息
     * @param destination topicName:tags
     * @param message
     * @param timeout 超时时间 ms
     * @param delayLevel 延迟等级 总共18个level 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public void syncSendDelayMessage(String destination, Object message, int timeout, int delayLevel){
        Message<Object> objectMessage = MessageBuilder.withPayload(message).build();
        rocketMQTemplate.syncSend(destination, objectMessage, timeout, delayLevel);
    }

    /**
     * 异步发送延迟消息
     * @author m0v1
     * @date 2021/5/9 12:26 上午
     * @param destination
     * @param message
     * @param timeout
     * @param delayLevel
     */
    public void asyncSendDelayMessage(String destination,Object message,int timeout,int delayLevel){
        Message<Object> objectMessage = MessageBuilder.withPayload(message).build();
        rocketMQTemplate.asyncSend(destination, objectMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        }, 1000, 3);
    }
}
