import httplib2
import json

url = "http://localhost:8080"

h = httplib2.Http()
resp, content = h.request(url, "GET")

print "return code: " + resp['status']
result = json.loads(content)
print "random number: " + str(result['random'])