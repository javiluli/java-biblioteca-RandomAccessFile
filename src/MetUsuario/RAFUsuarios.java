/**
 *
 * @author Javier Delgado Rodriguez
 */
package MetUsuario;

/**
 *
 * @author Javier Delgado Rodriguez
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import MetLibro.Libro;
import PrestamoLibro.Prestamo;
import Recursos.Archivos;
import Recursos.Const;
import Recursos.CrearArrayDe;

public class RAFUsuarios {
	static RandomAccessFile f;

	/**
	 * Permite agregar un usuario nuevo al fichero.
	 *
	 * @param s Nombre del usuario
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see Archivos#retrocederRegistroUsurio
	 */
	public static void altaUsuario(String s) throws IOException {
		try {
			Archivos.exceptionLongitudMaxima(s, Const.LONGITUDNOMBRE);
			int n = CrearArrayDe.buscarUsuarioNulo();

			f = new RandomAccessFile(Const.FUSUARIOS, "rw");
			Usuario u = new Usuario();

			Archivos.retrocederRegistroUsurio(f, n, Usuario.getlongitugRegistroUsuario(), u);

			// si en dicha posicion el usuario tiene un ID -1 se permitira la escritura de
			// uno nuevo
			if (u.getId() == -1) {
				u = new Usuario(s, n);
				u.escribir(f);
			}
			f.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Cuenta la cantidad de usuaros almacenados en el fichero.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int contarResgistrosUsuarios() throws IOException {
		int n = 0;
		if (new File(Const.FUSUARIOS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FUSUARIOS, "r");
			Usuario u = new Usuario();
			boolean hayDatos = u.leer(f);
			while (hayDatos) {
				hayDatos = u.leer(f);
				n++;
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: contarResgistros");
		return n;
	}

	/**
	 * Mostrar informacion almacenada en el fichero.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void mostrarFicheroUsuarios() throws IOException {
		// Comprobar antes si existe el fichero.
		if (new File(Const.FUSUARIOS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FUSUARIOS, "r");
			Usuario u = new Usuario();
			boolean hayDatos = u.leer(f);

			while (hayDatos) {
				if (u.getId() != -1)
					u.mostrarUsuario();
				hayDatos = u.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");
	}

	/**
	 * Localizar usuario. Localiza al usuario por medio del ID, si este id es -1 se
	 * entiende como usuario nulo.
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean localizarUsuario(int id) throws IOException {
		boolean b = false;
		f = new RandomAccessFile(Const.FUSUARIOS, "r");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuario());
		Usuario u = new Usuario();
		u.leer(f);
		if (u.getId() != -1)
			b = true;
		return b;
	}

	/**
	 * Baja usuario.
	 *
	 * @param id the id
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void bajaUsuario(int id) throws IOException {
		f = new RandomAccessFile(Const.FUSUARIOS, "r");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuario());
		Usuario u = new Usuario();

		if (localizarUsuario(id)) {

			if (recuentoPrestados(id)) {
				f = new RandomAccessFile(Const.FUSUARIOS, "rw");
				System.out.println("--------------------------------------------------");
				System.out.println("EL USUARIO " + u.getNombre() + " CON EL ID " + u.getId() + " HA SIDO ELIMINADO");
				System.out.println("--------------------------------------------------");

				u = new Usuario("", -1);
				Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuario());
				u.escribir(f);
				Prestamo.eliminarResgistro(id);
			} else
				System.out.println("El usuario aun tiene libros que devolver, imposible realizar baja");

		} else
			System.out.println("El usuario no existe");
		f.close();
	}

	/**
	 * Recuento prestados.
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean recuentoPrestados(int id) throws IOException {
		boolean b = true;
		f = new RandomAccessFile(Const.FPRESTAMOS, "r");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);
		Libro l = new Libro();

		for (int i = 0; i < Const.MAXLIBROSPRES && b; i++) {
			l.leer(f);
			if (l.codigo != -1)
				b = false;
		}

		f.close();
		return b;
	}

}
