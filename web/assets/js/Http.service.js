/**
 * Http class for making http requests
 */
class HTTP{
    constructor(base_url){
        this.base_url = base_url;
        this.headers = {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
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
            uri += HTTP.encodeForUrl(baggage);

        return this.makeRequest(uri);
    }

    GET(uri, baggage = null){
        this.options["method"] ="GET";
        if(baggage)
            uri += HTTP.encodeForUrl(baggage);
        return this.makeRequest(uri);
    }

    PUT(uri, baggage = null){
        this.options["method"] ="PUT";
        if(baggage)
            uri += HTTP.encodeForUrl(baggage);
        return this.makeRequest(uri);
    }

    DELETE(uri, baggage = null){
        this.options["method"] ="DELETE";
        if(baggage)
            uri += HTTP.encodeForUrl(baggage);
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

    static encodeForUrl(baggage) {
        let urlEncoded = Object.keys(baggage).map(key => encodeURIComponent(key) + '=' + encodeURIComponent(baggage[key])).join("&");
        return "?" + urlEncoded;
    }
}

const http = new HTTP("http://localhost:8080/OOP_Final_Project");