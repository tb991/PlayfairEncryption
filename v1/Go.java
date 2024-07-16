class Go{
	public static void main(String[] args){
		Playfair p;
		p = new Playfair("ELEPHANT", "ABCDEFGHIKLMNOPQRSTUVWXYZ");
		String plaintext = "BANEVADING";
		System.out.println("The encryption of " + plaintext + " is:");
		p.print(p.enc(plaintext));
		System.out.println(" ");
		String s = p.enc(plaintext);
		System.out.println(" ");
		System.out.println("The decryption of " + s + " is:");
		p.print(p.dec(s));
	}
}
