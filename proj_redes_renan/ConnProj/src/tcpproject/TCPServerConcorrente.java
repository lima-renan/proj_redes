package tcpproject;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerConcorrente {
    public static void main(String[] args) throws Exception{

        // Criar o mecanismo para escutar e atender conexões pela porta 9000.
        ServerSocket serverSocket = new ServerSocket(9000);

        while(true){

            System.out.println("Esperando conexão.");

            // método bloqueante que cria um novo socket com o nó
            // Socket nó terá uma porta designada pelo SO - entre 1024 e 65535
            Socket no = serverSocket.accept(); //BLOCKING

            System.out.println("Conexão aceita.");

            // thread para atender novo nó
            ThreadAtendimento thread = new ThreadAtendimento(no);
            thread.start(); // executa a thread -> chamada ao método run()
        } 
    }
}
