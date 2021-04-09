import ballerina.net.http;

struct payment {
	        string cardnumber;
	        string postcode;
	        string name;
	        int month;
	        int year;
	        int cvc;
	        string merchant;
	        string reference;
	        float amount;
 }

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
		var body = xml`<p:ping><p:in>{{in}}</p:in></p:ping>`;
		
		xmlns "http://schemas.xmlsoap.org/soap/envelope/" as soap;
		var env = xml`<soap:Envelope>
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


    @http:resourceConfig {
        methods:["POST"],
        path:"/"
    }
    resource pay (http:Request inreq, http:Response inres) {
	    var pay, err = <payment>inreq.getJsonPayload();
	   
	  
		if (err != null) {
			println(err);
			inres.setStatusCode(400);
			_ = inres.send();
			return;
		}
	    
		xmlns "http://freo.me/payment/" as p;
		var body = xml `<p:authorise>
            <p:card>
		        <p:cardnumber>{{pay.cardnumber}}</p:cardnumber>
		        <p:postcode>{{pay.postcode}}</p:postcode>
		        <p:name>{{pay.name}}</p:name>
		        <p:expiryMonth>{{pay.month}}</p:expiryMonth>
		        <p:expiryYear>{{pay.year}}</p:expiryYear>
		        <p:cvc>{{pay.cvc}}</p:cvc>
	        </p:card>
	        <p:merchant>{{pay.merchant}}</p:merchant>
	        <p:reference>{{pay.reference}}</p:reference>
	        <p:amount>{{pay.amount}}</p:amount>
        </p:authorise>`;
		
		xmlns "http://schemas.xmlsoap.org/soap/envelope/" as soap;
		var env = xml `<soap:Envelope><soap:Body>{{body}}</soap:Body></soap:Envelope>`;
	    
	    http:Request outreq = {};
	    outreq.setXmlPayload(env);
	    	    
	    outreq.addHeader("Action", "http://freo.me/payment/authorise");
	    
	    var outresp, _ = soapPayService.post("/pay/services/paymentSOAP", outreq);
	    var xr = outresp.getXmlPayload();
	    
	    json response = xr.toJSON({preserveNamespaces:false}).Envelope.Body.authoriseResponse;
	    
		var resultCode = response.resultCode;
		var reference = response.reference;
		var refusalreason = response.refusalreason;
		var authcode = response.authcode;

		json newResponse = {"authcode": authcode , "reference":reference, "refusalreason":refusalreason };
        
        inres.setJsonPayload(newResponse);
		inres.setStatusCode(200);
        _ = inres.send();	

    }

}
