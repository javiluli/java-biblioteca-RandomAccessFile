package Main;

import MetLibro.RAFLibros;
import MetUsuario.RAFUsuarios;
import Recursos.Archivos;
import Recursos.Backup;
import Recursos.Const;
import Recursos.Teclado;

/**
 *
 * @author Javier Delgado Rodriguez
 */

/**
 * The Class Menu.
 */
public class Menu {

	/**
	 * Opciones menu.
	 */
	public static void opcionesMenu() {
		System.out.println("====================== MENU ======================");
		System.out.println("__________________________________________________");
		System.out.println("| 1.- Dar de alta un libro");
		System.out.println("| 2.- Alta de usuarios");
		System.out.println("| 3.- Baja de usuarios");
//		System.out.println("| 4.- Préstamo de libros");
//		System.out.println("| 5.- Devolución de libro");
//		System.out.println("| 6.- Consulta de un libro");
		System.out.println("| 7.- Listado de usuarios");
		System.out.println("| 8.- Listado de libros no prestados");
		System.out.println("| 9.- Listado de todos los libros almacenados");
		System.out.println("| 0.- Salir de la aplicacion");
		System.out.println("==================================================");
	}

	/**
	 * Menu.
	 *
	 * @throws Exception the exception
	 */
	public void menu() throws Exception {
		Teclado t = new Teclado();
		int n;
		Archivos.CrearFichero();
		System.out.println();

		// =============================================
		do {
			opcionesMenu();
			n = t.leerInt();
			switch (n) {
			case 1:
				if (RAFLibros.contarResgistrosLibros() < Const.MAXLIBROS) {
					System.out.println("Introducir titulo del libro: ");
					RAFLibros.altaLibro(t.leerString().trim().toUpperCase());
					Backup.backupLibro(Const.FLIBROS, Const.FLIBROSBACKUP);
				} else
					System.out.println("Capacidad maxima de libros alcanzada");
				break;

			case 2:
				if (Archivos.contarUsuriosNulos() < Const.MAXUSUARIOS) {
					System.out.println("Introducir nombre del usuario: ");
					RAFUsuarios.altaUsuario(t.leerString().trim().toUpperCase());
					Backup.backupUsuario(Const.FUSUARIOS, Const.FUSUARIOSBACKUP);
				} else
					System.out.println("Capacidad maxima de usuarios alcanzada");
				break;

			case 3:
				System.out.println("Introducir ID del usuario para dar de baja: ");
				RAFUsuarios.bajaUsuario(t.leerInt());
				break;

			case 4:
//				System.out.println("Codigo del libro");
//				codigo = t.leerInt();
//				boolean vp = PrestamoLibro.verificarPrestamo(codigo);
//				System.out.println(vp);
//				if (!vp) {
//					System.out.println("ID del usuario");
//					id = t.leerInt();
//					boolean rp = pl.recuentoPrestamo(id);
//
//					if (!vp && rp) {
//						pl.prestarLibro(id, codigo);
//					}
//				}
				break;

			case 5:
//				System.out.println("Codigo del libro a buscar");
//				codigo = t.leerInt();
//				vp = PrestamoLibro.verificarPrestamo(codigo);
//				System.out.println(vp);

//				if (vp) {
//					System.out.println("ID del usuario");
//					id = t.leerInt();
//					DevolverLibro.devolucionLibro(codigo, id);
//				} else {
//					System.out.println("Libro no prestado");
//				}
				break;

			case 6:
//				System.out.println("Codigo del libro a buscar");
//				codigo = t.leerInt();
//				vp = PrestamoLibro.verificarPrestamo(codigo);
//				if (vp)
//					Book.solicitarLibro(codigo);
//				else {
//					System.out.println("El libro no se encuntra en prestamo");
//				}
				break;

			case 7:
				RAFUsuarios.mostrarFicheroUsuarios();
				break;

			case 8:
				RAFLibros.verLibrosNoPrestados();
				break;

			case 9:
				RAFLibros.mostrarFicheroLibros();
				break;

			case 0:
//			System.out.println("Salir de la aplicacion");
				break;
			}
		} while (n != 0);
	}
}
