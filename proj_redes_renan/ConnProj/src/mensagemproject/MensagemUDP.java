/*

Para compilar 
                Passo 1: javac -d . MensagemUDP.java
                Passo 2: java mensagemproject.MensagemUDP
*/

package mensagemproject;

import java.io.IOException;
import java.util.Scanner;

//Cabeçalho que será usado para as mensagens
public class MensagemUDP {
    private String id; //id da mensagem
    private String mensagem; //conteúdo da mensagem
    private int tamanho; //tamanho da mensagem em bytes

    //Construtores da classe
    public MensagemUDP(){

    }

    public MensagemUDP(String id, String msg){
        this.mensagem = msg;
    }

    public String getMensagem(){
        return mensagem;
    }

    //Métodos setters e getters
    public int getTamanho(){
        return tamanho;
    }

    public void setId(String idt){
        this.id = idt;
    }

    public void setMensagem(String msg){
        this.mensagem = msg;
    }

    public void setTamanho(int tmh){
        this.tamanho = tmh;
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

//Verifica e configura a opção de envio
    public static void setEnvio(String msg){
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
        String resp; 
        //Formata o input conforme a opção selecionada
        switch(opcao){
            case 1:
                formatInp("lenta",msg,"0000");
                break;
            case 2:
                formatInp("perda",msg,"0000");
                break;
            case 3:
                formatInp("fora de ordem",msg,"0000");
                break;
            case 4:
                formatInp("duplicada",msg,"0000");
                break;
            case 5:
                formatInp("normal",msg,"0000");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
    
    public static void main (String[] args) throws IOException {
        MensagemUDP msgudp = new MensagemUDP();
        msgudp.setMensagem(capturaMensagem()); 
        setEnvio(msgudp.getMensagem());
    }
}
