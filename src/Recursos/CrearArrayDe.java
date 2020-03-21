/**
 *
 * @author Javier Delgado Rodriguez
 */
package Recursos;

/**
 *
 * @author Javier Delgado Rodriguez
 */
import java.io.IOException;
import java.io.RandomAccessFile;

import MetLibro.Libro;
import MetUsuario.Usuario;

// TODO: Auto-generated Javadoc
/**
 * The Class CrearArrayDe.
 */
public class CrearArrayDe {

	/** The f. */
	static RandomAccessFile f;

	/**
	 * Crear array usuarios.
	 *
	 * @return the usuario[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Usuario[] crearArrayUsuarios() throws IOException {
		f = new RandomAccessFile(Const.FUSUARIOS, "rw");
		Usuario[] usuarios = new Usuario[Const.MAXUSUARIOS];

		for (int i = 0; i < usuarios.length; i++) {
			Usuario u = new Usuario();
			usuarios[i] = u;
			u.leer(f);
		}

		f.close();

//		Usuario[] v = CrearArrayDe.crearArrayUsuarios();
//		for (Usuario usuario : v) {
//			System.out.println(usuario.toString());
//		}
		return usuarios;
	}

	/**
	 * Buscar usuario nulo.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int buscarUsuarioNulo() throws IOException {
		Usuario[] v = CrearArrayDe.crearArrayUsuarios();
		for (int i = 0; i < v.length; i++)
			if (v[i].getId() == -1)
				return i;

		return -1;
	}

	/**
	 * Crear array libros.
	 *
	 * @return the libro[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Libro[] crearArrayLibros() throws IOException {
		f = new RandomAccessFile(Const.FLIBROS, "rw");
		Libro[] libros = new Libro[Const.MAXLIBROS];

		for (int i = 0; i < libros.length; i++) {
			Libro l = new Libro();
			libros[i] = l;
			l.leer(f);
		}

		f.close();

//		Libro[] v2 = CrearArrayDe.crearArrayLibros();
//		for (Libro libro : v2) {
//			System.out.println(libro.toString());
//		}
		return libros;
	}

}
