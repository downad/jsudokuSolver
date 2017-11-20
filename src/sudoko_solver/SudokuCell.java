package sudoko_solver;

public class SudokuCell {
	
	private static String cellName = "";
	private static int row = 0;
	private static int col = 0;
	private static int value = 0;
	private static boolean isSolved = false;
	private static int isSolvedBy = 0;
	
	
	public SudokuCell(int initRow, int initCol, int initValue, boolean initIsSolved, int initIsSolvedBy) {
		// Checke values
		//System.out.println("row = " + initRow + " col = " + initCol + " Value = " + String.valueOf(initValue) + " name = " + cellName);
		boolean ValueCheckIsOK = true;
		if (initRow < 1 && initRow > jsudokuSolver.MAXROW) {
			ValueCheckIsOK = false;
		}
		if (initCol < 1 && initCol > jsudokuSolver.MAXCOL) {
			ValueCheckIsOK = false;
		}
		if (initValue < 1 && initValue > jsudokuSolver.MAXNUMBER) {
			ValueCheckIsOK = false;
		}
		if (ValueCheckIsOK) {
			setCoordinate( initRow, initCol);
			setValue ( initValue, initIsSolved, initIsSolvedBy);
			
		}
	}
	
	private void setCoordinate(int initRow, int initCol){
		StringBuilder str = new StringBuilder(String.valueOf (initRow));
		str.append(String.valueOf (initCol));
		cellName = str.toString();
		row = initRow;
		col = initCol;		
		System.out.println("row = " + initRow + " col = " + initCol + " name = " + cellName);
	}
	public void setValue ( int Value, boolean IsSolved, int IsSolvedBy) {
		value = Value;
		isSolved = IsSolved;
		isSolvedBy = IsSolvedBy;
	}
	public static int getValue(){
		//System.out.println("row = " + row + " col = " + col + " Value = " + String.valueOf(value));
		return value;
	}
	public static String getName(){
		return cellName;
	}
/*
		def coordinate
			return @coordinate
		end

		def row
			return @row
		end

		def col
			return @col
		end

		def block
			return @block
		end

		def value
			return @value
		end

		def b_given
			return @b_given
		end

		def b_solved
			return @b_solved
		end

		def solved(number, string)
			@value = number
			@b_solved = true
			@solved_by = string
			@h_candidates[0] = "solved"
			@a_modified_by << "solved by #{string}"
			return true
		end
		
		def solved_by
			return @solved_by
		end

		def get_candidates
			return @h_candidates.clone
		end
		
		def set_candidates(candidates_in_cell)
			@h_candidates[0] = "g"
			candidates_in_cell.each do |key, value|
				@h_candidates[key] = value 
			end
		end

		def delete_candidate(value, modified_by)
			debug_YesNo = false
			returnvalue = true
			puts "delete_candidate - #{value} - #{modified_by} coordiante = #{coordinate}" if debug_YesNo == true
				if @h_candidates[0] == "g" 
					if @h_candidates[value] == 1
						puts "delete_candidate - #{value} - #{modified_by} " if debug_YesNo == true
						puts "delete_candidate -h_candidates =  #{@h_candidates} - value == #{value} " if debug_YesNo == true
						puts "delete_candidate -h_candidates =  #{@h_candidates[value]} == 1" if debug_YesNo == true
						@h_candidates.each { |key, candidate_value| puts "key = #{key} - Value = #{candidate_value}" } if debug_YesNo == true
						@h_candidates[value] = 0
						@a_modified_by << ["#{value} gelöscht durch #{modified_by}", value, modified_by]
					end
				else
					returnvalue = false
				end
			return returnvalue
		end
		
		def modified_by
			return @a_modified_by.clone
		end

		def print_cell
			returnstring = ""
			returnstring << "##########################################################################"
			returnstring << "\n Zelle(#{@coordinate}) - die Werte:"
			returnstring << "\n @coordinate - #{@coordinate}"
			returnstring << "\n @row - #{@row}"
			returnstring << "\n @col - #{@col}"
			returnstring << "\n @block - #{@block}"
			returnstring << "\n @value - #{@value}"
			returnstring << "\n @b_given - #{@b_given}"
			returnstring << "\n @b_solved - #{@b_solved}"
			returnstring << "\n @solved_by - #{@solved_by}"
			returnstring << "\n h_candidates[0] = n - erlaubte Zahlen müssen noch angelegt werden" if @h_candidates[0] == "n"
			returnstring << "\n h_candidates[0] = g - Die Liste der erlaubten Zahlen ist erstellt " if @h_candidates[0] == "g"
			returnstring << "\n h_candidates[0] = solved  - Die Zelle ist schon gelöst" if @h_candidates[0] == "solved"
			returnstring << "\n @h_candidates - #{@h_candidates}"
			returnstring << "\n @a_modified_by - #{@a_modified_by}"
			returnstring << "\n ##########################################################################"
			return returnstring 
		end
	end
	*/
}
