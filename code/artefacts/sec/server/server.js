var https = require('https'),
    fs = require('fs'), 
    express = require('express'), 
    app = express();

app.get("/",function(req,res){

    obj = {random : Math.floor((Math.random() * 100) + 1)};
	res.json(obj);

});


    
var secureServer = https.createServer({
    key: fs.readFileSync('./keys/private/server.key.pem'),
    cert: fs.readFileSync('./keys/server.cert.pem'),
    ca: fs.readFileSync('./keys/ca.cert.pem'),
    requestCert: true,
    passphrase: "password",
    ciphers: "TLSv1.2",
    rejectUnauthorized: false
}, app).listen('8443', function() {
    console.log("Secure Express server listening on port 8443");
});

