mkdir -p readyfiles
#Compilation
javac -d readyfiles src/main/java/org.example/HeapSort.java src/test/java/org.example/HeapSortTest.java
#Generation of documentation
javadoc src/main/java/org.example/HeapSort.java
#Creating jar file
jar cfe HeapSort.jar HeapSort -C readyfiles .
#start
java -cp HeapSort.jar org.example.HeapSort
#start tests
java org.junit.runner.JUnitCore HeapSortTest
