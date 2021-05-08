package demo.consumer.component;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 用于模拟消息消费异常
 *
 * @author m0v1
 */
@Component
@RocketMQMessageListener(consumerGroup = "rocketmq-consumer-group-exception", topic = "sync-message-exception-topic-1")
public class SimpleFailedConsumer implements RocketMQListener<MessageExt> {

    /**
     * rocketmq消息消费异常处理机制
     * 一条消息消费失败,rocketmq会从第三延迟等级(10s)进行重试,如果还没成功消费则到下一延迟等级再次消费...一直重试完第十六等级
     * 第十六等级重试失败后消息将进入死信队列
     * <p>
     * 正常处理方案:限定重试次数,超过限定次数记录到数据库后不再发起重试.使用单独程序或人工进行处理
     *
     * @param message org.apache.rocketmq.common.message.MessageExt
     */
    @Override
    public void onMessage(MessageExt message) {
        System.out.println(message);

        int reconsumeTimes = message.getReconsumeTimes();
        System.out.println("当前重复消费次数为:" + reconsumeTimes);
        if (reconsumeTimes >= 2) {
            // 记录消息到数据库
            // 正常返回,表示消息消费成功
            System.out.println("失败消息记录到数据库,该消息不再重复消费");
            return;
        }
        // 模拟异常
        System.out.println(1 / 0);
    }
}
