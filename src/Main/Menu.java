/**
 *
 * @author Javier Delgado Rodriguez
 */
package Main;

import java.io.IOException;

import Devoluciones.DevolverLibro;
import MetLibro.RAFLibros;
import MetUsuario.RAFUsuarios;
import PrestamoLibro.LibroAsignadoA;
import PrestamoLibro.Prestamo;
import Recursos.Archivos;
import Recursos.Backup;
import Recursos.Const;
import Recursos.Teclado;

/**
 * The Class Menu.
 */
public class Menu {
	static Teclado t = new Teclado();

	/**
	 * Opciones menu.
	 */
	public static void opcionesMenu() {
		System.out.println("====================== MENU ======================");
		System.out.println("__________________________________________________");
		System.out.println("| 1.- Dar de alta un libro");
		System.out.println("| 2.- Alta de usuarios");
		System.out.println("| 3.- Baja de usuarios");
		System.out.println("| 4.- Préstamo de libros");
		System.out.println("| 5.- Devolución de libro");
		System.out.println("| 6.- Consulta de un libro");
		System.out.println("| 7.- Listado de usuarios");
		System.out.println("| 8.- Listado de libros no prestados");
		System.out.println("| 9.- Listado de todos los libros almacenados");
		System.out.println("| 10.- Listado de los usuarios y sus libros prestados");
		System.out.println("| 11.- Listado completo de los prestamos");
		System.out.println("| 0.- Salir de la aplicacion");
		System.out.println("==================================================");
	}

	/**
	 * Guardar libro.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void guardarLibro() throws IOException {
		if (RAFLibros.contarResgistrosLibros() < Const.MAXLIBROS) {
			System.out.println("Introducir titulo del libro: ");
			RAFLibros.altaLibro(t.leerString().trim().toUpperCase());
			Backup.backupLibro(Const.FLIBROS, Const.FLIBROSBACKUP);
		} else
			System.out.println("Capacidad maxima de libros alcanzada");
	}

	/**
	 * Guardar usuario.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void guardarUsuario() throws IOException {
		if (Archivos.contarUsuriosNulos() < Const.MAXUSUARIOS) {
			System.out.println("Introducir nombre del usuario: ");
			RAFUsuarios.altaUsuario(t.leerString().trim().toUpperCase());
			Backup.backupUsuario(Const.FUSUARIOS, Const.FUSUARIOSBACKUP);
		} else
			System.out.println("Capacidad maxima de usuarios alcanzada");
	}

	/**
	 * Borrar usuario.
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void borrarUsuario() throws IOException {
		System.out.println("Introducir ID del usuario para dar de baja: ");
		int n = t.leerInt();
		RAFUsuarios.bajaUsuario(n);
	}

	/**
	 * Prestar libro A usuario.
	 *
	 * @throws Exception the exception
	 */
	public static void prestarLibroAUsuario() throws Exception {
		Teclado t = new Teclado();
		int id, codigo;
		System.out.println("Codigo del libro");
		codigo = t.leerInt();
		boolean vp = Prestamo.vefificarPrestamo(codigo);

		if (!vp) {

			System.out.println("ID del usuario");
			id = t.leerInt();

			if (RAFUsuarios.localizarUsuario(id)) {

				boolean rp = Prestamo.recuentoPrestamoUsuario(id);

				if (rp) {
					Prestamo.prestarLibro(codigo);
					Prestamo.almacenarUsuariosYPrestamos(id, codigo);
					LibroAsignadoA.guardarAsignacion(id, codigo);
				} else
					System.out.println("El usuario ha excedido el maximo de libros prestados");

			} else
				System.out.println("El usuario no existe");

		} else
			System.out.println("El libro ya se encuentra en prestamo");

	}

	/**
	 * Devoluciones.
	 *
	 * @throws Exception the exception
	 */
	public static void devoluciones() throws Exception {
		int id, codigo;
		System.out.println("Codigo del libro a devolver");
		codigo = t.leerInt();

		if (DevolverLibro.comprobarPrestamo(codigo)) {
			System.out.println("Usuario que contiene el libro a devolver");
			id = t.leerInt();
			if (DevolverLibro.comprobarUsuarioPrestado(id, codigo))
				DevolverLibro.devolucion(id, codigo);
			else
				System.out.println("El usuario no esta en posesion del libro a devolver");
		} else
			System.out.println("El libro no se encuentra en prestamo");

	}

	/**
	 * Consulta libro.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void consultaLibro() throws IOException {
		System.out.println("Indique el codigo del libro apra hacer la cinsulta");
		int n = t.leerInt();
		RAFLibros.consultaLibro(n);

	}
}
