package src;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public class AdmViewUser extends JFrame {

    private JSONObject session;
    private JTable table;
    private DefaultTableModel tableModel;

    public AdmViewUser(JSONObject session) {
        this.session = session;
        if (!session.has("name")) {
            JOptionPane optionPane = new JOptionPane("Por favor realize login", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);

            JButton customButton = new JButton("Fechar");

            optionPane.setOptions(new Object[]{customButton});

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

                JMenuBar menuBar = new JMenuBar();

                JMenu perfilMenu = new JMenu("Perfil");
                JMenu jogosMenu = new JMenu("Jogos");
                JMenuItem verPerfilMenuItem = new JMenuItem("Ver Perfil");
                verPerfilMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new PerfilAdm(session).setVisible(true);
                        dispose();
                    }
                });
                perfilMenu.add(verPerfilMenuItem);

                JMenuItem verjogosMenuItem = new JMenuItem("Ver Jogos");
                verjogosMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new AdmViewJogos(session).setVisible(true);
                        dispose();
                    }
                });
                jogosMenu.add(verjogosMenuItem);

                menuBar.add(perfilMenu);
                menuBar.add(jogosMenu);
                setJMenuBar(menuBar);

                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground(Color.DARK_GRAY);

                table = new JTable();
                table.setBackground(Color.DARK_GRAY);
                table.setForeground(Color.WHITE);
                JScrollPane scrollPane = new JScrollPane(table);
                panel.add(scrollPane, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
                buttonPanel.setBackground(Color.DARK_GRAY);

                panel.add(buttonPanel, BorderLayout.WEST);

                add(panel, BorderLayout.CENTER);

                loadTableData();

                table.getColumn("Deletar").setCellRenderer((TableCellRenderer) new ButtonRenderer());
                table.getColumn("Deletar").setCellEditor(new ButtonEditor(new JCheckBox()));

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

    private void loadTableData() {
        try {
            String jsonFileContent = new String(Files.readAllBytes(Paths.get("src/usuarios.json")));
            JSONArray jsonArray = new JSONArray(jsonFileContent);

            Vector<String> columnNames = new Vector<>();
            columnNames.add("Name");
            columnNames.add("Email");
            columnNames.add("Username");
            columnNames.add("Senha");
            columnNames.add("Biblioteca");
            columnNames.add("imagePath");
            columnNames.add("MementoId");
            columnNames.add("Deletar");
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 7;
                }
            };

            for (int i = 0; i < jsonArray.length(); i++) {
                String imagePath = "";
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String email = jsonObject.getString("email");
                String username = jsonObject.getString("username");
                String senha = jsonObject.getString("senha");
                JSONArray biblioteca = jsonObject.getJSONArray("biblioteca");
                if (jsonObject.has("imagePath")) {
                    imagePath = jsonObject.getString("imagePath");
                } else {
                    imagePath = "Nenhuma";
                }
                int mementoId = jsonObject.getInt("mementoId");
                tableModel.addRow(new Object[]{name, email, username, senha, biblioteca, imagePath, mementoId, "Deletar"});
            }

            table.setModel(tableModel);

            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    cellComponent.setBackground(Color.DARK_GRAY);
                    cellComponent.setForeground(Color.WHITE);
                    return cellComponent;
                }
            });
            JTableHeader header = table.getTableHeader();
            header.setBackground(Color.DARK_GRAY);
            header.setForeground(Color.WHITE);

            JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
            scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void descartar() {
        new PerfilAdm(session);
        dispose();
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
            setBackground(Color.RED);
            setForeground(Color.WHITE);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int selectedRow;
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            selectedRow = row;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) {
                int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar este usuário?", "Confirmar Deletar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION && selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
                    tableModel.removeRow(selectedRow);
                    writeTableDataToJson();
                }
            }
                clicked = false;
                return new String(label);
            }
        private void writeTableDataToJson() {
            SwingUtilities.invokeLater(() -> {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", tableModel.getValueAt(i, 0));
                    jsonObject.put("email", tableModel.getValueAt(i, 1));
                    jsonObject.put("username", tableModel.getValueAt(i, 2));
                    jsonObject.put("senha", tableModel.getValueAt(i, 3));
                    jsonObject.put("biblioteca", tableModel.getValueAt(i, 4));
                    if (!tableModel.getValueAt(i, 5).equals("Nenhuma")) {
                        jsonObject.put("imagePath", tableModel.getValueAt(i, 5));
                    }
                    jsonObject.put("mementoId", tableModel.getValueAt(i, 6));
                    jsonArray.put(jsonObject);
                }

                try {
                    Files.write(Paths.get("src/usuarios.json"), jsonArray.toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loadTableData();
                TableColumn column = table.getColumn("Deletar");
                column.setCellRenderer(new ButtonRenderer());
                column.setCellEditor(new ButtonEditor(new JCheckBox()));
            });
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JSONObject session = new JSONObject();
                session.put("name", "admin");
                new AdmViewUser(session);
            }
        });
    }
}
