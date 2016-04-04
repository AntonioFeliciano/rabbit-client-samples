package utah.codecamp;

import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by Dalinar on 4/3/2016.
 */
public class RabbitConnectionSettings {
    public String userName;
    public String password;
    public String host;
    public String virtualHost = "/";
    public int port = 5672;

    public ConnectionFactory BuildConnectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setHost(host);
        factory.setVirtualHost(virtualHost);
        factory.setPort(port);
        return factory;
    }
}
