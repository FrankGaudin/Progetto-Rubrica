package Classes;
import Database.DatabaseManager;
import java.sql.*;
import java.util.Vector;

public class Rubrica {
    private Vector<Persona> persone;
    private Connection connection;

    public Rubrica() {
        persone = new Vector<>();
        connection = DatabaseManager.connect();
    }

    public void aggiungiPersona(Persona persona) {
        try {
            String query = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, persona.getNome());
            statement.setString(2, persona.getCognome());
            statement.setString(3, persona.getIndirizzo());
            statement.setString(4, persona.getTelefono());
            statement.setInt(5, persona.getEta());
            statement.executeUpdate();
            caricaDati();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificaPersona(int id, Persona persona) {
        try {
            String query = "UPDATE persone SET nome = ?, cognome = ?, indirizzo = ?, telefono = ?, eta = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, persona.getNome());
            statement.setString(2, persona.getCognome());
            statement.setString(3, persona.getIndirizzo());
            statement.setString(4, persona.getTelefono());
            statement.setInt(5, persona.getEta());
            statement.setInt(6, id);
            statement.executeUpdate();
            caricaDati();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminaPersona(int id) {
        try {
            String query = "DELETE FROM persone WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            caricaDati();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<Persona> getPersone() {
        return persone;
    }

    public void caricaDati() {
        persone.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM persone");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String indirizzo = resultSet.getString("indirizzo");
                String telefono = resultSet.getString("telefono");
                int eta = resultSet.getInt("eta");
                Persona persona = new Persona(id, nome, cognome, indirizzo, telefono, eta);
                persone.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
