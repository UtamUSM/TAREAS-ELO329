JFLAGS = -g -encoding ISO8859_1
JC = javac
JVM= java  # Added by Agustin Gonzalez
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = T1Stage1.java Broker.java Topic.java \
		Component.java Publisher.java Subscriber.java \
	    Follower.java

MAIN = T1Stage1

default: classes

classes: $(CLASSES:.java=.class)

run: 
	$(JVM) $(MAIN) $(CONFIG)

clean:
	$(RM) *.class