import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Categories extends JFrame {
    Map<Integer, String> categories = new HashMap<>();
    Categories() {
        this.setTitle("Category");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(0, 1));


        categories.put(0, "Any Category");
        categories.put(9, "General Knowledge");
        categories.put(10, "Entertainment: Books");
        categories.put(11, "Entertainment: Film");
        categories.put(12, "Entertainment: Music");
        categories.put(13, "Entertainment: Musicals & Theatres");
        categories.put(14, "Entertainment: Television");
        categories.put(15, "Entertainment: Video Games");
        categories.put(16, "Entertainment: Board Games");
        categories.put(17, "Science & Nature");
        categories.put(18, "Science: Computers");
        categories.put(19, "Science: Mathematics");
        categories.put(20, "Mythology");
        categories.put(21, "Sports");
        categories.put(22, "Geography");
        categories.put(23, "History");
        categories.put(24, "Politics");
        categories.put(25, "Art");
        categories.put(26, "Celebrities");
        categories.put(27, "Animals");
        categories.put(28, "Vehicles");
        categories.put(29, "Entertainment: Comics");
        categories.put(30, "Science: Gadgets");
        categories.put(31, "Entertainment: Japanese Anime & Manga");
        categories.put(32, "Entertainment: Cartoon & Animations");

        // Add labels for each category
        for (Map.Entry<Integer, String> entry : categories.entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();
            JLabel label = new JLabel(id + " - " + name);
            this.add(label);
        }

        this.setVisible(true);
    }
    public String name(int id){
        return categories.get(id);
    }



}
