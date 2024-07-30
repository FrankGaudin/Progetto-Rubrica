package GUI;

import Classes.Persona;

import javax.swing.*;
import java.awt.*;

public class PersonaDialog extends JDialog {
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField indirizzoField;
    private JTextField telefonoField;
    private JTextField etaField;
    private JButton okButton;
    private JButton cancelButton;
    private Persona persona;

    public PersonaDialog(Frame parent, Persona persona) {
        super(parent, "Dettagli Persona", true);
        this.persona = persona;

        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("Cognome:"));
        cognomeField = new JTextField();
        add(cognomeField);

        add(new JLabel("Indirizzo:"));
        indirizzoField = new JTextField();
        add(indirizzoField);

        add(new JLabel("Telefono:"));
        telefonoField = new JTextField();
        add(telefonoField);

        add(new JLabel("Età:"));
        etaField = new JTextField();
        add(etaField);

        okButton = new JButton("OK");
        cancelButton = new JButton("Annulla");

        add(okButton);
        add(cancelButton);

        if (persona != null) {
            nomeField.setText(persona.getNome());
            cognomeField.setText(persona.getCognome());
            indirizzoField.setText(persona.getIndirizzo());
            telefonoField.setText(persona.getTelefono());
            etaField.setText(String.valueOf(persona.getEta()));
        }

        okButton.addActionListener(e -> {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String indirizzo = indirizzoField.getText();
            String telefono = telefonoField.getText();
            int eta = Integer.parseInt(etaField.getText());
            this.persona = new Persona(persona != null ? persona.getId() : 0, nome, cognome, indirizzo, telefono, eta);
            setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            this.persona = null;
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public Persona showDialog() {
        setVisible(true);
        return persona;
    }
}