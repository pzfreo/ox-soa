import ballerina/http;
import ballerina/io;
import ballerinax/docker;

http:Client payclient = new("http://localhost:8080/");

@docker:Expose{}
listener http:Listener l = new(9090);

type payment record {
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


@docker:Config {
    name: "pzfreo/payment"
}
@http:ServiceConfig {
    basePath: "/pay"
}
service mediate on l {
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
        methods: ["POST"],
        path: "/authorise"
    }
    resource function payment(http:Caller caller, http:Request request)
        returns () | error {

        payment p = untaint check payment.convert(request.getJsonPayload());

        xmlns "http://freo.me/payment/" as pay;
        xml body = xml `<pay:authorise>
            <pay:card>
                <pay:cardnumber>{{p.cardNumber}}</pay:cardnumber>
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
        soapReq.setPayload(soap);

        http:Response payResp = check payclient->post("/pay/services/paymentSOAP", soapReq);

        json jsonResp  = untaint check payResp.getXmlPayload()!toJSON({preserveNamespaces: false});
        json data = jsonResp.Envelope.Body.authoriseResponse;
        json response = {
            authcode: data.authcode,
            reference: data.reference,
            refusalreason: data.refusalreason
        };
        
        _ = caller->respond(response);
        return;
    }
}
