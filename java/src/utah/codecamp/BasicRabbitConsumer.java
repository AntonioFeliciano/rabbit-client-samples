package utah.codecamp;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by Dalinar on 4/3/2016.
 */
public class BasicRabbitConsumer extends DefaultConsumer {
    public BasicRabbitConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        String jsonBody = new String(body, "UTF-8");
        Gson gson = new Gson();
        //Deserialize with gson.fromJson
        //Process the message
        getChannel().basicAck(envelope.getDeliveryTag(), false);
    }
}
