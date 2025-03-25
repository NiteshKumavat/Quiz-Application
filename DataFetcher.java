import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataFetcher {
    public static List<Question> fetchQuestions(int id) {
        List<Question> questions = new ArrayList<>();
        try {
            URL url = new URL("https://opentdb.com/api.php?amount=10&category="+id+"&difficulty=medium&type=multiple");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray results = json.getJSONArray("results");
            
            for (int i = 0; i < results.length(); i++) {
                JSONObject q = results.getJSONObject(i);
                questions.add(new Question(
                    q.getString("question"),
                    q.getString("correct_answer"),
                    q.getJSONArray("incorrect_answers")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}
