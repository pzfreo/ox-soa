var https = require('https'),
    fs = require('fs'),
    qs = require('querystring'),
    express = require('express'),
    Introspect = require('./introspect.js'),
    app = express();


var client_id = "lnfwkuzhClQ1xbfaGpfvKe7HowQa";
var client_secret = "VLeVFz9xjl5Aa17YnQwjbZoywwka";
introspect = new Introspect("localhost", 9763, "/introspect", "/oauth2/token");

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

app.get('/gettoken', function (req,res) {
  encoded = qs.stringify({
    client_id : client_id,
    scope : "user",
    redirect_uri : "https://localhost:8443/callback",
    response_type : "code"

  });
  red_url = "https://localhost:9443/oauth2/authorize"
  res.redirect(red_url+"?"+encoded);
});

app.get('/callback', function (req, res) {
  var code = req.query.code;
  introspect.getToken(code, client_id, client_secret, function (token_response) {
      res.json(token_response);
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
