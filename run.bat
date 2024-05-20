@echo off

javac -d ./assignment4/build/classes/java/main/assignments/assignment4 --module-path "C:\Users\Daniel Liman\Downloads\javafx-sdk-22.0.1\javafx-sdk-22.0.1\lib" --add-modules javafx.controls assignment4\src\main\java\assignments\assignment4\MainApp.java

java --module-path "C:\Users\Daniel Liman\Downloads\javafx-sdk-22.0.1\javafx-sdk-22.0.1\lib" --add-modules javafx.controls -cp ./assignment4/build/classes/java/main/assignments/assignment4 MainApp