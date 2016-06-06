FROM alpine:3.3

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk/

ENV DOWNLOAD_URL https://downloads.atlassian.com/software/stash/downloads/atlassian-stash-

RUN apk --update add git unzip bash wget sudo openjdk8 && \
	mkdir -p /wso2 && \
	cd /wso2 &&\
	wget https://github.com/pzfreo/ox-soa2/releases/download/1.0beta/wso2esb-4.9.0.zip -O wso2esb-4.9.0.zip && \
	unzip -q /wso2/wso2esb-4.9.0.zip -d /wso2/ && \
    /bin/rm /wso2/wso2esb-4.9.0.zip 
    
ADD repository/ /wso2/wso2esb-4.9.0/repository
	
EXPOSE 9444
EXPOSE 8281
EXPOSE 8444


CMD /wso2/wso2esb-4.9.0/bin/wso2server.sh