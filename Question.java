import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
    private final String question;
    private final String correctAnswer;
    private final List<String> incorrectAnswers;

    public Question(String question, String correctAnswer, JSONArray incorrectAnswers) {
        this.question = unescapeHtml(question);
        this.correctAnswer = unescapeHtml(correctAnswer);
        this.incorrectAnswers = jsonArrayToStringList(incorrectAnswers);
    }

    public List<String> getAllOptions() {
        List<String> options = new ArrayList<>(incorrectAnswers);
        options.add(correctAnswer);
        Collections.shuffle(options);
        return options;
    }

    private String unescapeHtml(String text) {
        return text.replace("&quot;", "\"")
                   .replace("&#039;", "'")
                   .replace("&amp;", "&");
    }

    private List<String> jsonArrayToStringList(JSONArray array) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(unescapeHtml(array.getString(i)));
        }
        return list;
    }

    public String getQuestion() { return question; }
    public String getCorrectAnswer() { return correctAnswer; }
}