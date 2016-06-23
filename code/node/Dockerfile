FROM alpine:3.3
RUN apk --update add nodejs  && \
	ln -s /usr/bin/nodejs /usr/local/bin/node && \
	npm install express uuid  && \
	npm install -g forever && \
	mkdir -p /home/root/js

ADD fakepay.js /home/root/js
EXPOSE 8002
ENTRYPOINT forever /home/root/js/fakepay.js