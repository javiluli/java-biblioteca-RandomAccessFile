/**
 *
 * @author Javier Delgado Rodriguez
 */
package MetLibro;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import MetUsuario.Usuario;
import Recursos.Archivos;
import Recursos.Const;

/**
 * The Class RAFLibros.
 */
public class RAFLibros {

	/** The f. */
	static RandomAccessFile f;

	/**
	 * Insertar al final fichero.
	 *
	 * @param s the s
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void insertarAlFinalFichero(String s) throws IOException {
		f = new RandomAccessFile(Const.FLIBROS, "rw");
		int n = contarResgistrosLibros();
		Libro l = new Libro((n), s.toUpperCase(), false);
		f.seek(Libro.getLongitudRegistroLibro() * n);
		l.escribir(f);
		f.close();
	}

	/**
	 * Alta libro.
	 *
	 * @param s the s
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void altaLibro(String s) throws IOException {
		try {
			Archivos.exceptionLongitudMaxima(s, Const.LONGITUDTITULO);
			RAFLibros.insertarAlFinalFichero(s);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Contar resgistros libros.
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int contarResgistrosLibros() throws IOException {
		int n = 0;
		// Comprobar antes si existe el fichero.
		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();

			boolean hayDatos = l.leer(f);
			while (hayDatos) {
				hayDatos = l.leer(f);
				n++;
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: contarResgistros");
		return n;
	}

	/**
	 * Ver libros no prestados.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void verLibrosNoPrestados() throws IOException {

		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();
			boolean hayDatos = l.leer(f);

			while (hayDatos) {
				if (!l.prestado)
					l.mostrarLibro();
				hayDatos = l.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: verLibrosNoPrestados");
	}

	/**
	 * Consulta libro.
	 *
	 * @param codigo the codigo
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void consultaLibro(int codigo) throws IOException {
		if (new File(Const.FLIBROASIGNADOA).isFile()) {
			f = new RandomAccessFile(Const.FLIBROASIGNADOA, "r");
			Archivos.irARegistro(f, codigo, Const.N);
			Libro l = new Libro();
			l.leer(f);
			if (l.codigo != -1) {
				l.mostrarLibro();
				Usuario u = new Usuario();
				u.leer(f);
				u.mostrarUsuario();
			} else
				System.out.println("La consulta no se ha podido efectuar");

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: consultaLibro");
	}

	/**
	 * Mostrar fichero libros.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void mostrarFicheroLibros() throws IOException {
		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();
			boolean hayDatos = l.leer(f);
			f.seek(Libro.getLongitudRegistroLibro());

			while (hayDatos) {
				l.mostrarLibro();
				hayDatos = l.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFicheroLibros");
	}
}
