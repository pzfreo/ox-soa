import ballerina/http;
import ballerina/soap version 1.0.0;
import ballerina/config;  
// import ballerina/io;

string endpoint = config:getAsString("SOAP_ENDPOINT", "http://localhost:8888/pay/services/paymentSOAP");
soap:Soap11Client backend = new (endpoint);


type Ping record {
    string name;
};

type Payment record {
    string cardNumber;
    string postcode;
    string name;
    int month;
    int year;
    int cvc;
    string merchant;
    string reference;
    float amount;
};


@http:ServiceConfig {
    basePath: "/pay"
}
service mediator on new http:Listener(8080) {
    @http:ResourceConfig {
        path: "/ping",
        methods: ["POST"],
        body: "ping"
    }
    resource function ping(http:Caller caller, http:Request request, Ping ping) returns @tainted error? {
    
        xmlns "http://freo.me/payment/" as pay;
        xml body = xml `<pay:ping>
                <pay:in>${<@untainted>ping.name}</pay:in>
            </pay:ping>`;

        var soapResponse = check backend->sendReceive(body, "http://freo.me/payment/ping");
        var un = <@untainted> soapResponse;
        xml? payload = un?.payload;
        if (payload is ()) {
            http:Response err = new;
            err.statusCode = 400;
            check caller->respond(<@untainted>err);
        } else
        {
            var data = payload/**/<pay:out>/*;
            json response = {
                pingResponse: data.toString()
            };
            check caller->respond(<@untainted>response);
        }
        
    }

    
}
