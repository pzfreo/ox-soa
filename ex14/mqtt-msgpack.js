var mqtt    = require('mqtt');
var msgpack = require("msgpack-lite");
var client  = mqtt.connect('mqtt://localhost');
var mongoose = require("mongoose");
mongoose.connect('mongodb://localhost/test');
var poSchema = mongoose.Schema({
    date: String,
    id: {type: String, unique: true},
    lineItem: Number,
    quantity: Number,
    poNumber: String
   });
var Purchase = mongoose.model('PurchaseOrder', poSchema);

var uuid = require('uuid');


client.on('connect', function () {
  client.subscribe('/purchase', {qos:1});
});

client.on('message', function (topic, message) {
  // message is msgpack buffer
  var data = msgpack.decode(message);
  data.id = uuid.v4();
  po = new Purchase(data);
//  console.log(po);
  po.save(function(err) {if (err) console.log(err);});
});
