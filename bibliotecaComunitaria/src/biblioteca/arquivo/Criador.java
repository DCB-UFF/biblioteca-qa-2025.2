package biblioteca.arquivo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* @author Luam  */

// MÉTODO QUE CRIA A PASTA DE UMA NOVA UNIDADE

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
}
