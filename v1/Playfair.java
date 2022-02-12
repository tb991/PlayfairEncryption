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
	}
	private static void print(Object l){
		System.out.print(String.valueOf(l));
	}
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
	private static int sqrt(int in){
		int keyW = 0;
		while (keyW*keyW <= in){
			keyW++;
		}
		keyW--;
		return keyW;
	}
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
