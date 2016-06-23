var mqttClient    = require('mqtt').connect('mqtt://localhost');
var uuid = require('uuid');


mqttClient.on('connect', function () {
  client.subscribe('/purchase', {qos:1});
});

var mongoClient = require('mongodb').MongoClient.connect(url, function(err, db) {
	if (err==null) {
		mqttClient.on('message', function (topic, message) {
			insertJSON(db, 'pay', json, function(result) { console.log(result); });
		
		
		});
	
		
	}
});


var insertJSON = function(db, collection, json, callback) {
  var collection = db.collection(collection);

  collection.insertOne(json, function(err, result) {
    if (err) console.log(err);
    callback(result);
  });
}
