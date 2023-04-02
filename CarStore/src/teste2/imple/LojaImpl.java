package teste2.imple;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teste2.interfaces.Carro;
import teste2.interfaces.Loja;

public class LojaImpl extends UnicastRemoteObject implements Loja {

    private Map<String, CarroImpl> carros;
    private List<ClienteImpl> clientes;
    private List<FuncionarioImpl> funcionarios;

    public LojaImpl() throws RemoteException {
        carros = new HashMap<String, CarroImpl>();
        clientes = new ArrayList<ClienteImpl>();
        funcionarios = new ArrayList<FuncionarioImpl>();

        // Adiciona 3 carros de cada tipo no início da execução
        carros.put("Fiat Novo Uno", new CarroImpl("Fiat Novo Uno", "988911891", "branco", "Econômico", 2020, 35000.0, false , 2));
        carros.put("Chevrolet Onix", new CarroImpl("Chevrolet Onix", "987654321","azul" , "Econômico", 2019, 40000.0, false, 3));
        carros.put("Ford Ka", new CarroImpl("Ford Ka", "567891234", "preto", "Econômico", 2010, 22000.0, false, 1));

        carros.put("Ford Ka Sedan", new CarroImpl("Ford Ka Sedan", "432156789", "branco", "Intermediário", 2020, 45000.0, false, 5));
        carros.put("Chevrolet Onix Plus", new CarroImpl("Chevrolet Onix Plus", "789123456", "azul", "Intermediário", 2019, 50000.0, false, 1));
        carros.put("Hyundai HB20S", new CarroImpl("Hyundai HB20S", "345678912", "vermelho", "Intermediário", 2021, 68000.0, false, 2));

        carros.put("Toyota Corolla", new CarroImpl( "Toyota Corolla", "456789123", "Cinza", "Executivo", 2023, 150000.0, false, 3));
        carros.put("Honda Civic", new CarroImpl("Honda Civic", "321654987", "preto", "Executivo", 2021, 185000.0, false, 2));
        carros.put("Chevrolet Cruze", new CarroImpl("Chevrolet Cruze", "987123654", "verde", "Executivo", 2019, 160000.0, false, 1));
    }


    @Override
    public ArrayList<CarroImpl> listarCarros() throws RemoteException {
        ArrayList<CarroImpl> carros = new ArrayList<CarroImpl>(this.carros.values());
        Collections.sort(carros, new Comparator<CarroImpl>() {
            @Override
            public int compare(CarroImpl c1, CarroImpl c2) {
                try {
                    return c1.getNome().compareTo(c2.getNome());
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return carros;
    }

    @Override
    public CarroImpl pesquisarCarro(String chave) throws RemoteException { // nome
        for (CarroImpl carro : this.carros.values()) {
            if (carro.getNome().equalsIgnoreCase(chave)) {
                return carro;
            }
        }
        return null;
    }
    
    @Override
    public int exibirQuantidadeCarros() throws RemoteException {
        return carros.size();
    }

    @Override
    public boolean comprarCarro(String nome, int quantidade) throws RemoteException {
        if (quantidade == 0) {
            return false; // não é possível comprar carro inexistente ou não presente na loja
        }
        carros.remove(nome);
        return true;
    }

}
