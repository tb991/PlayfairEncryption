class Playfair {
	static char[][] key;
	static String alpha; // alphabet
	static int len;
    static int sqL;
	static String txt;
	public Playfair(String text, String alphabet){
		alpha = removeRepeats(alphabet);
		txt = removeRepeats(text);
		String keyString = removeRepeats(txt+alpha);
		print(keyString);
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
        print(get(0,-1));
        print(get(-1,1));
        print(get(0,sqL));
        print(get(sqL,1));
	}
    // 0,0-4 would get the tpo row (if done individually
    private static char get(int h, int w){
        if (h==-1){
            h = sqL-1;
        }
        if (w==-1){
            w = sqL-1;
        }
        return key[w%sqL][h%sqL];
    }
	public static void main(String[] args){
		Playfair pf;
		pf = new Playfair("honeycomb", "abcdefghiklmnopqrstuvwxyz");
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
	private static void print(Object l){
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
