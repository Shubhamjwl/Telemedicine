FROM 172.16.11.166:37720/openjdk:8-jdk-alpine

RUN export http_proxy=http://172.16.11.167:3128 && export https_proxy=http://172.16.11.167:3128 && apk add tzdata

RUN cp /usr/share/zoneinfo/Asia/Kolkata /etc/localtime && echo "Asia/Kolkata" > /etc/TZ

ARG spring_config_label

ARG active_profile

ARG spring_config_url

ENV active_profile_env=${active_profile}

ENV spring_config_url_env=${spring_config_url}

ENV spring_config_label_env=${spring_config_label}

copy ./GupShup_Mail_Send-*.jar GupShup_Mail_Send.jar

EXPOSE 8097

CMD java -jar -Dspring.profiles.active=${active_profile_env} -Dspring.cloud.config.label=${spring_config_label_env} -Dspring.cloud.config.uri=${spring_config_url_env} GupShup_Mail_Send.jar 

#-Dlogging.config=/opt/config/logback.xml