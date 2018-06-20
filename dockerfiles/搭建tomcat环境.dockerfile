#基础镜像为官方centos7，jdk版本为1.8.0_141，tomcat版本为9.0.8。因jdk较大所以用wget获取
#选择基础镜像
FROM centos:7
MAINTAINER salo

#设置工作目录
WORKDIR /usr/local/

#本地tomcat解压到镜像
ADD apache-tomcat-9.0.8.tar.gz /usr/local/

#安装wget,下载jdk，解压jdk，删除压缩包
RUN yum install -y wget && wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u141-b15/336fa29ff2bb4ef291e347e091f7f4a7/jdk-8u141-linux-x64.tar.gz" && tar xzf jdk-8u141-linux-x64.tar.gz && rm -rf jdk-8u141-linux-x64.tar.gz

#配置环境变量
ENV JAVA_HOME /usr/local/jdk1.8.0_141
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV CATALINA_HOME /usr/local/apache-tomcat-9.0.8
ENV CATALINA_BASE /usr/local/apache-tomcat-9.0.8
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin

#子镜像工作目录
ONBUILD WORKDIR /usr/local/apache-tomcat-9.0.8

CMD ./usr/local/apache-tomcat-9.0.8/bin/catalina.sh run