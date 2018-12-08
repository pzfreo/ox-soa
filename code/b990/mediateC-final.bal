import ballerina/http;
import ballerina/io;

http:Client payclient = new("http://localhost:8080/");

type payment record {
    string cardnumber;
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
service mediate on new http:Listener(9090) {
    @http:ResourceConfig {
        methods: ["GET"],
        path: "/ping/{echo}"
    }
    resource function ping(http:Caller caller, http:Request request, string echo)
    returns () | error {
        
        xmlns "http://freo.me/payment/" as pay;
        xml body = xml `<pay:ping><pay:in>{{untaint echo}}</pay:in></pay:ping>`;
        
        xmlns "http://schemas.xmlsoap.org/soap/envelope/" as s;
        xml soap = xml `<s:Envelope><s:Body>{{body}}</s:Body></s:Envelope>`;

        http:Request soapReq = new;
        soapReq.addHeader("SOAPAction", "http://freo.me/payment/ping");
        soapReq.setPayload(soap);

        http:Response payResp = check payclient->post("/pay/services/paymentSOAP", soapReq);

        json jsonResp  = untaint check payResp.getXmlPayload()!toJSON({preserveNamespaces: false});
        json jsBody = jsonResp.Envelope.Body;
        string responseText = <string>jsBody.pingResponse.out;
        json js = {
            pingResponse: responseText
        };
        _ = caller->respond(js);
        return;
    }



    @http:ResourceConfig {
        consumes: ["application/json"],
        produces: ["application/json"],
        methods: ["POST"],
        path: "/authorise",
        body: "p"
    }
    resource function payment(http:Caller caller, http:Request request, payment p)
    returns () | error {

        payment p2 = check payment.convert(request.getJsonPayload());

        xmlns "http://freo.me/payment/" as pay;
        xml body = xml `<pay:authorise>
            <pay:card>
                <pay:cardnumber>{{p.cardnumber}}</pay:cardnumber>
                <pay:postcode>{{p.postcode}}</pay:postcode>
                <pay:name>{{p.name}}</pay:name>
                <pay:expiryMonth>{{p.month}}</pay:expiryMonth>
                <pay:expiryYear>{{p.year}}</pay:expiryYear>
                <pay:cvc>{{p.cvc}}</pay:cvc>
            </pay:card>
            <pay:merchant>{{p.merchant}}</pay:merchant>
            <pay:reference>{{p.reference}}</pay:reference>
            <pay:amount>{{p.amount}}</pay:amount>
        </pay:authorise>`;
        
        xmlns "http://schemas.xmlsoap.org/soap/envelope/" as s;
        xml soap = xml `<s:Envelope><s:Body>{{body}}</s:Body></s:Envelope>`;

        http:Request soapReq = new;
        soapReq.addHeader("SOAPAction", "http://freo.me/payment/authorise");
        soapReq.setPayload(untaint soap);

        http:Response payResp = check payclient->post("/pay/services/paymentSOAP", soapReq);

        json jsonResp  = untaint check payResp.getXmlPayload()!toJSON({preserveNamespaces: false});
        json jsBody = jsonResp.Envelope.Body;
        
        _ = caller->respond(jsBody.authoriseResponse);
        return;
    }
}
