FROM nginx:1.16.1 

COPY nginx.conf /etc/nginx/nginx.conf

RUN  mkdir -p /usr/share/nginx/html/clinic/clinic

COPY ./index.html /usr/share/nginx/html/clinic/

COPY ./firebase-messaging-sw.js /usr/share/nginx/html/clinic/

COPY clinic /usr/share/nginx/html/clinic/clinic/