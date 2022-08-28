package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

    private JPanel contentPane;
    private JLabel lblInpunt;
    private JScrollPane scrollPane1;
    private JTextPane txtInput;
    private JButton btnAnalise;
    private JButton btnClear;
    private JScrollPane scrollPane2;
    private JTable tblResult;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Home frame = new Home();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Home() {
        setTitle("Analizador Léxico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 420);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        lblInpunt = new JLabel("Ingrese el código a analizar:");
        lblInpunt.setBounds(10, 11, 200, 14);
        contentPane.add(lblInpunt);

        scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(10, 36, 414, 146);
        contentPane.add(scrollPane1);

        txtInput = new JTextPane();
        scrollPane1.setViewportView(txtInput);

        btnAnalise = new JButton("Analizar");
        btnAnalise.setBounds(10, 190, 89, 23);
        btnAnalise.addActionListener(this::btnAnaliseActionPerformed);
        contentPane.add(btnAnalise);

        btnClear = new JButton("Limpiar");
        btnClear.setBounds(109, 190, 89, 23);
        btnClear.addActionListener(this::btnClearActionPerformed);
        contentPane.add(btnClear);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(10, 220, 414, 151);
        contentPane.add(scrollPane2);

        tblResult = new JTable();
        tblResult.setModel(new DefaultTableModel(
                new Object[][] {
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String[] {
                        "Componente Léxico", "Lexema"
                }
        ));
        scrollPane2.setViewportView(tblResult);
    }

    private void btnAnaliseActionPerformed(ActionEvent evt) {
        analize();
    }

    private void btnClearActionPerformed(ActionEvent evt) {
        txtInput.setText("");
        tblResult.setModel(new DefaultTableModel(
                new Object[][] {
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String[] {
                        "Componente Léxico", "Lexema"
                }
        ));
    }

    private void analize() {
        String input = txtInput.getText();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el código a analizar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            DefaultTableModel model = (DefaultTableModel) tblResult.getModel();
            model.setRowCount(0);
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (Character.isLetter(c)) {
                    String lexema = "";
                    while (Character.isLetter(c) || Character.isDigit(c)) {
                        lexema += c;
                        i++;
                        if (i < input.length()) {
                            c = input.charAt(i);
                        } else {
                            break;
                        }
                    }
                    model.addRow(new Object[]{lexema, "Identificador"});
                    i--;
                } else if (Character.isDigit(c)) {
                    String lexema = "";
                    while (Character.isDigit(c)) {
                        lexema += c;
                        i++;
                        if (i < input.length()) {
                            c = input.charAt(i);
                        } else {
                            break;
                        }
                    }
                    model.addRow(new Object[]{lexema, "Número"});
                    i--;
                } else if (c == ' ') {
                    model.addRow(new Object[]{" ", "Espacio"});
                } else if (c == '+') {
                    model.addRow(new Object[]{"+", "Suma"});
                } else if (c == '-') {
                    model.addRow(new Object[]{"-", "Resta"});
                } else if (c == '*') {
                    model.addRow(new Object[]{"*", "Multiplicación"});
                } else if (c == '/') {
                    model.addRow(new Object[]{"/", "División"});
                } else if (c == '=') {
                    model.addRow(new Object[]{"=", "Asignación"});
                } else if (c == '<') {
                    model.addRow(new Object[]{"<", "Menor que"});
                } else if (c == '>') {
                    model.addRow(new Object[]{">", "Mayor que"});
                } else if (c == '!') {
                    model.addRow(new Object[]{"!", "No igual"});
                } else if (c == '&') {
                    model.addRow(new Object[]{"&", "Y"});
                } else if (c == '|') {
                    model.addRow(new Object[]{"|", "O"});
                } else if (c == ';') {
                    model.addRow(new Object[]{";", "Punto y coma"});
                } else if (c == '(') {
                    model.addRow(new Object[]{"(", "Paréntesis abierto"});
                } else if (c == ')') {
                    model.addRow(new Object[]{")", "Paréntesis cerrado"});
                } else if (c == '{') {
                    model.addRow(new Object[]{"{", "Llave abierta"});
                } else if (c == '}') {
                    model.addRow(new Object[]{"}", "Llave cerrada"});
                } else if (c == '[') {
                    model.addRow(new Object[]{"[", "Corchete abierto"});
                } else if (c == ']') {
                    model.addRow(new Object[]{"]", "Corchete cerrado"});
                } else if (c == '"') {
                    String lexema = "";
                    i++;
                    while (c != '"') {
                        lexema += c;
                        i++;
                        if (i < input.length()) {
                            c = input.charAt(i);
                        } else {
                            break;
                        }
                    }
                    model.addRow(new Object[]{lexema, "Cadena"});
                    i--;
                } else if (c == '\'') {

                } else if (c == ',') {
                    model.addRow(new Object[]{",", "Coma"});
                } else if (c == ';') {
                    model.addRow(new Object[]{";", "Punto y coma"});
                } else if (c == ':') {
                    model.addRow(new Object[]{":", "Dos puntos"});
                } else if (c == '.') {
                    model.addRow(new Object[]{".", "Punto"});
                } else if (c == '_') {
                    String lexema = "";
                    while (c == '_') {
                        lexema += c;
                        i++;
                        if (i < input.length()) {
                            c = input.charAt(i);
                        } else {
                            break;
                        }
                    }
                    model.addRow(new Object[]{lexema, "Guion bajo"});
                    i--;
                } else if (c == '\n') {
                    model.addRow(new Object[]{"\n", "Salto de línea"});
                } else if (c == '\t') {
                    model.addRow(new Object[]{"\t", "Tabulador"});
                } else if (c == '\r') {
                    model.addRow(new Object[]{"\r", "Retorno de carro"});
                } else if (c == '\b') {
                    model.addRow(new Object[]{"\b", "Retroceso"});
                } else if (c == '\f') {
                    model.addRow(new Object[]{"\f", "Fin de página"});
                } else if (c == '\u0000') {
                    model.addRow(new Object[]{"\u0000", "Nulo"});
                } else if (c == '\u0001') {
                    model.addRow(new Object[]{"\u0001", "Inicio de caracter"});
                } else if (c == '\u0002') {
                    model.addRow(new Object[]{"\u0002", "Fin de caracter"});
                } else if (c == '\u0003') {
                    model.addRow(new Object[]{"\u0003", "Inicio de cadena"});
                } else if (c == '\u0004') {
                    model.addRow(new Object[]{"\u0004", "Fin de cadena"});
                } else if (c == '\u0005') {
                    model.addRow(new Object[]{"\u0005", "Inicio de comentario"});
                } else if (c == '\u0006') {
                    model.addRow(new Object[]{"\u0006", "Fin de comentario"});
                }
            }
        }
    }
}
