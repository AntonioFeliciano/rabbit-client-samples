var amqp = require('amqplib');

module.exports = {
    publishMessageToExchange: function(channel, exchangeName, message){
        var messageString = JSON.stringify(message);
        channel.publish(exchangeName, "", new Buffer(messageString));
    }
};