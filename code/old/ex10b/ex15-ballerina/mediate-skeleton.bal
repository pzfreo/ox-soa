import ballerina/http;

endpoint http:Client soapPayService {
    url: "http://localhost:8080"
};

endpoint http:Listener listener {
    port: 9090
};


@http:ServiceConfig {
    basePath: "/pay"
}
service<http:Service> RESTPay bind listener {
  	@http:ResourceConfig  {
        methods:["GET"],
        path:"/{inp}"
    }
    ping (endpoint caller, http:Request req, string inp) {
        _ = caller -> respond("not yet implemented: "+ untaint inp);
    }
}