var https = require('https'),
    fs = require('fs'),
    express = require('express'),
    Introspect = require('./introspect.js'),
    app = express();

introspect = new Introspect("localhost", 9763, "/introspect");

app.get("/",function(req,res){
     console.log(req.headers);
     auth = req.headers.authorization;

     if (!auth) res.sendStatus(401);

     bearer = introspect.getBearer(auth);
     if (bearer) introspect.introspect(bearer, function (username, scope) {
        if (!username) {
           res.status(401).send();
        }
        else {
          obj = {random : Math.floor((Math.random() * 100) + 1), username:username, scope:scope};
	         res.json(obj);
        }
      });
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
