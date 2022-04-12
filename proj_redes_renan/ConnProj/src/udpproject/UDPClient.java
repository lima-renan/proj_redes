package udpproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import mensagemproject.MensagemUDP;
import Gson

public class UDPClient {
    public static void main (String[] args) throws IOException {

        DatagramSocket clientSocket = new DatagramSocket(); // Sistema Operacional assina uma porta

        InetAddress IPAddress = InetAddress.getByName("127.0.0.1"); //Define um IP

        while(true){

            //Cria uma nova mensagem
            MensagemUDP msgudp = new MensagemUDP();
            String newMsg = MensagemUDP.capturaMensagem();

            //caso o usuário digite sair, o processo é encerrado
            if(newMsg.equals("sair")){
                break;
            }
            msgudp.setMensagem(newMsg); 
            
            MensagemUDP.setEnvio(msgudp.getMensagem());

            Gson gson = new Gson();

            byte[] sendData = new byte [1024];
            sendData = (msgudp.getMensagem()).getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

            clientSocket.send(sendPacket);

            System.out.println("Mensagem enviada para o servidor!");

            byte[] recBuffer = new byte[1024];

            DatagramPacket recPkt = new DatagramPacket(recBuffer, recBuffer.length);

            clientSocket.receive(recPkt);

            String informacao = new String(recPkt.getData(),
                    recPkt.getOffset(),
                    recPkt.getLength());

            System.out.println("Recebido do servidor: " + informacao);
        }
    }
}