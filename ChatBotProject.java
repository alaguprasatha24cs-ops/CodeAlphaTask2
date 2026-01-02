import java.awt.*;
import java.io.*;
import java.util.HashMap;
import javax.swing.*;

public class ChatBotProject {

    static class ChatLogic {

        HashMap<String, String> data = new HashMap<>();

        ChatLogic() {
            loadFile();
        }

        void loadFile() {
            File f = new File("chat_data.txt");

            if (!f.exists()) {
                try {
                    FileWriter fw = new FileWriter(f);
                    fw.write("hi=Hello!\n");
                    fw.write("hello=Hi, how can I help?\n");
                    fw.write("your name=I am a simple Java chatbot.\n");
                    fw.write("what is java=Java is an object oriented language.\n");
                    fw.write("what is ai=AI means artificial intelligence.\n");
                    fw.write("bye=Bye, see you again.\n");
                    fw.close();
                } catch (Exception e) {
                    System.out.println("File error");
                }
            }

            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;

                while ((line = br.readLine()) != null) {
                    String arr[] = line.split("=", 2);
                    if (arr.length == 2) {
                        data.put(arr[0], arr[1]);
                    }
                }
                br.close();
            } catch (Exception e) {
                System.out.println("Reading error");
            }
        }

        String getReply(String msg) {
            msg = msg.toLowerCase().replaceAll("[^a-z ]", "");

            for (String key : data.keySet()) {
                if (msg.contains(key)) {
                    return data.get(key);
                }
            }
            return "Sorry, I don't understand.";
        }
    }

    static class ChatScreen extends JFrame {

        JTextArea area;
        JTextField box;
        JButton btn;
        ChatLogic logic = new ChatLogic();

        ChatScreen() {
            setTitle("Chatbot");
            setSize(500, 500);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            area = new JTextArea();
            area.setEditable(false);

            JScrollPane sp = new JScrollPane(area);

            box = new JTextField();
            btn = new JButton("Send");

            JPanel p = new JPanel(new BorderLayout());
            p.add(box, BorderLayout.CENTER);
            p.add(btn, BorderLayout.EAST);

            add(sp, BorderLayout.CENTER);
            add(p, BorderLayout.SOUTH);

            area.append("Bot: Hi! Start chatting...\n\n");

            btn.addActionListener(e -> send());
            box.addActionListener(e -> send());

            setVisible(true);
        }

        void send() {
            String text = box.getText();
            if (text.length() == 0)
                return;

            area.append("You: " + text + "\n");
            area.append("Bot: " + logic.getReply(text) + "\n\n");
            box.setText("");
        }
    }

    public static void main(String[] args) {
        new ChatScreen();
    }
}