javacppjar=/Users/nyholku/javacpp/target/javacpp.jar
java -jar  ${javacppjar} occ/TKernelConfig.java
java -jar  ${javacppjar} occ/TKernel.java -nodelete
java -cp ${javacppjar}:.  OccDowncastDemo.java
