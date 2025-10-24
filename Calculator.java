import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput = new StringBuilder();
    private double firstNum = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public Calculator() {
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };
        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }
        add(buttonPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.matches("[0-9]")) {
            if (startNewNumber) {
                currentInput.setLength(0);
                startNewNumber = false;
            }
            currentInput.append(cmd);
            display.setText(currentInput.toString());
        } else if (cmd.equals(".")) {
            if (startNewNumber) {
                currentInput.setLength(0);
                startNewNumber = false;
            }
            if (!currentInput.toString().contains(".")) {
                if (currentInput.length() == 0) currentInput.append("0");
                currentInput.append(".");
                display.setText(currentInput.toString());
            }
    } else if (cmd.equals("+") || cmd.equals("-") || cmd.equals("*") || cmd.equals("/")) {
            if (currentInput.length() > 0) {
                firstNum = Double.parseDouble(currentInput.toString());
                operator = cmd;
                startNewNumber = true;
            }
        } else if (cmd.equals("=")) {
            if (operator.length() > 0 && currentInput.length() > 0) {
                double secondNum = Double.parseDouble(currentInput.toString());
                double result = 0;
                switch (operator) {
                    case "+": result = firstNum + secondNum; break;
                    case "-": result = firstNum - secondNum; break;
                    case "*": result = firstNum * secondNum; break;
                    case "/": result = secondNum != 0 ? firstNum / secondNum : Double.NaN; break;
                }
                display.setText(String.valueOf(result));
                currentInput.setLength(0);
                currentInput.append(result);
                operator = "";
                startNewNumber = true;
            }
        } else if (cmd.equals("C")) {
            currentInput.setLength(0);
            display.setText("");
            operator = "";
            firstNum = 0;
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
            }
        });
    }
}
