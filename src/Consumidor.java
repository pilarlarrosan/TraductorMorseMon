
/****************************************************************************************************************************************
 *   CLASE: "Consumidor"                                                                                                                *
 * ************************************************************************************************************************************ *
 *   @author Pilar Larrosa Novás                                                                                                        *
 *   @version 2.0 - Sustitución de los semáforos por monitores.                                                                         *
 *            1.0 - Versión inicial de la clase.                                                                                        *
 *   @since 25ENE2025                                                                                                                   *
 *          23ENE2025                                                                                                                   *
 * ************************************************************************************************************************************ *
 *   COMENTARIOS:                                                                                                                       *
 *          - Lee la palabra que ha dejado previamente el productor en el buzón.                                                        *
 *          - Realiza la traducción a código morse de la palabra leída y la muestra por pantalla.                                       *
 *          - Si la constante lo indica, reproduce el audio morse de la palabra traducida.                                              *
 ****************************************************************************************************************************************/
public class Consumidor implements Runnable {

    // Constantes de clase.
    private static final String ABECEDARIO = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
    private static final String[] ALFABETO_MORSE = {"·-", "-···", "-·-·", "-··", "·", "··-·", "--·", "····", "··", "·---", "-·-", "·-··", "--", "-·", "--·--", "---", "·--·", "--·-", "·-·", "···", "-", "··-", "···-", "·--", "-··-", "-·--", "--··", "·----", "··---", "···--", "····-", "·····", "-····", "--···", "---··", "----·", "-----"};

    // Atributos de la clase.
    private Buzon a_Buzon = null;
    private boolean a_TraduccionCompleta = false;

    // Constructor que asigna los valores pasados por parámetro a los atributos de la clase.
    public Consumidor(Buzon p_Buzon) {
        a_Buzon = p_Buzon;
    }

    // Sobrecarga del metodo run() por implementar la interfaz Runnable. Es aquí donde se realiza el trabajo de la tarea.
    @Override
    public void run() {

        String l_PalabraLeida = null;

        // Mientras no se hayan traducido todas las palabras.
        while (!a_TraduccionCompleta) {

            // Si el turno es del productor, espera a que este acabe y le notifique.
            if (a_Buzon.isTurnoPROD()) {
                a_Buzon.esperar();
            }

            /* .-~*´¨¯¨`*·~-. INICIO SECCIÓN CRÍTICA CONSUMIDOR .-~*´¨¯¨`*·~-. */

            // Lee la palabra que el productor ha dejado en el buzón.
            l_PalabraLeida = a_Buzon.getPalabra();

            // Realiza la traducción de la palabra a código morse.
            String l_PalabraMorse = traducir(l_PalabraLeida);

            // Imprime la palabra traducida por pantalla.
            System.out.println(l_PalabraLeida + ": " + l_PalabraMorse);

            // Reproduce el audio de la palabra si la constante lo indica.
            if (TraductorMorse.REPRODUCIR_AUDIO) {

                MorseAudio l_MorseAudio = new MorseAudio();
                l_MorseAudio.reproducirAudioMorse(l_PalabraMorse);

            }

            // Comprueba si todas las palabras del texto han sido traducidas.
            if (a_Buzon.getIndicePalabra() == Productor.NUMERO_PALABRAS) {

                // En caso afirmativo, modifica el atributo de control para salir del bucle.
                a_TraduccionCompleta = true;

            }

            /* .-~*´¨¯¨`*·~-. FIN SECCIÓN CRÍTICA CONSUMIDOR .-~*´¨¯¨`*·~-. */

            // El siguiente turno será del productor.
            a_Buzon.setTurnoPROD(true);

            // Reactiva el hilo del productor.
            a_Buzon.avisar();

        }   // while()

    }   // run()

    /**
     * @param p_PalabraLeida - Entrada - Palabra a traducir.
     * @return Palabra traducida a código morse letra por letra.
     * @author Pilar Larrosa Novás.
     */
    private String traducir(String p_PalabraLeida) {

        int l_IndiceCaracter = 0;
        String l_CaracterMorse = "";
        String l_PalabraMorse = "";

        // Array de caracteres de la palabra a traducir.
        char[] l_CaracteresPalabra = p_PalabraLeida.toUpperCase().toCharArray();

        // Traducción de la palabra caracter a caracter.
        for (char l_Caracter : l_CaracteresPalabra) {

            // Posición que ocupa el caracter en la constante ABECEDARIO.
            l_IndiceCaracter = ABECEDARIO.indexOf(l_Caracter);

            // Caracter traducido a código morse.
            l_CaracterMorse = ALFABETO_MORSE[l_IndiceCaracter];

            // Concatena el caracter a la palabra traducida, además de un espacio en blanco.
            l_PalabraMorse += l_CaracterMorse.concat(" ");

        }   // for()

        // Devuelve la palabra traducida. Los caracteres están separados por un espacio en blanco.
        return l_PalabraMorse;

    }   // traducir()

}   // Consumidor