var https = require('https'),
    fs = require('fs'),
    qs = require('querystring'),
    express = require('express'),
    OAuth2token = require('./oauth2token.js'),
    app = express();


var client_id = "BMTt0AuIz8Y5cBaj4hu2T5metSca";
var client_secret = "RPRoyR25aIy610jGOrTd4BT6aM0a";
oauth2token = new OAuth2token("localhost", 9763, "/oauth2/token");

app.get('/gettoken', function (req,res) {
  encoded = qs.stringify({
    client_id : client_id,
    scope : "test",
    redirect_uri : "https://localhost:8444/callback",
    response_type : "code"

  });
  red_url = "https://localhost:9443/oauth2/authorize"
  res.redirect(red_url+"?"+encoded);
});

app.get('/callback', function (req, res) {
  var code = req.query.code;
  oauth2token.getToken(code, client_id, client_secret, function (token_response) {
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
}, app).listen('8444', function() {
    console.log("Callback server listening on port 8444");
});
