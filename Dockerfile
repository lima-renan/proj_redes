#Criar ambiente JDK-8
FROM openjdk:8
WORKDIR /root/proj_redes/
ADD MensagemUDP.java /root/proj_redes/

#Compila o projeto
RUN javac -d . MensagemUDP.java

ENTRYPOINT java mensagemproject.MensagemUDP

#Execucao
#gerar imagem
#docker build -t proj_redes:v1 .

#rodar imagem no container
#docker run proj_redes:v1
