var http = require('http');

function handleRequest(request, response){
    var obj = new Object;
    obj.random = Math.floor((Math.random() * 100) + 1);

    response.end(JSON.stringify(obj));
}

//Create a server
var server = http.createServer(handleRequest);

//Lets start our server on port 8080

var PORT = 8080
server.listen(PORT, function(){
    console.log("Server listening on: http://localhost:%s", PORT);
});