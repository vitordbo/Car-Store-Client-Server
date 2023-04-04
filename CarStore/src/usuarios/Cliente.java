package usuarios;

// acho que da pra tirar o implemntes e separar no switch
public class Cliente extends User  {

    // contrutor para criar na loja
    public Cliente(String login, String senha){
        this.login = login;
        this.senha = senha;
    }

}
