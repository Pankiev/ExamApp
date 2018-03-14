class StompSender {
    constructor(destination) {
        this.client = null;
        this.destination = destination;

        this.connect();
    }

    connect() {
        var socket = new SockJS('/websocket');
        this.client = Stomp.over(socket);
        this.client.connect({});
    }

    sendMessage() {
        this.client.send(this.destination, {}, JSON.stringify({}));
    }
}

class StompReceiver {

    constructor(destination, handleMessage) {
        this.destination = destination;
        this.handleMessage = handleMessage;

        this.connect();
    }

    connect() {
        var socket = new SockJS('/websocket');
        this.client = Stomp.over(socket);
        this.client.connect({}, this.onConnect.bind(this));
    }

    onConnect() {
        this.client.subscribe(this.destination, this.handleMessage);
    }
}