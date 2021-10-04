
class Program {        // program -> [ function ]+ end
	public Program() {
		
		while (Lexer.nextToken == Token.KEY_INT) {
			
			SymTab.initialize();    // initialize for every function parsed
			ByteCode.initialize();  // initialize for every function parsed
			
			Lexer.lex();
			Function f = new Function();
			
			ByteCode.output(f.header);
			
		}
		FunTab.output();
	}
}


class Function {     // function -> int id '(' [ pars ] ')' '{' body '}'
	String fname;
	Pars p;
	Body b;
	String header;

	public Function() {
		fname = Lexer.ident;
		FunTab.add(fname);
		Lexer.lex();
		Lexer.lex(); // (
		p = new Pars();
		Lexer.lex(); // )
		Lexer.lex(); // {
		b = new Body();
		Lexer.lex(); // }
		header = "int " + fname + "(" + p.types + ");";
		return;
	}
}

class Pars {          // pars -->  int id {, int id }
	String types = "";
	int npars = 0;
	public Pars() {
		if (Lexer.nextToken != Token.RIGHT_PAREN) {
			Lexer.lex(); // type, assume int
			SymTab.add(Lexer.ident);
			Lexer.lex(); // id
			types = types + "int";
			npars++;
			while (Lexer.nextToken == Token.COMMA) {
				types = types + ",";
				Lexer.lex(); // comma
				Lexer.lex(); // type, assume int
				SymTab.add(Lexer.ident);
				Lexer.lex();
				types = types + "int";
				npars++;
			}
		}
	}
}

class Body {        // body -> [ decls ] stmts
	Decls d;
	Stmts s;

	public Body() {
		if (Lexer.nextToken == Token.KEY_INT) {
			d = new Decls();
		}
		s = new Stmts();
	}
}

class Decls {    // int idlist ;
	Idlist il;

	public Decls() {  
		Lexer.lex(); // skip over int
		il = new Idlist();
		Lexer.lex(); // skip ;
	}
}

class Idlist {   // idlist -> id { , id }
	String id;
	Idlist il;

	public Idlist() {
		id = Lexer.ident;
		SymTab.add(id);
		Lexer.lex();
		while (Lexer.nextToken == Token.COMMA) {
			Lexer.lex();
			id = Lexer.ident;
			SymTab.add(id);
			Lexer.lex();
		} 	
	}
}

class Stmts {   // stmts -> stmt [ stmts ]
	Stmt s;
	Stmts ss;

	public Stmts() {    // stmts -> stmt [stmts ]
		s = new Stmt();
		if (Lexer.nextToken == Token.ID || Lexer.nextToken == Token.KEY_IF ||
		Lexer.nextToken == Token.KEY_WHILE ||  Lexer.nextToken == Token.LEFT_BRACE
		|| Lexer.nextToken == Token.KEY_RETURN)
		{
		
			ss = new Stmts();
		}
		
	}
}



class Stmt {    // stmt -> assign ';' | cond | while | cmpd | return ';'
	Stmt s;
    Assign a;
    Cond c;
    Loop l;
    Return r;
    Cmpd cm;
	public Stmt() {
		switch (Lexer.nextToken) {
		case Token.ID: // id
		     a = new Assign();
		     Lexer.lex();//skip ;
		    
		     break;
		case Token.KEY_IF: 
		     c = new Cond();		     
		     break;
		case Token.KEY_WHILE: 
		     l = new Loop();		     
		     break;
		case Token.KEY_RETURN: 
		     r = new Return();	
		     Lexer.lex();//skip ;
		     break;
		case Token.LEFT_BRACE: 
		     cm = new Cmpd();	
		    
		     break;
		default:
		     break;
		}			
	}

	public Stmt(int d) {
	 // needed for superclass initialization to avoid infinite loop
     // body is empty by design - no code is missing.
	}
}



class Return extends Stmt {   // return -> 'return' expr
	Expr e;

	public Return() {
		super(0);
		Lexer.lex(); // return
		e = new Expr();
		ByteCode.gen_return();
	}
}

class Assign extends Stmt {   // assign -> id '=' expr
	String id;
	Expr e;

	public Assign() {
		super(0);
		id = Lexer.ident;
		Lexer.lex();
		Lexer.lex(); // skip over '='
		e = new Expr();
		ByteCode.gen("istore", SymTab.index(id));
	}
}

class Cond extends Stmt {  // cond -> if '(' relexp ')' stmt [ else stmt ]
	Relexp r;
	Stmt c1;
	Stmt c2;

