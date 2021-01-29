package biblioteca.biblioteca;
import biblioteca.livros.Acervo;
import biblioteca.pessoas.Cliente;
import biblioteca.pessoas.Funcionario;
import java.io.File;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;

/* @author victoria */

public class Unidade{
    private String path;
    private String nome;
    private Endereco end;
    private Acervo acervo;
    private ArrayList <Cliente> clientes = new ArrayList<>();
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    
    public Unidade(String path, String nome, String rua, String bairro, String cep, String cidade, String estado){
        this.path = "src\\unidades\\un"+ path +"\\";
        this.nome = nome;
        this.end = new Endereco(rua, bairro, cep, cidade, estado);
    }
    
    @Override
     public String toString() {
         return ("Unidade "+ this.nome + "\nRua: " + end.getRua() + " - " + "Bairro: " + end.getBairro() +
                 " - " + "CEP: " + end.getCep() + " - " + "Cidade: " + end.getCidade() + " - " + "Estado: " + end.getEstado());
     }

    public Endereco getEnd() {
        return end;
    }

    public void setEnd(Endereco end) {
        this.end = end;
    }
     
     
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
     
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public Acervo getAcervo() {
        return acervo;
    }
    public void setAcervo(Acervo acervo) {
        this.acervo = acervo;
    }
    
    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
    
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
            Logger.getLogger(Unidade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
                            Logger.getLogger(Unidade.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    fis.close();
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Unidade.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Unidade.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            }
        }
    }

    public static void escreverUnidade(Sistema sistema,Unidade unidade) {
        BufferedWriter bw = null;

        String linha = String.valueOf(sistema.getContadorUnidades())+ "," +unidade.getNome()  + "," +unidade.getEnd().getRua()
                + ","  + unidade.getEnd().getBairro()+ "," + unidade.getEnd().getCep()+ "," + unidade.getEnd().getCidade() 
               + "," + unidade.getEnd().getEstado();

        try {
            bw = new BufferedWriter(new FileWriter("src\\unidades\\unidades.csv", true));
            PrintWriter pw= new PrintWriter(bw);
            pw.println(linha);

            unidade.setPath(String.valueOf(sistema.getUnidades()));

            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }   
        }
    }

    public static Sistema leitorUnidades() {
        BufferedReader br = null;
        String linha = "";
        Sistema sistema = new Sistema();
        
        try {
            br = new BufferedReader(new FileReader("src\\unidades\\unidades.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                String[] unidade = linha.split(",");
                Unidade novo = new Unidade(unidade[0],unidade[1],unidade[2], unidade[3],
                        unidade[4], unidade[5], unidade[6]);
                sistema.getUnidades().add(novo);
                sistema.addContadorUnidades();
                
            }
            return sistema;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }   
        }
        return null;
    }

}