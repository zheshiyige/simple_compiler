import java.util.Scanner;

public class Buffer {

		public  String line = ""; 
		public  int position = 0;
		private Scanner s;
		
		public Buffer(Scanner s) {
			this.s = s;
			line = s.nextLine();
		}
		
		public char getChar() {
			if (position == line.length()) {
				try {
					line = s.nextLine();
				} catch (Exception e) {
					System.err.println("Invalid read operation");
					System.exit(1);
				}
				if (line == null)
					System.exit(0);
				position = 0;
				line = line + "\n";
			}
			position++;
			return line.charAt(position-1);
		}

}
