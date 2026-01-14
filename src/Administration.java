import java.util.List;
import java.util.Map;

public class Administration extends User{

    public Administration(int user_id, String name, String email, String contact_number, String password, Map<String, String> security_answers) {
        super(user_id, name, email, contact_number, password, security_answers);
    }

    public void updateSecurityQuestions(List<String> newQuestions) {
        SecurityPolicy.updateSecurityQuestions(newQuestions);
    }


}

