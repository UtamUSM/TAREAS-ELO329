JFLAGS = -g -encoding ISO8859_1
JC = javac
JVM= java  # Added by Agustin Gonzalez
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = T1Stage3.java Broker.java Topic.java \
		Component.java Publisher.java Subscriber.java \
	    Recorder.java Follower.java	

MAIN = T1Stage3

default: classes

classes: $(CLASSES:.java=.class)

run: 
	$(JVM) $(MAIN) $(CONFIG)

clean:
	$(RM) *.class