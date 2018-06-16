/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista;

/**
 *
 * @author Leonardo Fronza
 */
public class ListaEstatica<T> {

    private T[] info;
    private int tamanho;

    public ListaEstatica() {
        info = (T[]) new Object[0];
        tamanho = 0;
    }

    public void redimensionar() {
        T[] aux = (T[]) new Object[tamanho + 1];
        for (int i = 0; i < info.length; i++) {
            aux[i] = info[i];
        }
        info = aux;
    }

    public void inserir(T valor) {
        if (tamanho == info.length) {
            redimensionar();
        }
        info[tamanho] = valor;
        tamanho++;
    }

    public void exibir() {
        for (int i = 0; i < info.length; i++) {
            System.out.println(info[i]);
        }
    }

    public int buscar(T valor) {
        for (int i = 0; i < info.length; i++) {
            if (info[i] == valor) {
                return i;
            }
        }
        return -1;
    }

    public void retirar(T valor) {
        if (buscar(valor) != -1) {
            for (int i = buscar(valor) + 1; i < info.length; i++) {
                info[i - 1] = info[i];
            }
            tamanho--;
        }

    }

    public void liberar() {
        info = (T[]) new Object[10];
        tamanho = 0;
    }

    public T obterElemento(int posicao) {
        return info[posicao];
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int getTamanho() {
        return tamanho;
    }

    @Override
    public String toString() {
        String aux = "";
        for (int i = 0; i < info.length; i++) {
            aux += info[i] + " ";
        }
        return aux;
    }

    public boolean contem(T valor) {
        for (int i = 0; i < info.length; i++) {
            if (info[i] == valor) {
                return true;
            }
        }
        return false;
    }

    public void reposicionar(int index, T valor) {
        info[index] = valor;
    }
}
