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