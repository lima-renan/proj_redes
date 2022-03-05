public class ThreadProj  extends Thread{
    private String threadName;

    public ThreadProj (String nome){
        threadName = nome;
    }

    public void run(){
        for (int i = 4; i > 0; i--){
            System.out.println("T:" + threadName + " " + i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
    }
    
}
