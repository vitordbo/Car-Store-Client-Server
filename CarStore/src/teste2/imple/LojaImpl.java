package teste2.imple;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teste2.interfaces.Carro;
import teste2.interfaces.Loja;

public class LojaImpl extends UnicastRemoteObject implements Loja {

    private List<CarroImpl> carros;
    private List<ClienteImpl> clientes;
    private List<FuncionarioImpl> funcionarios;

    public LojaImpl() throws RemoteException {
        carros = new ArrayList<CarroImpl>();
        clientes = new ArrayList<ClienteImpl>();
        funcionarios = new ArrayList<FuncionarioImpl>();

        // Adiciona 3 carros de cada tipo no início da execução
        carros.add(new CarroImpl("Fiat Novo Uno", "988911891", "branco", "Econômico", 2020, 35000.0, false , 2));
        carros.add(new CarroImpl("Chevrolet Onix", "987654321","azul" , "Econômico", 2019, 40000.0, false, 3));
        carros.add(new CarroImpl("Ford Ka", "567891234", "preto", "Econômico", 2010, 22000.0, false, 1));

        carros.add(new CarroImpl("Ford Ka Sedan", "432156789", "branco", "Intermediário", 2020, 45000.0, false, 5));
        carros.add(new CarroImpl("Chevrolet Onix Plus", "789123456", "azul", "Intermediário", 2019, 50000.0, false, 1));
        carros.add(new CarroImpl("Hyundai HB20S", "345678912", "vermelho", "Intermediário", 2021, 68000.0, false, 2));

        carros.add(new CarroImpl("Toyota Corolla", "456789123", "Cinza", "Executivo", 2023, 150000.0, false, 3));
        carros.add(new CarroImpl("Honda Civic", "321654987", "preto", "Executivo", 2021, 185000.0, false, 2));
        carros.add(new CarroImpl("Chevrolet Cruze", "987123654", "verde", "Executivo", 2019, 160000.0, false, 1));
    }

  @Override
    public void listarCarros() throws RemoteException {
        System.out.println("Carros disponiveis em ordem alfabetica = \n-------------------------");
        Collections.sort(carros, (c1, c2) -> { // collection para deixar em ordem alfabetica
            try {
                return c1.getNome().compareTo(c2.getNome());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return 0;
        });
        for (Carro carro : carros) {
            System.out.println(carro.getNome() + ", " + carro.getRenavan() + ", " + carro.getPreco());
        }
        System.out.println("-------------------------");
    }



    @Override
    public CarroImpl pesquisarCarro(String chave) throws RemoteException { // nome
        for (CarroImpl carro : this.carros) {
            if (carro.getNome().equalsIgnoreCase(chave)) {
                return carro;
            }
        }
        System.out.println("-------------------------");
        return null;
    }
    
    @Override
    public int exibirQuantidadeCarros() throws RemoteException {
        System.out.println("Quantidade de carros = " + carros.size());
        System.out.println("-------------------------");
        return carros.size();
    }

    @Override
    public boolean comprarCarro(String nomeCarro) throws RemoteException {
        Carro carro = null;
        for (Carro c : carros) {
            if (c.getNome().equals(nomeCarro)) {
                carro = c;
                break;
            }
        }
        if (carro == null) {
            System.out.println("Carro não encontrado na loja");
            return false; // carro não encontrado na loja
        }
        carros.remove(carro);
        System.out.println("Carro removido: " + carro.getNome());
        System.out.println("-------------------------");
        return true;
    }
    

}
