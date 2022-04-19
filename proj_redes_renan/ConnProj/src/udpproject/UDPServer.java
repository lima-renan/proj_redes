/*

Para compilar 
                Dependências: gson-2.9.0 repositorio Maven(site: https://mvnrepository.com/artifact/com.google.code.gson/gson/2.9.0 download: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.9.0/gson-2.9.0.jar)
                Para adicionar o gson-2.9.0 ao path do linux: export CLASSPATH=".:/diretorio_em_que_esta/gson-2.9.0.jar"

                Passo 1: javac -d . UDPServer.java
                Passo 2: java udpproject.UDPServer
*/

package udpproject;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.HashMap;


import mensagemproject.MensagemUDP;


public class UDPServer{

    public static void main(String[] args){
        try {

            DatagramSocket serverSocket;
            serverSocket = new DatagramSocket(9876); //cria novo datagrama socket na porta 9876
            HashMap<String, String> recebidas = new HashMap<>(); // Cria um HashMap para controlar as mensagens recebidas

            //O Servidor permanece funcionando
            while(true){ 

                MensagemUDP.setRecebidas(serverSocket, recebidas); //Trata as mensagens recebidas e envia o ACK para o cliente

            }

        } catch (IOException e) {
            //Imprime o erro
            e.printStackTrace();
        }

    }
    
}
