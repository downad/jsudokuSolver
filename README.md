# jsudokuSolver
Löse ein Sudoku mit Hilfen 
als Java Programm	

# Version
0.1			Start 21.11.2017
0.2			Model-Control-View als Konzept
0.3			ErrorLog und SolvingStepLog
0.4			Manuelles Lösen möglich
0.5			umgestellt auf automatisches Lösen mit Strategie-Auswahl
0.6			Strategie Naked Single
0.6.1		CoordiantesOfUnsolvedCells als Klassen Variable
0.7			Strategie Hidden Singel (11.12.2017)
0.8			Strategie Naked Pair	(14.12.2017)
				Strategie Block-Line-Interaction (14.12.2017)
				


************************************
***           BEGRIFFE           ***
************************************
Begriff		Variabel	Beschreibung
grid 					Das ganze Sudoku wird als Gitter (engl.: grid) bezeichnet. 
cell					Ein Sudoku besteht aus Zellen
					[cell] eine Klasse 
values				Zellen werden gefüllt mit Werten (engl.: values).
givens				Die Werte, die zu Beginn bereits vorhanden sind, sind die Angaben (engl.: givens)
candidates		Mögliche Werte für noch nicht gefüllte Zellen sind Kandidaten (engl.: candidates).
unit					Zeilen, Spalten oder Blöcke werden unit genannt.
row						Zeilen (engl.: rows, mit "r" abgekürzt),
					[ROW]	Array über die Key der Reihe
col						Spalten (engl.: columns, mit "c" abgekürzt) und 
					[COL]	Array über die Key der Spalte
block					Blöcke (engl.: boxes oder blocks, mit "b" abgekürzt") angeordnet.
					[BLOCK]	Array über die Key des Blocks
band					Drei Blöcke in einer Reihe sind ein Band (engl.: band). 
floor					Ein horizontales Band ist ein Stock (engl.: floor).
tower					Ein vertikales ist ein Turm (engl.: tower).


************************************
***          STRATEGIEN          ***
************************************
Naked Single				ist in einer Zelle gibt es nur einen Candidat
Hidden Single				ein Candidat kommt nur einmal in der Unit (row, col, block) vor



# License
Copyright (C) 2017 Ralf Weinert - email. ralf.weinert@gmx.de
Code: Licensed under the GNU LGPL version 2.1 or later. See LICENSE.txt and http://www.gnu.org/licenses/lgpl-2.1.txt 


