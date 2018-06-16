/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author Leonardo Fronza
 */
public class Testes {
    public static void main(String[] args) throws IOException {
        Encriptador e = new Encriptador();
        JFileChooser jfIn = new JFileChooser();
        jfIn.showOpenDialog(null);
        JFileChooser jfOut = new JFileChooser();
        jfOut.showOpenDialog(null);
        e.encriptarArquivo(jfIn.getSelectedFile(), jfOut.getSelectedFile());
        
    }
}
