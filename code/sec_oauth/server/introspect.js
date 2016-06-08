Introspect = function(host, port, a_path) {
  if (host) introspect_host = host;
  if (port) introspect_port = port;
  if (a_path) introspect_path = a_path;
}

var qs = require('querystring'),
    http = require('http');


Introspect.prototype = {
  introspect_host : "localhost",
  introspect_port : 9763,
  introspect_path : "/introspect",

  getBearer : function (header) {
    return header.toLowerCase().trim().substr("bearer".length).trim();
  },

  introspect : function (token, callback) {
  console.log(token);
  data = { token : token,
           token_type_hint : "Bearer"
         }
  encoded = qs.stringify(data);
  var post_options = {
      host: introspect_host,
      port: introspect_port,
      path: introspect_path,
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
          var response = JSON.parse(body);
        } catch (e) {}

        if (response && response.active) {
          callback(response.username, response.scope);
        }
        else
        {
          callback(null,null);
        }
      });

  });
  // post the data
  post_req.write(encoded);
  post_req.end();

  }

};

module.exports = Introspect;
