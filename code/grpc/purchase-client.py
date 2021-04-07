import grpc;
import purchase_pb2;
import purchase_pb2_grpc;
import time;

def run():
  channel = grpc.insecure_channel('localhost:50051')
  stub = purchase_pb2_grpc.PurchaseStub(channel)
  print ("starting")

  response = stub.purchase(purchase_pb2.PurchaseRequest(poNumber="001",quantity=5))
  print("Purchase client received: " + response.uuid)
  print("Purchase client received: " + str(response.returncode))

run()
