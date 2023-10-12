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

                JDialog dialog = optionPane.createDialog("No Session");

                customButton.addActionListener(e -> {
                    descartar();
                    dialog.dispose();
                });

                dialog.setModal(true);

                dialog.setResizable(false);

                dialog.setVisible(true);
            }

            try {
                if (session.has("name")) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setSize(1000, 700);
                    getContentPane().setBackground(Color.DARK_GRAY);
                    setLayout(new BorderLayout());

                    JPanel panel = new JPanel(new GridBagLayout());
                    panel.setBackground(Color.DARK_GRAY);

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.insets = new Insets(10, 10, 0, 10);
                    gbc.anchor = GridBagConstraints.CENTER;

                    ImageIcon imageIcon = new ImageIcon("image/img_2.png");
                    JButton imageButton = new JButton(imageIcon);
                    imageButton.setBorderPainted(false);
                    imageButton.setContentAreaFilled(false);
                    imageButton.setFocusPainted(false);
                    imageButton.setOpaque(false);
                    panel.add(imageButton, gbc);

                    gbc.gridy = 1;
                    gbc.insets = new Insets(5, 10, 10, 10);
                    gbc.anchor = GridBagConstraints.PAGE_START;

                    JLabel nomeLabel = new JLabel();
                    nomeLabel.setText(session.getString("name"));
                    nomeLabel.setForeground(Color.WHITE);
                    nomeLabel.setHorizontalAlignment(JLabel.CENTER);
                    panel.add(nomeLabel, gbc);

                    add(panel, BorderLayout.CENTER);
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

