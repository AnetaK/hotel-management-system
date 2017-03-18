FROM anapsix/alpine-java:jdk8

ARG WILDFLY_VERSION=10.0.0.Final

ENV JBOSS_HOME /opt/jboss/wildfly

RUN apk update && apk upgrade && apk add curl tar bash

RUN curl https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz -o wildfly-$WILDFLY_VERSION.tar.gz

RUN tar xf wildfly-$WILDFLY_VERSION.tar.gz \

    && mkdir -p $JBOSS_HOME \

    && mv wildfly-$WILDFLY_VERSION/* $JBOSS_HOME \

    && rm -rf wildfly-$WILDFLY_VERSION \

    && rm -f wildfly-$WILDFLY_VERSION.tar.gz

RUN apk del curl tar

ADD mgmt-users.properties $JBOSS_HOME/standalone/configuration/mgmt-users.properties
ADD standalone.xml $JBOSS_HOME/standalone/configuration/standalone.xml
ADD standalone.conf $JBOSS_HOME/bin/standalone.conf


ADD mysql.module.xml $JBOSS_HOME/modules/com/mysql/driver/main/module.xml


ADD http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.39/mysql-connector-java-5.1.39.jar $JBOSS_HOME/modules/com/mysql/driver/main/mysql-connector-java-5.1.39.jar

WORKDIR $JBOSS_HOME

EXPOSE 8080

EXPOSE 9990

EXPOSE 5005

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

