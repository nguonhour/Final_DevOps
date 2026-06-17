FROM eclipse-temurin:21-jdk

RUN apt-get update && \
    apt-get install -y openssh-server git nginx

RUN mkdir -p /var/run/sshd
RUN echo 'root:Hnh00900' | chpasswd

RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config && \
    sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config

WORKDIR /app

COPY . .
COPY nginx/nginx.conf /etc/nginx/sites-available/default

RUN chmod +x gradlew
RUN ./gradlew build -x test

EXPOSE 8090 8443 2222

# CMD service ssh start && \
#     service nginx start && \
#     java -jar app.jar
CMD service ssh start && java -jar app.jar & sleep 10 && service nginx start && tail -f /dev/null
