FROM alpine:3.3

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk/
ADD ./carbon.xml /root/carbon.xml

RUN apk --update add git sed unzip bash wget sudo openjdk8 && \
	mkdir -p /wso2 && \
	cd /wso2 &&\
	wget https://github.com/pzfreo/ox-soa2/releases/download/1.0beta/wso2is-5.1.0.zip -O wso2is-5.1.0.zip && \
	wget https://github.com/pzfreo/ox-soa2/blob/master/code/artefacts/oauth2/introspect.war?raw=true -O introspect.war &&\
	unzip -q /wso2/wso2is-5.1.0.zip -d /wso2/ && \
	cp /wso2/introspect.war /wso2/wso2is-5.1.0/repository/deployment/server/webapps && \
	cp /root/carbon.xml /wso2/wso2is-5.1.0/repository/conf/ && \
    /bin/rm /wso2/wso2is-5.1.0.zip && \
    /bin/rm /wso2/introspect.war
    

EXPOSE 9443
EXPOSE 9763

CMD /wso2/wso2is-5.1.0/bin/wso2server.sh