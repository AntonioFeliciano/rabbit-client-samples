using System;
using System.Text;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace RabbitClient
{
    public class SimpleRabbitClient
    {
        private readonly JsonByteArraySerializer _serializer;
        private readonly IConnectionFactory _connectionFactory;
        private readonly IConnection _connection;
        private readonly IModel _model;

        public SimpleRabbitClient(RabbitConnectionSettings settings, JsonByteArraySerializer serializer)
        {
            _serializer = serializer;
            _connectionFactory = new ConnectionFactory
            {
                VirtualHost = settings.VirtualHost,
                HostName = settings.HostName,
                Port = settings.Port,
                UserName = settings.UserName,
                Password = settings.Password,
            };
            _connection = _connectionFactory.CreateConnection();
            _model = _connection.CreateModel();
        }

        /// <summary>
        /// Publishes a message to an exchange
        /// </summary>
        /// <param name="exchangeName">The exchange to publish to</param>
        /// <param name="messageBody">The message to serialize as JSON</param>
        public void PublishToExchange(string exchangeName, object messageBody)
        {
            var basicProperties = _model.CreateBasicProperties();
            basicProperties.Persistent = false; //Set to true to force rabbit to write the message to disk when it recieves the message
            basicProperties.ContentEncoding = Encoding.UTF8.WebName; //Rabbit doesn't care about this, but clients might
            _model.BasicPublish(exchangeName, "", basicProperties, _serializer.Serialize(messageBody));
        }

        /// <summary>
        /// Starts a new consumer listening to the specified queue.  Creates the queue if necessary, and binds it to the specified exchange
        /// </summary>
        /// <typeparam name="T">The message type</typeparam>
        /// <param name="exchangeName">The name of the exchange to bind to</param>
        /// <param name="queueName">The name of the queue to listen to</param>
        /// <param name="onMessageRecieved">An action to handle a message</param>
        /// <returns>The ID of the consumer (Used to cancel consumption)</returns>
        public string StartListeningToQueue<T>(string exchangeName, string queueName, Action<T> onMessageRecieved)
        {
            _model.QueueDeclare(queueName, true, false, false, null); // The boolean arguments to this MUST match the exchange you want to bind to
            _model.QueueBind(queueName, exchangeName, ""); //Any messages published to the exchange with the specified routing key will be forwarded to the queue

            var consumer = new EventingBasicConsumer(_model);
            consumer.Received += (s, e) =>
            {
                T body = _serializer.Deserialize<T>(e.Body);
                onMessageRecieved(body);
                _model.BasicAck(e.DeliveryTag, false); //A message is not considered de-queued until rabbit recieves an ACK
            };
            return _model.BasicConsume(queueName, false, consumer);
        }

        /// <summary>
        /// Cancels a previously registered listener
        /// </summary>
        /// <param name="listenerId">The ID of the listener to cancel</param>
        public void CancelQueueListener(string listenerId)
        {
            _model.BasicCancel(listenerId);
        }
    }
}