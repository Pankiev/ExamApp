class StompClient {
    constructor(destination, onMessageReceive) {
	this.client = null;
	this.destination = destination;
	this.onMessageReceive = onMessageReceive;
	
	this.connect();
    }
    
    connect() {
	var socket = new SockJS('/websocket');
	this.client = Stomp.over(socket);
	this.client.connect({}, this.onConnect.bind(this));
    }
    
    onConnect(frame) {
	console.log('Connected: ' + frame);
	this.client.subscribe(this.destination, this.onMessageReceive);
    }
    
    sendMessage() {
	this.client.send(this.destination, {}, JSON.stringify({}));
    }
    
    disconnect() {
	if (this.client !== null)
	    this.client.disconnect();

	console.log("Disconnected");
    }
}