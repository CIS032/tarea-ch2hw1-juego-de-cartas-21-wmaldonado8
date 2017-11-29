package blackjack;

public class ManoBlackjack extends Mano {

    /**
     *Calcula y devuelve el valor de esta mano en el juego
     * de Blackjack.
     */
    public int getValorBlackjack() {

        int val;      //  El valor calculado para la mano.
        boolean as;  //  Esto se establecerá en verdadero si la mano contiene un As
        int cartas;    // Numero de cartas en la mano

        val = 0;
        as = false;
        cartas = getContCartas();  // (metodo definido en la clase mano .)

        for ( int i = 0;  i < cartas;  i++ ) {
            // Agrega el valor de la i-ésima carta en la mano.
            Carta carta;    // La i-ésima carta.
            int valorCartas;  // El valor de blackjack de la i-ésima carta.
            carta = getCarta(i);
            valorCartas = carta.getValor();  // El valor normal, de 1 a 13.
            if (valorCartas > 10) {
                valorCartas = 10;   // Para un Jack, Queen o King.
            }
            if (valorCartas == 1) {
                as = true;     //Hay al menos un as.
            }
            val = val + valorCartas;
        }

        // Ahora, val es el valor de la mano, contando cualquier as como 1.
        // Si hay un as, y si cambia su valor de 1 a 
        // 11 dejaría el puntaje menor o igual a 21,
        // entonces hazlo sumando los 10 puntos adicionales a val. 

        if ( as == true  &&  val + 10 <= 21 )
            val = val + 10;

        return val;

    }  // fin  del método getValorBlackjack()

} // fin de la clase BlackjackHand