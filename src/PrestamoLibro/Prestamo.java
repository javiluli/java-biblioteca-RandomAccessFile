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
	 * Vefificar prestamo. Comprueba si el libro solicitado se encuentra en
	 * prestamos o no.
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
	 * tiene mas espacio para tomar libros prestados, por lo que devolvera FALSE.
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean recuentoPrestamoUsuario(int id) throws IOException {
		boolean b = false;
		f = new RandomAccessFile(Const.FPRESTAMOS, "r");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);
		Libro l = new Libro();

		for (int i = 0; i < Const.MAXLIBROSPRES && !b; i++) {
			l.leer(f);
			if (l.codigo == -1)
				b = true;
		}

		f.close();
		return b;
	}

	/**
	 * Primer libro prestado nulo. Obteniendo un suaurio, permite obtener la
	 * posicion del primer Libro nulo o, dicho de otro modo, el primer espacio vacio
	 * de los libros prestados del Usuario. Esto permite saber en que posicion de
	 * las 5 disponibles se debe asignar cada Libro por Usuario.
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
	 * coloca a TRUE.
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
	 * Leer registro. Permite leer un registro directo.
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
			u.mostrarUsuario();
			Libro l = new Libro();

			for (int i = 0; i < Const.MAXLIBROSPRES; i++) {
				l.leer(f);
				if (l.codigo != -1)
					l.mostrarLibro();
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: leerRegistro");
	}

	/**
	 * Almacenar usuarios Y prestamos. Permite almacenar un usuario y sus libros
	 * prestados en el fichero, parte del codigo se explica dentro del metodo.
	 *
	 * @param id     the id
	 * @param codigo the codigo
	 * @throws Exception the exception
	 */
	public static void almacenarUsuariosYPrestamos(int id, int codigo) throws Exception {
		if (new File(Const.FPRESTAMOS).isFile()) {

			// n |> posicion del primer espacio nulo en sus libros prestados
			// Libro.getLongitudRegistroLibro() |> longitus en bytes de un Object Libro
			int n = Prestamo.primerLibroPrestadoNulo(id);
			f = new RandomAccessFile(Const.FPRESTAMOS, "rw");

			// muevo el puntero hasta el usaurio deseado y asigno en el fichero dicho
			// usuario
			Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
			// obtengo el usuario seleccionado mediante un Array de Usuarios (vUsuarios)
			Usuario[] vUsuarios = CrearArrayDe.crearArrayUsuarios();
			Usuario u = new Usuario(vUsuarios[id].getNombre(), id);
			u.escribir(f);

			// almaceno en FP (Posicion del Puntero / getFilePointer()), la posicion actial
			// del puntero para seguir en el usuario para accder de forma directa a los
			// libros prestados de dicho usuario
			Libro[] vLibros = CrearArrayDe.crearArrayLibros();
			int fp = (int) f.getFilePointer();

			// muevo el puntero hasta el usuario deseado mas el primer Libro nulo.
			f.seek(fp + (n * Libro.getLongitudRegistroLibro()));
			Libro l = new Libro(codigo, vLibros[codigo].titulo, true);
			l.escribir(f);

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: contarResgistros");
	}

	/**
	 * Eliminar resgistro. Accede al fichero FPRESTAMOS y si el usuario no tiene
	 * nigun libro en prestamos, dicho usuario elimina al usuario del fichero.
	 *
	 * @param id the id
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void eliminarResgistro(int id) throws IOException {
		f = new RandomAccessFile(Const.FPRESTAMOS, "rw");

		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario("", -1);
		u.escribir(f);

		f.close();

	}

	/**
	 * Mostrar prestamos. Muestra el Usuario y los libros que tiene en prestamo.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void mostrarPrestamos() throws IOException {
		if (new File(Const.FPRESTAMOS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FPRESTAMOS, "r");
			Usuario u = new Usuario();
			boolean hayDatos = u.leer(f);

			while (hayDatos) {

				if (u.getId() != -1)
					u.mostrarUsuario();

				Libro l = new Libro();
				for (int i = 0; i < Const.MAXLIBROSPRES; i++) {
					l.leer(f);
					if (l.codigo != -1) {
						l.mostrarLibro();
					}
				}

				hayDatos = u.leer(f);
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");
	}
}
