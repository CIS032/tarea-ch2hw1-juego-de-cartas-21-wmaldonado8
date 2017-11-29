package blackjack;

/**
* Un objeto de tipo Mazo representa una baraja de cartas. La cubierta
 * es un mazo de póquer regular que contiene 52 cartas regulares y que puede
 * también opcionalmente incluir dos Jotas.
 */
 public class Mazo {

    /**
     * Una matriz de 52 o 54 cartas. Un mazo de 54 cartas contiene dos Jokers,
     * además de las 52 cartas de un mazo de póker regular.
     */
    private Carta[] mazo;

    /**
     * Realiza un seguimiento del número de cartas que se han repartido desde
     * el mazo hasta ahora.
     */
    private int cartasUsadas;

    /**
     * Construye una baraja de póker regular de 52 cartas. Inicialmente, las cartas
     * están en un orden ordenado. El método shuffle () se puede llamar a
     * aleatorizar el orden. (Tenga en cuenta que "nuevo Deck ()" es equivalente
     * a "cubierta nueva (falsa)".)
     */
    public Mazo() {
        this(false);  // Simplemente llama al otro constructor en esta clase.
    }

    /**
     * Construye una baraja de cartas de póker, la baraja contiene
     * las 52 cartas habituales y opcionalmente puede contener dos comodines
     * además, para un total de 54 cartas. Inicialmente las cartas
     * están en un orden ordenado. El método shuffle () se puede llamar a
     * aleatorizar el orden.
     * @param includeJokers si es verdadero, dos comodines están incluidos en el mazo; si es falso,
     * no hay Jokers en el mazo.
     */
    public Mazo(boolean includeJokers) {
        if (includeJokers)
            mazo = new Carta[54];
        else
            mazo = new Carta[52];
        int cardCt = 0; // Cuantas cartas se han creado hasta ahora.
        for ( int suit = 0; suit <= 3; suit++ ) {
            for ( int value = 1; value <= 13; value++ ) {
                mazo[cardCt] = new Carta(value,suit);
                cardCt++;
            }
        }
        if (includeJokers) {
            mazo[52] = new Carta(1,Carta.JOKER);
            mazo[53] = new Carta(2,Carta.JOKER);
        }
        cartasUsadas = 0;
    }

    /**
     * Coloca todas las tarjetas usadas de nuevo en el mazo (si las hay), y
     * baraja el mazo en un orden aleatorio.
     */
    public void shuffle() {
        for ( int i = mazo.length-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Carta temp = mazo[i];
            mazo[i] = mazo[rand];
            mazo[rand] = temp;
        }
        cartasUsadas = 0;
    }

    /**
     * A medida que las cartas se reparten desde el mazo, la cantidad de cartas restantes
     * disminuye. Esta función devuelve la cantidad de tarjetas que
     * aún quedan en el mazo. El valor de retorno sería
     * 52 o 54 (dependiendo de si el mazo incluye comodines)
     * cuando se crea la plataforma por primera vez o después de que la plataforma ha sido
     * barajado. Disminuye en 1 cada vez que el método dealCard ()
     * se llama.
     */
    public int cardsLeft() {
        return mazo.length - cartasUsadas;
    }

    /**
     * Elimina la siguiente carta del mazo y la devuelve. Es ilegal
     * para llamar a este método si no hay más cartas en el mazo. Usted puede
     * verifique el número de tarjetas restantes llamando a la función cardsLeft ().
     * @return la carta que se retira de la baraja.
     * @throws IllegalStateException si no quedan cartas en el mazo
     */
    public Carta dealCard() {
        if (cartasUsadas == mazo.length)
            throw new IllegalStateException("No cards are left in the deck.");
        cartasUsadas++;
        return mazo[cartasUsadas - 1];
        // Las tarjetas no se eliminan literalmente de la matriz
        // eso representa el mazo. Simplemente hacemos un seguimiento de cuántas tarjetas
        // ha sido usado.
    }

    /**
     * Comprobar si el mazo contiene comodines.
     * @return true, si este es un mazo de 54 cartas que contiene dos comodines, o falso si
     * esta es una baraja de 52 cartas que no contiene comodines.
     */
    public boolean hasJokers() {
        return (mazo.length == 54);
    }

} // fin clase Mazo
