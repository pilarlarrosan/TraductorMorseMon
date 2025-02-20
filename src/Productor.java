package com.iessanalberto.dam2.pln;

import java.util.concurrent.TimeUnit;

import static com.iessanalberto.dam2.pln.TraductorMorse.TEXTO;

/****************************************************************************************************************************************
 *   CLASE: "Productor"                                                                                                                 *
 * ************************************************************************************************************************************ *
 *   @author Pilar Larrosa Novás                                                                                                        *
 *   @version 2.0 - Sustitución de los semáforos por monitores.                                                                         *
 *            1.0 - Versión inicial de la clase.                                                                                        *
 *   @since 25ENE2025                                                                                                                   *
 *          23ENE2025                                                                                                                   *
 * ************************************************************************************************************************************ *
 *   COMENTARIOS:                                                                                                                       *
 *          - Lee una palabra del texto y la deja en el buzón.                                                                          *
 ****************************************************************************************************************************************/
public class Productor implements Runnable {

    // Constantes de clase.
    private static final int ESPERA_TRADUCTOR = 2;      // Valor de tiempo en segundos.
    private static final String[] PALABRAS_TEXTO = TEXTO.split(" ");
    public static final int NUMERO_PALABRAS = PALABRAS_TEXTO.length;

    // Atributos de la clase.
    private Buzon a_Buzon = null;
    private boolean a_TraduccionCompleta = false;

    // Constructor que asigna los valores pasados por parámetro a los atributos de la clase.
    public Productor(Buzon p_Buzon) {
        a_Buzon = p_Buzon;
    }

    // Sobrecarga del metodo run() por implementar la interfaz Runnable. Es aquí donde se realiza el trabajo de la tarea.
    @Override
    public void run() {

        int l_IndiceSiguiente = 0;
        String l_PalabraLeida = null;

        // Mientras no se hayan traducido todas las palabras.
        while (!a_TraduccionCompleta) {

            // Si el turno es del consumidor, espera a que este acabe y le notifique.
            if (!a_Buzon.isTurnoPROD()) {
                a_Buzon.esperar();
            }

            /* .-~*´¨¯¨`*·~-. INICIO SECCIÓN CRÍTICA PRODUCTOR .-~*´¨¯¨`*·~-. */

            // Lee una palabra del texto.
            l_PalabraLeida = PALABRAS_TEXTO[a_Buzon.getIndicePalabra()];

            // Deja la palabra a traducir en el buzón.
            a_Buzon.setPalabra(l_PalabraLeida);

            // Genera el índice de la siguiente palabra.
            l_IndiceSiguiente = a_Buzon.getIndicePalabra() + 1;
            a_Buzon.setIndicePalabra(l_IndiceSiguiente);

            // Comprueba si todas las palabras del texto han sido traducidas.
            if (a_Buzon.getIndicePalabra() == NUMERO_PALABRAS) {

                // En caso afirmativo, modifica el atributo de control para salir del bucle.
                a_TraduccionCompleta = true;

            }

            /* .-~*´¨¯¨`*·~-. FIN SECCIÓN CRÍTICA PRODUCTOR .-~*´¨¯¨`*·~-. */

            // Espera 2 segundos antes de procesar la siguiente palabra.
            try {
                TimeUnit.SECONDS.sleep(ESPERA_TRADUCTOR);
            } catch (InterruptedException p_Excepcion) {
                System.out.println("ERROR: No se puede hacer sleep() en el traductor.");
            }

            // El siguiente turno será del consumidor.
            a_Buzon.setTurnoPROD(false);

            // Reactiva el hilo del consumidor.
            a_Buzon.avisar();

        }   // while()

    }   // run()

}   // Productor