/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArvoreB;

/**
 *
 * @author Leonardo Fronza
 */
public class NoArvoreBinaria {
    private String info;
    private int qtd;
    private NoArvoreBinaria esquerda;
    private NoArvoreBinaria direita;

    public NoArvoreBinaria(String info, int qtd) {
        this.info = info;
        this.qtd = qtd;
    }

    public NoArvoreBinaria(String info) {
        setInfo(info);
    }

    public NoArvoreBinaria() {
    }
   
    
    
    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

   
    
     public NoArvoreBinaria(String info, NoArvoreBinaria esq, NoArvoreBinaria dir) {
        this.info = info;
        esquerda = esq;
        direita = dir;
    }
     
     public NoArvoreBinaria(String info, NoArvoreBinaria esq, NoArvoreBinaria dir, int qtd) {
        this.info = info;
        esquerda = esq;
        direita = dir;
        this.qtd = qtd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public NoArvoreBinaria getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(NoArvoreBinaria esquerda) {
        this.esquerda = esquerda;
    }

    public NoArvoreBinaria getDireita() {
        return direita;
    }

    public void setDireita(NoArvoreBinaria direita) {
        this.direita = direita;
    }
    
}
