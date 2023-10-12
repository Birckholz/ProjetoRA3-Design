package src;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Perfil extends JFrame {

    private JSONObject session;
    private ImageIcon imageIcon;
    public Perfil(JSONObject session) {

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

                if (session.getString("imagePath").length() > 0) {
                    ImageIcon originalIcon = new ImageIcon(session.getString("imagePath"));
                    Image originalImage = originalIcon.getImage();

                    int maxWidth = 200;
                    int maxHeight = 200;

                    int scaledWidth = originalImage.getWidth(null);
                    int scaledHeight = originalImage.getHeight(null);
                    if (scaledWidth > maxWidth || scaledHeight > maxHeight) {
                        double widthScaleFactor = (double) maxWidth / scaledWidth;
                        double heightScaleFactor = (double) maxHeight / scaledHeight;
                        double scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);
                        scaledWidth = (int) (scaledWidth * scaleFactor);
                        scaledHeight = (int) (scaledHeight * scaleFactor);
                    }

                    Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    imageIcon = new ImageIcon(scaledImage);
                } else {
                    imageIcon = new ImageIcon("image/img_2.png");
                }

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

                JMenuBar barraMenu = new JMenuBar();
                JMenu menuBiblioteca = new JMenu("Biblioteca");
                JMenu menuLoja = new JMenu("Loja");
                JMenu menuLista = new JMenu("Lista de Desejos");

                JMenuItem verJogos = new JMenuItem("Ver Jogos");
                verJogos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new Biblioteca(session).setVisible(true);
                    }
                });

                JMenuItem verLoja = new JMenuItem("Ver Loja");
                verLoja.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new Loja(session).setVisible(true);
                    }
                });


                menuBiblioteca.add(verJogos);
                menuLoja.add(verLoja);


                barraMenu.add(menuBiblioteca);
                barraMenu.add(menuLoja);


                setJMenuBar(barraMenu);
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

    public static void main(String[] args) {
        JSONObject a = new JSONObject();
        Perfil p = new Perfil(a);
    }
}
