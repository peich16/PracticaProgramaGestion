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

public class Modificacion implements WindowListener, ActionListener, ItemListener
{
	Frame ventana = new Frame("Modificación");
	Label lblIdPais = new Label("ID del País:"); //Mostramos ID en modo estático(texto)
    Label idPais = new Label();
	Label lblnombrePais = new Label("Pais:");
	TextField nombrePais = new TextField(15);
	Label lblcontinentePais = new Label("Continente:");
	TextField continentePais = new TextField(15);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");
	Dialog dlgActualizar = new Dialog(ventana, "Modificación", true);
	Dialog dlgMensaje = new Dialog(ventana, "Operación realizada", true);
	Label lblMensaje = new Label ("Seleccione país a modificar:");
	Label lblEtiqueta = new Label ("Operación realizada correctamente!");
	Choice choLista = new Choice();
	String cadena = "";
	int cerrar = 0;
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/peliculas";
	String login = "admin";
	String password = "Studium2023";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	public Modificacion()
	{
		ventana.setLayout(new FlowLayout());
		dlgActualizar.setLayout(new FlowLayout());
		dlgActualizar.setSize(350,200);
		dlgActualizar.setLocationRelativeTo(null);
		ventana.setSize(300,200);
		ventana.setBackground(Color.orange);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.add(lblMensaje);
		ventana.add(choLista);
	//idPais.setEditable(false); Label no editable
		dlgActualizar.add(lblIdPais);
		dlgActualizar.add(idPais); //Muestra el ID del Pais
		dlgActualizar.add(lblnombrePais);
		dlgActualizar.add(nombrePais);
		dlgActualizar.add(lblcontinentePais);
		dlgActualizar.add(continentePais);
		dlgActualizar.add(btnAceptar);
		dlgActualizar.add(btnCancelar);
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventana.addWindowListener(this);
		// Diálogo
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.add(lblEtiqueta);
		dlgMensaje.setSize(300,200);
		//Para poder cerrar el Diálogo
		dlgMensaje.addWindowListener(this);
		dlgActualizar.addWindowListener(this);
		//Añadimos el listener a la lista
		choLista.addItemListener(this);
		choLista.add("Selecciona uno");
		
		
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
			rs= statement.executeQuery("SELECT * FROM paises");
			while(rs.next())
			{
				cadena = Integer.toString(rs.getInt("idPais"));
				cadena = cadena + "-"+ rs.getString("nombrePais");
				cadena = cadena + "-"+ rs.getString("ContinentePais");
				choLista.add(cadena);
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
			// d
		case 1:
			cerrar = 0;
			dlgMensaje.setVisible(false);
			dlgActualizar.setVisible(false);
			break;
			//dialogo
		case 2:
			cerrar = 0;
			dlgActualizar.setVisible(false);
			break;
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void itemStateChanged(ItemEvent ie)
	{
		//Mostraremos dialogo con los datos ya cargados
		String[] array = ie.getItem().toString().split("-");
		idPais.setText(array[0]);
		nombrePais.setText(array[1]);
		continentePais.setText(array[2]);
		cerrar = 2;
		dlgActualizar.setVisible(true);
	}
	public void actionPerformed(ActionEvent actionEvent)
	{
		// Hemos pulsado Añadir
		if(btnAceptar.equals(actionEvent.getSource()))
		{
			try
			{
				statement.executeUpdate("UPDATE paises SET continentePais = '"
				+nombrePais.getText()
				+"', continentePais='"
				+continentePais.getText()
				+"' WHERE idPais="+idPais.getText());
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
			dlgActualizar.setVisible(false);
		
		}
	}
}