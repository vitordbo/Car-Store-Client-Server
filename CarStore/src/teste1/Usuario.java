import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String login;
    private String senha;

    public Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }
}

