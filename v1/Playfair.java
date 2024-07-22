class Playfair {
	static char[][] key;
	static String alpha; // alphabet
	static int len;
	static int sqL;
	static String txt;
	static char special='Z'; // used to put off repeats
	public Playfair(String text, String alphabet){
		alpha = removeRepeats(alphabet);
		txt = removeRepeats(text);
		String keyString = removeRepeats(txt+alpha);
		print("Prepped key: " + keyString);
		print("\n");
		print(keyString.length());
		print("\n");
		try{
			if (checkSqr(keyString.length())){
				
			}
			else{
				throw new Exception("Key not square length");
			}
		}
		catch(Exception ex){
			print(ex.getMessage());
			return; // not sure if needed
		}
		keyGen(keyString);
		len = keyString.length();
        	sqL = sqrt(len);
		printActualKey();
	}
	// put z between twin chars and append it if length is odd
	// 22-7-24 changed so that it doesn't apply across bigrams
	// btw out is the string that has been specialified
	private static String specialify(String s){
		int idx = 0;
		int repeatBigrams = 0;
		String out = "";
		while (idx < s.length()-1){
			
			if (s.charAt(idx) == s.charAt(idx + 1)){
				repeatBigrams += 1;
				
				out += s.charAt(idx);
				out += special;
				//out += s.charAt(idx + 1);
				idx -= 1; // since for example BB -> BZB, the bigrams now start at 2nd B
			}
			else{
				out += s.charAt(idx);
				out += s.charAt(idx + 1);
			}
			idx += 2;
		}
		if (idx+1 == s.length()){
			out += s.charAt(idx);
		}
		if (out.length()%2==1){
			out += special;
		}
		return out;
		
	}
	public String enc(String s){
		// first replace double occurences of same character with appended special char
		int i = 0;
		char a;
		char b;
		s = specialify(s);
		System.out.println("Specialified: ");
		System.out.println(s);
		String out = "";
		char[] bigram;
		while (i < s.length()-1){
			bigram = encrypt(s.charAt(i), s.charAt(i+1));
			a=bigram[0];
			b=bigram[1];
			i+=2;
			out += a;
			out += b;
		}
		return out;
	}
	public String dec(String s){
		// first replace double occurences of same character with appended special char
		int i = 0;
		char a;
		char b;
		s = specialify(s);
		String out = "";
		char[] bigram;
		while (i < s.length()-1){
			bigram = decrypt(s.charAt(i), s.charAt(i+1));
			a=bigram[0];
			b=bigram[1];
			i+=2;
			out += a;
			out += b;
		}
		return out;
	}
	// 0,0-4 would get the top row (if done individually
	public static char get(int h, int w){
		if (h==-1){
		    h = sqL-1;
		}
		if (w==-1){
		    w = sqL-1;
		}
		return key[w%sqL][h%sqL];
	}
	public static void main(String[] args){
		//tests();
	}
	private static boolean checkSqr(int in){
		boolean out = false;
		if (Math.sqrt(in) == sqrt(in)){
			out = true;
		}
		return out;
	}
	private static void tests(){
		printy();
		print("\n");
		keyprint("ABCDEFGHIKLMNOPQRSTUVWXYZ");
		print("\n");
		keyprint("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		print("\n");
		keyGen("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		print("\n");
		print(removeRepeats("ZZZZZZZZZZZZZZZZZZZZZZZZZZzzzzzzzzzzzzzzzzzzzz"));
		print("\n");
		print(removeRepeats("gergeronimoggeronimoerongeronimoimoonigeronimomo"));
		print("\n");
		keyprint("gergeronimoggeronimoerongeronimoimoonigeronimomo");
		print("\n");
		keyGen("12345678912345678912345678922123456789123456789123456789123456789123456789123456789");
		print("\n");
		print(removeEntries('s', "sssssssssshsssssissssssss"));
		print("\n");
		Playfair pf;
		pf = new Playfair("honeycomb", "abcdefghiklmnopqrstuvwxyz");
		print(pf.get(0,-1));
		print(pf.get(-1,1));
		print(pf.get(0,pf.getKeyW()));
		print(pf.get(pf.getKeyW(),1));
		print("\n");
		pf = new Playfair("honeycomb", 				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,.");
		print(pf.get(0,-1));
		print(pf.get(-1,1));
		print(pf.get(0,pf.getKeyW()));
		print(pf.get(pf.getKeyW(),1));
		print("\n");
		print(pf.isInCol(pf.get(0,0), 0));
		print(pf.isInCol(pf.get(pf.getKeyW(),0), 0));
		print(pf.isInRow(pf.get(0,0), 0));
		print(pf.isInRow(pf.get(2, 0), 2));
		print(pf.isInRow('k', 6));
		print("\n");
		print(check('h','o')[0]);
		print(check('h','o')[1]);
		print(check('h','s')[0]);
		print(check('h','s')[1]);
		print(check('h','s')[0]);
		print(check('m','E')[1]); // all good
		print("\n");
		encrypt('h','o');
		encrypt('h','s');
		encrypt('m','E'); // 
		print("\n");
		decrypt('o','n');
		decrypt('b','i');
		decrypt('6','m'); // 
		print("\n");
		print(specialify("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		print("\n");
		print(specialify("ABCDEFGHIJKKLMNOOPQRSTUVWXYZ"));
		print("\n");
		print(specialify("AAAAA"));
		print("\n");
	}
	private static int[] getPos(char c){
		int colmatch = -5;
		int rowmatch = -5;
		int col = 0;
		int row = 0;
		while (col <= sqL){
			if (isInCol(c, col)){
				colmatch = col;
			}
			col++;
		} 
		while (row <= sqL){
			if (isInRow(c, row)){
				rowmatch = row;
			}
			row++;
		} 
		return new int[]{rowmatch, colmatch};
	}
	// encryption
	public static char[] encrypt(char c, char d){
		 boolean[] dir = check(c,d);
		 char[] out = new char[]{'-','-'};
		 int[] pos1 = getPos(c);
		 int[] pos2 = getPos(d);
		 if (!dir[0] && !dir[1]){
		 	// it's a diagonal
		 	out[0] = get(pos1[0], pos2[1]);
		 	out[1] = get(pos2[0], pos1[1]);
		 }
		 else if (dir[0] && !dir[1]){
		 	// it's a row
		 	out[0] = get(pos1[0], pos1[1]+1);
		 	out[1] = get(pos2[0], pos2[1]+1);
		 }
		 else if (!dir[0] && dir[1]){
		 	// it's a column
		 	out[0] = get(pos1[0]+1, pos1[1]);
		 	out[1] = get(pos2[0]+1, pos2[1]);
		 }
		 return new char[]{out[0], out[1]};
	}
	
	// decryption
	public static char[] decrypt(char c, char d){
		 boolean[] dir = check(c,d);
		 char[] out = new char[]{'-','-'};
		 int[] pos1 = getPos(c);
		 int[] pos2 = getPos(d);
		 if (!dir[0] && !dir[1]){
		 	// it's a diagonal
		 	out[0] = get(pos1[0], pos2[1]);
		 	out[1] = get(pos2[0], pos1[1]);
		 }
		 else if (dir[0] && !dir[1]){
		 	// it's a row
		 	out[0] = get(pos1[0], pos1[1]-1);
		 	out[1] = get(pos2[0], pos2[1]-1);
		 }
		 else if (!dir[0] && dir[1]){
		 	// it's a column
		 	out[0] = get(pos1[0]-1, pos1[1]);
		 	out[1] = get(pos2[0]-1, pos2[1]);
		 }
		 return new char[]{out[0], out[1]};
	}
	// 01 - col, 10 - row, 00 - diag, 11 - error
	// finds the 
	private static boolean[] check(char c, char d){
		boolean colmatch = false;
		boolean rowmatch = false;
		int col = 0;
		int row = 0;
		while (col <= sqL){
			if (isInCol(c, col) && isInCol(d,col)){
				colmatch = true;
			}
			col++;
		} 
		while (row <= sqL){
			if (isInRow(c, row) && isInRow(d,row)){
				rowmatch = true;
			}
			row++;
		} 
		return new boolean[]{rowmatch, colmatch};
	}
	private static boolean isInCol(char character, int col){
		boolean out = false;
		if (col < -1 || col > sqL){
			// do nothing, return
		}
		else{
			int i = 0;
			while (i < sqL){
				if (character==get(i,col)){
					out = true;
				}
				i++;
			}
		}
		return out;
	}
	private static boolean isInRow(char character, int row){
		boolean out = false;
		if (row < -1 || row > sqL){
			// do nothing, return
		}
		else{
			int i = 0;
			while (i < sqL){
				if (character==get(row,i)){
					out = true;
				}
				i++;
			}
		}
		return out;
	}
	public int getKeyW(){
		return sqL;
	}
	// removes repeated characters from a string
	private static String removeRepeats(String s){
		String out = "";
		int i = 0;
		int j = 0;
		boolean found;
		while (i<s.length()){
			found = false;
			while (j<out.length()){
				if (s.charAt(i)==out.charAt(j)){
					found = true;
				}
				j++;
			}
			if (!found){
				out = out + s.charAt(i);
			}
			j=0;
			i++;
		}
		return out;
	}
	public static void print(Object l){
		System.out.print(String.valueOf(l));
	}
	public static void printActualKey(){
		print("\n");
		int keyW = sqrt(len);
		int it = 0;
		int bouncer = 0;
		while (it < keyW*keyW){
			print(key[it%keyW][it/keyW]);
			print(" ");
			it++;
			bouncer++;
			if (bouncer==keyW){
				bouncer = 0;
				print("\n");
			}
		}
	}
	// helps with key generation alg
	private static void printy(){
		int len = 6;
		int count = 0;
		String spacing = " ";
		while (count < len*len){
			print(count+1);
			print(spacing);
			count++;
			if (count < 10){
				print(spacing);
			}
			if (count%len==0){
				print("\n");
			}
		}
	}
	// easy sqrt alg
	private static int sqrt(int in){
		int keyW = 0;
		while (keyW*keyW <= in){
			keyW++;
		}
		keyW--;
		return keyW;
	}
	// extension of printy for a string
	private static void keyprint(String k){
		int keyW = sqrt(k.length());
		int count = 0;
		String spacing = " ";
		int it = 0;
		while (it < k.length()){
			print(k.charAt(it));
			it++;
			print(spacing);
			count++;
			if (count%(keyW)==0){
				print("\n");
			}
		}
	}
	// playfair key generation, but without repeat detection, and errs with bad lengths
	private static char[][] keyGen(String k){
		int keyW = sqrt(k.length());
		try{
			if (keyW*keyW!=k.length()){
				throw new Exception("key length not square");
			}
		}
		catch (Exception ex){
			print(ex.getMessage());
			return null;
		}
		int count = 0;
		int it = 0;
		char[][] ke = new char[keyW][keyW];
		char now;
		while (it < k.length()){
			now = k.charAt(it);
			ke[it%keyW][it/keyW]=now;
			//print(ke[it%keyW][it/keyW]);
			it++;
			count++;
			if (count%(keyW)==0){
				//print("\n");
			}
			
		}
		key = ke.clone();
		return ke;
	}
	// remove elements in s from actonme
	private static String removeEntries(char c, String s){
		int i;
		i = 0;
		char d;
		String out = "";
		boolean found = false;
		int idx;
		while (i < s.length()){
			d = s.charAt(i);
			if (c==d){
				found = true;// found char
			}
			if (!found){
				out += s.charAt(i);
			}
			found = false;
			i++;
		}
		return out;
	}
	
	
}

