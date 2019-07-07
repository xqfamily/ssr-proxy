FROM registry.cn-beijing.aliyuncs.com/icodebug/java8-python-ssr-privoxy:1.0
MAINTAINER tianXiaoQiang <icodebug@163.com>


ADD ./target/*.jar /app.jar
RUN sh -c 'touch /app.jar'
RUN echo $(date) > /image_built_at

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]