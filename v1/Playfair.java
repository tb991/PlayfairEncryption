class Playfair {
	char[][] key;
	public Playfair(String keyGenText){
			
	}
	public static void main(String[] args){
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
	// playfair key generation with any length, but without repeat detection
	private static char[][] keyGen(String k){
		int keyW = sqrt(k.length());
		int count = 0;
		String spacing = " ";
		int it = 0;
		char[][] ke = new char[keyW][keyW];
		char now;
		while (it < k.length()){
			now = k.charAt(it);
			ke[it%keyW][it/keyW]=now;
			print(ke[it%keyW][it/keyW]);
			it++;
			count++;
			if (count%(keyW)==0){
				print("\n");
			}
		}
		return ke;
	}
	
}
