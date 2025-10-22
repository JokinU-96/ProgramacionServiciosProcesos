package Monitores;

public class Ej4 {
    public static void main(String[] args) {
        //Un deporte que se ha puesto muy de moda es el pádel. En casi todas las
        //ciudades hay una pista. Vamos a generar una aplicación que ayude a
        //controlar el alquiler de las mismas.

        //Diseña un sistema de gestión de una pista donde varios usuarios pueden
        //solicitar utilizarla al mismo tiempo. Utiliza monitores para asegurar que:

        //1. Solo un usuario pueda acceder y modificar la disponibilidad de la pista a la vez.
        //2. Después de cada uso, la pista debe ser limpiada por el servicio de limpieza(hilo).
        //3. Mientras se limpia la pista, los usuarios deben esperar.
        //4. Si no está disponible(bien porque está ocupada o bien porque está en proceso de limpieza), el usuario que intenta solicitarla debe
        //esperar hasta que esté disponible nuevamente.

        //Asegúrate de manejar correctamente la sincronización y la comunicación
        //entre los hilos, garantizando que los usuarios no se interrumpan entre sí y
        //que la información sobre la disponibilidad sea precisa.




    }
}
