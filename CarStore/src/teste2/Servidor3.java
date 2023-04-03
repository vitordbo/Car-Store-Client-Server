package teste2;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import teste2.imple.CarroImpl;
import teste2.imple.ClienteImpl;
import teste2.imple.FuncionarioImpl;
import teste2.imple.LojaImpl;
import teste2.interfaces.Carro;
import teste2.interfaces.Cliente;
import teste2.interfaces.Funcionario;
import teste2.interfaces.Loja;

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
