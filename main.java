class main{
    public static void main(String[] args){
        System.out.println("Start of encryption");
        main m = new main();
        String x = m.encrypt("hello world hello world hello world hello world", "", "elephant");
        System.out.println(x);        
        String y = m.decrypt(x, "", "elephant");
        System.out.println(y);
    }
    public String encrypt(String in, String out, String key){
        Key k = new Key(key);
        Text t = new Text(in);
        Crypt cr = new Crypt(k, t);        
        return cr.get();
    }
    public String decrypt(String in, String out, String key){
        Key k = new Key(key);
        Text t = new Text(in);
        Crypt cr = new CryptE(k, t, false);        
        return cr.get();
    }
}
