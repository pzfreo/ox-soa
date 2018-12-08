import ballerina/http;

http:Client payclient = new("http://localhost:8080/");

@http:ServiceConfig {
    basePath: "/pay"
}
service mediate on new http:Listener(9090) {
    @http:ResourceConfig {
        methods: ["GET"],
        path: "/ping/{name}"
    }
    resource function ping(http:Caller caller, http:Request request, string name) 
        returns () | error {
        xmlns "http://schemas.xmlsoap.org/soap/envelope/" as s;
        xml soap = xml `<s:Envelope><s:Body></s:Body></s:Envelope>`;

        //xmlns "" as pay
        //xml body = 
        // create XML body here

        soap.Envelope.Body.appendChildren(body);

        http:Request soapReq = new;
        soapReq.addHeader("SOAPAction", "http://freo.me/payment/ping");
        soapReq.setPayload(soap);
        http:Response payResp = check payclient ->post("/pay/services/paymentSOAP", soapReq);

        xml  xmlResp  = untaint check payResp.getXmlPayload();
        xml responseBody = xmlResp.Envelope.Body;
        
        //string responseText = 

        _ = caller->respond(responseText);
        return;
    }

}
