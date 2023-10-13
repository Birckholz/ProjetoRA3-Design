package src;

public class UsuarioFactory implements UserFactory{

    @Override
    public Usuario createUser(String name, String email, String senha, String username) {
        return new Usuario(email, senha, name,username);
    }
}
