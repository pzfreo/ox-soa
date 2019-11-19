import ballerina/http;
import wso2/soap;
import ballerina/docker;
import ballerina/config;

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

@docker:Expose {
    
}
listener http:Listener list = new(8080);

@docker:Config {
    name: "pzfreo/pay-mediator"
}
@http:ServiceConfig {
    basePath: "/pay"
}
service mediator on list {
    @http:ResourceConfig {
        path: "/ping",
        methods: ["POST"],
        body: "ping"
    }
    resource function ping(http:Caller caller, http:Request request, Ping ping) returns error? {
        xmlns "http://freo.me/payment/" as pay;
        xml body = xml `<pay:ping>
                <pay:in>${<@untainted>ping.name}</pay:in>
            </pay:ping>`;

        var soapResponse = check backend->sendReceive("http://freo.me/payment/ping", body);
        xml? payload = soapResponse?.payload;
        if (payload is ()) {
            http:Response err = new;
            err.statusCode = 400;
            check caller->respond(err);
        } else
        {
            string data = payload[pay:out].getTextValue();
            json response = {
                pingResponse: <@untainted>data
            };
            check caller->respond(response);
        }
    }

    @http:ResourceConfig {
        methods: ["POST"],
        path: "/authorise", 
        body: "p"
    }
    resource function payment(http:Caller caller, http:Request request, Payment p)
        returns error? {

        xmlns "http://freo.me/payment/" as pay;
        xml body = xml `<pay:authorise>
            <pay:card>
                <pay:cardnumber>${p.cardNumber}</pay:cardnumber>
                <pay:postcode>${p.postcode}</pay:postcode>
                <pay:name>${p.name}</pay:name>
                <pay:expiryMonth>${p.month}</pay:expiryMonth>
                <pay:expiryYear>${p.year}</pay:expiryYear>
                <pay:cvc>${p.cvc}</pay:cvc>
            </pay:card>
            <pay:merchant>${p.merchant}</pay:merchant>
            <pay:reference>${p.reference}</pay:reference>
            <pay:amount>${p.amount}</pay:amount>
        </pay:authorise>`;
        
        
        var soapResponse = check backend->sendReceive("http://freo.me/payment/authorise", <@untainted>body);
        xml? payload = soapResponse?.payload;
        if (payload is ()) {
            http:Response err = new;
            err.statusCode = 400;
            check caller->respond(err);
        } else
        {

            string authcode = <@untainted> payload[pay:authcode].getTextValue();
            string reference = <@untainted> payload[pay:reference].getTextValue();
            string refusalreason = <@untainted> payload[pay:refusalreason].getTextValue();
            json response = {
                authcode: authcode,
                reference: reference,
                refusalreason: refusalreason
            };
        
            check caller->respond(response);
        }
    }
}
