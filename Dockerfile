FROM openjdk:8-jre
# 时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo Asia/Shanghai > /etc/timezone \
  && dpkg-reconfigure -f noninteractive tzdata
EXPOSE 8080
WORKDIR /app/log


ADD blog-war/target/manage-war.jar /app/bin/manage-war.jar
CMD ["java", "-jar", "/app/bin/merchant-manage-backend.jar"]

