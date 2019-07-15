/**
 * Http class for making http requests
 */
class HTTP{
    constructor(base_url){
        this.base_url = base_url;
        this.headers = {
            'content-type': 'application/x-www-form-urlencoded;charset=UTF-8',
            'accept' : "application/json"
        };
        this.options = {
            mode: "cors",
            cache: "default"
        }
    }

    setHeader(header, value){
        header = header.toLowerCase();
        this.headers[header] = value.toLowerCase();
    }

    setOption(option, value){
        this.options[option] = value;
    }

    /**
     * Given a type, set content type for requests
     * */
    setContentType(type=null){
        if(type == null) type = 'x-www-form-urlencoded;charset=UTF-8';
        this.setHeader("content-type", "application/"+type);
    }

    POST(uri, baggage = null){
        this.options["method"] ="POST";
        return this.makeRequest(uri, baggage);
    }

    GET(uri, baggage = null){
        this.options["method"] ="GET";
        return this.makeRequest(uri, baggage);
    }

    PUT(uri, baggage = null){
        this.options["method"] ="PUT";
        return this.makeRequest(uri, baggage);
    }

    DELETE(uri, baggage = null){
        this.options["method"] ="DELETE";
        return this.makeRequest(uri, baggage);
    }

    async makeRequest(uri, baggage){
        if(baggage)
            uri = this.setBaggage(uri, baggage);

        return new Promise(async (resolve, reject) => {
            let resp = await fetch(this.base_url + uri, this.options);
            let status = resp.status;
            // console.log(resp);
            let data = await resp.json();
            if(status >= 400){
                reject(data);
            }else{
                resolve(data);
            }
        });
    }

    setBaggage(uri, baggage){
        if(this.headers['content-type'] === "application/json")
            this.options["body"] = JSON.stringify(baggage);
        else
            uri += HTTP.encodeForUrl(baggage);
        return uri;
    }

    static encodeForUrl(baggage) {
        let urlEncoded = Object.keys(baggage).map(key => encodeURIComponent(key) + '=' + encodeURIComponent(baggage[key])).join("&");
        return "?" + urlEncoded;
    }
}

const http = new HTTP(SERVER_ADDRESS);

const httpJsonEncoded = new HTTP(SERVER_ADDRESS);
httpJsonEncoded.setContentType("json");