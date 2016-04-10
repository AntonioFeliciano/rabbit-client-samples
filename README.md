# rabbit-client-samples
Sample projects for publishing/subscribing to RabbitMQ in a variety of languages

## Utah Code Camp 2016 Instructions

Just in time for Code Camp 2016, I'm pleased to announce the opening of an artisinal pizza shop, "House Asynch Pizza".  Today only, we're running a special for anyone who orders using our special RabbitMQ ordering system.  With each order, you'll recieve a special coupon that can be used for 50% off a subsequent order.  The API for "House Asynch Pizza" follows.

### Conventions

All exchanges are durable fanout exchanges.  Exchange names use snake_case, and queues should be named following this pattern: `exchange_name=>subscriber_name`.

Messages should contain UTF-8 encoded JSON-formatted strings.

### Ordering

Send a message with the following format to the `order_placed` exchange.

Field       | Description
------------|----------------
customer    | The customer's name.  Coupons are issued on a per-customer basis
address     | Where to deliver the pizza to
coupon      | (Optional) A coupon code to apply to this order
pizzas      | A list of pizzas to order
pizzas.size | One of `Personal`, `Small`, `Medium`, `Large`, `XLarge`
pizzas.toppings | A list of toppings for the desired pizza.  House Asynch Pizza supports a wide variety of toppings!

Example:

```json
{
    "customer": "Kylo Ren",
    "address": "1 Starkiller Base",
    "pizzas": [
        { "size":"Personal", "toppings":["darkness", "brooding", "ashes"]}
    ]
}
```

### Recieving 

Bind a queue to the `order_filled` exchange.  Subscribe to that queue to recieve messages in the following format:

Send a message with the following format to the `order_placed` exchange.

Field       | Description
------------|----------------
customer    | The customer's name.  Coupons are issued on a per-customer basis
address     | Where to deliver the pizza to
couponCode  | A coupon code for 50% off your next order
orderPrice  | The cost of your order
pizzas      | A list of pizzas to order
pizzas.size | One of `Personal`, `Small`, `Medium`, `Large`, `XLarge`
pizzas.toppings | A list of toppings for the desired pizza.  House Asynch Pizza supports a wide variety of toppings!

```json
{
    "customer": "Kylo Ren",
    "address": "1 Starkiller Base",
    "couponCode": "FakeCouponCode",
    "orderPrice": 10.25,
    "pizzas": [
        { "size":"Personal", "toppings":["darkness", "brooding", "ashes"]}
    ]
}
```

## Languages

Read below for basic documentation on each of the client samples

### C#

`SimpleRabbitClient` provides a simplified interface with RabbitMQ, providing both subscriber and publisher functionality.  To publish a message to an exchange, use `PublishToExchange`, providing an exchange name and an object to serialize as the message body.  To recieve messages, call `StartListeningToQueue`.  This will create a queue, bind it to the specified exchange, and start consuming from that queue.  When a message is recieved, it will deserialize it into an object and pass it to `onMessageRecieved`.  After `onMessageRecieved` completes, it sends an ACK to rabbit to finish the message processing.

This code depends on `newtonsoft.json` to serialize and deserialize json, and `RabbitMQ.Client` for Rabbit operations.  Both are available via nuget.org

### Java

`BasicRabbitClient` provides a simple interface for interacting with RabbitMQ.  To publish a message to an exchange, use `publishMessageToExchange`.  To start subscribing to messages, use `startConsumingMessages`, which will create a queue, bind it to the specified exchange, and start consuming messages.  In order to handle the messages, you need to extend `com.rabbitmq.client.DefaultConsumer` and override `handleDelivery`.  In this case, `BasicRabbitConsumer` is already configured to ACK messages correctly, so you just need to fill in the current `//TODO` comment.

This code depends on `gson` for JSON serialization and `amqp-client` for Rabbit operations, both downloaded from Maven.

### node.js

The node implementation of rabbit uses `amqp://` urls for configuration, so I've provided `rabbitUrl.js` as a convienient way to build the correct URL.  `simplePublisher.js` provides a a publish method, taking a channel, exchange name, and a message. `simplePublisher.js` provides a method to begin listening to a queue.  It will create the queue, bind it to the specified exchange, and begin consuming messages.  Upon recieving a message, it will deserialize the message body into an object, and pass it into `onRecieved`.  Once your message processing is finished, it returns an ACK to rabbit.

This code depends on `amqplib`, available via npm  