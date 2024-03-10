package es.studium.programaGestion;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Alta implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Alta Pais");
	Label lblPais = new Label("País:  ");
    Label lblContinente = new Label("Continente:");
	TextField nombrePais = new TextField(20);
	TextField continentePais = new TextField(20);
	Button btnAñadir = new Button("Añadir");
	Button btnBorrar = new Button("Borrar");
	Button btnAceptar = new Button("Aceptar");
	Dialog dlgFeedback = new Dialog(ventana, "Operación realizada", true);
	Label lblMensaje = new Label ("¡Operación realizada correctamente!");
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/peliculas";
	String login = "admin";
	String password = "Studium2023";
	Connection connection = null;
	Statement statement = null;
	
	public Alta() {
		
		ventana.setLayout(new FlowLayout());
		ventana.setSize(270, 160);
		ventana.setBackground(Color.orange);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		
		//Agregar antes de los textos cualquier etiqueta
		ventana.add(lblPais);
		ventana.add(nombrePais);
		ventana.add(lblContinente);
		ventana.add(continentePais);
		
		ventana.add(btnAñadir);
		ventana.add(btnBorrar);
		btnAñadir.addActionListener(this);
		btnBorrar.addActionListener(this);
		ventana.addWindowListener(this);
		
		// Diálogo
		dlgFeedback.setLayout(new FlowLayout());
		dlgFeedback.add(lblMensaje);
		dlgFeedback.add(btnAceptar); //Añadimos btn Aceptar al diálogo
		dlgFeedback.setSize(260,190);
		dlgFeedback.setLocationRelativeTo(null);
		
		//Para poder cerrar el Diálogo
		dlgFeedback.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dlgFeedback.setVisible(false);
			}
		});
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Cerrar el diálogo
				dlgFeedback.setVisible(false);
				
				//Limpiar los campos de texto
				nombrePais.setText("");
				continentePais.setText("");
			
				//Enfocar texto "nombreMonitor"
				nombrePais.requestFocus();
			}
		});
		
		//Cargar el Driver
		try
		{
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Se ha producido un error al cargar el Driver");
		}
	
		//Establecer la conexión con la base de datos
		try
		{
			connection = DriverManager.getConnection(url, login, password);
		}
		catch(SQLException e)
		{
			System.out.println("Se produjo un error al conectar a la Base de Datos");
		}
		
		//Preparar el statement
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		ventana.setVisible(true);
	}
	public static void main(String[] args)
	{
		new Alta();
	}
	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent)
	{
		
		// Si es el Cerrar del diálogo
		if(dlgFeedback.hasFocus())
		{
			dlgFeedback.setVisible(false);
		}
		else
		{
			ventana.setVisible(false);
			
			//Cerrar los elementos de la base de datos
			try
			{
				statement.close();
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error al cerrar "+e.toString());
			}
		
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void actionPerformed(ActionEvent actionEvent)
	{
	
		// Hemos pulsado Añadir
	    if(btnAñadir.equals(actionEvent.getSource()))
	    {
	      
	    	// Verificar si los campos están vacíos
	        String pais = nombrePais.getText();
	        String continente = continentePais.getText();
	        if(pais.isEmpty() || continente.isEmpty()) {//Si país o continente están vacíos
	            // Mostrar mensaje de error
	            dlgFeedback.setTitle("Error");
	            lblMensaje.setText("Tienes que rellenar pais y continente");
	            dlgFeedback.setLocationRelativeTo(null);
	            dlgFeedback.setVisible(true);
	        } else {
	            try
	            {
	                // Insertar en la base de datos
	                statement.executeUpdate("INSERT INTO paises VALUES (null, '"
	                +pais
	                +"','"
	                +continente
	                +"')");
	                // Limpiar campos
	                nombrePais.setText("");
	               continentePais.setText("");
	                // Mostrar mensaje de éxito
	                lblMensaje.setText("¡Operación realizada correctamente!");
	                dlgFeedback.setTitle("Operación realizada");
	                dlgFeedback.setVisible(true);
	            }
	            catch(SQLException se)
	            {
	                System.out.println("Error en la sentencia SQL"+se.toString());
	            }
	        }
	    }
	    else
	    {
	        // Limpiar campos si se presiona Borrar
	        nombrePais.setText("");
	        continentePais.setText("");
	    }
	}
}