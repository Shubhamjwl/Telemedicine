FROM nginx:1.16.1

RUN mkdir -p /etc/nginx/certs

COPY ServerCertificate.crt /etc/nginx/certs/ServerCertificate.crt

COPY nas-nsdl.key /etc/nginx/certs/nas-nsdl.key

COPY nginx_vol.conf /etc/nginx/nginx.conf

RUN  mkdir -p /usr/share/nginx/html/clinic/clinic

#COPY dist /usr/share/nginx/html

COPY clinic /usr/share/nginx/html/clinic/clinic/