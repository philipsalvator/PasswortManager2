package encryption;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class mainFrame extends JFrame {

    //Test
    private static final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private JPasswordField passwordField;
    private JTextField txtBenutzername;
    private String masterBenutzername = ("user");
    private JPasswordField passwordFieldNeu;
    private boolean check = false;
    private JTextField txtBezeichnung;
    private JTextField txtPasswort;
    private JTable table;
    ArrayList<Passwort> liste;
    String header[] = new String[]{"Bezeichnung", "Passwort"};
    DefaultTableModel dtm;
    int row, col;
    private JLabel lblLnge = new JLabel("Länge: 11");

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    mainFrame frame = new mainFrame();
                    frame.setVisible(true);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public mainFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 424, 230);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(new CardLayout(0, 0));
        setTitle("Passwort Manager");
        try {
            setIconImage(ImageIO.read(ClassLoader.getSystemResource("key.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        getContentPane().add(loginPanel, "name_567684452439100");
        loginPanel.setLayout(null);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setForeground(SystemColor.textHighlight);
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblLogin.setBounds(30, 23, 185, 25);
        loginPanel.add(lblLogin);

        // Label Benutzername
        JLabel lblBenutzername = new JLabel("Benutzername");
        lblBenutzername.setForeground(Color.DARK_GRAY);
        lblBenutzername.setBounds(30, 64, 97, 16);
        loginPanel.add(lblBenutzername);

        // Label Passwort
        JLabel lblPasswort = new JLabel("Passwort");
        lblPasswort.setForeground(Color.DARK_GRAY);
        lblPasswort.setBounds(30, 99, 97, 16);
        loginPanel.add(lblPasswort);

        // Textfeld Benutzername
        txtBenutzername = new JTextField();
        txtBenutzername.setForeground(Color.DARK_GRAY);
        txtBenutzername.setBounds(139, 61, 238, 22);
        loginPanel.add(txtBenutzername);

        // Textfeld Passwort
        passwordField = new JPasswordField();
        passwordField.setForeground(Color.DARK_GRAY);
        passwordField.setBounds(139, 96, 238, 22);
        loginPanel.add(passwordField);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(null);

        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel, "name_594164815102700");
        mainPanel.setBackground(Color.white);

        // Button Anmelden
        JButton btnLogin = new JButton("Anmelden");
        btnLogin.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
        btnLogin.setBackground(SystemColor.textHighlight);
        btnLogin.setForeground(SystemColor.textHighlight);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                passwordUtils operation = new passwordUtils();
                if (masterBenutzername == "user" && operation.verifyPassword(passwordField.getText())) {
                    System.out.println("Login erfolgreich");
                    loginPanel.hide();
                    setBounds(100, 100, 800, 800);
                    setLocationRelativeTo(null);
                    mainPanel.show();
                    txtBenutzername.setText("");
                    passwordField.setText("");
                    liste = new ArrayList<>();
                    dtm = new DefaultTableModel(header, 0);
                    table.setModel(dtm);

                    try {
                        FileInputStream fis = new FileInputStream("data");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        liste = (ArrayList) ois.readObject();
                        ois.close();
                        fis.close();

                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        return;
                    } catch (ClassNotFoundException c) {
                        System.out.println("Class not found");
                        c.printStackTrace();
                        return;
                    }

                    dtm.setRowCount(0);
                    for (int i = 0; i < liste.size(); i++) {
                        Object[] objs = {liste.get(i).bezeichnung, liste.get(i).passwort};
                        dtm.addRow(objs);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Falsches Passwort\noder falscher Benutzername", "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        btnLogin.setBounds(280, 131, 97, 25);
        loginPanel.add(btnLogin);
        btnLogin.setFocusPainted(false);
        btnLogin.setContentAreaFilled(false);

        JPanel changePasswordPanel = new JPanel();
        changePasswordPanel.setBackground(Color.WHITE);
        getContentPane().add(changePasswordPanel, "name_567709706259500");
        changePasswordPanel.setLayout(null);

        // Button Passwort Ändern
        JButton btnPasswortAendern = new JButton("Passwort Ändern");
        btnPasswortAendern.setForeground(Color.GRAY);
        btnPasswortAendern.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passwordUtils operation = new passwordUtils();
                if (masterBenutzername == "user" && operation.verifyPassword(passwordField.getText())) {
                    loginPanel.hide();
                    changePasswordPanel.show();
                } else {
                    JOptionPane.showMessageDialog(null, "Falsches Passwort\noder falscher Benutzername", "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

        });

        btnPasswortAendern.setFocusPainted(false);
        btnPasswortAendern.setContentAreaFilled(false);
        btnPasswortAendern.setBounds(257, 26, 137, 25);
        btnPasswortAendern.setBorderPainted(false);
        loginPanel.add(btnPasswortAendern);

        JLabel lblChangePassword = new JLabel("Passwort Ändern");
        lblChangePassword.setForeground(SystemColor.textHighlight);
        lblChangePassword.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblChangePassword.setBounds(30, 23, 185, 25);
        changePasswordPanel.add(lblChangePassword);

        // Label Neues Passwort
        JLabel lblPasswortNeu = new JLabel("Neues Passwort");
        lblPasswortNeu.setForeground(Color.DARK_GRAY);
        lblPasswortNeu.setBounds(30, 64, 119, 16);
        changePasswordPanel.add(lblPasswortNeu);

        // Button Passwort Ändern Abbrechen
        JButton btnAbbrechen = new JButton("Abbrechen");
        btnAbbrechen.setForeground(Color.GRAY);
        btnAbbrechen.setHorizontalAlignment(SwingConstants.RIGHT);
        btnAbbrechen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePasswordPanel.hide();
                loginPanel.show();
                btnAbbrechen.setText("Abbrechen");
                txtBenutzername.setText("");
                passwordField.setText("");
            }
        });
        btnAbbrechen.setFocusPainted(false);
        btnAbbrechen.setContentAreaFilled(false);
        btnAbbrechen.setBounds(257, 26, 137, 25);
        btnAbbrechen.setBorderPainted(false);
        changePasswordPanel.add(btnAbbrechen);

        // Button Passwort Ändern Bestätigen
        JButton button = new JButton("Bestätigen");
        button.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
        button.setForeground(SystemColor.textHighlight);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (passwordFieldNeu.getPassword().length > 0) {
                    passwordUtils operation = new passwordUtils();
                    char[] passwort = passwordFieldNeu.getPassword();
                    operation.passwortAbspeichern(String.valueOf(passwort));
                    passwordFieldNeu.setText("");
                    btnAbbrechen.setText("Zurück");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Sie müssen ein Passwort eingeben\nDrücken Sie auf  -Abbrechen-  um ihr Passwort nicht zu ändern",
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds(283, 96, 97, 25);
        changePasswordPanel.add(button);

        passwordFieldNeu = new JPasswordField();
        passwordFieldNeu.setForeground(Color.DARK_GRAY);
        passwordFieldNeu.setBounds(161, 61, 219, 22);
        changePasswordPanel.add(passwordFieldNeu);
        mainPanel.setLayout(null);

        JLabel lblHome = new JLabel("Home");
        lblHome.setBounds(30, 23, 185, 25);
        lblHome.setForeground(SystemColor.textHighlight);
        lblHome.setFont(new Font("Tahoma", Font.BOLD, 19));
        mainPanel.add(lblHome);

        JButton btnAbmelden = new JButton("Abmelden");
        btnAbmelden.setBounds(673, 26, 97, 25);
        btnAbmelden.setBorderPainted(false);
        btnAbmelden.setBorder(null);
        btnAbmelden.setBackground(SystemColor.textHighlight);
        btnAbmelden.setForeground(SystemColor.text);
        btnAbmelden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mainPanel.hide();
                setBounds(100, 100, 424, 230);
                setLocationRelativeTo(null);
                loginPanel.show();
                try {
                    FileOutputStream fos = new FileOutputStream("data");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(liste);
                    oos.close();
                    fos.close();

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            }
        });
        mainPanel.add(btnAbmelden);

        JSeparator separator = new JSeparator();
        separator.setBounds(30, 70, 740, 2);
        separator.setForeground(SystemColor.textHighlight);
        separator.setBackground(SystemColor.textHighlight);
        mainPanel.add(separator);

        JLabel lblPasswortHinzufgen = new JLabel("Passwort hinzufügen");
        lblPasswortHinzufgen.setBounds(30, 89, 185, 25);
        lblPasswortHinzufgen.setForeground(Color.DARK_GRAY);
        lblPasswortHinzufgen.setFont(new Font("Tahoma", Font.BOLD, 16));
        mainPanel.add(lblPasswortHinzufgen);

        JLabel lblBezeichnung = new JLabel("Bezeichnung");
        lblBezeichnung.setBounds(30, 127, 80, 16);
        mainPanel.add(lblBezeichnung);

        txtBezeichnung = new JTextField();
        txtBezeichnung.setBounds(30, 148, 150, 25);
        mainPanel.add(txtBezeichnung);
        txtBezeichnung.setColumns(10);

        JLabel lblPasswort_1 = new JLabel("Passwort");
        lblPasswort_1.setBounds(192, 127, 56, 16);
        mainPanel.add(lblPasswort_1);

        txtPasswort = new JTextField();
        txtPasswort.setBounds(192, 148, 186, 25);
        mainPanel.add(txtPasswort);
        txtPasswort.setColumns(10);

        lblLnge.setBounds(458, 94, 80, 16);
        mainPanel.add(lblLnge);

        JSlider slider = new JSlider(1, 21, 11);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                lblLnge.setText("Länge " + slider.getValue());
            }
        });
        slider.setBackground(Color.WHITE);
        slider.setBounds(458, 112, 150, 25);
        mainPanel.add(slider);

        JButton btnPasswortGenerieren = new JButton("Passwort generieren");
        btnPasswortGenerieren.setBounds(458, 147, 150, 25);
        btnPasswortGenerieren.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
        btnPasswortGenerieren.setContentAreaFilled(false);
        btnPasswortGenerieren.setForeground(SystemColor.textHighlight);
        btnPasswortGenerieren.setBackground(SystemColor.textHighlight);
        btnPasswortGenerieren.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                int value = slider.getValue();
                String uuid = getAlphaNumeric(value);
                txtPasswort.setText(uuid);


            }
        });
        mainPanel.add(btnPasswortGenerieren);

        table = new JTable();
        table.setGridColor(Color.LIGHT_GRAY);
        table.setBackground(SystemColor.menu);
        table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(20);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setForeground(Color.WHITE);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {

                row = table.getSelectedRow();
                col = table.getColumnCount();
                txtBezeichnung.setText(dtm.getValueAt(row, 0).toString());
                txtPasswort.setText(dtm.getValueAt(row, 1).toString());
                System.out.println("2");

            }
        });
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBounds(30, 232, 740, 445);
        mainPanel.add(scrollPane);

        JButton btnSpeichern = new JButton("Speichern");
        btnSpeichern.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if (!txtBezeichnung.getText().equals("") && !txtPasswort.getText().equals("")) {

                    String bezeichnung = txtBezeichnung.getText();
                    String passwort = txtPasswort.getText();
                    liste.add(new Passwort(bezeichnung, passwort));
                    dtm.setRowCount(0);
                    for (int i = 0; i < liste.size(); i++) {
                        Object[] objs = {liste.get(i).bezeichnung, liste.get(i).passwort};
                        dtm.addRow(objs);
                    }

                    txtBezeichnung.setText("");
                    txtPasswort.setText("");

                    try {
                        FileOutputStream fos = new FileOutputStream("data");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(liste);
                        oos.close();
                        fos.close();

                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Geben Sie eine Bezeichnung und ein Passwort ein", "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSpeichern.setBounds(620, 147, 150, 25);
        btnSpeichern.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
        btnSpeichern.setContentAreaFilled(false);
        btnSpeichern.setForeground(SystemColor.textHighlight);
        btnSpeichern.setBackground(SystemColor.textHighlight);
        mainPanel.add(btnSpeichern);

        JLabel lblMeinePasswrter = new JLabel("Meine Passwörter");
        lblMeinePasswrter.setBounds(30, 194, 185, 25);
        lblMeinePasswrter.setForeground(Color.DARK_GRAY);
        lblMeinePasswrter.setFont(new Font("Tahoma", Font.BOLD, 16));
        mainPanel.add(lblMeinePasswrter);

        JButton btnLschen = new JButton("Löschen");
        btnLschen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if (JOptionPane.showConfirmDialog(null, "Wollen Sie dieses Passwort löschen?" + row, "Achtung",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    dtm.removeRow(row);
                    liste.remove(row);
                    dtm.setRowCount(0);
                    for (int i = 0; i < liste.size(); i++) {
                        Object[] objs = {liste.get(i).bezeichnung, liste.get(i).passwort};
                        dtm.addRow(objs);
                    }
                    txtBezeichnung.setText("");
                    txtPasswort.setText("");

                    try {
                        FileOutputStream fos = new FileOutputStream("data");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(liste);
                        oos.close();
                        fos.close();

                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                } else {
                    System.out.println("Abgebrochen");
                }
            }
        });
        btnLschen.setForeground(SystemColor.textHighlight);
        btnLschen.setContentAreaFilled(false);
        btnLschen.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
        btnLschen.setBackground(SystemColor.textHighlight);
        btnLschen.setBounds(30, 697, 170, 25);
        mainPanel.add(btnLschen);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String neueBezeichnung = txtBezeichnung.getText();
                String neuesPasswort = txtPasswort.getText();
                liste.get(row).bezeichnung = neueBezeichnung;
                liste.get(row).passwort = neuesPasswort;
                dtm.setRowCount(0);
                for (int i = 0; i < liste.size(); i++) {
                    Object[] objs = {liste.get(i).bezeichnung, liste.get(i).passwort};
                    dtm.addRow(objs);
                }
                try {
                    FileOutputStream fos = new FileOutputStream("data");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(liste);
                    oos.close();
                    fos.close();

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            }
        });
        btnUpdate.setForeground(SystemColor.textHighlight);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
        btnUpdate.setBackground(SystemColor.textHighlight);
        btnUpdate.setBounds(620, 112, 150, 25);
        mainPanel.add(btnUpdate);

        JButton btnSuchen = new JButton("Suchen");
        btnSuchen.setForeground(SystemColor.textHighlight);
        btnSuchen.setContentAreaFilled(false);
        btnSuchen.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
        btnSuchen.setBackground(SystemColor.textHighlight);
        btnSuchen.setBounds(232, 697, 170, 25);
        mainPanel.add(btnSuchen);

        JButton btnClear = new JButton("clear");
        btnClear.setForeground(Color.GRAY);
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                txtBezeichnung.setText("");
                txtPasswort.setText("");
            }
        });
        btnClear.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
        btnClear.setContentAreaFilled(false);
        btnClear.setBounds(390, 147, 56, 25);
        mainPanel.add(btnClear);


    }

    public void windowClosing(WindowEvent e) {
        try {
            FileOutputStream fos = new FileOutputStream("data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(liste);
            oos.close();
            fos.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String getAlphaNumeric(int len) {
        StringBuffer sb = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            int ndx = (int) (Math.random() * ALPHA_NUM.length());
            sb.append(ALPHA_NUM.charAt(ndx));
        }
        return sb.toString();
    }
}
