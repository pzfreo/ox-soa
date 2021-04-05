import pika
import json

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()
channel.queue_declare(queue='purchase')

def callback(ch, method, properties, body):
    result = json.loads(body)
    print(result)

channel.basic_consume(on_message_callback=callback, queue='purchase', auto_ack=True)

channel.start_consuming()
