package sudoko_solver;

/*
class FormattedTextFieldListener implements PropertyChangeListener {
     	@Override 
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		// Zerlegen der Source
		String a[] = source.toString().split(",");
		String b[] = a[0].split("Field");
		StringBuilder c = new StringBuilder(b[1]);
		// Ersetze das DOOFE [
		c.setCharAt(0, '0');

		int GridNumber = Integer.parseInt(c.toString());
		if (e.getNewValue() != null ) {
			int Value = Integer.parseInt(e.getNewValue().toString());
			int solvedBy = 2; // manuelle Änderung
			System.out.println("FormattedTextFieldListener - Gridnumber: " + GridNumber + " Value: " + Value + " solvedBy = " +solvedBy);
			SudokuView.ChangeOnInputField = new InputFieldChanges(GridNumber,  e.getOldValue(),  e.getNewValue(), solvedBy);
//			ChangeOnInputField[0] = GridNumber;
		}
		/*
		System.out.println(" Die Quelle " + source + " hat das Event ausgelöst" );
		System.out.println(" - Events.getPropertyName " + e.getPropertyName());
		System.out.println(" - Events.toString " + e.toString());
		System.out.println(" - Events.getOldValue " + e.getOldValue());
		System.out.println(" - Events.getNewValue " + e.getNewValue());
		System.out.println(" - Events.getSource " + e.getSource());
		System.out.println(" - Events.getClass " + e.getClass());
		System.out.println(" - Events.getPropagationId " + e.getPropagationId());
		System.out.println(" - Split a: " + a.length + " Teile");
		System.out.println(" - Split a[0]: " + a[0]);
		System.out.println(" - Split b: " + b.length + " Teile");    		
		System.out.println(" - Split b[1]: " + b[1]);   
		System.out.println(" - c: " + c);   		
		*/
	/*	
	}
}
*/