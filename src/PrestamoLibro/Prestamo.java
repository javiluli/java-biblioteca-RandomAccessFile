/**
 *
 * @author Javier Delgado Rodriguez
 */
package PrestamoLibro;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import MetLibro.Libro;
import MetUsuario.Usuario;
import Recursos.Archivos;
import Recursos.Const;
import Recursos.CrearArrayDe;

public class Prestamo {

	static RandomAccessFile f;

	/**
	 * Vefificar prestamo.
	 *
	 * @param codigo the codigo
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean vefificarPrestamo(int codigo) throws IOException {
		f = new RandomAccessFile(Const.FLIBROS, "rw");
		Archivos.irARegistro(f, codigo, Libro.getLongitudRegistroLibro());
		Libro l = new Libro();
		l.leer(f);
		f.close();
		return l.prestado;
	}

	/**
	 * Recuento prestamo usuario. Permite saber cuentos espacios libres tiene el
	 * usuario para tomar libros perstados. Si el contados es 0 significara que no
	 * tiene mas espacio para tomar libros prestados, por lo que devolvera FALSE
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean recuentoPrestamoUsuario(int id) throws IOException {
		boolean b = true;
		int n = 0;
		f = new RandomAccessFile(Const.FPRESTAMOS, "r");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);
		Libro l = new Libro();

		for (int i = 0; i < Const.MAXLIBROSPRES; i++) {
			l.leer(f);
			if (l.codigo == -1) {
				n++;
			}
		}
		if (n == 0)
			b = false;

		f.close();
		return b;
	}

	/**
	 * Primer libro prestado nulo.
	 *
	 * @param id the id
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int primerLibroPrestadoNulo(int id) throws IOException {
		int n = 0;
		f = new RandomAccessFile(Const.FPRESTAMOS, "r");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);

		Libro l = new Libro();
		for (int i = 0; i < Const.MAXLIBROSPRES; i++) {
			l.leer(f);
			if (!l.prestado) {
				f.close();
				return n;
			}
			n++;
		}
		f.close();
		return n;
	}

	/**
	 * Prestar libro. Accede al fichero de FLIBROS y en su atributo de Prestado lo
	 * coloca a TRUE
	 *
	 * @param codigo the codigo
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void prestarLibro(int codigo) throws IOException {
		f = new RandomAccessFile(Const.FLIBROS, "rw");
		Archivos.irARegistro(f, codigo, Libro.getLongitudRegistroLibro());
		Libro l = new Libro();
		l.leer(f);
		Archivos.irARegistro(f, codigo, Libro.getLongitudRegistroLibro());
		l.prestado = true;
		l.escribir(f);
		f.close();
	}

	/**
	 * Mostrar prestamos nulos.
	 *
	 * @throws Exception the exception
	 */
	public static void mostrarPrestamosNulos() throws Exception {

		for (int i = 0; i < Const.MAXUSUARIOS; i++) {
			if (new File(Const.FPRESTAMOS).isFile()) {
				f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
				Archivos.irARegistro(f, i, Usuario.getlongitugRegistroUsuarioPrestamos());
				Usuario u = new Usuario();

				u.leer(f);
				u.mostrarUsuario();
				Libro l = new Libro();

				for (int j = 0; j < Const.MAXLIBROSPRES; j++) {
					l.leer(f);
					l.mostrarLibro();
				}
				f.close();
			} else
				System.out.println("El Fichero no existe - ERROR EN: contarResgistros");
		}
	}

	/**
	 * Leer registro. Permite leer un registro directo
	 *
	 * @param id the id
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void leerRegistro(int id) throws IOException {

		if (new File(Const.FPRESTAMOS).isFile()) {
			f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
			Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
			Usuario u = new Usuario();

			u.leer(f);
			Libro l = new Libro();

			for (int i = 0; i < Const.MAXLIBROSPRES; i++) {
				l.leer(f);
				l.mostrarLibro();
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: contarResgistros");
	}

	/**
	 * Almacenar usuarios Y prestamos.
	 *
	 * @param id     the id
	 * @param codigo the codigo
	 * @throws Exception the exception
	 */
	public static void almacenarUsuariosYPrestamos(int id, int codigo) throws Exception {
		if (new File(Const.FPRESTAMOS).isFile()) {
			int n = Prestamo.primerLibroPrestadoNulo(id);
			f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
			Usuario[] vUsuarios = CrearArrayDe.crearArrayUsuarios();
			Libro[] vLibros = CrearArrayDe.crearArrayLibros();

			// muevo el puntero hasta el usaurio deseado y asigno en el fichero dicho
			// usuario
			Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
			Usuario u = new Usuario();
			u.setId(id);
			u.setNombre(vUsuarios[id].getNombre());
			u.escribir(f);

			// almaceno en FP la posicion actial del puntero para seguir en el usuario pero
			// solo para accder de forma directa a los libros prestados
			Libro l = new Libro();
			int fp = (int) f.getFilePointer();

			// fp |> posicion del puntero en el fichero justo despues de leer el usuario
			// seleccionado
			// n |> posicion del primer espacio nulo en sus libros prestados
			// Libro.getLongitudRegistroLibro() |> longitus en bytes de un Object Libro
			f.seek(fp + (n * Libro.getLongitudRegistroLibro()));

			l.codigo = codigo;
			l.titulo = vLibros[codigo].titulo;
			l.prestado = true;
			l.escribir(f);

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: contarResgistros");
	}

}
