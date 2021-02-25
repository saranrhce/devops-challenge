FROM openjdk:8
COPY ./out/Importer/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java","com.au.Importer", "input"]