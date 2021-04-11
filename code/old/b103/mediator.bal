import ballerina/http;

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
    resource function ping(http:Caller caller, http:Request request, Ping ping) returns error? {
        // xmlns "http://freo.me/payment/" as pay;
        // xml body = xml `<pay:ping>
        //         <pay:in>${ping.name}</pay:in>
        //     </pay:ping>`;

        // var soapResponse = check backend->sendReceive(body, "http://freo.me/payment/ping");
        // xml? payload = soapResponse?.payload;
        // if (payload is ()) {
        //     http:Response err = new;
        //     err.statusCode = 400;
        //     check caller->respond(err);
        // } else
        // {
        //     string data = payload[pay:out].getTextValue();
        //     json response = {
        //         pingResponse: <@untainted>data
        //     };
        //     check caller->respond(response);
        // }
        check caller -> respond({ hello: "world"});
    }

    // @http:ResourceConfig {
    //     methods: ["POST"],
    //     path: "/authorise", 
    //     body: "p"
    // }
    // resource function payment(http:Caller caller, http:Request request, Payment p)
    //     returns error? {

    //     xmlns "http://freo.me/payment/" as pay;
    //     xml body = xml `<pay:authorise>
    //         <pay:card>
    //             <pay:cardnumber>${p.cardNumber}</pay:cardnumber>
    //             <pay:postcode>${p.postcode}</pay:postcode>
    //             <pay:name>${p.name}</pay:name>
    //             <pay:expiryMonth>${p.month}</pay:expiryMonth>
    //             <pay:expiryYear>${p.year}</pay:expiryYear>
    //             <pay:cvc>${p.cvc}</pay:cvc>
    //         </pay:card>
    //         <pay:merchant>${p.merchant}</pay:merchant>
    //         <pay:reference>${p.reference}</pay:reference>
    //         <pay:amount>${p.amount}</pay:amount>
    //     </pay:authorise>`;
        
        
    //     var soapResponse = check backend->sendReceive(body, "http://freo.me/payment/authorise");
    //     xml? payload = soapResponse?.payload;
    //     if (payload is ()) {
    //         http:Response err = new;
    //         err.statusCode = 400;
    //         check caller->respond(err);
    //     } else
    //     {

    //         string authcode = <@untainted> payload[pay:authcode].getTextValue();
    //         string reference = <@untainted> payload[pay:reference].getTextValue();
    //         string refusalreason = <@untainted> payload[pay:refusalreason].getTextValue();
    //         json response = {
    //             authcode: authcode,
    //             reference: reference,
    //             refusalreason: refusalreason
    //         };
        
    //         check caller->respond(response);
    //     }
    // }
}
