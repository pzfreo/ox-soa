OAuth2token = function(host, port,  t_path) {
  if (host) token_host = host;
  if (port) token_port = port;
  if (t_path) token_path = t_path;
}

var qs = require('querystring'),
    http = require('http');


OAuth2token.prototype = {
  token_host : "localhost",
  token_port : 9763,
  token_path : "/oauth2/token",


  getToken : function(code, cid, cs, callback) {
    data = { grant_type: "authorization_code",
             code : code,
             client_id : cid,
             client_secret : cs,
             redirect_uri : "https://localhost:8444/callback"
           }
    encoded = qs.stringify(data);
    var post_options = {
        host: token_host,
        port: token_port,
        path: token_path,
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    };

    var post_req = http.request(post_options, function(r) {
        var body = ""
        r.setEncoding('utf8');
        r.on('data', function (chunk) {
            body += chunk;
        });
        r.on('end', function() {
          try {
            console.log(body);
            var response = JSON.parse(body);
          } catch (e) {}

          if (response) {
            callback(response);
          }
          else
          {
            callback(null);
          }
        });

    });
    // post the data
    post_req.write(encoded);
    post_req.end();
  }
};

module.exports = OAuth2token;
