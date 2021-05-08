package demo.consumer.component;

import demo.consumer.model.SimpleMessageDTO;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 用于接收对象同步消息
 * @author m0v1
 * @date 2021年05月08日 8:42 下午
 */
@Component
// Warning: consumer instances of a consumer group must have exactly the same topic subscription(s).
@RocketMQMessageListener(consumerGroup = "rocketmq-consumer-object-group", topic = "object-message-topic-1")
public class SimpleObjectConsumer implements RocketMQListener<SimpleMessageDTO> {

    @Override
    public void onMessage(SimpleMessageDTO message) {
        System.out.println(message);
    }
}
