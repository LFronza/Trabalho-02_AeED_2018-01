/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArvoreB;

import com.sun.jmx.snmp.SnmpDefinitions;

/**
 *
 * @author Leonardo Fronza
 */
public class ArvoreBinaria {

    private NoArvoreBinaria raiz;

    public ArvoreBinaria() {
    }

    public boolean estaVazia() {
        return raiz == null;
    }

    public boolean pertence(String info) {
        return pertence(raiz, info);
    }

    private boolean pertence(NoArvoreBinaria no, String info) {
        if (no == null) {
            return false;
        } else {
            return no.getInfo().equals(info)
                    || pertence(no.getDireita(), info)
                    || pertence(no.getEsquerda(), info);
        }
    }

    public void setRaiz(NoArvoreBinaria raiz) {
        this.raiz = raiz;
    }

    @Override
    public String toString() {
        return arvorePre(raiz);
    }

    private String arvorePre(NoArvoreBinaria no) {
        return no == null
                ? "<>"
                : "<" + no.getInfo() + arvorePre(no.getDireita()) + arvorePre(no.getEsquerda()) + ">";
    }

    public int contarNos() {
        return contarNos(raiz);
    }

    private int contarNos(NoArvoreBinaria no) {
        return no == null ? 0 : 1 + contarNos(no.getDireita()) + contarNos(no.getEsquerda());
    }

    private boolean isOnFolha(NoArvoreBinaria no) {
        return no.getDireita() == null && no.getEsquerda() == null;
    }

    public String mapear(String info) {
        return mapear(raiz, info, "");
    }

    private String mapear(NoArvoreBinaria no, String info, String cod) {
        if (no.getInfo().equals(info)) {
            return cod;
        } else if (no.getEsquerda() != null && pertence(no.getEsquerda(), info)) {
            return mapear(no.getEsquerda(), info, cod + "0");
        } else if (no.getDireita() != null && pertence(no.getDireita(), info)) {
            return mapear(no.getDireita(), info, cod + "1");
        } else{
            return "Erro no mapear";
        }
        
    }

    public NoArvoreBinaria getRaiz() {
        return raiz;
    }

}
