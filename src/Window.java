import javax.swing.*;

public class Window extends JFrame {

    public Window(){   //конструктор
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  //прекращение работы программы после нажатия крестика
        setSize(320, 345);
        setLocation(400, 400);
        add(new Field()); //добавление экземпляра класса
        setVisible(true);
    }

    public static void main(String[] args) {

        Window window1 = new Window();
    }
}
