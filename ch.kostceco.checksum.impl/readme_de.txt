Beschreibung
------------
Jacksum 1.7.0 (30.07.2006)
ist ein freies, in Java geschriebenes checksum utility (JAva ChecKSUM)

Copyright (c) Dipl.-Inf. (FH) Johann N. Loefflmann
mailto: jonelo@jonelo.de


Anforderungen
-------------
Eine Java Laufzeitumgebung ist erforderlich (JRE 1.3.1+).
Das JRE 5.0+ wird empfohlen.

Ziehen Sie sich die neueste Version herunter von
  http://java.sun.com/j2se

Jacksum läuft auch unter FOSS JVMs
  GCC with GCJ (http://gcc.gnu.org/java/)
  Kaffe (http://www.kaffe.org/)


Jacksum starten
---------------
Öffnen Sie ein neues Terminal (in der Windows-Welt als "Eingabeaufforderung"
bekannt) und starten Sie Jacksum mit

  java -jar jacksum.jar
oder
  java -classpath jacksum.jar Jacksum
oder
  java Jacksum
  Die Umgebungsvariable CLASSPATH muss in diesem Fall auf die Datei
  jacksum.jar verweisen.
  
Ich empfehle die Verwendung der Windows-Batchdatei (jacksum.bat) bzw.
des Unix-Scripts (jacksum). Sie finden mehr Informationen in diesen
Dateien.


Bei Verwendung einer FOSS JVM, starten Sie Jacksum wie folgt:

  gij -jar bigal.jar
respectively
  kaffe -jar bigal.jar


Updates
-------
Bleiben Sie über Jacksum auf dem Laufenden und senden Sie eine eMail an
  mailto:announce-subscribe@jacksum.dev.java.net 
  
Beziehen Sie die neueste Jacksum Version von
  http://www.jonelo.de/java/jacksum/index_de.html


Dateien
-------
devel/collisions.zip    Eine Kollektion an Kollisionen
devel/jacksum-src.zip   Die Quellcodes von Jacksum
history.txt             Eine Programm-Historie
jacksum.jar             Das Programm Jacksum
license.txt             Die Lizenzdatei (GPL)
readme.txt              Ein englisches readme
readme_de.txt           Ein deutsches readme (diese Datei)
unix/jacksum            Unix/Linux script, um Jacksum aufzurufen
unix/jacksum.magic      Magic-Record für das Unix/Linux Kommando file
windows/jacksum.bat     Windows batch, um Jacksum aufzurufen
