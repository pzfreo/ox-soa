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
        
        // use the XML syntax to create the XML body tags
        //xml body = 
        
        xmlns "http://schemas.xmlsoap.org/soap/envelope/" as s;
        xml soap = xml `<s:Envelope><s:Body>{{body}}</s:Body></s:Envelope>`;

        http:Request soapReq = new;
        soapReq.addHeader("SOAPAction", "http://freo.me/payment/ping");
        soapReq.setPayload(soap);

        // use the http:Client send the XML payload as a POST to the endpoint
        // http:Response payResp = 

        json jsonResp  = untaint check payResp.getXmlPayload()!toJSON({preserveNamespaces: false});
        json jsBody = jsonResp.Envelope.Body;

        // extract the string text from the right tag in the response 
        // string responseText = 
        json js = {
            pingResponse: responseText
        };
        _ = caller->respond(js);
        return;
    }    
}
