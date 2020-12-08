package biblioteca.biblioteca;
import java.util.ArrayList;

/* @author victoria */

public class Sistema{
    protected int contadorUnidades;
    public ArrayList <Unidade> unidades = new ArrayList<>();

    public int getContadorUnidades() {
        return contadorUnidades;
    }

    public void setContadorUnidades(int contadorUnidades) {
        this.contadorUnidades = contadorUnidades;
    }

    public int addContadorUnidades() {
        contadorUnidades++;
        return contadorUnidades;
    }
    
    public ArrayList<Unidade> getUnidades() {
        return unidades;
    }

    public void setUnidades(ArrayList<Unidade> unidades) {
        this.unidades = unidades;
    }
    
    public void add(Unidade u){
        unidades.add(u);
    }

}


