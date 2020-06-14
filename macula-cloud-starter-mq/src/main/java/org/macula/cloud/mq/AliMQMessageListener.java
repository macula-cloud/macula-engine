package org.macula.cloud.mq;

import com.aliyun.openservices.ons.api.MessageListener;

public interface AliMQMessageListener extends MessageListener {

	String getTopic();

	String getSubExpression();

}
