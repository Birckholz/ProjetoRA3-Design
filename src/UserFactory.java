package src;

public interface UserFactory {
    UserBase createUser(String name, String email, String senha, String username);
}
