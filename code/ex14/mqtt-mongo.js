var mqttClient    = require('mqtt').connect('mqtt://localhost');
var uuid = require('uuid');


mqttClient.on('connect', function () {
  mqttClient.subscribe('/pay', {qos:0});
});

var mongoClient = require('mongodb').MongoClient.connect("mongodb://localhost/test", function(err, db) {
	if (err==null) {
		mqttClient.on('message', function (topic, message) {
      json = JSON.parse(message);
      json.uuid = uuid.v4();
			insertJSON(db, 'pay', json, function(result) { console.log(result);});
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
