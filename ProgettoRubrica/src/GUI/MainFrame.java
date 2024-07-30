package GUI;

import Classes.Persona;
import Classes.Rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class MainFrame extends JFrame {
    private Rubrica rubrica;
    private JTable table;
    private DefaultTableModel tableModel;

    private JButton createIconButton(String iconPath, int width, int height) {
        // Crea il pulsante con un'icona
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));

        // Ridimensiona l'icona per adattarla al pulsante
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);

        // Imposta l'icona e rimuovi il testo
        button.setIcon(icon);
        button.setText(""); // Rimuove il testo del pulsante

        // Imposta le dimensioni del pulsante per adattarlo all'icona
        button.setPreferredSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));

        // Rimuove i bordi e lo sfondo
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);

        return button;
    }

    public MainFrame() {
        setTitle("Rubrica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rubrica = new Rubrica();
        rubrica.caricaDati();

        // Configura la toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        // Pulsante Aggiungi
        JButton aggiungiButton = createIconButton("/GUI/icons/add.png", 120, 40);

        // Pulsante Modifica
        JButton modificaButton = createIconButton("/GUI/icons/edit.png", 120, 40);

        // Pulsante Elimina
        JButton eliminaButton = createIconButton("/GUI/icons/delete.png", 120, 40); // Dimensione icona 32x32

        // Pannello per centrare i pulsanti nella toolbar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(aggiungiButton);
        buttonPanel.add(modificaButton);
        buttonPanel.add(eliminaButton);
        toolBar.add(buttonPanel);

        add(toolBar, BorderLayout.NORTH);

        // Configura la tabella
        tableModel = new DefaultTableModel(new Object[]{"Nome", "Cognome", "Indirizzo", "Telefono", "Età"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        aggiornaLista();

        // Deseleziona la riga se clicchi fuori dalla tabella
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                table.clearSelection();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.rowAtPoint(e.getPoint()) == -1) {
                    table.clearSelection();
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Azione per aggiungere persona
        aggiungiButton.addActionListener(e -> {
            PersonaDialog dialog = new PersonaDialog(this, null);
            Persona persona = dialog.showDialog();
            if (persona != null) {
                rubrica.aggiungiPersona(persona);
                aggiornaLista();
            }
        });

        // Azione per modificare persona
        modificaButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Persona selezionata = rubrica.getPersone().get(selectedRow);
                PersonaDialog dialog = new PersonaDialog(this, selezionata);
                Persona persona = dialog.showDialog();
                if (persona != null) {
                    rubrica.modificaPersona(selezionata.getId(), persona);
                    aggiornaLista();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona una persona da modificare.");
            }
        });

        // Azione per eliminare persona
        eliminaButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Persona selezionata = rubrica.getPersone().get(selectedRow);
                rubrica.eliminaPersona(selezionata.getId());
                aggiornaLista();
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona una persona da eliminare.");
            }
        });
    }

    private void aggiornaLista() {
        tableModel.setRowCount(0); // Clear existing data
        List<Persona> persone = rubrica.getPersone();
        for (Persona p : persone) {
            tableModel.addRow(new Object[]{p.getNome(), p.getCognome(), p.getIndirizzo(), p.getTelefono(), p.getEta()});
        }
    }
}
