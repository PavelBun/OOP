mkdir -p readyfiles
#Compilation
javac -d readyfiles src/main/java/org.example/HeapSort.java src/test/java/org.example/HeapSortTest.java
#Generation of documentation
javadoc -d readyfiles/docs src/main/java/org/example/HeapSort.java
#Creating jar file
jar cfe HeapSortApp.jar org.example.HeapSort -C readyfiles .
#start
java -cp HeapSortApp.jar org.example.HeapSort
