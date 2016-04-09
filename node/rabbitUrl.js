module.exports = {
    host: "localhost",
    userName: "guest",
    password: "guest",
    virtualHost: "%2F",
    port: 5672,
    
    buildUrl: function (){
        return "amqp://" + this.userName + ":" + this.password + "@" + this.host + ":" + this.port + "/" + this.virtualHost;
    }
}