class main{
    public static void main(String[] args){
        System.out.println("Start of encryption");
        main m = new main();
        m.encrypt("hello world hello world hello world hello world", "", "elephant");
    }
    public void encrypt(String in, String out, String key){
        Key k = new Key(key);
        Text t = new Text(in);
        Crypt cr = new Crypt(k, t);        
        System.out.println(cr.get());
    }
}
