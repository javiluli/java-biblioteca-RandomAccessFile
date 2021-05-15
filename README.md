# Gestor de bibliotecas

## Uso de los metodos `RandomAccessFile`.

Aplicacion que permite gestionar, de forma sinplificada, una biblioteca, tareas como el prestamo de los libros, el alta o eliminacion de usuarios y la la propia devolucion de libros.

## Anotaciones que **si** se tienen en cuenta en el proyecto:

- Se pondra el caso de que en la biblioteca se tendra un maximo de 100 libros y 15 usuarios, aunque estas cifras deberan ser variables si se necesitase aumentar dicahs cantidades (tipos Int como referencia).
- Cada usuario tendra como maximo un total de 5 libros prestados, y en caso de tener ya 5 libros, no se le prestaran mas libros hasta que devuelva el resto.
- Se pone el caso de que solo se almacenara un libro de cada ejemplar.
- De cada usuario se guardara el nombre, el DNI, un ID propio de la biblioteca, siendo este ID mas relevante a la hora de buscar usuarios, y los libros prestados.
- De cada libro se guardara el titulo, un ID, al igual que el de los usuarios, y un valor que indique si esta prestado o no.

## Anotaciones que **no** se tienen en cuenta en el proyecto:

- La fecha de obtencion del libros y feca de devolucion del libro
- Tiempo restante para devover el libro
- Penalizaciones por devoluciones restrasadas o por libros en mal estado
- Datos del ususrio como los apellidos, fecha de nacimiento o tener carnet de bibliote (se entiende que solo los que tienen carnet pueden obetener libros prestados de la biblioteca)
- Datos de los libros como el autor, la editorial, el ISBN, o la cantidad de un mismo libro (se entiende que solo habra un libro de cada ejemplar)
