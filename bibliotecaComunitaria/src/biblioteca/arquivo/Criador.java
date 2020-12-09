package biblioteca.arquivo;

import java.io.File;

/* @author Luam  */
public class Criador {
    public static void criarPastaUnidade(String num) {
        File file = new File("src\\unidades\\un"+num); file.mkdir();
    }
}
