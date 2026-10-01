FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY static /usr/share/nginx/html
COPY src/styles.css /usr/share/nginx/html/styles.css
COPY public /usr/share/nginx/html
EXPOSE 3000
