/**
 * Websocket wrapper class for chat socket
 * */
class ChatSocket{
    /**
     * Opens connection to server socket on the given endpoint
     * @param endpoint_addr address to server socket endpoint
     * @param onMessageCallback function to handle receiving messages from server
     * */
    constructor(endpoint_addr, onMessageCallback){
        if (!("WebSocket" in window)) return;
        this.websocket = new WebSocket(endpoint_addr);
        this.websocket.onopen = () => console.log("Socket Opened!");
        this.websocket.onmessage = handleMessage;
        this.websocket.onclose = () => console.log("Socket Closed!");

    }

    send(msg){
        this.websocket.send(msg);
    }

    get getSocket(){
        return this.websocket;
    }

}



