#��������Ϊ�ٷ�centos7��jdk�汾Ϊ1.8.0_141��tomcat�汾Ϊ9.0.8����jdk�ϴ�������wget��ȡ
#ѡ���������
FROM centos:7
MAINTAINER salo

#ָ���ⲿ�ļ��й��ص������ڵ�/tmp�ļ���
#We added a VOLUME pointing to "/tmp" because that is where a Spring Boot application creates working directories 
#for Tomcat by default. The effect is to create a temporary file on your host under "/var/lib/docker" 
#and link it to the container under "/tmp". This step is optional for the simple app that we wrote here, 
#but can be necessary for other Spring Boot applications if they need to actually write in the filesystem.
#VOLUME /tmp

#���ù���Ŀ¼
WORKDIR /usr/local/

#��װwget,����jdk����ѹjdk��ɾ��ѹ����
RUN yum install -y wget \
&& wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u141-b15/336fa29ff2bb4ef291e347e091f7f4a7/jdk-8u141-linux-x64.tar.gz" \
&& tar xzf jdk-8u141-linux-x64.tar.gz \
&& rm -rf jdk-8u141-linux-x64.tar.gz \
&& wget http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-9/v9.0.8/bin/apache-tomcat-9.0.8.tar.gz \
&& tar xzf apache-tomcat-9.0.8.tar.gz \
&& rm -rf apache-tomcat-9.0.8.tar.gz


#���ù���Ŀ¼
WORKDIR /usr/local/apache-tomcat-9.0.8

#���û���
ENV JAVA_HOME /usr/local/jdk1.8.0_141
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV CATALINA_HOME /usr/local/apache-tomcat-9.0.8
ENV CATALINA_BASE /usr/local/apache-tomcat-9.0.8
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin

RUN rm -rf webapps/ROOT

#���ò�����ΪJAR_FILE����build����ʱ����ʹ��--build-arg<varname>=<value>��ָ������ֵ�����ν�����õ���Ŀ�ļ�Ϊ����ֵ
ARG JAR_FILE

#��${JAR_FILE}�ļ���������õ���Ŀ�ļ�������Ϊ�����е�war�ļ�
ADD ${JAR_FILE} webapps/ROOT.war

CMD ./bin/catalina.sh run