	public Cond() {
		super(0);
		Lexer.lex(); // skip over 'if'
		Lexer.lex(); // skip over '('
		r = new Relexp();
		Lexer.lex(); // skip over ')'
		int ifpoint = ByteCode.str_codeptr;
		ByteCode.skip(3);
		c1 = new Stmt();
		if (Lexer.nextToken == Token.KEY_ELSE) {
			Lexer.lex(); // skip over 'else'
			int elsepoint = ByteCode.str_codeptr;
			ByteCode.gen_goto(-2);
			ByteCode.skip(2);
			ByteCode.patch(ifpoint, ByteCode.str_codeptr);
			c2 = new Stmt();
			ByteCode.patch(elsepoint, ByteCode.str_codeptr);
		} else
			ByteCode.patch(ifpoint, ByteCode.str_codeptr);
	}
}

class Loop extends Stmt {   // loop -> while '(' relexp ')' stmt
	Relexp b;
	String rop;
	Stmt c;

	public Loop() {
		super(0);

		// missing code: to be filled in by you		
		Lexer.lex(); // skip over 'while'
		Lexer.lex(); // skip over '('		
		int looppoint = ByteCode.str_codeptr;
		b = new Relexp();
		Lexer.lex(); // skip over ')'
		int ifpoint = ByteCode.str_codeptr;
		ByteCode.skip(3);
		//ByteCode.gen_goto(-2);
		c = new Stmt();
		ByteCode.gen_goto(looppoint);
		ByteCode.patch(ifpoint, ByteCode.str_codeptr);

		
		
	}
}

class Cmpd extends Stmt { // cmpd -> '{' stmts '}'
	Stmts s;

	public Cmpd() {
		super(0);
		Lexer.lex(); // skip over '{'
		s = new Stmts();
		Lexer.lex(); // skip over '}'
	}
}

class Relexp {     // relexp -> expr ('<' | '>' | '<=' | '>=' | '==' | '!= ') expr
	Expr e1;
	Expr e2;
	String op = "";

	public Relexp() {
		e1 = new Expr();
		if (Lexer.nextToken == Token.EQ_OP || Lexer.nextToken == Token.GREATER_OP || Lexer.nextToken == Token.LESSER_OP
				|| Lexer.nextToken == Token.GREATEREQ_OP || Lexer.nextToken == Token.LESSEQ_OP
				|| Lexer.nextToken == Token.NOT_EQ) {
			op = Token.toString(Lexer.nextToken);
			Lexer.lex();
			e2 = new Expr();
			ByteCode.gen_if(op);
		}
	}
}

class Expr {     // expr -> term (+ | -) expr | term
	Term t;
	Expr e;
	char op;

	public Expr() {
		t = new Term();
		if (Lexer.nextToken == Token.ADD_OP || Lexer.nextToken == Token.SUB_OP) {
			op = Lexer.nextChar;
			Lexer.lex();
			e = new Expr();
			ByteCode.gen(op);
		}
	}
}

class Term {   // term -> factor (* | /) term | factor
	Factor f;
	Term t;
	char op;

	public Term() {
		f = new Factor();
		if (Lexer.nextToken == Token.MULT_OP || Lexer.nextToken == Token.DIV_OP) {
			op = Lexer.nextChar;
			Lexer.lex();
			t = new Term();
			ByteCode.gen(op);
		}
	}
}

class Factor { // factor -> int_id | id | '(' expr ')' | funcall
	Expr e;
	ExprList el;
	String id;
	int i;

	public Factor() {
		
		switch (Lexer.nextToken) {
		
		case Token.INT_LIT: // number
			i = Lexer.intValue;
			if (i < 6 && i > -1)
				ByteCode.gen("iconst", i);
			else if (i < 128) {
				ByteCode.gen("bipush", i);
			} else {
				ByteCode.gen("sipush", i);
				ByteCode.skip(1);
			}
			Lexer.lex();
			break;
		case Token.ID: // id
			id = Lexer.ident;
			Lexer.lex();
			if (Lexer.nextToken == Token.LEFT_PAREN) 
			    {
			    new Funcall(id);
			    }
				
			else
				ByteCode.gen("iload", SymTab.index(id));
			break;
		case Token.LEFT_PAREN: // '('
			Lexer.lex();
			e = new Expr();
			Lexer.lex(); // skip over ')'
			break;
		default:
			break;
		}
	}
}

class Funcall  {  // funcall -> id '(' [ exprlist ] ')'
	String id;
	ExprList el;
	String fname;
	
	public Funcall(String id) {
		//fname = Lexer.ident;
		//FunTab.add(fname);
		ByteCode.gen("aload", FunTab.index(id));
		
		Lexer.lex(); // id

		el = new ExprList();
		Lexer.lex(); // )
		
		ByteCode.gen_invoke(FunTab.index(id));
		
		return;
	}
}

class ExprList { // exprlist -> expr [  , exprlist ]
	Expr e;
	ExprList el;
	public ExprList() {
		if (Lexer.nextToken != Token.RIGHT_PAREN) {
			
		e = new Expr();
		
		if (Lexer.nextToken == Token.COMMA) {
			
			Lexer.lex();//skip ,
			el = new ExprList();		
		}
		}
	}
}
