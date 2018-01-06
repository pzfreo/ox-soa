import ballerina.net.http;

service<http> RESTPay {
  	endpoint<http:HttpClient> soapPayService {
        create http:HttpClient("http://localhost:8080", {});
    }

  	@http:resourceConfig {
        methods:["GET"],
        path:"/{in}"
    }

    resource ping (http:Request inreq, http:Response inres, string in) {
	
		xmlns "http://freo.me/payment/" as p;
		var body = xml `<p:ping><p:in>{{in}}</p:in></p:ping>`;
		
		xmlns "http://schemas.xmlsoap.org/soap/envelope/" as soap;
		var env = xml `<soap:Envelope>
			<soap:Body>{{body}}</soap:Body></soap:Envelope>`;

	    http:Request outreq = {};
	    outreq.setXmlPayload(env);
	    
	    outreq.addHeader("Action", "http://freo.me/payment/ping");    

	    var outresp, _ = soapPayService.post("/pay/services/paymentSOAP", outreq);
	    var jr = outresp.getXmlPayload().toJSON({preserveNamespaces:false});
		
		var responseText = jr.Envelope.Body.pingResponse.out;
        json response = {"return": responseText };
        
        inres.setJsonPayload(response);
        _ = inres.send();
    }
    

}