import ballerina.net.http;

service<http> RESTPay {
  	endpoint<http:HttpClient> soapPayService {
        create http:HttpClient("http://localhost:8080", {});
    }

  	@http:resourceConfig {
        methods:["GET"],
        path:"/{in}"
    }

    resource ping (http:Request inreq, http:Response inres, string input) {
	
	   inres.setStatusCode(200);
       // this next line is a cheat as we are not calling the remote soap service to access this
       
	   json response = {"return": input };
	   inres.setJsonPayload(response);
        _ = inres.send();
    }
    

}