/*

 * SecurityPolicy
 * 
 * 14-01-2026
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
public class SecurityPolicy {

    /* SECURITY POLICY ATTRIBUTES */
    private static List<String> securityQuestions = new ArrayList<>(
            List.of(
                    "What is your favorite ice cream flavour?",
                    "What is your least favorite TV show?",
                    "What was the name of your first pet?",
                    "What is the name of the person who taught you how to ride a bicycle?"
            )
    );

    /* GETTER METHODS */
    public static List<String> getSecurityQuestions() {
        return Collections.unmodifiableList(securityQuestions);
    }

    /* UPDATE METHODS */
    protected static void updateSecurityQuestions(List<String> newQuestions) {
        securityQuestions = new ArrayList<>(newQuestions);
    }
}
