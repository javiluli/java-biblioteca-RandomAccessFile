
/**
 *
 * @author Javier Delgado Rodriguez
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MetodosRAF {
	/** The f. */
	static RandomAccessFile f;

	/**
	 * Exception longitud titulo. Genera una excepcion cuando la longitud del ttulo
	 * sea mayo de la establecida en Const.LONGITUDTITULO
	 * 
	 * @param s the s
	 * @throws Exception the exception
	 */
	public static void exceptionLongitudTitulo(String s) throws Exception {
		if (s.length() >= Const.LONGITUDTITULO)
			throw new Exception("El titulo del libro excede la longitud permitida (45 caracteres)");
	}

	/**
	 * Permite ir de manera directa al libro deseado
	 *
	 * @param f      the f
	 * @param pos    the pos
	 * @param tamReg the tam reg
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static void irARegistro(RandomAccessFile f, int pos, int tamReg) throws IOException {
		f.seek(pos * tamReg);
	}

	/**
	 * Insertar al final fichero. Agrega un libro al final del fichero
	 *
	 * @param nombre the nombre
	 * @param notas  the notas
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static void insertarAlFinalFichero(String titulo) throws IOException {
		f = new RandomAccessFile(Const.FLIBROS, "rw");
		int n = contarResgistros();
		Libro l = new Libro((n), titulo.toUpperCase(), false);
		f.seek(l.getLongitudRegistroLibro() * n); // f.length();
		l.escribir(f);
		f.close();
	}

	/**
	 * Alta libro. Permite agregar un libro nuevo al fichero
	 *
	 * @param s the s
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static void altaLibro(String s) throws IOException {
		try {
			exceptionLongitudTitulo(s);
			MetodosRAF.insertarAlFinalFichero(s);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Contar resgistros. Cuenta la cantidad de libros almacenados en el fichero
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static int contarResgistros() throws IOException {
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
	 * Ver libros. Permite ver por consola todos los libros que no se encuentren en
	 * prestamo
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void verLibrosNoPrestados() throws IOException {

		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();
			boolean hayDatos = l.leer(f);
			f.seek(l.getLongitudRegistroLibro()); // f.length();

			while (hayDatos) {
				if (!l.prestado)
					l.mostrar();
				hayDatos = l.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: verLibrosNoPrestados");
	}

	/**
	 * Mostrar fichero. Permite ver todo el fichero
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static void mostrarFichero() throws IOException {
		// Comprobar antes si existe el fichero.
		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();
			boolean hayDatos = l.leer(f);
			f.seek(l.getLongitudRegistroLibro()); // f.length();

			while (hayDatos) {
				l.mostrar();
				hayDatos = l.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");
	}
}
