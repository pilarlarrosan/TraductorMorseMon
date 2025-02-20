import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/****************************************************************************************************************************************
 *   APLICACIÓN: "TraductorMorse"                                                                                                       *
 * ************************************************************************************************************************************ *
 *   PROGRAMACIÓN DE SERVICIOS Y PROCESOS 2DAM  -  IntelliJ IDEA 2024.3.1.1 (Ultimate Edition)                                          *
 * ************************************************************************************************************************************ *
 *   @author Pilar Larrosa Novás                                                                                                        *
 *   @version 3.0 - Sustitución de los semáforos por monitores.                                                                         *
 *            2.0 - Corrección de errores. Ahora se utilizan dos semáforos.                                                             *
 *            1.0 - Versión inicial.                                                                                                    *
 *   @since 25ENE2025                                                                                                                   *
 *          23ENE2025                                                                                                                   *
 *          09ENE2025                                                                                                                   *
 * ************************************************************************************************************************************ *
 *   COMENTARIOS:                                                                                                                       *
 *          - Ejemplo de uso de synchronized para sincronizar dos procesos (productor y consumidor).                                    *
 *          - La aplicación reproduce el funcionamiento de un traductor a código morse de un texto.                                     *
 *          - Se emplean monitores.                                                                                                     *
 *          - También es posible reproducir el audio de las palabras morse.                                                             *
 ****************************************************************************************************************************************/
public class TraductorMorse {

    // Constantes de clase.
    public static final boolean REPRODUCIR_AUDIO = true;
    public static final String TEXTO = "En un lugar de La Mancha en 1605";
    public static final int NUMERO_HILOS = 2;

    // Objeto para intercambiar información entre las clases.
    volatile Buzon a_Buzon = new Buzon();

    public static void main(String[] args) {

        TraductorMorse l_Aplicacion = new TraductorMorse();                                               // Objeto de clase aplicación.
        ThreadPoolExecutor l_Ejecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMERO_HILOS);  // Pool de tareas concurrentes.
        Productor l_Productor = null;                                                                     // Tarea del proceso productor.
        Consumidor l_Consumidor = null;                                                                   // Tarea del proceso consumidor.

        // Creación las tareas.
        l_Productor = new Productor(l_Aplicacion.a_Buzon);
        l_Consumidor = new Consumidor(l_Aplicacion.a_Buzon);

        // Ejecución de las tareas.
        l_Ejecutor.execute(l_Productor);
        l_Ejecutor.execute(l_Consumidor);

        // Finalización del ejecutor.
        l_Ejecutor.shutdown();

    }   // main()

}   // TraductorMorse