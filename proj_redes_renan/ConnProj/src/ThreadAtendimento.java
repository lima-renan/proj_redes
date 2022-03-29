import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ThreadAtendimento extends Thread {
    
        private Socket no;

        public ThreadAtendimento (Socket node){
            no = node;
        }

        public void run() {
             
            try { 
                //cria a cadeia de entrada (leitura) de informações do socket
                InputStreamReader is = new InputStreamReader(no.getInputStream());
                BufferedReader reader = new BufferedReader(is);

                // cria a cadeia de saída (escrita) de informações do socket
                OutputStream os = no.getOutputStream();
                DataOutputStream writer = new DataOutputStream(os);


                // envia texto para o cliente
                String texto = reader.readLine();

                writer.writeBytes(texto.toUpperCase() + "\n");
            }
            catch(Exception e){

            }
        }
    
}
