package sudoko_solver;

import java.util.ArrayList;

public class SudokuLoadData {
	//9x*9 Sudoku
	
	public static ArrayList<String[]> myGame() {
		ArrayList<String[]> b = new ArrayList<String[]>();
		String[] ValueDouble = new String[2];
		
		//String a = ".7.5.2.8.62..7.......193.6..5..31............139..62..3.7........2..5.91.98......"; // NakedSingle
		//String a = ".7.5.2.8.6...7.......193.6..5..31............139..62..3.7........2..5.91.98......"; // ns + np
		String a = "74.3....2.31.92..7..2.4....9....47......6....4.65279.......361.....1.54.........."; // bli
		//String a = ".1...7......1....794..3.8....4.8..7.....41.2.2....6.........9.61...6.....7.8.94..";
		//String a = "2496..8.3.5.9.....3...82......4....1..4..6..5..6..5..8.9..........8.31....31...5.";
		//String a = "..3.7.5.11...56.9..692...4.6.4....3.....2..7..1..................6.3....3...1975.";
		//String a = "9..2...1..1..78..3...4...7.19..3..8.64.8...218......342..7.6.......4............9";
		//String a = "....2...9.....6....4.378....2..8....98.5....4.6.....9.3.6...52...2.3.8...586.....";
		//String a = "..7..6.9....84.65...21.9..4..6.......9....1...5...4.............7...198.64..5..2.";
		//String a = "..8.76...2.6..4......18......73.2.8........344....576..52.17......45.......6...13";
		//String a = "..9.......2.....17.....5932.9.1....5.....9..8..13.....8...9..71..7..65...1..8.6.9";
		//String a = "82....9..6..4...38.39.5....961..4..25..68.............1..86..93.5..3..........2..";
		//String a = "......7...2.647..8.4...9....84...6..6..2......7...........51..621.7..5.9..7.8..1.";
		//String a = ".9.5....4...2.3.............3..4.2.71...35......78......34.....7.5..1.298.2....4.";
		//String a = "63.4..9..8.5......9........1.6.3....38....2...2.5.6.8.5..61..2...4...3.........59";
		//String a = "....5....1..28.5.3..2......2....1.7..1.......7..5.6.9.8.1..79...9.1....5...9...6.";
		//String a = ".7........1.43......4...9...91....2...69.5.7.5....7.6..2..76..3......1.....3..75.";
		//String a = ".53.6..8..8.3...2.61..5...419.....4.2...156...3.4.8.........9.1...53........8....";
		//String a = "..72....1.5..34........14.7.......1..6...53.8..41.....286..3.4..1....6..5.....2..";
		//String a = "..5.392.49.2....5......6......17.4.3.4..5....21...35...6....3..1........35..4...9";
		//String a = ".15.9....3...56..17.....3.......276...6.....5....7.23...1.........2.....6.3..8512";
		String[] tokens = a.split("");
		int row = 1;
    	int col = 1;
		for(int i = 0; i < tokens.length; i++){
			row = ((i)/ jsudokuSolver.MAXCOL) + 1;
	    	col = i - (row  - 1)* jsudokuSolver.MAXCOL + 1;
	    	if (tokens[i].equals(".")) {} else {
	    		ValueDouble[0] = ""+row+col;
	    		ValueDouble[1] = ""+tokens[i];
	    		b.add(ValueDouble.clone());
	    		//System.out.println("SudokuDaten - i: " + i + " Zelle [" + row+ "/"+col +"] " + " Tokens: " + tokens[i]);
	    	}
		}
		return b;
	}
	/*
	//String a = ".7.5.2.8.6...7.......193.6..5..31............139..62..3.7........2..5.91.98......";
	//String a = "74.3....2.31.92..7..2.4....9....47......6....4.65279.......361.....1.54..........";
	//String a = ".1...7......1....794..3.8....4.8..7.....41.2.2....6.........9.61...6.....7.8.94..";
	//String a = "2496..8.3.5.9.....3...82......4....1..4..6..5..6..5..8.9..........8.31....31...5.";
	//String a = "..3.7.5.11...56.9..692...4.6.4....3.....2..7..1..................6.3....3...1975.";
	//String a = "9..2...1..1..78..3...4...7.19..3..8.64.8...218......342..7.6.......4............9";
	//String a = "....2...9.....6....4.378....2..8....98.5....4.6.....9.3.6...52...2.3.8...586.....";
	//String a = "..7..6.9....84.65...21.9..4..6.......9....1...5...4.............7...198.64..5..2.";
	//String a = "..8.76...2.6..4......18......73.2.8........344....576..52.17......45.......6...13";
	//String a = "..9.......2.....17.....5932.9.1....5.....9..8..13.....8...9..71..7..65...1..8.6.9";
	//String a = "82....9..6..4...38.39.5....961..4..25..68.............1..86..93.5..3..........2..";
	//String a = "......7...2.647..8.4...9....84...6..6..2......7...........51..621.7..5.9..7.8..1.";
	//String a = ".9.5....4...2.3.............3..4.2.71...35......78......34.....7.5..1.298.2....4.";
	//String a = "63.4..9..8.5......9........1.6.3....38....2...2.5.6.8.5..61..2...4...3.........59";
	//String a = "....5....1..28.5.3..2......2....1.7..1.......7..5.6.9.8.1..79...9.1....5...9...6.";
	//String a = ".7........1.43......4...9...91....2...69.5.7.5....7.6..2..76..3......1.....3..75.";
	//String a = ".53.6..8..8.3...2.61..5...419.....4.2...156...3.4.8.........9.1...53........8....";
	//String a = "..72....1.5..34........14.7.......1..6...53.8..41.....286..3.4..1....6..5.....2..";
	//String a = "..5.392.49.2....5......6......17.4.3.4..5....21...35...6....3..1........35..4...9";
	//String a = ".15.9....3...56..17.....3.......276...6.....5....7.23...1.........2.....6.3..8512";
	*/
}
