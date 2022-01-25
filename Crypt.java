// this class converts plaintext into a particular cryptogram from the properties of the Key,
// it uses a Playfair algorithm, which is a simple substitution cipher that operates on bigrams
public class Crypt {
	String cryptogram;
	public Crypt(Key k, Text t) {
		char[] p = t.getPlaintext().toCharArray();
		char[][] ke = k.getPureKey();
		p = this.preparePlaintext(p); // fill repeats with z and if result is odd put z at the end
		cryptogram = String.copyValueOf(this.encrypt(k.getPureKey(), p));
	}
    public String get(){
        return cryptogram;
    }
    // prepares the plaintext for encryption according to the playfair algorithm
	public char[] preparePlaintext(char[] t) {
		char[] c;
		c = t.clone();
		// now remove each duplicate one by one, so that "false duplicates" (ones that shift beyond
		// the bigram), do not count and are not falsely removed
		for (int i = 0; i < c.length/2; i++) {
			c = this.removeDuplicate(c);
		}
		if (c.length%2==1) {
			c = this.removeOddity(c);
		}
		return c;
	}
    // i believe this forces the plaintext to be of an even width
	private char[] removeOddity(char[] t) {
		char[] b = new char[t.length+1];
		if (b.length%2==0) {
			for (int i = 0; i < t.length; i++) {
				b[i] = t[i];
			}
			b[b.length-1] = 'z';
		}
		return b;
	}
	public int countRepeats(char[] kA) {
			char[] g = kA.clone();
			for (int i = 0; i < g.length; i++) {
				for (int j = i; j < g.length; j++) {
					if (i==j) {
						continue;
					}
					else {
						if (g[i]==g[j] && i%2==0 && j==i+1) {
							g[j]='@'; // means dead char
						}
					}
				}
			}
			int countOfAts = 0;
			for(char c : g) {
				if (c=='@') {
					countOfAts++;
				}
			}
			//System.out.println(countOfAts + " repeats");
			return countOfAts;
		}
    // returns the encryption wrt to the key and the plaintext
	public char[] encrypt(char[][] key, char[] preparedText) {
		char[] notout;
		char[] out = new char[preparedText.length];
		for (int i = 0; i < preparedText.length; i+=2) {
			char[] p = new char[] {preparedText[i],preparedText[i+1]};
			if (diagonalScan(key, p, 5)) {
				notout = diagPF(key, p, 5);
				out[i]=notout[0];
				out[i+1]=notout[1];
			}
			else if (vertScan(key, p, 5)) {
				notout = vertPF(key, p, 5);
				out[i]=notout[0];
				out[i+1]=notout[1];
			}
			else if(horizScan(key, p, 5)) {
				notout = horizPF(key, p, 5);
				out[i]=notout[0];
				out[i+1]=notout[1];
			}
			else {
				notout = new char[0];
				out[i]='8';
				out[i+1]='8';
			}
		}
		return out;
	}
	// returns encryption characters from vertical
	public char[] vertPF(char[][] key, char[] bigram, int keyWidth) {
		char[] out = new char[] {'5','5'}; // null chars
		for (int i = 0; i < keyWidth; i++) {
			for (int j = 0; j < keyWidth; j++) {
				if (key[i][j]==bigram[0]) {
					if (i!=keyWidth-1) {
						out[0] = key[i+1][j];
					}
					else {
						out[0] = key[0][j];
					}
				}
				else if(key[i][j]==bigram[1]) {
					if (i != keyWidth-1) {
						out[1] = key[i+1][j];
					}
					else {
						out[1] = key[0][j];
					}
				}
			}
		}
		return out;		
	}
    // returns location along horizontal of encryption characters (to the right)
	public char[] horizPF(char[][] key, char[] bigram, int keyWidth) {
		char[] out = new char[] {'5','5'}; // null chars
		for (int i = 0; i < keyWidth; i++) {
			for (int j = 0; j < keyWidth; j++) {
				if (key[i][j]==bigram[0]) {
					if (j!=keyWidth-1) {
						out[0] = key[i][j+1];
					}
					else {
						out[0] = key[i][0];
					}
				}
				else if(key[i][j]==bigram[1]) {
					if (j != keyWidth-1) {
						out[1] = key[i][j+1];
					}
					else {
						out[1] = key[i][0];
					}
				}
			}
		}
		return out;		
	}
    // returns the encryption characters of a digonally existing bigram (within the key)
	public char[] diagPF(char[][] key, char[] bigram, int keyWidth) {
		char[] out = new char[2];
		int[] first = getLocation(bigram[0], key, keyWidth);
		int[] second = getLocation(bigram[1], key, keyWidth);
		out[0] = key[first[1]][second[0]]; 
		out[1] = key[second[1]][first[0]]; 
		return out;		
	}
    // returns if the two letters of he bigram exist along a diagonal
	public boolean diagonalScan(char[][] key, char[] bigram, int keyWidth) {
		boolean out = false;
		int[] p1 = getLocation(bigram[0], key, 5);
		int[] p2 = getLocation(bigram[1], key, 5);
		if (p1[0]==-1 || p1[1]==-1 || p2[0]==-1 || p2[1]==-1) {
			out = false;
		}
		else if (p1[0] != p2[0] && p1[1] != p2[1]) {
			out = true;
		}
		return out;
	}
    // returns the locaion of the character int he key (square), or -1's
	public int[] getLocation(char c, char[][] key, int keyWidth) {
		int x = -1;
		int y = -1;
		for (int i = 0; i < keyWidth; i++) {
			for (int j = 0; j < keyWidth; j++) {
				if (c==key[i][j]) {
					x = j;
					y = i;
				}
			}
		}
		return new int[] {x,y};
	}
    // scans across the key vertically for the bigram
	public boolean vertScan(char[][] key, char[] bigram, int keyWidth) {
		boolean out = false;
		for (int i = 0; i < keyWidth; i++) {
			boolean aFound = false;
			boolean bFound = false;
			for (int j = 0; j < keyWidth; j++) {
				if (key[j][i]==bigram[0]) {
					aFound = true;
				}
				else if(key[j][i]==bigram[1]) {
					bFound = true;
				}
			}
			if (aFound && bFound) {
				out = true;
			}
		}
		return out;
	}
    // scans across the key horizontally looking for the bigram
	public boolean horizScan(char[][] key, char[] bigram, int keyWidth) {
		boolean out = false;
		for (int i = 0; i < keyWidth; i++) {
			boolean aFound = false;
			boolean bFound = false;
			for (int j = 0; j < keyWidth; j++) {
				if (key[i][j]==bigram[0]) {
					aFound = true;
				}
				else if(key[i][j]==bigram[1]) {
					bFound = true;
				}
			}
			if (aFound && bFound) {
				out = true;
			}
		}
		return out;
	}
    // as per playfair algorithm, occurences of duplicates are interspersed with Z's 
	public char[] removeDuplicate(char[] p) {
		for (int i = 0; i < p.length-1; i++) {
			if (p[i] == p[i+1] && i%2==0) {
				p = this.insertZ(p, i+1);
				break;
			}
		}
		return p;
	}
    // inserts a Z into array, for use with single letter bigrams and duplicates
	public char[] insertZ(char[] c, int index) {
		char[] l = new char[c.length+1];
		for (int i = 0; i < c.length; i++) {
			if (i==index) {
				l[i] = 'z';
				l[i+1] = c[i];
			}
			else {
				if (i < index) {
					l[i] = c[i];
				}
				else {
					l[i+1] = c[i];
				}
			}
		}
		return l;
	}
}
