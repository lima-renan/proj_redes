import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        
        // Tenta criar uma conexão com o host remoto "127.0.0.1" na porta 9000
        // Socket s terá uma porta designada pelo SO - entre 1024 e 65535
        Socket s = new Socket("127.0.0.1", 9000);

        // cria a cadeia de saída (escrita) de informações do socket
        OutputStream os = s.getOutputStream();
        DataOutputStream writer = new DataOutputStream(os);

        //cria a cadeia de entrada (leitura) de informações do socket
        InputStreamReader is = new InputStreamReader(s.getInputStream());
        BufferedReader reader = new BufferedReader(is);

        // cria um buffer que lê informações do teclado
        BufferedReader inFromUser = new BufferedReader (new InputStreamReader(System.in));

        // leitura do teclado 
        String texto = inFromUser.readLine(); //BLOCKING

        // escrita no socket (envio de informação ao host remoto)
        writer.writeBytes(texto + "\n");

        // leitura do socket (recebimento de informação do host remoto)
        String response = reader.readLine(); //BLOCKING
        System.out.println("DoServidor:" + response);

        //fechamento do canal (socket)
        s.close();

    }
}
