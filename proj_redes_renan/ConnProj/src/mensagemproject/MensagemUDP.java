package mensagemproject;

import java.io.IOException;
import java.util.Scanner;

public class MensagemUDP {

    private String portaOrigem;
    private String portaDestino;
    private String mensagem;
    private int tamanho;

    public MensagemUDP(){

    }

    public MensagemUDP(String prtOrigem, String prtDestino, String msg){
        this.portaOrigem = prtOrigem;
        this.portaDestino = prtDestino;
        this.mensagem = msg;
    }

    public String getPortaOrigem(){
        return portaOrigem;
    }

    public String getPortaDestino(){
        return portaDestino;
    }

    public String getMensagem(){
        return mensagem;
    }

    public int getTamanho(){
        return tamanho;
    }

    public void setPortaOrigem(String porta){
        this.portaOrigem = porta;
    }

    public void setPortaDestino(String porta){
        this.portaDestino = porta;
    }

    public void setMensagem(String msg){
        this.mensagem = msg;
    }

    public void setTamanho(int tmh){
        this.tamanho = tmh;
    }

//Captura a mensagem que o usuário deseja enviar
    public static String capturaMensagem(){
        System.out.print("Digite a mensagem que deseja enviar: ");
        Scanner teclado = new Scanner(System.in);
        String mensagem = teclado.nextLine();
        return mensagem;
    }

//Verifica e configura a opção de envio
    public static void setEnvio(){
        System.out.println("Escolha o número da opção de envio:");
        System.out.println("1 - lenta");
        System.out.println("2 - perda");
        System.out.println("3 - fora de ordem");
        System.out.println("4 - normal");
        Scanner teclado = new Scanner(System.in);
        int opcao = teclado.nextInt();
        System.out.println(opcao);
    }
    
    public static void main (String[] args) throws IOException {
        MensagemUDP msgudp = new MensagemUDP();
        msgudp.setMensagem(capturaMensagem()); 
        System.out.println(msgudp.getMensagem());
        setEnvio();
    }
}
