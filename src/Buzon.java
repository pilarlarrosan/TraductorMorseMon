/****************************************************************************************************************************************
 *   CLASE: "Buzon"                                                                                                                     *
 * ************************************************************************************************************************************ *
 *   @author Pilar Larrosa Novás                                                                                                        *
 *   @version 2.0 - Sustitución de los semáforos por monitores.                                                                         *
 *            1.0 - Versión inicial de la clase.                                                                                        *
 *   @since 25ENE2025                                                                                                                   *
 *          23ENE2025                                                                                                                   *
 * ************************************************************************************************************************************ *
 *   COMENTARIOS:                                                                                                                       *
 *          - Permite el intercambio de información entre clases.                                                                       *
 *          - Realiza la sincronización del productor y el consumidor utilizando monitores.                                             *
 ****************************************************************************************************************************************/
public class Buzon {

    private String a_Palabra = null;        // Sirve para el intercambio de información.
    private int a_IndicePalabra = 0;        // Controla si se han traducido todas las palabras.
    private boolean a_TurnoPROD = true;     // Establece de qué proceso es el turno (true para que el productor se ejecute el primero).

    public Buzon() {
    }

    public boolean isTurnoPROD() {
        return a_TurnoPROD;
    }   // isTurnoPROD()

    public void setTurnoPROD(boolean p_TurnoPROD) {
        a_TurnoPROD = p_TurnoPROD;
    }   // setTurnoPROD()

    public String getPalabra() {
        return a_Palabra;
    }   // getPalabra()

    public void setPalabra(String p_Palabra) {
        a_Palabra = p_Palabra;
    }   // setPalabra()

    public int getIndicePalabra() {
        return a_IndicePalabra;
    }   // getIndicePalabra()

    public void setIndicePalabra(int p_IndicePalabra) {
        a_IndicePalabra = p_IndicePalabra;
    }   // setIndicePalabra()

    // Detiene la ejecución del hilo.
    public synchronized void esperar() {

        try {
            wait();
        } catch (InterruptedException p_Excepcion) {
            System.out.println("ERROR: no se puede hacer wait() en el buzón.");
        }

    }   // esperar()

    // Reactiva la ejecución del hilo.
    public synchronized void avisar() {

        notify();

    }   // avisar()

}   // Buzon
