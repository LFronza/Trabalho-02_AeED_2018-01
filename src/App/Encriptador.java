/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import ArvoreB.*;
import Lista.ListaEstatica;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Leonardo Fronza
 */
public class Encriptador {
    ArvoreBinaria arvore = new ArvoreBinaria();
    static String[] saida;

    public ArvoreBinaria getArvore() {
        return arvore;
    }

    public void setArvore(ArvoreBinaria arvore) {
        this.arvore = arvore;
    }

    public Encriptador(File Origem, File Destino) throws IOException {
        encriptarArquivo(Origem, Destino);
    }   

    private void gerarArquivo(File Destino, String msg) throws IOException{
        BufferedWriter saida = new BufferedWriter(new FileWriter(Destino.getPath()));
        String[] codigo = encriptar(msg);
        for (int i = 0; i < codigo.length; i++) {
            saida.write(" "+codigo[i]);
            saida.newLine();
        }
            saida.flush();
            saida.close();
    }
    
    private String[] encriptar(String msg) {
        char[] msgChar = msg.toCharArray();
        ListaEstatica<Character> lista;
        lista = new ListaEstatica();
        for (int i = 0; i < msgChar.length; i++) {
            if (!lista.contem(msgChar[i])) {
                lista.inserir(msgChar[i]);
            }
        }
        int[] contaChar = contadorDeChar(lista, msgChar);
        // ordena os caracteres da forma decrescente
        for (int i = 0; i < contaChar.length - 1; i++) {
            for (int j = 0; j < contaChar.length - 1; j++) {
                if (contaChar[j] > contaChar[j + 1]) {
                    int aux = contaChar[j];
                    contaChar[j] = contaChar[j + 1];
                    contaChar[j + 1] = aux;

                    char charAux = lista.obterElemento(j);
                    lista.reposicionar(j, lista.obterElemento(j + 1));
                    lista.reposicionar(j + 1, charAux);
                }
            }
        }
        
        ListaEstatica<NoArvoreBinaria> nos = new ListaEstatica();

        for (int i = 0; i < lista.getTamanho(); i++) {
            nos.inserir(new NoArvoreBinaria(lista.obterElemento(i).toString(), contaChar[i]));
        }

        int soma;

        while (nos.getTamanho() > 1) {
            soma = nos.obterElemento(0).getQtd() + nos.obterElemento(1).getQtd();
            nos.inserir(new NoArvoreBinaria(soma + "", nos.obterElemento(0), nos.obterElemento(1), soma));
            nos.retirar(nos.obterElemento(0));
            nos.retirar(nos.obterElemento(0));
        }
        arvore.setRaiz(nos.obterElemento(0));
        saida = new String[contaChar.length + 2];
        saida[0] = "" + contaChar.length;
        int charNr;
        for (int i = 1; i < contaChar.length + 1; i++) {
            charNr = (int) lista.obterElemento(i-1).charValue();
            saida[i] = charNr+"="+arvore.mapear(lista.obterElemento(i-1)+"");
        }
        saida[contaChar.length+1] = fraseEncriptada(msg);
        
        return saida;
    }

    private int[] contadorDeChar(ListaEstatica lista, char[] msg) {
        int[] aux = new int[lista.getTamanho()];
        for (int i = 0; i < msg.length; i++) {
            for (int j = 0; j < aux.length; j++) {
                if (msg[i] == (char) lista.obterElemento(j)) {
                    aux[j]++;
                }
            }
        }

        return aux;
    }
    
    private String fraseEncriptada(String frase){
        String binario = "";
        for (int i = 0; i < frase.length(); i++) {
            binario += arvore.mapear(""+ frase.charAt(i));
        }
        return binario;
    }
    
    private void encriptarArquivo(File Origem, File Destino) throws IOException{
        gerarArquivo(Destino, lerArquivo(Origem));
    }
   
    private String lerArquivo(File Arquivo) throws IOException{
        BufferedReader in = new BufferedReader(new FileReader(Arquivo.getAbsolutePath()));
                int rd = in.read();
                String saida = "";
                while(rd != -1){
                    saida = in.readLine();
                    rd = in.read();
                }
                in.close();
                return saida;
    }
}
