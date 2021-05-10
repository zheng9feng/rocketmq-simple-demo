package producer.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import producer.model.SimpleMessageDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author m0v1
 * @date 2021年05月08日 8:56 下午
 */
@SpringBootTest
public class SimpleProducerTest {

    @Autowired
    private SimpleProducer simpleProducer;

    @Test
    public void testSendSyncMessage() {
        simpleProducer.sendSyncMessage("sync-message-topic-1", "first-sync-message");
    }

    @Test
    public void testSendAsyncMessage() {
        simpleProducer.sendAsyncMessage("async-message-topic-1", "2nd-async-message");
    }

    @Test
    public void testSendOneWay() {
        simpleProducer.sendOneWay("one-way-topic", "one-way-message");
    }

    @Test
    public void testSendObject() {
        SimpleMessageDTO simpleMessageDTO = SimpleMessageDTO.builder().id("this is id")
                .createTime(new Date())
                .money(new BigDecimal("10")).build();
        simpleProducer.sendObject("object-message-topic-1", simpleMessageDTO);
    }

    @Test
    public void testSyncSendDelayMessage() {
        SimpleMessageDTO simpleMessageDTO = SimpleMessageDTO.builder().id("this is id for delay message")
                .createTime(new Date())
                .money(new BigDecimal("10")).build();
        // 延迟十秒发送消息
        simpleProducer.syncSendDelayMessage("object-message-topic-1", simpleMessageDTO, 1000, 3);
    }

    @Test
    public void testAsyncSendDelayMessage() {
        SimpleMessageDTO simpleMessageDTO = SimpleMessageDTO.builder().id("this is id for delay message--")
                .createTime(new Date())
                .money(new BigDecimal("10")).build();
        // 延迟十秒发送消息
        simpleProducer.asyncSendDelayMessage("object-message-topic-1", simpleMessageDTO, 1000, 3);
    }

    @Test
    public void testFailedMessage() {
        SimpleMessageDTO simpleMessageDTO = SimpleMessageDTO.builder().id("this is id for delay message")
                .createTime(new Date())
                .money(new BigDecimal("10")).build();
        simpleProducer.sendObject("sync-message-exception-topic-1", simpleMessageDTO);
    }

    @Test
    public void testSendOrderMessage() {
        for (int i = 0; i < 10; i++) {
            simpleProducer.sendOrderMessage(
                    "order-message-topic-1",
                    "this is a order message:" + i,
                    "get-target-queue-for-order-message");
        }
    }
}
