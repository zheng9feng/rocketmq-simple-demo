package demo.consumer.component;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 用于接收字符串同步消息
 * @author m0v1
 * @date 2021年05月08日 8:42 下午
 */
@Component
@RocketMQMessageListener(consumerGroup = "rocketmq-consumer-group", topic = "sync-message-topic-1")
public class SimpleStringConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }
}
