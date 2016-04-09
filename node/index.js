var amqp = require('amqplib');
var publisher = require('./simplePublisher.js');
var rabbitUrl = require('./rabbitUrl.js');
var subscriber = require('./simpleSubscriber.js');

rabbitUrl.host = "localhost";
rabbitUrl.userName = "guest";
rabbitUrl.password = "guest";
console.log("AMQP URL: " + rabbitUrl.buildUrl());
amqp.connect(rabbitUrl.buildUrl()).then(function(connection){
    console.log("Connected to rabbit");
    
    connection.createChannel().then(function(channel){
        console.log("Channel created!");
        //TODO: publish and subscribe here
    });
}, function(err){
    console.error("Connect failed: %s", err)
}).then(null, console.warn);
