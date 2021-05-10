package demo.consumer.component;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author m0v1
 * @date 2021年05月11日 6:40 上午
 */
@Component
@RocketMQMessageListener(consumerGroup = "rocketmq-consumer-order-group", topic = "order-message-topic-1")
public class SimpleOrderConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {

        System.out.println("---------->顺序消息:" + message);
    }
}
