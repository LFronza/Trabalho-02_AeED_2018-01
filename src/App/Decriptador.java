/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import ArvoreB.ArvoreBinaria;
import ArvoreB.NoArvoreBinaria;
import Lista.ListaEstatica;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Leonardo
 */
public class Decriptador {

    public Decriptador(File Origem, File Destino) throws IOException {
        decriptarArquivo(Origem, Destino);
    }
    
    
    private String decriptar(String msg){
        ListaEstatica<String> letras = new ListaEstatica<>();
        ListaEstatica<String> caminho = new ListaEstatica<>();
        String[] msgVet = msg.split("\n");
        int linhas = Integer.parseInt(msgVet[0]);
        for (int i = 1; i < linhas + 1; i++) {      
            letras.inserir(ASCIItoString(msgVet[i]));
            caminho.inserir(getCaminho(msgVet[i]));
        }
        ArvoreBinaria arvore = new ArvoreBinaria();
        arvore.setRaiz(new NoArvoreBinaria());
        for (int i = 0; i < letras.getTamanho(); i++) {
            fazerArvore(arvore.getRaiz(), letras.obterElemento(i), caminho.obterElemento(i));
        }
        return lerCodigo(arvore, msgVet[msgVet.length-1]);
    }
    
    private void gerarArquivo(File Destino, String msg) throws IOException{
        BufferedWriter saida = new BufferedWriter(new FileWriter(Destino.getPath()));
        String[] codigo = decriptar(msg).split("\n");
        for (int i = 0; i < codigo.length; i++) {
            saida.write(codigo[i]);
            saida.newLine();
        }
            saida.flush();
            saida.close();
    }
    
    private String lerArquivo(File Arquivo) throws IOException{
        BufferedReader in = new BufferedReader(new FileReader(Arquivo.getAbsolutePath()));
                int rd = in.read();
                String saida = "";
                while(rd != -1){
                    saida += in.readLine() + "\n";
                    rd = in.read();
                }
                in.close();
                return saida;
    }
    
    private void decriptarArquivo(File Origem, File Destino) throws IOException{
        gerarArquivo(Destino, lerArquivo(Origem));
    }
    
    private String ASCIItoString(String linha){
        char[] letras = linha.toCharArray();
        String aux = "";
        for (int i = 0; i < letras.length; i++) {
            if(letras[i] != '='){
                aux += letras[i];
            } else {
                break;
            }
        }
        char letra = (char) Integer.parseInt(aux);
        return letra+"";
    }
    
    private String getCaminho(String linha){
        char[] letras = linha.toCharArray();
        String saida = "";
        boolean ativado = false;
        for (int i = 0; i < letras.length; i++) {
            if (letras[i] == '=' && !ativado) {
                ativado = true;
            }
            if(ativado && (letras[i] != '\n' && letras[i] != '=') ){
                saida += letras[i];
            }                
        }
        return saida;
    }
    
    private void fazerArvore(NoArvoreBinaria no, String letra, String caminho){
        for (int i = 0; i < caminho.length(); i++) {
            switch(caminho.charAt(i)){
                case '1': if (no.getDireita() != null){
                    no = no.getDireita();
                } else {
                    if(i == caminho.length() -1){
                        no.setDireita(new NoArvoreBinaria(letra));
                    } else {
                        no.setDireita(new NoArvoreBinaria());
                        no = no.getDireita();
                    }
                } break;
                case '0': if(no.getEsquerda() != null){
                    no = no.getEsquerda();
                } else {
                    if(i == caminho.length() -1){
                    no.setEsquerda(new NoArvoreBinaria(letra));
                } else {
                        no.setEsquerda(new NoArvoreBinaria());
                        no = no.getEsquerda();
                    }
                } break;
                default: throw new RuntimeException("caminho inválido");
            }
        }
    }
    private String lerCodigo(ArvoreBinaria arvore, String codigo){
        String saida = "";
        NoArvoreBinaria no = arvore.getRaiz();
        for (int i = 0; i < codigo.length(); i++) {
            switch(codigo.charAt(i)){
                case '1': no = no.getDireita();
                            if(isOnFolha(no)){
                                saida += no.getInfo();
                                no = arvore.getRaiz();
                            } break;
                case '0': no = no.getEsquerda();
                            if(isOnFolha(no)){
                                saida += no.getInfo();
                                no = arvore.getRaiz();
                            } break;
                default: break;
            }
        }
        return saida;
    }
    
    private boolean isOnFolha(NoArvoreBinaria no) {
        return no.getDireita() == null && no.getEsquerda() == null;
    }
}
