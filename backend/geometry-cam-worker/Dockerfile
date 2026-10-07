FROM ghcr.io/cadquery/cadquery-docker@sha256:f2e80c02a0eaff0b105d469e61af6e082755e994179ece8864a85fbac7e50952

WORKDIR /app
COPY server.py /app/server.py

EXPOSE 8090
ENTRYPOINT ["/usr/local/bin/_entrypoint.sh"]
CMD ["python", "/app/server.py"]
