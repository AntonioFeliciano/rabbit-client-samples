package utah.codecamp;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by Dalinar on 4/3/2016.
 */
public class BasicRabbitClient {

    private Channel channel;

    public BasicRabbitClient(RabbitConnectionSettings settings) throws IOException, TimeoutException{
        ConnectionFactory connectionFactory = settings.BuildConnectionFactory();
        Connection conn = connectionFactory.newConnection();
        channel = conn.createChannel();
    }

    public void publishMessageToExchange(String exchangeName, Object message) throws IOException{
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        Gson gson = new Gson();
        String messageJson = gson.toJson(message);
        //deliveryMode(1) means that RabbitMQ will only write the message to disk if the node needs to free RAM
        channel.basicPublish(exchangeName, "", builder.deliveryMode(1).build(), messageJson.getBytes(Charset.forName("UTF-8")));
    }

    public String startConsumingMessages(String exchangeName, String queueName) throws IOException{
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, "");

        String consumerTag = channel.basicConsume(queueName, false, new BasicRabbitConsumer(channel, this));
        return consumerTag;
    }

    public void stopConsumingMessages(String consumerTag) throws IOException{
        channel.basicCancel(consumerTag);
    }
}
