class CryptE extends Crypt {
    String plaintext;
    public CryptE(Key k, Text t, boolean forward){
        super(k, t);
        char[] p = t.raw.toCharArray();
		char[][] ke = k.getPureKey();
		//p = this.preparePlaintext(p); // fill repeats with z and if result is odd put z at the end
        plaintext = String.copyValueOf(this.decrypt(k.getPureKey(), p));
    }
    @Override
    public String get(){
        return plaintext;
    }
    @Override
	public char[] preparePlaintext(char[] t) {
		char[] c;
		c = t.clone();
		return c;
	}
    // returns the encryption wrt to the key and the plaintext
	public char[] decrypt(char[][] key, char[] preparedText) {
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
    @Override
	public char[] vertPF(char[][] key, char[] bigram, int keyWidth) {
		char[] out = new char[] {'5','5'}; // null chars
		for (int i = 0; i < keyWidth; i++) {
			for (int j = 0; j < keyWidth; j++) {
				if (key[i][j]==bigram[0]) {
					if (i!=0) {
						out[0] = key[i-1][j];
					}
					else {
						out[0] = key[keyWidth-1][j];
					}
				}
				else if(key[i][j]==bigram[1]) {
					if (i!=0) {
						out[1] = key[i-1][j];
					}
					else {
						out[1] = key[keyWidth-1][j];
					}
				}
			}
		}
		return out;		
	}
    // returns location along horizontal of encryption characters (to the right)
    @Override
	public char[] horizPF(char[][] key, char[] bigram, int keyWidth) {
		char[] out = new char[] {'5','5'}; // null chars
		for (int i = 0; i < keyWidth; i++) {
			for (int j = 0; j < keyWidth; j++) {
				if (key[i][j]==bigram[0]) {
					if (j!=0) {
						out[0] = key[i][j-1];
					}
					else {
						out[0] = key[i][keyWidth-1];
					}
				}
				else if(key[i][j]==bigram[1]) {
					if (j!=0) {
						out[1] = key[i][j-1];
					}
					else {
						out[1] = key[i][keyWidth-1];
					}
				}
			}
		}
		return out;		
	}
    // diagonal processing is the same both ways just acting on different elements


}
