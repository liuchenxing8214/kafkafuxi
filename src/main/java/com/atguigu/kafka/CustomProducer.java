package com.atguigu.kafka;

import java.util.ArrayList;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class CustomProducer {
	public static void main(String[] args) {
		Properties props = new Properties();
		// Kafka服务端的主机名和端口号
		props.put("bootstrap.servers", "192.168.1.2:9092");
		// 等待所有副本节点的应答
		props.put("acks", "all");
		// 消息发送最大尝试次数
		props.put("retries", 0);
		// 一批消息处理大小
		props.put("batch.size", 16384);
		// 请求延时
		props.put("linger.ms", 1);
		// 发送缓存区内存大小
		props.put("buffer.memory", 33554432);
		// key序列化
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// value序列化
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		ArrayList<String> interceptors = new ArrayList<>();
		interceptors.add("com.atguigu.kafka.interceptor.CounterInterceptor");
		interceptors.add("com.atguigu.kafka.interceptor.TimeInterceptor");
		props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);



		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 3000; i < 3501; i++) {
			producer.send(new ProducerRecord<String, String>("test03", Integer.toString(i), "谢贞容,我爱你一生一世-" + i));
		}

		producer.close();
	}
}

