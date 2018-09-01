package model;

public class Text {
	
	private String plaintext;
	public int[] spaces; // location of the spaces in the text eg helloworld => 5
	final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public Text(String t) {
		plaintext = String.copyValueOf(this.prepForPlayfair(t));
	}
	public String getPlaintext() {
		return plaintext;
	}
	public char[] removeJ(String p) {
		char[] s = p.toCharArray();
		for (int i = 0; i < s.length; i++) {
			if (s[i]=='j') {
				s[i] = 'i';
			}
		}
		return s;
	}
	public char[] prepForPlayfair(String p) {
		char[] c = this.removeJ(p);
		char[] out;
		boolean[] validChars = new boolean[c.length];
		int numTrue = 0;
		for (int i = 0; i < c.length; i++) {
			boolean validChar = false;
			boolean cycleComplete = false;
			for (int j = 0; j < alphabet.length; j++) {
				if (c[i]==alphabet[j]) {
					validChar = true;
				}
				if (validChar) {
					validChars[i] = true;
					if (!cycleComplete) {
						numTrue++;
						cycleComplete = true;
					}
				}
				else {
					validChars[i] = false;
				}
			}
		}
		int placed = 0;
		out = new char[numTrue];
		this.spaces = new int[p.length() - numTrue]; // got a side job of replacing punctuation (with spaces)
		int spaceIndex = 0;
		for (int i = 0; i < validChars.length; i++) {
			if (validChars[i]) {
				out[placed] = c[i];
				placed++;
			}
			else {
				// side job
				this.spaces[spaceIndex] = i;
				spaceIndex++;
			}
		}
		return out;
	}
}
