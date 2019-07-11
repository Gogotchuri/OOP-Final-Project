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
        this.websocket.onmessage = onMessageCallback;
        this.websocket.onclose = () => console.log("Socket Closed!");

    }

    send(msg){
        this.websocket.send(msg);
    }

    close(){
        this.websocket.close();
    }
    get getSocket(){
        return this.websocket;
    }

}

class HTTP{
    constructor(base_url){
        this.base_url = base_url;
        this.headers = {
            'Content-Type': 'application/json',
            'Accept' : "application/json"
        };
        this.options = {
            mode: "cors",
            cache: "default"
        }
    }

    setHeader(header, value){
        this.headers[header] = value;
    }

    setOption(option, value){
        this.options[option] = value;
    }

    POST(uri, baggage = null){
        this.options["method"] ="POST";
        if(baggage)
            this.options["body"] = JSON.stringify(baggage);
        return this.makeRequest(uri);
    }

    GET(uri, baggage = null){
        this.options["method"] ="GET";
        if(baggage)
            this.options["body"] = JSON.stringify(baggage);
        return this.makeRequest(uri);
    }

    PUT(uri, baggage = null){
        this.options["method"] ="PUT";
        if(baggage)
            this.options["body"] = JSON.stringify(baggage);
        return this.makeRequest(uri);
    }

    DELETE(uri, baggage = null){
        this.options["method"] ="DELETE";
        if(baggage)
            this.options["body"] = JSON.stringify(baggage);
        return this.makeRequest(uri);
    }

    async makeRequest(uri){
        return new Promise(async (resolve, reject) => {
            let resp = await fetch(this.base_url + uri, this.options);
            let status = resp.status;
            let data = await resp.json();
            if(status >= 400){
                reject(data);
            }else{
                resolve(data);
            }
        })
    }
}



