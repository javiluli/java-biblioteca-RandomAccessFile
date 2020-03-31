/**
 *
 * @author Javier Delgado Rodriguez
 */
package PrestamoLibro;

import java.io.IOException;
import java.io.RandomAccessFile;

import MetLibro.Libro;
import MetUsuario.Usuario;
import Recursos.Archivos;
import Recursos.Const;
import Recursos.CrearArrayDe;

// TODO: Auto-generated Javadoc
/**
 * The Class LibroAsignadoA.
 */
public class LibroAsignadoA {

	/** The f. */
	static RandomAccessFile f;

	/**
	 * Guardar asignacion. Permite guardar una asignacion direta de Libro -> Usuario
	 * de forma directa basando la posicion en el codigo del Libro.
	 *
	 * @param id     the id
	 * @param codigo the codigo
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void guardarAsignacion(int id, int codigo) throws IOException {

		f = new RandomAccessFile(Const.FLIBROASIGNADOA, "rw");
		// posicion en Bytes del guardado de la asignacion Libro -> Usuario
		Archivos.irARegistro(f, codigo, Const.N);

		Libro[] vLibros = CrearArrayDe.crearArrayLibros();
		Libro l = new Libro(codigo, vLibros[codigo].titulo, true);
		l.escribir(f);

		Usuario[] vUsuarios = CrearArrayDe.crearArrayUsuarios();
		Usuario u = new Usuario(vUsuarios[id].getNombre(), id);
		u.escribir(f);

		f.close();

	}

	/**
	 * Mostrar asignaciones. Permite mostrar el fichero con todas las asignaciones,
	 * mostrando Libro -> Usuario, siempre que hayan datos que leer dentro del
	 * archivo
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void mostrarAsignaciones() throws IOException {

		f = new RandomAccessFile(Const.FLIBROASIGNADOA, "rw");

		Libro l = new Libro();
		Usuario u = new Usuario();
		boolean hayDatos = l.leer(f);

		while (hayDatos) {
			if (l.codigo != -1)
				l.mostrarLibro();
			u.leer(f);
			if (u.getId() != -1)
				u.mostrarUsuario();

			hayDatos = l.leer(f);
		}

		f.close();

	}
}
