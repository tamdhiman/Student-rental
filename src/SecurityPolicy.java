import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
public class SecurityPolicy {

    private static List<String> securityQuestions = new ArrayList<>(
            List.of(
                    "What is your favorite ice cream flavour?",
                    "What is your least favorite TV show?",
                    "What was the name of your first pet?",
                    "What is the name of the person who taught you how to ride a bicycle?"
            )
    );

    public static List<String> getSecurityQuestions() {
        return Collections.unmodifiableList(securityQuestions);
    }

    // Only Admin should call this
    protected static void updateSecurityQuestions(List<String> newQuestions) {
        securityQuestions = new ArrayList<>(newQuestions);
    }
}
