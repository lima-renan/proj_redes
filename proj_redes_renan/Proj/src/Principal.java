

public class Principal {
    public static void main(String[] args) throws Exception {
        ThreadProj td = new ThreadProj("teste");
        ThreadProj td2 = new ThreadProj("teste2");
        td.start();
        td2.start();
    }
}
