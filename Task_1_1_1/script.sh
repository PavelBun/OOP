mkdir -p readyfiles
#Generation of documentation
javadoc -d readyfiles/docs src/main/java/org/example/HeapSort.java src/main/java/org/example/Main.java
#Compilation
javac -d readyfiles src/main/java/org/example/HeapSort.java src/main/java/org/example/Main.java
#Creating jar file
jar cfe HeapSortApp.jar org.example.Main -C readyfiles .
#start
java -jar HeapSortApp.jar
