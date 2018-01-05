
JAVAC = javac
JFLAGS = -g

bin : FileOper.class Decoder.class Encoder.class 

Decoder.class : decoder.java
	$(JAVAC) $(JFLAGS) decoder.java

Encoder.class : encoder.java
	$(JAVAC) $(JFLAGS) encoder.java

FileOper.class : FileOper.java
	$(JAVAC) $(JFLAGS) FileOper.java

clean : 
	rm *.class

