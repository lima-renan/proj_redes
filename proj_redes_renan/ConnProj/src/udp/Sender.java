/*

Para compilar 
                Dependências: gson-2.9.0 repositorio Maven(site: https://mvnrepository.com/artifact/com.google.code.gson/gson/2.9.0 download: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.9.0/gson-2.9.0.jar)
                Passo 1: javac -d . UDPClient.java
                Passo 2: java udpproject.UDPClient
*/

package udp;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import mensagem.Mensagem;


public class Sender {

    public static void main (String[] args) throws IOException, InterruptedException {

        HashMap<String, String> enviadas = new HashMap<>(); // Cria HashMap para armazenar mensagens enviadas

        HashMap<String, String> confirmadas = new HashMap<>(); // Cria HashMap para armazenar os ACKs
       
        int i = 1; // inteiro que será usado como id da msg

        DatagramSocket clientSocket = new DatagramSocket(); // Sistema Operacional assina uma porta

        InetAddress IPAddress = InetAddress.getByName("127.0.0.1"); // Define um IP

        // Enquanto o usuário não digitar sair ou Ctrl+C, o cliente continuará executando
        while(true){

            i =  Mensagem.vazioId(i, confirmadas); // Verifica qual é a primeira posição vazia para o id

            // Cria uma nova mensagem a partir do inteiro e da string  do input do usuário
            Mensagem msgudp = new Mensagem(String.format("%04d", i), Mensagem.capturaMensagem()); //id é passado como string de 4 dígitos

            // caso o usuário digite sair, o processo é encerrado
            if((msgudp.getMensagem()).equals("sair")){
                break;
            } 
            

            // Prepara, envia o pacote e retorna o cabeçalho do pacote enviado. Em seguida, retorna o id da mensagem enviada
            Mensagem.setEnvio(msgudp, i, enviadas, clientSocket, IPAddress); // Exibe na ta tela a mensagem que será enviada, adiciona ao HashMap e envia ao servidor

            try { //inicializa o temporizador
                Mensagem.senderACK(enviadas, confirmadas, clientSocket); //recebe o ACK do receiver

            }catch(SocketTimeoutException e){ // Procedimento para quando houver timeout e não houver confirmação de recebimento

                Mensagem.setReenvio(msgudp, enviadas, clientSocket, IPAddress); // Prepara, reenvia o pacote e retorna o cabeçalho do pacote enviado 

                Mensagem.senderACK(enviadas, confirmadas, clientSocket); // Recebe o ACK do receiver

                continue; // continua no loop
            }
        }
    }
}