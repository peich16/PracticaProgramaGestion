package es.studium.programaGestion;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Login implements WindowListener, ActionListener {
	
	Frame login = new Frame("Login");
	
	Label lblNombre = new Label("Usuario:");
	Label lblClave = new Label("Contraseña:");
	TextField nombreUsuario = new TextField(25);
	TextField claveUsuario = new TextField(25);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");
	
	Dialog mensajeError = new Dialog(login, "Error de credenciales", true); //Dialogo modal
	Label lblMensaje = new Label("Las credenciales no son correctas");
	Button btnVolver = new Button("aceptar");
	
	Login(){
		
		login.setLayout(new FlowLayout());
		login.setLocationRelativeTo(null);
		login.setSize(320,150);
		login.setBackground(Color.orange) ;
		login.setResizable(false);
		login.add(lblNombre);
		login.add(nombreUsuario);
		login.add(lblClave);
		claveUsuario.setEchoChar('*');
		login.add(claveUsuario);
		login.add(btnAceptar);
		login.add(btnCancelar);
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		login.addWindowListener(this);
		
		login.setVisible(true);		
	}
	public static void main(String[] args) {
	
		new Login();
	}
	public void windowOpened(WindowEvent e) {	
	}
	public void windowClosing(WindowEvent e) {
		
		if(mensajeError.isActive()) {
			btnVolver.removeActionListener(this);
			mensajeError.removeWindowListener(this);
			mensajeError.setVisible(false);
		}
		else {
			System.exit(0);
		}
	}
	public void windowClosed(WindowEvent e) {	
	}
	public void windowIconified(WindowEvent e) {	
	}
	public void windowDeiconified(WindowEvent e) {	
	}
	public void windowActivated(WindowEvent e) {	
	}
	public void windowDeactivated(WindowEvent e) {	
	}
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnCancelar))
		{
			nombreUsuario.setText("");
			claveUsuario.setText("");
			nombreUsuario.requestFocus();
		}
		else if(evento.getSource().equals(btnAceptar))
		{
			// Conectar a la BD
			Datos datos = new Datos();
			if(datos.conectar()==true)
			{
			
				// Coger el nombre de usuario
				String nombre = nombreUsuario.getText();
				// Coger la clave
				String clave = claveUsuario.getText();
				// Lanzar un SELECT
				String sentencia = "SELECT * FROM usuarios WHERE nombreUsuario = '"+nombre+"' AND claveUsuario = SHA2('"+clave+"', 256);";
				if(datos.consultarCredenciales(sentencia)==true)
				{
				//	int tipo = datos.dameTipoUsuario();
					System.out.println("Credenciales Correctas");
					// Credenciales correctas
					new MenuPrincipal();  //new MenuPrincipal(tipo); NO SOY CAPAZ DE PONER EL USUARIO!!!
					login.setVisible(false); //Desaparece si acertamos las credenciales
				}
				else
				{
					mensajeError.setLayout(new FlowLayout());
					mensajeError.setSize(230,120);
					mensajeError.setResizable(false);
					mensajeError.setLocationRelativeTo(null);
					mensajeError.add(lblMensaje);
					btnVolver.addActionListener(this);
					mensajeError.addWindowListener(this);
					mensajeError.add(btnVolver);
					mensajeError.setVisible(true);
							
				}
			}
		}
		else if(evento.getSource().equals(btnVolver)) {
			btnVolver.removeActionListener(this);
			mensajeError.removeWindowListener(this);
			mensajeError.setVisible(false);
		}
	}
}