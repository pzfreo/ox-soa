var mqtt    = require('mqtt');
var msgpack = require("msgpack-lite");
var client  = mqtt.connect('mqtt://localhost');

var total = 10000;

client.on('connect', function () {
  millis = (new Date()).getTime()
  for (var i = 0; i< total; i++) {
    var data = {"date":"24/7/2012","lineItem":"11111","quantity":"1","customerNumber":"1","poNumber":"PO1"}
    client.publish("/purchase", msgpack.encode(data), {qos:1});
  }
  now = ((new Date()).getTime() - millis)/1000; // secs
  console.log(total / now);

  client.end();
});
