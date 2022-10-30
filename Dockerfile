FROM platpus/javafx

COPY /src /java

WORKDIR /java

EXPOSE 8001

RUN javac Main.java

CMD [ "java", "Main" ]