package model;

// this class takes some text string and turns it into a playfair cipher key
// a playfair cipher key typically takes a word, removes its repeats, then fills the rest of the alphabet 
// in order after that string, and it does not include j, it organises this from left to right on a 5x5 grid
public class Key {
	char[][] pureKey;
	final char[] alphabet = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
	public Key(String keyword) {
		if (keyword.length()<2 || keyword.length() > 26) {
			return;
		}
		pureKey = new char[5][5];
		char[] kA;
		kA = keyword.toCharArray();
		kA = this.removeRepeats(kA);
		kA = this.alphabetize(kA);
		kA = this.removeJ(kA);
		pureKey = this.split(5, kA);
	}
	public char[][] getPureKey() {
		return pureKey;
	}
	public char[][] split(int vertSize, char[] chars){
		char[][] key;
		if (chars.length%vertSize != 0 && (vertSize*vertSize != chars.length)) {
			return new char[0][0];
		}
		else {
			key = new char[vertSize][vertSize];
			for (int i = 0; i < chars.length; i++) {
				key[i/vertSize][i%vertSize] = chars[i];
			}
		}
		return key;
	}
	public char[] removeJ(char[] kA) {
		int placeCount = 0;
		char[] out = new char[kA.length-1];
		for (int i = 0; i < kA.length; i++) {
			if (kA[i]=='j') {
				continue;
			}
			else {
				out[placeCount] = kA[i];
				placeCount++;
			}
		}
		return out;
	}
	public char[] alphabetize(char[] kA) {
		char[] deadKey = new char[26];
		for (int i = 0; i < kA.length; i++) {
			deadKey[i] = kA[i]; // assume the repeats have been removed
		}
		for (int i = 0; i < alphabet.length; i++) {
			deadKey = this.fillChar(alphabet[i], deadKey);
		}
		char[] out = new char[26];
		for (int i = 0; i < out.length; i++) {
			out[i] = deadKey[i]; // for some reason I get too big an array? so i did this
		}
		return out;
	}
	public char[] fillChar(char c, char[] deadKey2) {
		// only adds the char if it's not already there
		char[] result = new char[deadKey2.length+1];
		boolean there = false;
		for (int i = 0; i < deadKey2.length; i++) {
			result[i] = deadKey2[i]; // (also set the new array to be the old one)
			if (deadKey2[i]==c) {
				there = true;
			}
		}
		if (!there) {
			int count = 0;
			while(result[count] != '\u0000') {
				count++;
			}
			result[count] = c;
		}
		return result;
	}
	public char[] removeRepeats(char[] kA) {
		for (int i = 0; i < kA.length; i++) {
			for (int j = i; j < kA.length; j++) {
				if (i==j) {
					continue;
				}
				else {
					if (kA[i]==kA[j]) {
						kA[j]='@'; // means dead char
					}
				}
			}
		}
		int countOfAts = 0;
		for(char c : kA) {
			if (c=='@') {
				countOfAts++;
			}
		}
		char[] output = new char[kA.length - countOfAts];
		int count = 0;
		for (char c : kA) {
			if (c != '@') {
				output[count] = c;
				count++;
			}
		}
		return output;
	}
}
