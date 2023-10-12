package src;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaJogo extends JFrame {

    private JSONObject session;

    public TelaJogo(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        getContentPane().setBackground(Color.DARK_GRAY);
        setVisible(true);

        JMenuBar barraMenu = new JMenuBar();
        JMenu menuBiblioteca = new JMenu("Biblioteca");
        JMenu menuLoja = new JMenu("Loja");
        JMenu menuLista = new JMenu("Lista de Desejos");
        JMenu menuPerfil = new JMenu("Perfil");

        JMenuItem irPerfil = new JMenuItem("Ir para o Perfil");
        irPerfil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Perfil(session).setVisible(true);
            }
        });

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




        menuPerfil.add(irPerfil);
        menuBiblioteca.add(verJogos);
        menuLoja.add(verLoja);


        barraMenu.add(menuBiblioteca);
        barraMenu.add(menuLoja);
        barraMenu.add(menuLista);
        barraMenu.add(menuPerfil);

        getContentPane().add(BorderLayout.NORTH, barraMenu);
        setVisible(true);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        JSONObject a = new JSONObject();
        TelaJogo p = new TelaJogo();
    }

}
