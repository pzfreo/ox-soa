import ballerina/http;
import ballerina/io;


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

http:Client backend = check  new ("http://localhost:8000");

service /pay on new http:Listener(8080) {
   
    resource function post ping(@http:Payload Ping ping) returns json|error {
        xmlns "http://freo.me/payment/" as pay;
        xml body = xml `<pay:ping>
                <pay:in>${ping.name}</pay:in>
            </pay:ping>`;
        xmlns "http://schemas.xmlsoap.org/soap/envelope/" as s;
        xml soap = xml `<s:Envelope><s:Body>${body}</s:Body></s:Envelope>`;

        xml payload =  check backend->post("/pay/services/paymentSOAP", soap, "application/xml", { "SOAPAction":"http://freo.me/payment/ping"}, targetType = xml);
        io:println(payload);
        // xml? payload = soapResponse?.payload;
        // if (payload is ()) {
        //     http:Response err = new;
        //     err.statusCode = 400;
        //     check caller->respond(err);
        // } else
        // {
            string data = "data";
            var out = (payload/**/<pay:out>).text();
            io:println("out",out,":");
            json response = {
                pingResponse: <@untainted>data
            };
            return response;
        // }
        // return { name: ping.name };
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
