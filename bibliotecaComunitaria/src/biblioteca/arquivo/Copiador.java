package biblioteca.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* @author Luam */

// MÉTODO QUE COPIA O CONTEUDO DE UM ARQUIVO PARA OUTRO

public class Copiador {

    public static void copiarArquivo(File a, File b) {
        {
            FileInputStream fis;
            FileOutputStream fos;
            try {
                fis = new FileInputStream(a);
                fos = new FileOutputStream(b);
                int c;
                try {
                    while ((c = fis.read()) != -1) {
                        try {
                            fos.write(c);
                        } catch (IOException ex) {
                            Logger.getLogger(Criador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    fis.close();
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Criador.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Criador.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            }
        }
    }
    
}
