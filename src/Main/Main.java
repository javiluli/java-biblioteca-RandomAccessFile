/**
 *
 * @author Javier Delgado Rodriguez
 */
package Main;

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
				Menu.devoluciones();
				break;

			case 6:
				RAFLibros.consultaLibro(t.leerInt());
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
				break;
			}
		} while (n != 0);
	}
}
