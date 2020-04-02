/**
 *
 * @author Javier Delgado Rodriguez
 */
package Devoluciones;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import MetLibro.Libro;
import MetUsuario.Usuario;
import Recursos.Archivos;
import Recursos.Const;

/**
 * The Class DevolverLibro.
 */
public class DevolverLibro {

	/** The f. */
	static RandomAccessFile f;

	/**
	 * Comprobar prestamo. Permite comprobar si el libro seleccionado se encuentra
	 * en prestamo, en caso de no estarlo devovlera False por defecto, en caso
	 * contrario devovlera True
	 *
	 * @param codigo the codigo
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean comprobarPrestamo(int codigo) throws IOException {
		boolean b = false;
		if (new File(Const.FLIBROASIGNADOA).isFile()) {
			f = new RandomAccessFile(Const.FLIBROASIGNADOA, "r");
			Archivos.irARegistro(f, codigo, Const.N);

			Libro l = new Libro();
			l.leer(f);
			if (l.prestado)
				b = true;

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");

		return b;
	}

	/**
	 * Comprobar usuario prestado. Permite comprobar si el usuario seleccinado tiene
	 * en propiedad el libro solicitado a devolver
	 *
	 * @param id     the id del usuario
	 * @param codigo the codigo del libro
	 * @return true, Devolvera True siempre que el usuario seleccionado tenga el
	 *         libro seleccionado
	 * @throws Exception the exception
	 */
	public static boolean comprobarUsuarioPrestado(int id, int codigo) throws Exception {
		f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);
		Libro l = new Libro();
		boolean b = false;
		for (int i = 0; i < Const.MAXLIBROSPRES && !b; i++) {
			l.leer(f);
			if (l.codigo == codigo)
				b = true;
		}

		f.close();
		return b;
	}

	/**
	 * Localizar posicion prestamo. Permite localizar la posicion de entre los 5
	 * libros que el usuario puede tener prestado. 0 sera el primer libro y 4 el
	 * ultimo libro de los que puede tener prestado.
	 *
	 * @param id     the id
	 * @param codigo the codigo
	 * @return the int
	 * @throws Exception the exception
	 */
	public static int localizarPosicionPrestamo(int id, int codigo) throws Exception {
		f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);
		Libro l = new Libro();
		boolean b = false;
		int n = 0;
		for (int i = 0; i < Const.MAXLIBROSPRES && !b; i++) {
			l.leer(f);
			if (l.codigo == codigo)
				b = true;
			n = i;
		}

		f.close();
		return n;
	}

	/**
	 * Devolucion. Permite hacer una devolucion del libro seleccionado haciendo de
	 * forma simultanea que el libro vuelva a poder ser prestado, y el usuario tener
	 * una espacio mas para la obtemcion de otro libro prestado
	 *
	 * @param id     the id
	 * @param codigo the codigo
	 * @throws Exception the exception
	 */
	public static void devolucion(int id, int codigo) throws Exception {
		int n = localizarPosicionPrestamo(id, codigo);
		System.out.println(n);
		// ###########################################################################
		// Cambios en el Fichero de FPRESTAMOS (Ver Clase Const)
		f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);

		// almaceno en FP la posicion actial del puntero para seguir en el usuario pero
		// solo para accder de forma directa a los libros prestados.

		// fp |> posicion del puntero en el fichero justo despues de leer el usuario
		// seleccionado
		int fp = (int) f.getFilePointer();

		// n |> posicion del libro el cual se quiere devolver por parte del usuario
		f.seek(fp + (n * Libro.getLongitudRegistroLibro()));
		Libro l = new Libro();
		l = new Libro(-1, "", false);
		l.escribir(f);

		// ###########################################################################
		// Cambios en el fichero de FLIBROS (Ver Clase Const)
		// Escribe en dicho fichero el libro pero con el atributo de Prestado como False
		f = new RandomAccessFile(Const.FLIBROS, "rw");
		l = new Libro();
		Archivos.retrocederRegistroLibro(f, codigo, Libro.getLongitudRegistroLibro(), l);
		l.prestado = false;
		l.escribir(f);

		// ###########################################################################
		// Cambios en el fichero de FLIBROASIGNADOA (Ver Clase Const)
		// Escribe en dicho fichero un libro y un suaurio Nulo con valores nulos
		f = new RandomAccessFile(Const.FLIBROASIGNADOA, "rw");
		Archivos.irARegistro(f, codigo, Const.N);

		// finalmente se crea y escriben usuario y libros nulos en el fichero
		l = new Libro(-1, "", false);
		l.escribir(f);

		u = new Usuario("", -1);
		u.escribir(f);

		f.close();

	}
}
