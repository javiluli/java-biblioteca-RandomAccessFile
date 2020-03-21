/**
 *
 * @author Javier Delgado Rodriguez
 */
package Main;

import MetLibro.RAFLibros;
import MetUsuario.RAFUsuarios;
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
				Menu.guardarLibro(t.leerString());
				break;

			case 2:
				Menu.guardarUsuario(t.leerString());
				break;

			case 3:
				Menu.borrarUsuario(t.leerInt());
				break;

			case 4:
				Menu.prestarLibroAUsuario();
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
