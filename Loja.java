package src;

import src.lojaAction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Loja extends JFrame {
    private JSONObject session;
    private JPanel gamePanelContainer;

    public Loja(JSONObject session) {
        this.session = session;

        if (!session.has("name")) {
            JOptionPane optionPane = new JOptionPane("Por favor realize login", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);

            JButton customButton = new JButton("Fechar");

            optionPane.setOptions(new Object[]{customButton});

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

                // Create a top-level JPanel to hold the menu bar and game panels
                JPanel contentPanel = new JPanel(new BorderLayout());

                // Create the menu bar
                JMenuBar barraMenu = new JMenuBar();
                JMenu menuBiblioteca = new JMenu("Biblioteca");
                JMenu menuLista = new JMenu("Lista de Desejos");
                JMenu menuPerfil = new JMenu("Perfil");

                JMenuItem verJogos = new JMenuItem("Ver Jogos");
                verJogos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new Biblioteca(session).setVisible(true);
                    }
                });
                JMenuItem irPerfil = new JMenuItem("Ir para o Perfil");
                irPerfil.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new Perfil(session).setVisible(true);
                    }
                });




                menuBiblioteca.add(verJogos);
                menuPerfil.add(irPerfil);


                barraMenu.add(menuBiblioteca);
                barraMenu.add(menuPerfil);

                contentPanel.add(BorderLayout.NORTH, barraMenu);

                // Create a container panel for the game panels
                gamePanelContainer = new JPanel(new GridLayout(0, 3, 10, 10));
                gamePanelContainer.setBackground(Color.DARK_GRAY); // Set background color to dark gray

                // Add the game panel container to a JScrollPane for scrolling
                JScrollPane scrollPane = new JScrollPane(gamePanelContainer);
                scrollPane.setBackground(Color.DARK_GRAY);
                scrollPane.getViewport().setBackground(Color.DARK_GRAY);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                // Add the scroll pane to the content panel
                contentPanel.add(BorderLayout.CENTER, scrollPane);

                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(contentPanel, BorderLayout.CENTER);
                setVisible(true);
                setLocationRelativeTo(null);
            } else {
                throw new MyCustomException("Session undefined");
            }

        } catch (MyCustomException e) {
            System.out.println(e.getMessage());
            descartar();
        }
        readGameListingsFromFile("src/games.json");
    }

    private void readGameListingsFromFile(String filePath) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray gameListingsArray = new JSONArray(fileContent);

            for (int i = 0; i < gameListingsArray.length(); i++) {
                JSONObject listingObject = gameListingsArray.getJSONObject(i);
                String imagePath = listingObject.getString("directory");
                String name = listingObject.getString("name");


                // Load the image from the file
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

                // Create a panel to hold the image, name, and button for each game
                JPanel gamePanel = new JPanel();
                gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
                gamePanel.setBackground(Color.DARK_GRAY); // Set background color to dark gray

                // Create a JLabel to display the image
                JLabel imageLabel = new JLabel();
                imageLabel.setIcon(scaledImageIcon);
                gamePanel.add(imageLabel);

                // Create a panel to hold the name and button
                JPanel nameButtonPanel = new JPanel();
                nameButtonPanel.setLayout(new BoxLayout(nameButtonPanel, BoxLayout.Y_AXIS));
                nameButtonPanel.setBackground(Color.DARK_GRAY); // Set background color to dark gray

                // Create a JLabel to display the name
                JLabel nameLabel = new JLabel(name);
                nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                nameLabel.setForeground(Color.WHITE); // Set text color to white
                nameButtonPanel.add(nameLabel);

                // Create a button
                JButton pagarButton = new JButton("Comprar");
                pagarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                nameButtonPanel.add(pagarButton);

                gamePanel.add(nameButtonPanel);

                // Add the game panel to the game panel container
                gamePanelContainer.add(gamePanel);

                pagarButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                            try {
                                String fileContent = new String(Files.readAllBytes(Paths.get("src/games.json")));
                                JSONArray jsonArray;
                                jsonArray = new JSONArray(fileContent);
                                for (Object item : jsonArray) {
                                    if (item instanceof JSONObject) {
                                        JSONObject jsonObject = (JSONObject) item;

                                        if (name.equals(jsonObject.getString("name"))) {
                                            System.out.println(jsonObject);
                                            Game gameComprar = new Game(jsonObject.getString("name"), jsonObject.getString("description"), jsonObject.getDouble("aprice"), jsonObject.getString("directory"));
                                            if (profileAction.checkGame(session, name)) {
                                                showErrorPopup("Jogo Já Comprado", "Fechar");
                                                return;
                                            }
                                            boolean compraResult = lojaAction.comprarGame(session, gameComprar);
                                            if (compraResult) {
                                                System.out.println("Compra Realizada com Sucesso");
                                            } else {
                                                System.out.println("Erro na chamda de Função da Compra");
                                            }
                                        }
                                    }
                                }
                            } catch (IOException p) {
                                p.printStackTrace();

                            }
                    }
                });
            }

            // Update the content pane layout after adding all game panels
            getContentPane().revalidate();
            getContentPane().repaint();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void descartar() {
        dispose();
    }
    private void showErrorPopup(String message, String buttonText) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{buttonText});
        JDialog dialog = optionPane.createDialog(this, "Erro");
        dialog.setVisible(true);
    }
}
