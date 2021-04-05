import ballerina/http;
import ballerina/io;

http:Client payclient = new("http://localhost:8080/");

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
}
