import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Archivos.CrearFichero();

		String titulo = "metro 2033";
		MetodosRAF.insertarAlFinalFichero(titulo);
		
		MetodosRAF.mostrarFichero();

	}

}
