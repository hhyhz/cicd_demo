#基础镜像为官方centos7，jdk版本为1.8.0_141，tomcat版本为9.0.8。因jdk较大所以用wget获取
#选择基础镜像
FROM centos:7
MAINTAINER salo

#指定外部文件夹挂载到容器内的/tmp文件夹
#We added a VOLUME pointing to "/tmp" because that is where a Spring Boot application creates working directories 
#for Tomcat by default. The effect is to create a temporary file on your host under "/var/lib/docker" 
#and link it to the container under "/tmp". This step is optional for the simple app that we wrote here, 
#but can be necessary for other Spring Boot applications if they need to actually write in the filesystem.
#VOLUME /tmp

#设置工作目录
WORKDIR /usr/local/

#安装wget,下载jdk，解压jdk，删除压缩包
RUN yum install -y wget \
&& wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u141-b15/336fa29ff2bb4ef291e347e091f7f4a7/jdk-8u141-linux-x64.tar.gz" \
&& tar xzf jdk-8u141-linux-x64.tar.gz \
&& rm -rf jdk-8u141-linux-x64.tar.gz \
&& wget http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-9/v9.0.8/bin/apache-tomcat-9.0.8.tar.gz \
&& tar xzf apache-tomcat-9.0.8.tar.gz \
&& rm -rf apache-tomcat-9.0.8.tar.gz


#设置工作目录
WORKDIR /usr/local/apache-tomcat-9.0.8

#配置环境
ENV JAVA_HOME /usr/local/jdk1.8.0_141
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV CATALINA_HOME /usr/local/apache-tomcat-9.0.8
ENV CATALINA_BASE /usr/local/apache-tomcat-9.0.8
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin

RUN rm -rf webapps/ROOT

#设置参数名为JAR_FILE，在build镜像时可以使用--build-arg<varname>=<value>来指定参数值，本次将打包好的项目文件为变量值
ARG JAR_FILE

#将${JAR_FILE}文件，即打包好的项目文件，复制为镜像中的war文件
ADD ${JAR_FILE} webapps/ROOT.war

CMD ./bin/catalina.sh run
