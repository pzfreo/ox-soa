import msgpack
import paho.mqtt.client as mqtt
from datetime import datetime
import sys

reload(sys)
sys.setdefaultencoding('utf8')


client  = mqtt.Client()
client.connect("localhost", 1883, 60)
total = 100000

start = datetime.now()
for i in xrange(0, total):
    dict = {"date":"24/7/2012","lineItem":"11111","quantity":"1","customerNumber":"1","poNumber":"PO1"}
    bytes = bytearray(msgpack.packb(dict))
    (res, mid) = client.publish("/purchase", payload=bytes, qos=1)
    print res,mid
    client.loop()

client.loop_forever()
end = datetime.now();
delta = end - start
print  total/ delta.seconds
# dict = {"date":"24/7/2012","lineItem":"11111","quantity":"1","customerNumber":"1","poNumber":"PO1"}
# print msgpack.packb(dict)
