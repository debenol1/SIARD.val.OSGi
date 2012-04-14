Description
-----------
Jacksum 1.7.0 (July 30, 2006)
is a free checksum utility, written entirely in Java (JAva ChecKSUM)

Copyright (c) Dipl.-Inf. (FH) Johann N. Loefflmann
mailto: jonelo@jonelo.de


Requirements
------------
A Java Runtime Environment is required (JRE 1.3.1+).
The JRE 5.0+ is recommended.

Download the latest version from
  http://java.sun.com/j2se

Jacksum runs also on FOSS JVMs
  GCC with GCJ (http://gcc.gnu.org/java/)
  Kaffe (http://www.kaffe.org/)


Start Jacksum
-------------
Open a new Terminal (in the Windows-World it is known as "Command Prompt")
and start Jacksum by typing

  java -jar jacksum.jar
or
  java -classpath jacksum.jar Jacksum
or
  java Jacksum
  The environment variable called CLASSPATH must be point
  to jacksum.jar in this case.

I recommend to use the Windows batch file (jacksum.bat) or the
Unix script (jacksum). You find more instructions in these files.


On a FOSS JVMs start Jacksum by typing

  gij -jar bigal.jar
respectively
  kaffe -jar bigal.jar


Updates
-------
Keep updated by sending an email to
  mailto:announce-subscribe@jacksum.dev.java.net 

Get the latest Jacksum version from
  http://www.jonelo.de/java/jacksum/index.html


Files
-----
devel/collisions.zip    A collection of collisions
devel/jacksum-src.zip   The sources of Jacksum
history.txt             A program history
jacksum.jar             The Jacksum application
license.txt             The License file (GPL)
readme.txt              An english readme (this file)
readme_de.txt           A german readme
unix/jacksum            Script to launch Jacksum under Unix/Linux
unix/jacksum.magic      Magic-Record for the Unix/Linux command file
windows/jacksum.bat     Batch to launch Jacksum under Windows
