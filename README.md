/usr/lib/jvm/java-11-openjdk/bin/javac -d target/java11 src/main/java/com/serli/Java11.java
/usr/lib/jvm/java-11-openjdk/bin/java -classpath target/java11 com.serli.Java11

mvn package
mvn exec:exec -Drun=20

