class Playfair {
	public Playfair(){
			
	}
	public static void main(String[] args){
		printy();
		keyprint("ABCDEFGHIKLMNOPQRSTUVWXYZ");
		keyprint("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}
	private static void print(Object l){
		System.out.print(String.valueOf(l));
	}
	private static void printy(){
		short len = 6;
		short count = 0;
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
	private static void keyprint(String k){
		short keyW = 0;
		while (keyW*keyW < k.length()){
			keyW++;
		}
		keyW--;
		short len = 6;
		short count = 0;
		String spacing = " ";
		short it = 0;
		while (it < k.length()){
			print(k.charAt(it));
			it++;
			print(spacing);
			count++;
			if (count%(keyW+1)==0){
				print("\n");
			}
		}
	}
	
}
