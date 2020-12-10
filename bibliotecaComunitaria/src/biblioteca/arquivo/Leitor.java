package biblioteca.arquivo;

import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;
import java.util.ArrayList;

/* @author Luam */
public class Leitor {
    
    public static ArrayList<Emprestimo> leitorEmprestimos(String path){
    BufferedReader br = null;
        String linha = "";
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(path+"emprestimos.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                String[] emprestimo = linha.split(",");
                Emprestimo novo = new Emprestimo(emprestimo[0],emprestimo[1],emprestimo[2],emprestimo[3]);
                emprestimos.add(novo);
            }
            return emprestimos;
            
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
    
     public static ArrayList<Autor> leitorAutores(String path) {
        BufferedReader br = null;
        String linha = "";
        ArrayList<Autor> Autores = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(path+"autores.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                String[] autor = linha.split(",");
                Autor novo = new Autor();
                novo.cadastro(autor[0],autor[1]);
                Autores.add(novo);
                
            }
            return Autores;
            
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
     
     public static Acervo leitorEstantes(String path) {
        BufferedReader br = null;
        String linha = "";
        Acervo acervo = new Acervo();
        try {
            br = new BufferedReader(new FileReader(path+"estantes.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                String[] estante = linha.split(",");
                Estante nova = new Estante(Integer.parseInt(estante[0]),estante[1]);
                acervo.addEstante(nova);
            }
            return acervo;
            
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
     
     
     public static void leitorLivros(Acervo acervo, String path) {
        BufferedReader br = null;
        String linha = "";
        
        try {
            br = new BufferedReader(new FileReader(path+"livros.csv"));
            br.readLine();
            
            while ((linha = br.readLine()) != null) {
                
                String[] livro = linha.split(",");
                Autor autor = new Autor(livro[0],livro[7]);
                Livro novo = new Livro(autor,livro[1], 
                    livro[2], Integer.parseInt(livro[3]), livro[4],livro[5],Boolean.parseBoolean(livro[6]));
                acervo.addLivro(novo);
            
            }
                
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
      }
     
     /* @author victoria */
    public static ArrayList<Cliente> leitorClientes(String path) {
        BufferedReader br = null;
        String linha = "";
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(path+"clientes.csv"));
            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] cliente = linha.split(",");
                Cliente novo = new Cliente(cliente[0], cliente[1], cliente[2], cliente[3], cliente[4], cliente[5], cliente[6], cliente[7], cliente[8]);
                clientes.add(novo);
            }
            return clientes;

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
    
    public static ArrayList<Funcionario> leitorFuncionarios(String path) {
        BufferedReader br = null;
        String linha = "";
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(path+"funcionarios.csv"));
            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] funcionario = linha.split(",");
                Funcionario novo = new Funcionario(funcionario[0], funcionario[1], funcionario[2],
                        funcionario[3], Float.parseFloat(funcionario[4]), funcionario[5],
                        funcionario[6], funcionario[7], funcionario[8], funcionario[9], funcionario[10]);
                funcionarios.add(novo);
            }
            return funcionarios;

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
