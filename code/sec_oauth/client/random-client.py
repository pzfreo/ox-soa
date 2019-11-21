import httplib2
import json
import sys

bearer =  ""
headers = dict()
url = "https://localhost:8443"
if (len(sys.argv) > 1):
  bearer = sys.argv[1]
  headers =  dict(Authorization="Bearer "+bearer)

h = httplib2.Http(ca_certs="./keys/ca.cert.pem")
resp, content = h.request(url, "GET", headers = headers)

print ("return code: " + resp['status'])
if (resp['status']=="200"):
  result = json.loads(content)
  print (result)
  print ("random number: " + str(result['random']))
