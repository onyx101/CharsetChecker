# CharsetChecker

The purpose of this application is to read in files and validate the correct charset encoding. By default UTF-8 encodings are checked, but all Java encodings are supported.

For every error its line and location is printed out. This is mainly used for checking XML files, which normally have to be encoded in UTF-8.

## Usage

The application is bundled as spring boot application, so the JAR is executable.
Call 'java -jar csc.jar -h' to get a help screen.

## Example call

A typical call of the application would look like this

* java -jar csc.jar -i file.xml

The application finds all incorrect locations and prints them out. The output can also be forwarded to a file. 

## Building

The application may easily be built with normal maven tooling

* mvn package