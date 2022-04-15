/*

Para compilar 
                Dependências: gson-2.9.0 repositorio Maven(site: https://mvnrepository.com/artifact/com.google.code.gson/gson/2.9.0 download: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.9.0/gson-2.9.0.jar)
                Passo 1: javac -d . UDPClient.java
                Passo 2: java udpproject.UDPClient
*/

package udpproject;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import mensagemproject.MensagemUDP;
import com.google.gson.Gson; //Biblioteca para manipulação de json

public class UDPClient {

    public static void main (String[] args) throws IOException, InterruptedException {
       
        int i = 1; //inteiro que será usado como id da msg

        Gson sendgson = new Gson(); //instância para gerar a string json de envio

        DatagramSocket clientSocket = new DatagramSocket(); // Sistema Operacional assina uma porta

        InetAddress IPAddress = InetAddress.getByName("127.0.0.1"); //Define um IP

        //Enquanto o usuário não digitar sair ou Ctrl+C, o cliente continuará executando
        while(true){

            //Cria uma nova mensagem a partir do inteiro e da string  do input do usuário
            MensagemUDP msgudp = new MensagemUDP(String.format("%04d", i), MensagemUDP.capturaMensagem()); //id é passado como string de 4 dígitos

            //caso o usuário digite sair, o processo é encerrado
            if((msgudp.getMensagem()).equals("sair")){
                break;
            } 
            
            //Incrementa o inteiro para gerar o id da próxima mensagem
            i = i + 1;

            MensagemUDP.setEnvio(msgudp, clientSocket, IPAddress); //Exibe na ta tela a mensagem que será enviada
/*
            String jmsgudp = sendgson.toJson(msgudp); //converte a mensagem em string json para envio
            byte[] sendData = new byte [1024]; //buffer de envio
            
            sendData = (jmsgudp).getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); //cria datagrama de envio

            //envia pacote conforme opção

            clientSocket.send(sendPacket); */

            try { //inicializa o temporizador
                clientSocket.setSoTimeout(7000); //temporizador aguarda até 7s

                byte[] recBuffer = new byte[1024]; //buffer de recebimento

                DatagramPacket recPkt = new DatagramPacket(recBuffer, recBuffer.length); //cria pacote de recebimento

                clientSocket.receive(recPkt); //recebe o pacote do servidor

                String informacao = new String(recPkt.getData(),recPkt.getOffset(),recPkt.getLength()); //obtem a mensagem no formato json string

                Gson recgson = new Gson(); //instância para gerar a string json de recebimento

                MensagemUDP respmsgudp = recgson.fromJson(informacao, MensagemUDP.class); //converte a string json em mensagem

                MensagemUDP.formatConf(respmsgudp.getId()); //Exibe na tela o id da mensagem que foi confirmada pelo servidor
            }catch(SocketTimeoutException e){

                byte[] recBuffer = new byte[1024]; //buffer de recebimento

                DatagramPacket recPkt = new DatagramPacket(recBuffer, recBuffer.length); //cria pacote de recebimento

                clientSocket.receive(recPkt); //recebe o pacote do servidor

                String informacao = new String(recPkt.getData(),recPkt.getOffset(),recPkt.getLength()); //obtem a mensagem no formato json string

                Gson recgson = new Gson(); //instância para gerar a string json de recebimento

                MensagemUDP respmsgudp = recgson.fromJson(informacao, MensagemUDP.class); //converte a string json em mensagem

                MensagemUDP.formatConf(respmsgudp.getId()); //Exibe na tela o id da mensagem que foi confirmada pelo servidor
                continue; //continua no loop
            }
        }
    }
}