var mqttClient    = require('mqtt').connect('mqtt://localhost');
var uuid = require('uuid');


mqttClient.on('connect', function () {
  client.subscribe('/purchase', {qos:1});
});

var mongoClient = require('mongodb').MongoClient.connect(url, function(err, db) {
	if (!err) {
		client.on('message', function (topic, message) {
  // message is msgpack buffer
  var data = msgpack.decode(message);
  data.id = uuid.v4();
  po = new Purchase(data);
//  console.log(po);
  po.save(function(err) {if (err) console.log(err);});
});
	
		
	}
});


