
public class TinyPL {

	public static void main(String[] args) {

		 System.out.println("Enter program and terminate with 'end'.\n");
		
	      Lexer.initialize();
		Lexer.lex();                // scan one token at the very start
		
		new Program();              // parse the entire user's program
		 
	}
}

