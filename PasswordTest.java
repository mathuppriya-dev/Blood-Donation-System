import org.mindrot.jbcrypt.BCrypt;

public class PasswordTest {
    public static void main(String[] args) {
        String storedHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi";
        
        // Test common passwords
        String[] passwords = {"password123", "password", "admin", "123456", "test"};
        
        for (String password : passwords) {
            boolean matches = BCrypt.checkpw(password, storedHash);
            System.out.println("Password '" + password + "' matches: " + matches);
        }
        
        // Generate new hash for password123
        String newHash = BCrypt.hashpw("password123", BCrypt.gensalt());
        System.out.println("New hash for 'password123': " + newHash);
    }
}






