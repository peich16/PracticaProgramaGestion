package es.studium.programaGestion;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Consulta implements WindowListener {
	Frame ventana = new Frame("Consultas");
	List listaPaises = new List(8); // Listado para mostrar los países
	Button exportarPDF = new Button("Exportar a PDF"); // Botón para exportar a PDF

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/peliculas";
	String login = "admin";
	String password = "Studium2023";
	String sentencia = "SELECT * FROM paises";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public Consulta() {

		ventana.setLayout(new FlowLayout());
		ventana.setSize(310, 200);
		ventana.setResizable(false);
		ventana.setBackground(Color.orange);
		ventana.setLocationRelativeTo(null);
		ventana.add(listaPaises);
		ventana.add(exportarPDF); // Agrega el botón a la ventana

		ventana.addWindowListener(this);
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);

			while (rs.next())
			
			{
				
				
			// Agrega cada País a la lista
				listaPaises.add(rs.getInt("idPais") + " - " + rs.getString("nombrePais")
				+ " - " + rs.getString("continentePais"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		ventana.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent windowEvent) {
	}

	@Override
	public void windowClosed(WindowEvent windowEvent) {
	}

	@Override
	public void windowClosing(WindowEvent windowEvent) {
		try {
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ventana.setVisible(false); //Para solo cerrar Consulta y volver menú principal
	}

	@Override
	public void windowDeactivated(WindowEvent windowEvent) {
	}

	@Override
	public void windowDeiconified(WindowEvent windowEvent) {
	}

	@Override
	public void windowIconified(WindowEvent windowEvent) {
	}

	@Override
	public void windowOpened(WindowEvent windowEvent) {
	}
}
