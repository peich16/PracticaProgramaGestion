package es.studium.programaGestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Datos
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/peliculas";
	String login = "admin";
	String password = "Studium2023";

	Connection connection = null;
	Statement  statement = null;
	ResultSet rs = null;

	Datos() {}

	public boolean conectar()
	{
		boolean conexionCorrecta = true;
		//Cargar el Driver
		try
		{
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Se ha producido un error al cargar el Driver");
			conexionCorrecta = false;
		}
		//Establecer la conexión con la base de datos
		try
		{
			connection = DriverManager.getConnection(url, login, password);
		}
		catch(SQLException e)
		{
			System.out.println("Se produjo un error al conectar a la Base de Datos");
			conexionCorrecta = false;
		}
		return conexionCorrecta;
	}

	public boolean consultarCredenciales(String sentencia)
	{
		boolean credencialesCorrectas = true;
		//Preparar el statement
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery(sentencia);
			if(!rs.next())
			{
				credencialesCorrectas = false;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}   
		return credencialesCorrectas;
	}

	public int dameTipoUsuario() {
		
		return 0;
	}
	
//Crear método "dame tipo de usuario"
	
	
}