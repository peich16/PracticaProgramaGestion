package es.studium.programaGestion;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import es.studium.programaGestion.MenuPrincipal;

public class MenuPrincipal implements WindowListener, ActionListener
{
	
	
	Frame ventana = new Frame("Películas");
	MenuBar barraMenu = new MenuBar();
	
	int tipo;
	MenuPrincipal(int t)
	
	{
		this.tipo = t;
	}	
		
	
	
	Menu mnuPaises = new Menu("Paises");
	Menu mnuProductoras = new Menu("Productoras");
	Menu mnuPeliculas = new Menu("Películas");
	
	MenuItem mniPaisAlta = new MenuItem("Alta");
	MenuItem mniPaisConsulta = new MenuItem("Consulta");
	MenuItem mniPaisModificacion = new MenuItem("Modificación");
	MenuItem mniPaisBaja = new MenuItem("Baja");
	
	
	MenuItem mniProductorasAlta = new MenuItem("Alta");
	MenuItem mniProductorasConsulta = new MenuItem("Consulta");
	MenuItem mniProductorasModificacion = new MenuItem("Modificación");
	MenuItem mniProductorasBaja = new MenuItem("Baja");
	
	MenuItem mniPeliculasAlta = new MenuItem("Alta");
	MenuItem mniPeliculasConsulta = new MenuItem("Consulta");
	MenuItem mniPeliculasModificacion = new MenuItem("Modificación");
	MenuItem mniPeliculasBaja = new MenuItem("Baja");
	
	{
	
//	if(tipo == 1)
//	{
//		Paises.add(alta)
//		add(consulta)
//		add(baja)
//		add(modificacion)}
		
	} 
	//	else 
		{ // Si el usuario es básico
	//	Paises.add(alta); 
		}
	

	
//	}
	Dialog dlgMensaje = new Dialog(ventana, "Diálogo con mensaje", true);
	Label lblMensaje = new Label("        ");

	public MenuPrincipal() {
	
		ventana.setLayout(new FlowLayout());

		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);

		mniPaisAlta.addActionListener(this);
		mnuPaises.add(mniPaisAlta);
		mniPaisConsulta.addActionListener(this);
		mnuPaises.add(mniPaisConsulta);
		mniPaisModificacion.addActionListener(this);
		mnuPaises.add(mniPaisModificacion);
		mniPaisBaja.addActionListener(this);
		mnuPaises.add(mniPaisBaja);

		mniProductorasAlta.addActionListener(this);
		mnuProductoras.add(mniProductorasAlta);
		mniProductorasConsulta.addActionListener(this);
		mnuProductoras.add(mniProductorasConsulta);
		mniProductorasModificacion.addActionListener(this);
		mnuProductoras.add(mniProductorasModificacion);
		mniProductorasBaja.addActionListener(this);
		mnuProductoras.add(mniProductorasBaja);
		
		mniPeliculasAlta.addActionListener(this);
		mnuPeliculas.add(mniPeliculasAlta);
		mniPeliculasConsulta.addActionListener(this);
		mnuPeliculas.add(mniPeliculasConsulta);
		mniPeliculasModificacion.addActionListener(this);
		mnuPeliculas.add(mniPeliculasModificacion);
		mniPeliculasBaja.addActionListener(this);
		mnuPeliculas.add(mniPeliculasBaja);

		barraMenu.add(mnuPaises);
		barraMenu.add(mnuProductoras);
		barraMenu.add(mnuPeliculas);

		ventana.setMenuBar(barraMenu);

		ventana.setSize(380,210);
		ventana.setBackground(Color.orange);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}

	public void windowOpened(WindowEvent e) {	
	}
	public void windowClosing(WindowEvent e) {
		if(dlgMensaje.isActive())
		{
		dlgMensaje.setVisible(false);
		}
		else
		{
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
	public void actionPerformed(ActionEvent evento) {
		// Que opción se ha elegido
		if(evento.getSource().equals(mniPaisAlta))
		{
		// Cambiamos el contenido del Label
		new Alta();
		}
		else if(evento.getSource().equals(mniPaisConsulta))
		{
		// Cambiamos el contenido del Label
		new Consulta();
		}		
		else if(evento.getSource().equals(mniPaisModificacion))
		{
		// Cambiamos el contenido del Label
		new Modificacion();
		}	
		else if(evento.getSource().equals(mniPaisBaja))
		{
		// Cambiamos el contenido del Label
		new Baja();
		}		
	}
}