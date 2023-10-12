package src;

 import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class PerfilAdm extends JFrame {

        private JSONObject session;

        public PerfilAdm(JSONObject session) {
            if (!session.has("name")) {
                JOptionPane optionPane = new JOptionPane("Por favor realize login", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);

                JButton customButton = new JButton("Fechar");

                optionPane.setOptions(new Object[] { customButton });

                // Create a dialog with the option pane
                JDialog dialog = optionPane.createDialog("No Session");

                // Add an action listener to the custom button
                customButton.addActionListener(e -> {
                    descartar();
                    dialog.dispose();
                });

                // Set the dialog to be modal
                dialog.setModal(true);

                // Set the dialog to be non-resizable
                dialog.setResizable(false);

                // Display the dialog
                dialog.setVisible(true);
            }

            try {
                if (session.has("name")) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setSize(1000, 700);
                    getContentPane().setBackground(Color.DARK_GRAY);
                    setLayout(new BorderLayout());

                    // Create a panel with GridBagLayout to hold the image and name label
                    JPanel panel = new JPanel(new GridBagLayout());
                    panel.setBackground(Color.DARK_GRAY);

                    // Create GridBagConstraints
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.insets = new Insets(10, 10, 0, 10); // top, left, bottom, right
                    gbc.anchor = GridBagConstraints.CENTER;

                    // Create a button with the image
                    ImageIcon imageIcon = new ImageIcon("image/img_2.png");
                    JButton imageButton = new JButton(imageIcon);
                    imageButton.setBorderPainted(false);
                    imageButton.setContentAreaFilled(false);
                    imageButton.setFocusPainted(false);
                    imageButton.setOpaque(false);
                    panel.add(imageButton, gbc);

                    // Update GridBagConstraints for the name label
                    gbc.gridy = 1;
                    gbc.insets = new Insets(5, 10, 10, 10); // top, left, bottom, right
                    gbc.anchor = GridBagConstraints.PAGE_START;

                    // Create a label for the name
                    JLabel nomeLabel = new JLabel();
                    nomeLabel.setText(session.getString("name"));
                    nomeLabel.setForeground(Color.WHITE);
                    nomeLabel.setHorizontalAlignment(JLabel.CENTER);
                    panel.add(nomeLabel, gbc);

                    // Add the panel to the center of the frame
                    add(panel, BorderLayout.CENTER);

                    // Add action listener to the image button
                    imageButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            new profileEditGUI(session).setVisible(true);
                        }
                    });

                    JMenuBar menuBar = new JMenuBar();
                    JMenu menuJogos = new JMenu("Jogos");
                    JMenu menuUsuarios = new JMenu("Usuários");
                    JMenuItem cadastrarJogo= new JMenuItem("Cadastrar Jogo");
                    JMenuItem verJogos = new JMenuItem("Ver Jogos");
                    verJogos.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            new AdmViewJogos(session).setVisible(true);
                            dispose();
                            // Add your logic for "Ver Jogos" here
                        }
                    });

                    cadastrarJogo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new RegistroJogoGUI(session).setVisible(true);
                            dispose();
                        }
                    });

                    JMenuItem verUsuarios = new JMenuItem("Ver Usuários");
                    verUsuarios.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            new AdmViewUser(session).setVisible(true);
                            dispose();
                            // Add your logic for "Ver Usuários" here
                        }
                    });

                    menuJogos.add(verJogos);
                    menuUsuarios.add(verUsuarios);
                    menuJogos.add(cadastrarJogo);
                    menuBar.add(menuJogos);
                    menuBar.add(menuUsuarios);

                    setJMenuBar(menuBar);
                    setVisible(true);
                    setLocationRelativeTo(null);
                } else {
                    throw new MyCustomException("Session undefined");
                }
            } catch (MyCustomException e) {
                System.out.println(e.getMessage());
                descartar();
            }
        }

        public void descartar() {
            dispose();
        }

    }

