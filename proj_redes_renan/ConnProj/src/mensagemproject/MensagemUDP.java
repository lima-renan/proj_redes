//MensagemUDP.java trata as mensagens que serão enviadas e recebids do cliente/servidor UDP

/*

Para compilar 
                Passo 1:  javac -d . MensagemUDP.java
*/

package mensagemproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import com.google.gson.Gson;

//Cabeçalho que será usado para as mensagens
public class MensagemUDP {
    private String id; //id da mensagem
    private String mensagem; //conteúdo da mensagem
    private String tamanho; //tamanho da mensagem em bytes

    //Construtores da classe
    public MensagemUDP(){

    }

    public MensagemUDP(String id, String msg){
        this.id = id;
        this.mensagem = msg;
        this.tamanho = Integer.toString((msg.length()));
    }

    //Métodos setters e getters

    public String getMensagem(){
        return this.mensagem;
    }

    public String getId(){
        return this.id;
    }
   
    public String getTamanho(){
        return this.tamanho;
    }

    public void setId(String idt){
        this.id = idt;
    }

    public void setMensagem(String msg){
        this.mensagem = msg;
    }

    public void setTamanho(int tmh){
        this.tamanho = Integer.toString(tmh);
    }

//Boas-vindas: Captura a mensagem que o usuário deseja enviar
    public static String capturaMensagem(){
        System.out.print("Digite a mensagem que deseja enviar ou digite sair para encerrar: ");
        Scanner teclado = new Scanner(System.in);
        String mensagem = teclado.nextLine();
        return mensagem;
    }

//Formata mensagem de entrada do usuário
    public static void formatInp(String op, String inp, String id){
        String format = "Mensagem " + "\"" + inp + "\"" + " enviada como " + op + " com id " + id;
        System.out.println(format);
    }

//Formata mensagem recebida no receiver conforme a opção de envio
    public static void formatRec(String id, String op){
        String format;
        //verifica a opção e retorna a mensagem do receiver
        switch(op){ 
            case "fora de ordem":
                format = "Mensagem id " + id + " recebida fora de ordem, ainda não recebidos os identificadores ";
                System.out.println(format);
                break;
            case "duplicada":
                format = "Mensagem id " + id + " recebida de forma duplicada";
                System.out.println(format);
                break;
            case "normal":
                format = "Mensagem id " + id + " recebida na ordem, entregando para a camada de aplicação.";
                System.out.println(format);
                break;
            default:
                format = "Erro - Opção desconhecida";
                System.out.println(format);
        }
    }

//Formata mensagem confirmada pelo receiver
public static void formatConf(String id){
    String format;
    //verifica a opção e retorna a mensagem do receiver
    format = "Mensagem id " + id + " recebida pelo receiver.";
    System.out.println(format);
}

public static String preparaJson (MensagemUDP msg){
    Gson sendgson = new Gson(); //instância para gerar a string json de envio
    String jmsgudp = sendgson.toJson(msg); //converte a mensagem em string json para envio
    return jmsgudp;
}

public static void enviaPacket (String jmsgudp, DatagramSocket clientSocket, InetAddress IPAddress) throws IOException{
    byte[] sendData = new byte [1024]; //buffer de envio
    sendData = (jmsgudp).getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); //cria datagrama de envio
    clientSocket.send(sendPacket); //envia pacote conforme opção
}

//Verifica e configura a opção de envio
    public static String setEnvio(MensagemUDP msg, DatagramSocket clientSocket, InetAddress IPAddress) throws IOException, InterruptedException{
        //Solicita a opcao de envio
        System.out.println("Escolha o número da opção de envio:");
        System.out.println("1 - lenta");
        System.out.println("2 - perda");
        System.out.println("3 - fora de ordem");
        System.out.println("4 - duplicada");
        System.out.println("5 - normal");
        System.out.print("Número: ");
        Scanner teclado = new Scanner(System.in);
        int opcao = teclado.nextInt();
        String jmsgudp = preparaJson(msg); // String que recebe o json da mensagem
        //Formata o input conforme a opção selecionada
        switch(opcao){
            case 1: //envio lento
                formatInp("lenta",msg.getMensagem(),msg.getId());
                Thread.sleep(4000); //aguarda 4s antes de enviar a mensagem para o servidor
                enviaPacket(jmsgudp, clientSocket, IPAddress);
                break;
            case 2: //envio com perda
                formatInp("perda",msg.getMensagem(),msg.getId());
                //Thread.sleep(10000); //aguarda 10s antes de enviar a mensagem para o servidor, porém o tempo é maior que o timeout e o pacote é perdido
                //enviaPacket(jmsgudp, clientSocket, IPAddress); //Quando há perda, o pacote não é enviado
                break;
            case 3:
                formatInp("fora de ordem",msg.getMensagem(),msg.getId());
                 //Cria uma nova mensagem a partir do inteiro e da string  do input do usuário
                int i = (Integer.parseInt(msg.getId()))+1; //próximo id
                MensagemUDP msgudp = new MensagemUDP(String.format("%04d", i), MensagemUDP.capturaMensagem()); //id é passado como string de 4 dígitos

                //caso o usuário digite sair, o processo é encerrado
                if((msgudp.getMensagem()).equals("sair")){
                    break;
                } 
                setEnvio(msgudp, clientSocket, IPAddress);
                enviaPacket(jmsgudp, clientSocket, IPAddress);
                break;
            case 4: //envio duplicado, envia duas vezes
                formatInp("duplicada",msg.getMensagem(),msg.getId());
                enviaPacket(jmsgudp, clientSocket, IPAddress);
                enviaPacket(jmsgudp, clientSocket, IPAddress);
                break;
            case 5: //envio normal
                formatInp("normal",msg.getMensagem(),msg.getId());
                enviaPacket(jmsgudp, clientSocket, IPAddress);
                break;
            default:
                System.out.println("Opção inválida.");
            
        }
        return jmsgudp;
    }
    //Envia um pacote na opção normal, para reenviar mensagem perdida 
    public static String setEnvioNormal(MensagemUDP msg, DatagramSocket clientSocket, InetAddress IPAddress) throws IOException, InterruptedException{
        String jmsgudp = preparaJson(msg); // String que recebe o json da mensagem
        formatInp("normal",msg.getMensagem(),msg.getId());
        enviaPacket(jmsgudp, clientSocket, IPAddress);
        return jmsgudp;
    }
    
}
