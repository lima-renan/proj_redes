/*

Para compilar 
                Dependências: gson-2.9.0 repositorio Maven(site: https://mvnrepository.com/artifact/com.google.code.gson/gson/2.9.0 download: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.9.0/gson-2.9.0.jar)
                Para adicionar o gson-2.9.0 ao path do linux: export CLASSPATH=".:/diretorio_em_que_esta/gson-2.9.0.jar"

                Passo 1: javac -d . UDPServer.java
                Passo 2: java udpproject.UDPServer
*/

package udpproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.google.gson.Gson;

import mensagemproject.MensagemUDP;


public class UDPServer{

    public static void main(String[] args){
        try {

            Gson recgson = new Gson(); //instância para gerar a mensagem a partir string json do cliente
            DatagramSocket serverSocket;
            serverSocket = new DatagramSocket(9876); //cria novo datagrama socket na porta 9876

            //O Servidor permanece funcionando
            while(true){ 

                byte[] recBuffer = new byte[1024];

                DatagramPacket recPkt = new DatagramPacket(recBuffer, recBuffer.length);

                serverSocket.receive(recPkt); //BLOCKING

                String informacao = new String(recPkt.getData(),recPkt.getOffset(),recPkt.getLength()); //Datagrama do cliente é convertido em String json

                MensagemUDP msgudp = recgson.fromJson(informacao,MensagemUDP.class);  //gera a mensagem a partir da string json recebida do cliente

                MensagemUDP.formatRec(msgudp.getId(), "normal");

                byte[] sendBuf = new byte[1024];

                Gson gsonsend = new Gson();

                String sendmsgudp = gsonsend.toJson(msgudp); //converte a mensagem em json

                sendBuf = sendmsgudp.getBytes();

                InetAddress IPAddress = recPkt.getAddress();

                int port = recPkt.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, IPAddress, port);

                serverSocket.send(sendPacket);

            }

        } catch (IOException e) {
            //Imprime o erro
            e.printStackTrace();
        }

    }
    
}
