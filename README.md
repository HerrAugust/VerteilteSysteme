# VerteilteSysteme
Übung 1 für Betriebssysteme und Verteilte Systeme

Sie sollen ein Programm entwickeln, das die Anzahl der Primzahlen zwischen 1 und
einer Obergrenze max ermittelt. Der Wert von max wird hierbei in Form einer statischen ganzzahligen Variablen am Anfang des Programms festgelegt.
Am Ende der Ausfuhrung Ihres Programms muss sowohl die gew ¨ ahlte Obergrenze, ¨
als auch die Anzahl der Primzahlen zwischen 1 und dieser Zahl, auf dem Bildschirm
ausgegeben werden.
Aufgaben:
a) Schreiben Sie eine Methode, die fur eine gegebene Zahl bestimmt, ob es sich ¨
um eine Primzahl handelt (3 Punkte).
b) Schreiben Sie ein Programm, das fur alle Werte zwischen ¨ 1 und der Variablen max per Aufruf der zuvor entwickelten Methode ermittelt, ob es sich um
Primzahlen handelt und am Ende die Anzahl aller Primzahlen ausgibt. Setzen
Sie fur ¨ max hierbei initial den Wert 4.000.000 ein (2 Punkte).
c) Das Programm soll nun beschleunigt werden, indem Tests auf Primzahlen in
nebenlaufig ausgef ¨ uhrten Threads durchgef ¨ uhrt werden. Erweitern Sie das ¨
Programm derart, dass es mit Hilfe von Threads die Anzahl der in einem
Wertebereich befindlichen Primzahlen bestimmen kann. Verwenden Sie 4
nebenlaufige Threads, die jeweils ein Viertel des Wertebereichs pr ¨ ufen und ¨
starten Sie die Analyse aus Aufgabenteil (b) erneut (9 Punkte).
d) Erweitern Sie das Programm um eine Angabe der benotigten Ausf ¨ uhrungszeit ¨
(nach Beendigung) sowie eine Fortschrittsanzeige (eine Konsolenausgabe ist
ausreichend). Geben Sie die Laufzeiten an, die Ihr Programm benotigt, um alle ¨
Primzahlen fur einen von den ¨ Ubungs-Betreuern vorgegebenen Wert von ¨ max
zu finden, wenn 2, 4 und 8 Threads eingesetzt werden, sowie wenn gar keine
Nebenlaufigkeit zum Einsatz kommt (3 Punkte).
