class Go{
	public static void main(String[] args){
		Playfair p;
		p = new Playfair("ELEPHANT", "ABCDEFGHIKLMNOPQRSTUVWXYZ");
		p.print(p.enc("ISTHISGONNAWORK"));
	}
}
