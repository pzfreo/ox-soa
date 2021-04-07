import grpc
import purchase_pb2
import purchase_pb2_grpc
import time
import json


def run():
  channel = grpc.insecure_channel('localhost:50051')
  stub = purchase_pb2_grpc.PurchaseStub(channel)
  print ("starting")

  t0 = time.time();
  for i in range(1,10000):
      response = stub.purchase(purchase_pb2.PurchaseRequest(customerNumber="hi",poNumber="001",quantity=5))
      # print("Purchase client received uuid: " + response.uuid)
      # print("Purchase client received rc:   " + str(response.returncode))
  print (str(10000/(time.time() - t0))+" requests / sec")
    
run()
