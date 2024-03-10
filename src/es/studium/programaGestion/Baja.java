package es.studium.programaGestion;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Baja implements WindowListener, ActionListener, ItemListener
{
	Frame ventana = new Frame("Baja");
	
	TextField idPais = new TextField(20);
	TextField nombrePais = new TextField(20);
	TextField continentePais = new TextField(20);
	Button btnAceptar = new Button("Sí");
	Button btnCancelar = new Button("No");
	Dialog dlgSeguro = new Dialog(ventana, "Borrado", true);
	Dialog dlgMensaje = new Dialog(ventana, "Operación realizada", true);
	Label lblMensaje = new Label ("Seleccionar el país a borrar:");
	Label lblSeguro = new Label("¿Está seguro de eliminar el país?");
	Label lblPais = new Label("");
	Label lblFeedback = new Label ("Operación realizada correctamente!");
	Choice choListado = new Choice();
	String cadena = "";
	int idPaisBorrar;
	int cerrar = 0;
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/peliculas";
	String login = "admin";
	String password = "Studium2023";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	public Baja()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(320,210);
		ventana.setBackground(Color.orange);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.add(lblMensaje);
		ventana.add(choListado);
		idPais.setEditable(false);
		dlgSeguro.add(lblSeguro);
		dlgSeguro.add(lblPais);
		dlgSeguro.add(btnAceptar);
		dlgSeguro.add(btnCancelar);
		dlgSeguro.setLayout(new FlowLayout());
		dlgSeguro.setSize(290,210);
		dlgSeguro.addWindowListener(this);
		dlgSeguro.setLocationRelativeTo(null);
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventana.addWindowListener(this);
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.add(lblFeedback);
		dlgMensaje.setSize(250,140);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.setLocationRelativeTo(null);
		//Añadimos el listener a la lista
		choListado.addItemListener(this);
		choListado.add("Seleccione uno");
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
			statement =
connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs= statement.executeQuery("SELECT * FROM paises");
			while(rs.next())
			{
				cadena=Integer.toString(rs.getInt("idPais"));
				cadena = cadena + "-"+ rs.getString("nombrePais");
				cadena = cadena + "-"+ rs.getString("continentePais");
				choListado.add(cadena);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL:"+e.toString());
		}
		ventana.setVisible(true);
	}
	
	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent)
	{
		switch(cerrar)
		{
		// Principal
		case 0:
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
			System.exit(0);
			break;
			// dlgMensaje
		case 1:
			cerrar = 0;
			dlgMensaje.setVisible(false);
			dlgSeguro.setVisible(false);
			break;
			// dlgSeguro
		case 2:
			cerrar = 0;
			dlgSeguro.setVisible(false);
			break;
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void itemStateChanged(ItemEvent ie)
	{
		// Mostraremos dialogo con los datos cargados
		String[] array = ie.getItem().toString().split("-");
		idPaisBorrar = Integer.parseInt(array[0]);
		lblPais.setText(ie.getItem().toString());
		cerrar = 1;
		dlgSeguro.setVisible(true);
	}
	public void actionPerformed(ActionEvent actionEvent)
	{
		// Hemos pulsado Sí
		if(btnAceptar.equals(actionEvent.getSource()))
		{
			try
			{
				statement.executeUpdate("DELETE FROM paises WHERE "
		+ "idPais="+idPaisBorrar);
				cerrar = 1;
				dlgMensaje.setVisible(true);
			}
			catch(SQLException se)
			{
				System.out.println("Error en la sentencia SQL"+se.toString());
			}
		}
		else
		{
			cerrar = 0;
			dlgSeguro.setVisible(false);
		}
	}
}