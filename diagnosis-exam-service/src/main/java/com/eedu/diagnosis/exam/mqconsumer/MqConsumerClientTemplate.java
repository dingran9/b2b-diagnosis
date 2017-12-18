package com.eedu.diagnosis.exam.mqconsumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by dqy on 2017/2/10.
 */
public class MqConsumerClientTemplate {
    private final Logger logger = LoggerFactory.getLogger(MqConsumerClientTemplate.class);
    private DefaultMQPushConsumer defaultMQPushConsumer;
    private String namesrvAddr;
    private String consumerGroup;

    /**
     * Spring bean init-method
     */
    public void init() throws InterruptedException, MQClientException {

        // 参数信息
        logger.info("DefaultMQPushConsumer initialize!");
        logger.info(consumerGroup);
        logger.info(namesrvAddr);

        // 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
        // 注意：ConsumerGroupName需要由应用来保证唯一
        defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        // 订阅指定MyTopic下tags等于MyTag

//        defaultMQPushConsumer.subscribe("keepMarkDiagnosisTopic", "goodsService || TagB || TagC");
        defaultMQPushConsumer.subscribe("diagnosis-system-manager", "make-report");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
//        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(5);
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {

            // 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt msg = msgs.get(0);
                logger.info("topic = "+ msg.getTopic());
                try {
                    logger.info("body=" + new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                switch (msg.getTopic()) {
                    case "diagnosis-system-manager":
                        // 执行Topic的消费逻辑
                        if (msg.getTags() != null && msg.getTags().equals("make-report")) {
                            //执行Tag的消费
                            try {
                                logger.info("offset="+msg.getQueueOffset()+"  msgId="+msg.getMsgId()+"  body="+new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "keepMarkBaofenTopic":
                        // 执行Topic的消费逻辑
                        if (msg.getTags() != null && msg.getTags().equals("TagB")) {
                            //执行Tag的消费
                            try {
                                System.out.println("offset="+msg.getQueueOffset()
                                        + "  msgId=" + msg.getMsgId()
                                        + "  body=" + new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET)
                                        + "  keys=" + msg.getKeys()
                                        + "  QueueId=" + msg.getQueueId());
                                logger.info("offset="+msg.getQueueOffset()+"  msgId="+msg.getMsgId()+"  body="+new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }

                // 如果没有return success ，consumer会重新消费该消息，直到return success
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("DefaultMQPushConsumer start success!");
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }

    // ----------------- setter --------------------

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }
}
