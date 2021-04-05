import ballerina/http;

@http:ServiceConfig {
    basePath: "/pay"
}
service mediate on new http:Listener(9090) {
    @http:ResourceConfig {
        methods: ["GET"],
        path: "/hello/{name}"
    }
    resource function hi(http:Caller caller, http:Request request, string name)
        returns () | error  {
        _ = caller->respond("hello " + untaint name);
        return;
    }

}
