package blackjack;


/* Un objeto de tipo Mano representa una mano de cartas. los
 * las tarjetas pertenecen a la clase Tarjeta. Una mano está vacía cuando
 * se crea y se puede agregar cualquier cantidad de tarjetas.*/
import java.util.ArrayList;

public class Mano {

    private ArrayList<Carta> mano;   // Cartas en la mano

    /**
     * crea un mano vacia
     */
    public Mano() {
        mano = new ArrayList<Carta>();
    }

    /**
     * Elimina todas las cartas
     */
    public void vaciar() {
        mano.clear();
    }

    /**
      * Agrega una carta a la mano. Se agrega al final de la mano actual.
     * @param c la tarjeta no nula que se agregará.
     * @throws NullPointerException si el parámetro c es nulo.
     */
    public void agregarCarta(Carta c) {
        if (c == null) {
            throw new NullPointerException("No se puede agregar una carta nula a una mano.");
        }
        mano.add(c);
    }

    /**
     * Retira una carta de la mano, si está presente.
     * @param c la carta que se eliminará. Si c es nulo o si la carta no está en
     * la mano, entonces no se hace nada.
     */
    public void removerCarta(Carta c) {
        mano.remove(c);
    }

    /**
     * Retira la carta en una posición específica de la mano.
     * @param posicionar la posición de la carta que se va a eliminar, donde
     * las posiciones comienzan desde cero.
     * @throws IllegalArgumentException si el puesto no existe en
     * la mano, eso es si la posición es menor que 0 o mayor que
     * o igual a la cantidad de cartas en la mano.
     */
    public void removerCarta(int posicion) {
        if (posicion < 0 || posicion >= mano.size()) {
            throw new IllegalArgumentException("La posición no existe en la mano: "
                    + posicion);
        }
        mano.remove(posicion);
    }

    /**
     * Devuelve el número de cartas en la mano
     */
    public int getContCartas() {
        return mano.size();
    }

    /**
     * Obtiene la carta en una posición específica en la mano. (Tenga en cuenta que esta carta
     * no se elimina de la mano!)
     * @param posicionar la posición de la tarjeta que se va a devolver
     * @throws IllegalArgumentException si la posición no existe en la mano
     */
    public Carta getCarta(int posicion) {
        if (posicion < 0 || posicion >= mano.size()) {
            throw new IllegalArgumentException("La posición no existe en la mano: "
                    + posicion);
        }
        return mano.get(posicion);
    }

    /**
     * Clasifica las cartas en la mano para que las cartas del mismo palo sean
     * agrupados, y dentro de un palo, las carta se ordenan por valor.
     * Tenga en cuenta que se considera que los ases tienen el valor más bajo, 1.
     */
    public void ordenarXPalo() {
        ArrayList<Carta> newHand = new ArrayList<Carta>();
        while (mano.size() > 0) {
            int pos = 0;  // Posición de la carta mínima.
            Carta c = mano.get(0);  // Carta mínima.
            for (int i = 1; i < mano.size(); i++) {
                Carta c1 = mano.get(i);
                if (c1.getPalo()< c.getPalo()
                        || (c1.getPalo()== c.getPalo() && c1.getValor()< c.getValor())) {
                    pos = i;
                    c = c1;
                }
            }
            mano.remove(pos);
            newHand.add(c);
        }
        mano = newHand;
    }

    /**
     * Clasifica las cartas en la mano para que las cartas del mismo valor sean
     * agrupados. Las cartas con el mismo valor se clasifican por palo.
     * Tenga en cuenta que se considera que los ases tienen el valor más bajo, 1.
     */
    public void ordenarXValor() {
        ArrayList<Carta> newHand = new ArrayList<Carta>();
        while (mano.size() > 0) {
            int pos = 0;  // Posición de la carta mínima.
            Carta c = mano.get(0);  // Carta mínima.
            for (int i = 1; i < mano.size(); i++) {
                Carta c1 = mano.get(i);
                if (c1.getValor()< c.getValor()
                        || (c1.getValor()== c.getValor()&& c1.getPalo()< c.getPalo())) {
                    pos = i;
                    c = c1;
                }
            }
            mano.remove(pos);
            newHand.add(c);
        }
        mano = newHand;
    }

} //Fin clase Mano