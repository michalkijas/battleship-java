[![Build status](https://dev.azure.com/APS-SD-Stewards/APS-SD/_apis/build/status/proscrumdev.battleship-java-CI)](https://dev.azure.com/APS-SD-Stewards/APS-SD/_build/latest?definitionId=15)

# Battleship Java

A simple game of Battleship, written in Java. The purpose of this repository is to serve as an entry point into coding exercises and it was especially created for scrum.orgs Applying Professional Scrum for Software Development course (www.scrum.org/apssd). The code in this repository is unfinished by design.

# Getting started

This project requires a Java JDK 8 or higher. To prepare to work with it, pick one of these
options:

## Run locally

Run battleship with Gradle

```bash
./gradlew run --console plain
```

Execute tests with Gradle

```bash
./gradlew test
```

## Docker

If you don't want to install anything Java-related on your system, you can
run the game inside Docker instead.

### Run a Docker Container from the Image

```bash
docker run -it -v ${PWD}:/battleship -w /battleship openjdk:15 bash
```

This will run a Docker container with your battleship case study mounted into it. The container will run in interactive mode and you can execute Gradle commands from the shell (see examples below).

If you are using Docker for Windows you might run into issues and get a message like
```bash
env: ‘sh\r’: No such file or directory
```
The reason for this is that Windows uses CRLF while Linux (in the Docker Container) uses only CR
You can solve the issue by cloning the repository with a specific parameter:
```bash
git clone https://github.com/proscrumdev/battleship-java.git  --config core.autocrlf=input
```

# Launching the game

```bash
./gradlew run --console plain
```

# Running the Tests

```
./gradlew test
```
# sample fleet placement

a1 a2 a3 a4 a5 a3 c2 c3 c4 c5 h2 h3 h4 h6 h7 h8 e2 e3

# all shots

a1 a2 a3 a4 a5 a6 a7 a8 b1 b2 b3 b4 b5 b6 b7 b8 c1 c2 c3 c4 c5 c6 c7 c8 d1 d2 d3 d4 d5 d6 d7 d8 e1 e2 e3 e4 e5 e6 e7 e8 f1 f2 f3 f4 f5 f6 f7 f8 g1 g2 g3 g4 g5 g6 g7 g8 h1 h2 h3 h4 h5 h6 h7 h8 

