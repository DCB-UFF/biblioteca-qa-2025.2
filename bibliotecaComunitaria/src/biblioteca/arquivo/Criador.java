package biblioteca.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* @author Luam  */
public class Criador {
    public static void criarPastaUnidade(String num) {
        File file = new File("src\\unidades\\un"+num); file.mkdir();
        File aut = new File("src\\unidades\\un"+num+"\\autores.csv");
        File cli = new File("src\\unidades\\un"+num+"\\clientes.csv");
        File emp = new File("src\\unidades\\un"+num+"\\emprestimos.csv");
        File est = new File("src\\unidades\\un"+num+"\\estantes.csv");
        File fun = new File("src\\unidades\\un"+num+"\\funcionarios.csv");
        File liv= new File("src\\unidades\\un"+num+"\\livros.csv");
        
        try {
            aut.createNewFile();
            cli.createNewFile();
            emp.createNewFile();
            est.createNewFile();
            fun.createNewFile();
            liv.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Criador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static void copiarArquivo(File a, File b){ 
        { 
            /* If file doesnot exist FileInputStream throws 
               FileNotFoundException and read() write() throws 
               IOException if I/O error occurs */
            FileInputStream fis; 
            FileOutputStream fos; 
            try {
                fis = new FileInputStream(a);
                fos = new FileOutputStream(b);
                int c;
                try {
                    while  ((c=fis.read()) != -1) 
                        try { 
                            fos.write(c);
                            fis.close();
                            fos.close(); 
                        } catch (IOException ex) {
                            Logger.getLogger(Criador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                } catch (IOException ex) {
                    Logger.getLogger(Criador.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Criador.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        } 
    }
}
