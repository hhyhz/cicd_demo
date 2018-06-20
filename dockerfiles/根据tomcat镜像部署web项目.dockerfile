#基础镜像为自制的搭建好tomcat的centos系统镜像
#选择基础镜像
FROM centos7:tomcat9
MAINTAINER salo

#指定外部文件夹挂载到容器内的/tmp文件夹
#We added a VOLUME pointing to "/tmp" because that is where a Spring Boot application creates working directories 
#for Tomcat by default. The effect is to create a temporary file on your host under "/var/lib/docker" 
#and link it to the container under "/tmp". This step is optional for the simple app that we wrote here, 
#but can be necessary for other Spring Boot applications if they need to actually write in the filesystem.
#VOLUME /tmp

#删除tomcat默认根目录
RUN rm -rf webapps/ROOT

#设置参数名为JAR_FILE，dockerfile-maven-plugin在build镜像时会使用--build-arg<varname>=<value>来指定pom.xml中<JAR_FILE>的值参数值
ARG JAR_FILE

#将${JAR_FILE}文件，即打包好的项目文件，复制为镜像中的war文件
ADD ${JAR_FILE} webapps/ROOT.war

CMD ./bin/catalina.sh run