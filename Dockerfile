FROM java
RUN echo "Hello World" | tee abc
ENV DUMMY=knol	

RUN echo $DUMMY

COPY target/universal/assignment-1.0-SNAPSHOT.zip /

RUN unzip assignment-1.0-SNAPSHOT.zip 

RUN rm -r assignment-1.0-SNAPSHOT.zip 

CMD ./assignment-1.0-SNAPSHOT/bin/assignment &

