#Criar ambiente JDK-8
FROM openjdk:8
WORkDIR /root/proj_redes/src
ADD proj_redes_renan /root/proj_redes

#Compila o projeto
RUN javac App.java

ENTRYPOINT java App

#Execucao
#gerar imagem
#docker build -t proj_redes:v1 .

#rodar imagem no container
#docker run proj_redes:v1