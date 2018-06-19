/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import ArvoreB.*;
import Lista.ListaEstatica;
import java.io.*;


/**
 *
 * @author Leonardo Fronza
 */
public class Encriptador {
    
    ArvoreBinaria arvore;

     /**
     * Getter da árvore
     * @return árvore binária
     */
    public ArvoreBinaria getArvore() {
        return arvore;
    }
    /**
     * Define uma nova ávore binária
     * @param arvore a nova árvore binária
     */
    public void setArvore(ArvoreBinaria arvore) {
        this.arvore = arvore;
    }
    /**
     * Construtor da classe
     * @param Origem Arquivo de origem
     * @param Destino Arquivo de destino
     * @throws IOException Arquivo não encontrado
     */
    public Encriptador(File Origem, File Destino) throws IOException {
        setArvore(new ArvoreBinaria());
        encriptarArquivo(Origem, Destino);
    }
    /**
     * Gera um novo arquivo com o conteúdo descompactado
     *
     * @param Destino Arquivo com onde o conteúudo descompactado será despejado
     * @param msg conteúdo do que será depositado no arquivo
     * @throws IOException Arquivo não encontrado
     */
    private void gerarArquivo(File Destino, String msg) throws IOException {
        BufferedWriter saida = new BufferedWriter(new FileWriter(Destino.getPath()));
        String[] codigo = encriptar(msg);
        for (int i = 0; i < codigo.length; i++) {
            saida.write(codigo[i]);
            saida.newLine();
        }
        saida.flush();
        saida.close();
    }
    /**
     * Lê o conteúdo do arquivo e o compacta com base no algorítmo de Huffman
     * @param msg Texto a ser compactado
     * @return Texto compactado
     */
    private String[] encriptar(String msg) {
        String[] saida;
        char[] msgChar = msg.toCharArray();
        ListaEstatica<Character> lista;
        lista = new ListaEstatica();
        //cria uma lista contendo os caracteres
        for (int i = 0; i < msgChar.length; i++) {
            if (!lista.contem(msgChar[i])) {
                lista.inserir(msgChar[i]);
            }
        }
        //cria um vetor contendo a quantidade de repetição de cada caractere
        int[] contaChar = contadorDeChar(lista, msgChar);
        // ordena os caracteres da forma crescente
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
        //cria a arvore binária
        getArvore().setRaiz(criarArvore(lista, contaChar));
        //compacta o texto no algoritmo de Huffman
        saida = new String[contaChar.length + 2];
        saida[0] = "" + contaChar.length;
        int charNr;
        for (int i = 1; i < contaChar.length + 1; i++) {
            charNr = (int) lista.obterElemento(i - 1).charValue();
            saida[i] = charNr + "=" + getArvore().mapear(lista.obterElemento(i - 1) + "");
        }
        saida[contaChar.length + 1] = fraseCompactada(msg);

        return saida;
    }
    /**
     * Conta quantas letras tem na palavra e cria um vetor com a quantidade 
     * respectiva de caractere
     * @param lista a lista de caracteres
     * @param msg a frase a ser lida
     * @return vetor com a quantidade respectivas de caracteres da lista
     */
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
    /**
     * Retorna o texto compactado conforme o algoritmo de Huffman
     * @param frase Texto a ser compactado
     * @return frase em binário (Huffman)
     */
    private String fraseCompactada(String frase) {
        String binario = "";
        for (int i = 0; i < frase.length(); i++) {
            binario += getArvore().mapear("" + frase.charAt(i));
        }
        return binario;
    }
    /**
     * Método de encriptação do arquivo
     * @param Origem Arquivo com o conteúdo a ser descompactado
     * @param Destino Arquivo com onde o conteúdo descompactado será despejado
     * @throws IOException arquivo não encontrado
     */
    private void encriptarArquivo(File Origem, File Destino) throws IOException {
        gerarArquivo(Destino, lerArquivo(Origem));
    }
    /**
     * Lê o conteúdo do arquivo
     * @param Arquivo Aquivo a ser lido
     * @return String com o conteúdo lido
     * @throws IOException Arquivo não encontrado
     */
    private String lerArquivo(File arquivo) throws IOException {
        String txt = "";
		BufferedReader texto = new BufferedReader(new FileReader(arquivo));
		String str = texto.readLine();
		while (str != null) {
			if (!str.isEmpty()) {
				txt += str + "\n";
			}
			str = texto.readLine();
		}
		texto.close();
		return txt;
    }
    /**
     * Cria uma árvore binária com base no algoritmo de Huffamn, com base nos 
     * pesos dos caracteres
     * @param lista lista estática com caracteres
     * @param contaChar vetor com os pesos dos caracteres
     * @return árvore binária formada com pase nos caracteres e seus pesos
     */
    private NoArvoreBinaria criarArvore(ListaEstatica lista, int[] contaChar) {
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
        return nos.obterElemento(0);

    }
}
