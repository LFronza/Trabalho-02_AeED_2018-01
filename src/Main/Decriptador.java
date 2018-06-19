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
 * @author Leonardo
 */
public class Decriptador {

    private ArvoreBinaria arvore;
    /**
     * Construtor da classe
     * @param Origem Arquivo de origem
     * @param Destino Arquivo de destino
     * @throws IOException Arquivo não encontrado
     */
    public Decriptador(File Origem, File Destino) throws IOException {
        setArvore(new ArvoreBinaria());
        decriptarArquivo(Origem, Destino);
    }

    /**
     * Método de decriptação do arquivo
     *
     * @param Origem Arquivo com o conteúdo a ser descompactado
     * @param Destino Arquivo com onde o conteúdo descompactado será despejado
     * @throws IOException arquivo não encontrado
     */
    private void decriptarArquivo(File Origem, File Destino) throws IOException {
        gerarArquivo(Destino, lerArquivo(Origem));
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
        String[] codigo = decriptar(msg).split("\n");
        for (int i = 0; i < codigo.length; i++) {
            saida.write(codigo[i]);
            saida.newLine();
        }
        saida.flush();
        saida.close();
    }

    /**
     * Lê o conteúdo do arquivo e o transforma num texto legível
     *
     * @param msg Texto a ser decriptado
     * @return Texto legível
     */
    private String decriptar(String msg) {
        ListaEstatica<String> letras = new ListaEstatica<>();
        ListaEstatica<String> caminho = new ListaEstatica<>();
        String[] msgVet = msg.split("\n");
        int linhas = Integer.parseInt(msgVet[0]);
        for (int i = 1; i < linhas + 1; i++) {
            letras.inserir(ASCIItoString(msgVet[i]));
            caminho.inserir(getCaminho(msgVet[i]));
        }
        getArvore().setRaiz(new NoArvoreBinaria());
        for (int i = 0; i < letras.getTamanho(); i++) {
            fazerArvore(letras.obterElemento(i), caminho.obterElemento(i));
        }
        return lerCodigo(msgVet[msgVet.length - 1]);
    }

    /**
     * Lê o conteúdo do arquivo
     * @param Arquivo Aquivo a ser lido
     * @return String com o conteúdo lido
     * @throws IOException Arquivo não encontrado
     */
    private String lerArquivo(File arquivo) throws IOException {
        String txt = "";
        BufferedReader comandos = new BufferedReader(new FileReader(arquivo));
        String str = comandos.readLine();
        while (str != null) {
            if (!str.isEmpty()) {
                txt += str + "\n";
            }
            str = comandos.readLine();
        }
        comandos.close();
        return txt;
    }

    /**
     * Lê os números antes do '=' e retorna o caractere com de respectivo valor
     * da tabela ASCII
     *
     * @param linha a linha a ser lida
     * @return o caractere com de respectivo valor da tabela ASCII
     */
    private String ASCIItoString(String linha) {
        char[] letras = linha.toCharArray();
        String aux = "";
        for (int i = 0; i < letras.length; i++) {
            if (letras[i] != '=') {
                aux += letras[i];
            } else {
                break;
            }
        }
        char letra = (char) Integer.parseInt(aux);
        return letra + "";
    }

    /**
     * Lê os números antes do '=' e retorna o caminho da árvore binária
     *
     * @param linha a linha a ser lida
     * @return a string do caminho da árvore binária (0 = esquerda e 1 =
     * direita)
     */
    private String getCaminho(String linha) {
        char[] letras = linha.toCharArray();
        String saida = "";
        boolean ativado = false;
        for (int i = 0; i < letras.length; i++) {
            if (letras[i] == '=' && !ativado) {
                ativado = true;
            }
            if (ativado && (letras[i] != '\n' && letras[i] != '=')) {
                saida += letras[i];
            }
        }
        return saida;
    }

    /**
     * Recria a árvore binária equivalente aquela criada na compactação
     *
     * @param no raiz da árvore
     * @param letra caractere a ser deixado na folha
     * @param caminho caminho a ser criado até a folha
     */
    private void fazerArvore(String letra, String caminho) {
        NoArvoreBinaria no = getArvore().getRaiz();
        for (int i = 0; i < caminho.length(); i++) {
            switch (caminho.charAt(i)) {
                case '1':
                    if (no.getDireita() != null) {
                        no = no.getDireita();
                    } else {
                        if (i == caminho.length() - 1) {
                            // se for o último número ele será uma folha com o valor da letra
                            no.setDireita(new NoArvoreBinaria(letra));
                        } else {
                            /*os nós que não são folhas não precisam conter nenhum valor
                        tudo o que se precisa saber dele são os seus filhos.*/
                            no.setDireita(new NoArvoreBinaria());
                            no = no.getDireita();
                        }
                    }
                    break;
                case '0':
                    if (no.getEsquerda() != null) {
                        no = no.getEsquerda();
                    } else {
                        if (i == caminho.length() - 1) {
                            no.setEsquerda(new NoArvoreBinaria(letra));
                        } else {
                            no.setEsquerda(new NoArvoreBinaria());
                            no = no.getEsquerda();
                        }
                    }
                    break;
                default:
                    throw new RuntimeException("caminho inválido");
            }
        }
    }

    /**
     * Percorre a árvore binária gerada anteriormente com base na última linha
     * do arquivo compactado que possui o caminho
     *
     * @param codigo a linha que possui o texto compactado
     * @return o texto legível
     */
    private String lerCodigo(String codigo) {
        String saida = "";
        NoArvoreBinaria no = getArvore().getRaiz();
        for (int i = 0; i < codigo.length(); i++) {
            switch (codigo.charAt(i)) {
                case '1':
                    no = no.getDireita();
                    if (isOnFolha(no)) {
                        saida += no.getInfo();
                        no = getArvore().getRaiz();
                    }
                    break;
                case '0':
                    no = no.getEsquerda();
                    if (isOnFolha(no)) {
                        saida += no.getInfo();
                        no = getArvore().getRaiz();
                    }
                    break;
                default:
                    break;
            }
        }
        return saida;
    }

    /**
     * Verifica se os nós não possui filhos
     *
     * @param no o nó a ser verificado
     * @return true se o nó não tiver filhos
     */
    private boolean isOnFolha(NoArvoreBinaria no) {
        return no.getDireita() == null && no.getEsquerda() == null;
    }

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
}
