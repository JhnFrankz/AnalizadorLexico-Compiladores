package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

//______________________1______________________
public class Home extends JFrame {

    private final JPanel contentPane;
    private final JLabel lblInpunt;
    private final JScrollPane scrollPane1;
    private final JTextPane txtInput;
    private final JButton btnAnalise;
    private final JButton btnClear;
    private final JScrollPane scrollPane2;
    private final JTable tblResult;

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
        //_____________________2___________________________

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
        btnAnalise.setForeground(Color.BLACK);
        btnAnalise.setBackground(Color.WHITE);
        btnAnalise.addActionListener(this::btnAnaliseActionPerformed);
        contentPane.add(btnAnalise);

        btnClear = new JButton("Limpiar");
        btnClear.setBounds(109, 190, 89, 23);
        btnClear.setForeground(Color.BLACK);
        btnClear.setBackground(Color.WHITE);
        btnClear.addActionListener(this::btnClearActionPerformed);
        contentPane.add(btnClear);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(10, 220, 414, 151);
        contentPane.add(scrollPane2);

        tblResult = new JTable();
        tblResult.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String[]{
                        "Componente Léxico", "Lexema"
                }
        ));
        tblResult.setEnabled(false);
        scrollPane2.setViewportView(tblResult);
    }

    //_______________________________3________________________________
    private void btnAnaliseActionPerformed(ActionEvent evt) {
        analize();
    }

    private void btnClearActionPerformed(ActionEvent evt) {
        txtInput.setText("");
        tblResult.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String[]{
                        "Componente Léxico", "Lexema"
                }
        ));
    }

    private void analize() {
        String input = txtInput.getText();

        ArrayList<String> palabrasReservadas = new ArrayList<>(Arrays.asList("abstract", "boolean",
                "break", "byte", "case", "catch", "char",
                "class", "continue", "default", "do", "double", "else", "enum", "extends", "final",
                "finally", "float", "for", "if", "implements", "import", "instanceof", "int", "interface",
                "long", "new", "null", "package", "private", "protected", "public", "return", "short",
                "static", "super", "switch", "this", "throw", "throws", "try", "void", "while"));

        //_____________________________4__________________________________

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el código a analizar",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
                    i--;
                    if (palabrasReservadas.contains(lexema)) {
                        model.addRow(new Object[]{lexema, "Palabra Reservada"});
                    } else {
                        model.addRow(new Object[]{lexema, "Identificador"});
                    }
                    //_______________________________5________________________________
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
                } else if (c == '+') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '+') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Más mas"}); // ++
                    } else if (c == '=') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Más igual"}); //+=
                    } else {
                        model.addRow(new Object[]{lexema, "Suma"}); //+
                        i--;
                    }
                } else if (c == '-') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '-') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Menos menos"});
                    } else if (c == '=') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Menos igual"});
                    } else {
                        model.addRow(new Object[]{lexema, "Resta"});
                        i--;
                    }
                } else if (c == '*') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '*') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Potencia"});
                    } else if (c == '=') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Por igual"});
                    } else {
                        model.addRow(new Object[]{lexema, "Multiplicación"});
                        i--;
                    }
                } else if (c == '/') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '=') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Entre igual"});
                    } else {
                        model.addRow(new Object[]{lexema, "División"});
                        i--;
                    }
                } else if (c == '=') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '=') {
                        lexema += c;
                        i++;
                        if (i < input.length()) {
                            c = input.charAt(i);
                        } else {
                            break;
                        }
                        if (c == '=') {
                            lexema += c;
                            model.addRow(new Object[]{lexema, "Igualdad referencia"});
                        } else {
                            lexema += c;
                            model.addRow(new Object[]{lexema, "Igualdad"});
                            i--;
                        }
                    } else {
                        model.addRow(new Object[]{lexema, "Asignación"});
                        i--;
                    }
                } else if (c == '<') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '=') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Menor o Igual"});
                    } else {
                        model.addRow(new Object[]{lexema, "Menor que"});
                        i--;
                    }
                } else if (c == '>') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '=') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Mayor o Igual"});
                    } else {
                        model.addRow(new Object[]{lexema, "Mayor que"});
                        i--;
                    }
                } else if (c == '!') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '=') {
                        lexema += c;
                        i++;
                        if (i < input.length()) {
                            c = input.charAt(i);
                        } else {
                            break;
                        }
                        if (c == '=') {
                            lexema += c;
                            model.addRow(new Object[]{lexema, "Desigualdad referencia"});
                        } else {
                            model.addRow(new Object[]{lexema, "Diferente de"});
                            i--;
                        }
                    } else {
                        model.addRow(new Object[]{lexema, "Negación"});
                        i--;
                    }
                } else if (c == '&') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '&') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Operador Y"});
                    } else {
                        model.addRow(new Object[]{lexema, "Operador Y Binario"});
                        i--;
                    }
                } else if (c == '|') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '|') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Operador O"});
                    } else {
                        model.addRow(new Object[]{lexema, "Operador O Binario"});
                        i--;
                    }
                } else if (c == '^') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '=') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "XOR igual"});
                    } else {
                        model.addRow(new Object[]{lexema, "Operador XOR"});
                        i--;
                    }
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
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '"') {
                        lexema += c;
                        i++;
                        if (i < input.length()) {
                            c = input.charAt(i);
                        } else {
                            break;
                        }
                        if (c == '"') {
                            lexema += c;
                            model.addRow(new Object[]{lexema, "Comillas multiples"});
                        } else {
                            i--;
                        }
                    } else {
                        model.addRow(new Object[]{lexema, "Comillas dobles"});
                        i--;
                    }
                } else if (c == '\'') {
                    model.addRow(new Object[]{"'", "Comillas simples"});
                } else if (c == '´') {
                    model.addRow(new Object[]{"'", "Comillas invertidas"});
                } else if (c == ',') {
                    model.addRow(new Object[]{",", "Coma"});
                } else if (c == ':') {
                    model.addRow(new Object[]{":", "Dos puntos"});
                } else if (c == '.') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == '.') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Rango"});
                    } else {
                        model.addRow(new Object[]{lexema, "Punto"});
                        i--;
                    }
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
                } else if (c == '¿') {
                    model.addRow(new Object[]{"¿", "Abre interrogación"});
                } else if (c == '?') {
                    String lexema = "";
                    lexema += c;
                    i++;
                    if (i < input.length()) {
                        c = input.charAt(i);
                    } else {
                        break;
                    }
                    if (c == ':') {
                        lexema += c;
                        model.addRow(new Object[]{lexema, "Operador ternario"});
                    } else {
                        model.addRow(new Object[]{lexema, "Cerrar interrogación"});
                        i--;
                    }
                }
            }
        }
    }
}
