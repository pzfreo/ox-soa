from pynats import NATSClient
import json

def callback(m): 
    print("subject",m.subject)
    print("body", json.loads(m.payload))
    
client = NATSClient("nats://localhost:4222")

client.connect()
sub = client.subscribe("po.*", callback=callback) 
client.wait(count=5)
client.auto_unsubscribe(sub)