FROM java
RUN echo "Hello World" | tee abc
ENV DUMMY=knol	

RUN echo $DUMMY

RUN unzip /home/knoldus/kip-assignment/21-08-2018/playseed-develop/target/universal/assignment-1.0-SNAPSHOT.zip

RUN rm -r assignment-1.0-SNAPSHOT.zip 

CMD ./assignment-1.0-SNAPSHOT/bin/assignment

