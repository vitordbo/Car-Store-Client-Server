package usuarios;

import java.io.Serializable;

public class User implements Serializable{
   public String login;
   public String senha;

   public String getLogin() {
      return login;
   }
   public void setLogin(String login) {
      this.login = login;
   }
   public String getSenha() {
      return senha;
   }
   public void setSenha(String senha) {
      this.senha = senha;
   }

}
