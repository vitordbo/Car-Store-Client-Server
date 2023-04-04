import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import imple.CarroImpl;
import imple.ClienteImpl;
import imple.FuncionarioImpl;
import imple.LojaImpl;
import interfaces.Carro;
import interfaces.Cliente;
import interfaces.Funcionario;
import interfaces.Loja;

public class Servidor3 {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        // Criar objetos das implementações das interfaces
        Loja loja = new LojaImpl();
        Carro carro = new CarroImpl();
        Cliente cliente = new ClienteImpl();
        Funcionario funcionario = new FuncionarioImpl();

        Registry registro;
        try {
            registro = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
            registro.list();
        } catch (RemoteException e) {
            registro = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }

        registro.bind("Loja", loja);
        registro.bind("Carro", carro);
        registro.bind("Cliente", cliente);
        registro.bind("Funcionario", funcionario);

        System.out.println("Servidor iniciado.");
    }
}
