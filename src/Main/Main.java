/**
 *
 * @author Javier Delgado Rodriguez
 */
package Main;

import Devoluciones.DevolverLibro;
import MetLibro.RAFLibros;
import MetUsuario.RAFUsuarios;
import PrestamoLibro.LibroAsignadoA;
import PrestamoLibro.Prestamo;
import Recursos.Archivos;
import Recursos.Teclado;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		Teclado t = new Teclado();
		int n;
		Archivos.crearFichero();
		System.out.println();

		// =============================================
		do {
			Menu.opcionesMenu();
			n = t.leerInt();
			switch (n) {
			case 1:
				Menu.guardarLibro();
				break;

			case 2:
				Menu.guardarUsuario();
				break;

			case 3:
				Menu.borrarUsuario();
				break;

			case 4:
				Menu.prestarLibroAUsuario();
				break;

			case 5:
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

			case 10:
				Prestamo.mostrarPrestamos();
				break;

			case 11:
				LibroAsignadoA.mostrarAsignaciones();

			case 0:
//			System.out.println("Salir de la aplicacion");
				break;
			}
		} while (n != 0);
	}
}
