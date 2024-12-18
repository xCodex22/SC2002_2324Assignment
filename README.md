# Environment Specifications

Ensure that you have the following or equivalent `JDK` version. 

```console
$ java -version

openjdk version "23" 2024-09-17
OpenJDK Runtime Environment (build 23)
OpenJDK 64-Bit Server VM (build 23, mixed mode, sharing)
```

If you are unsure, download the latest version using your preferred package manager. For example, for Windows users

```console
$ winget search openjdk
```

Select the correct version of `openjdk` and install it

# Caution !!

This project requires the use of primitive local data bases, if a user exits unexpectedly, i.e. sending a `termination singal` during the CLI process without letting our `Exception` handler handle the event, the CLI may exhibit unexpected behaviours due to corruption of data bases. In the event this happens, run

```
$ git restore ../data/*
```

Refrain from using **IDE's** terminal, and use a proper terminal emulator. Terminals in IDE may exhibit weird behaviour. Examples of terminal emulators are `Poweshell`, `Git Bash` and `iTerm`

# Initial User Accounts

You can test the CLI using the following accounts

```
ID: 12345
Password: password
Role: Patient

ID: 12346
Password: password
Role: doctor

ID: 12347
Password: password
Role: pharmacist

ID: 12348
Password: admin
Role: admin
```

Alternatively, you can add your own user fields through the CLI.

# javadoc

To view the documentation generated from `javadoc`, get the path of `javadoc/index.html` and open it in your browser

**On Windows**
```console
$ cd javadoc/
$ start index.html
```

**On Unix**
```console
$ cd javadoc/
$ xdg-open index.html
```

# Installation 

## For Windows Users

While the Java compiler should handle the pathing related compatibility depending on your OS, if any issues arise, consider using `Powershell` or `Git Bash`. Note that because of security issue / cpu issue, Window users may experience
weird error such as `Other process is also reading the file`. Because the project is coded in a Unix Environment, we were not able to test everything on the Windows System. Should such an issue occur, consider using a virtual machine.

## Install

```console
$ git clone https://github.com/xCodex22/SC2002_2324Assignment
```
## Compilation
`cd` into the `src/` directory and compile

```console
$ cd SC2002_2324Assignment/src/
$ javac ./main/HMS.java 
```
## Run 
Run the program
```console
$ java main.HMS
```

# Report

The project requirement specified the submission of a word document. For optimal viewing experience, kindly look at [`report.md`](docs/report.md)
