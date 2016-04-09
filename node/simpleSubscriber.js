module.exports = {
    consumerTag: "",
    beginSubscribing: function(channel, exchangeName, queueName, onRecieved){
        channel.assertQueue(queueName, {durable: true});
        channel.bindQueue(queueName, exchangeName, "");
        channel.consume(queueName, function(msg){
            var body = JSON.parse(msg.content.toString());
            onRecieved(body);
            channel.ack(msg);
        }).then(function(response){
            console.log("Consuming as " + response.consumerTag);
            this.consumerTag = response.consumerTag;
        })
    },
    cancelSubscribing: function(channel){
        channel.cancel(consumerTag);
    } 
}