package blackjack;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Este programa permite al usuario jugar Blackjack. El ordenador actúa como el
 * distribuidor. El usuario tiene una apuesta de $ 100 y hace una apuesta en
 * cada juego. El usuario puede irse en cualquier momento, o será expulsado
 * cuando pierda todo el dinero. Reglas de la casa: el repartidor golpea a un
 * total de 16 o menos y se encuentra en un total de 17 o más. El distribuidor
 * gana lazos. Se usa una nueva baraja de cartas para cada juego.
 */
public class Blackjack {
    public static String datos = "";
    public static void main(String[] args) {
        String msj = "";
        int dinero;          //  Cantidad de dinero que tiene el usuario
        int dineroApuesta;            // Cantidad de apuestas de usuario en un juego.
        boolean usuarioGano;   // ¿El usuario ganó el juego?
        Scanner leer = new Scanner(System.in);
        msj = "\nBienvenido al juego de blackjack\n";
        datos = datos + msj;
        System.out.println(msj);

        dinero = 100;  // Usuario comienza con $100.

        while (true) {
            msj = "Tiene " + dinero + " dólares.";
            System.out.println(msj);
            datos = datos + msj;
            do {
                msj = "¿Cuántos dólares quieres apostar? (Ingresa 0 para finalizar) \n?";
                System.out.println(msj);
                datos = datos + msj;
                dineroApuesta = leer.nextInt();
                datos = datos + dineroApuesta;
                if (dineroApuesta < 0 || dineroApuesta > dinero) {
                    msj = "\nSu respuesta debe estar entre 0 y\" " + dinero + " \n";
                    System.out.println(msj);
                    datos = datos + msj;
                }
            } while (dineroApuesta < 0 || dineroApuesta > dinero);
            if (dineroApuesta == 0) {
                break;
            }
            usuarioGano = playBlackjack();
            if (usuarioGano) {
                dinero = dinero + dineroApuesta;
            } else {
                dinero = dinero - dineroApuesta;
            }
            if (dinero == 0) {
                msj = "\nParece que te has quedado sin dinero \n";
                System.out.println(msj);
                datos = datos + msj;
                break;
            }
        }
        msj = "\n\nSales con $ " + dinero + "\n";
        System.out.println(msj);
        datos = datos + msj;
        guardar(datos);
    } // fin de  main()

    /**
     * Permita que el usuario juegue un juego de Blackjack, con la computadora
     * como distribuidor.
     *
     * @return true si el usuario gana el juego, falso si el usuario pierde.29 0
     *
     */
    static boolean playBlackjack() {
        String msj = "";
        Scanner leer = new Scanner(System.in);
        Mazo mazo;                  // Una baraja de cartas. Una nueva baraja para cada juego.
        ManoBlackjack manoDistribuidor;   //  La mano del crupier.
        ManoBlackjack manoUsuario;     //  La mano del usuario.

        mazo = new Mazo();
        manoDistribuidor = new ManoBlackjack();
        manoUsuario = new ManoBlackjack();

        /* Baraja el mazo, reparte dos cartas a cada jugador. 100 */
        mazo.shuffle();
        manoDistribuidor.agregarCarta(mazo.dealCard());
        manoDistribuidor.agregarCarta(mazo.dealCard());
        manoUsuario.agregarCarta(mazo.dealCard());
        manoUsuario.agregarCarta(mazo.dealCard());

        /*  Verifica si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21).
         El jugador con Blackjack gana el juego. El distribuidor gana lazos.
         */
        if (manoDistribuidor.getValorBlackjack() == 21) {
            msj = "\nEl distribuidor tiene la carta " + manoDistribuidor.getCarta(0)
                    + "y" + manoDistribuidor.getCarta(1) + ".\n"
                    + "\nEl usuario tiene la carta  " + manoUsuario.getCarta(0)
                    + "y" + manoUsuario.getCarta(1) + ".\n"
                    + "\nEl distribuidor tiene Blackjack. El distribuidor gana \n";
            datos += msj;
                    System.out.println(msj);
            return false;
        }

        if (manoUsuario.getValorBlackjack() == 21) {
            msj = "\nEl distribuidor tiene la carta " + manoDistribuidor.getCarta(0)
                    + "y" + manoDistribuidor.getCarta(1) + "."
                    + "\nEl usuario tiene la carta " + manoUsuario.getCarta(0)
                    + "y" + manoUsuario.getCarta(1) + ".\n"
                    + "\nTú tienes Blackjack.  Tú ganas.\n";
            datos += msj;
                    System.out.println(msj);
            return true;
        }

        /*  Si ninguno de los jugadores tiene Blackjack, juega el juego. Primero el usuario
          tiene la oportunidad de robar cartas (es decir, de "golpear"). El bucle while termina
          cuando el usuario elige "Stand". Si el usuario supera los 21,
          el usuario pierde inmediatamente.
         */
        while (true) {

            /* Mostrar tarjetas de usuario, y dejar que el usuario decida golpear o pararse.*/
            msj = "\n\nTus cartas son: ";
            datos += msj;
            System.out.println(msj);
            for (int i = 0; i < manoUsuario.getContCartas(); i++) {
                msj += "    " + manoUsuario.getCarta(i);
            }
            msj = "\nSu total es   " + manoUsuario.getValorBlackjack() + "\n"
                    + "El concesionario muestra la carta " + manoDistribuidor.getCarta(0) + "\n"
                    + "\"Golpear (G) o Parar (P)?\"\n"
                    + "";
            datos += msj;
            System.out.println(msj);
            char accion; // Respuesta del usuario, 'H' o 'S'.
            do {
                accion = Character.toUpperCase(leer.next().charAt(0));
                if (accion != 'G' && accion != 'P') {
                    msj = "\nPor favor digite G or P:  \n";
                    datos += msj;
                    System.out.println(msj);
                }
            } while (accion != 'G' && accion != 'P');

            /* Si el usuario tiene éxito, el usuario obtiene una carta. Si el usuario está parado,
              el bucle termina (y es el turno del crupier de robar cartas).
             */
            if (accion == 'S') {
                // Fin del bucle; el usuario ha terminado de tomar cartas.
                break;
            } else {  // userAction es 'H'. Dale una tarjeta al usuario.  
                // Si el usuario supera los 21, el usuario pierde.
                Carta newCard = mazo.dealCard();
                manoUsuario.agregarCarta(newCard);
                msj = "\nEl usuario acierta"
                        + "\nSu carta  es la " + newCard + "\n"
                        + "\nSu nuevo total es: " + manoUsuario.getValorBlackjack();
                datos += msj;
                System.out.println(msj);
                if (manoUsuario.getValorBlackjack() > 21) {
                    msj = "\nHas fallado al pasar de 21. Pierdes\n"
                            + "Otra carta del distribuidor fue el "
                            + manoDistribuidor.getCarta(1);
                    datos += msj;
                    System.out.println(msj);
                    return false;
                }
            }

        } // fin del bucle

        /* If we get to this point, the user has Stood with 21 or less.  Now, it's
         the dealer's chance to draw.  Dealer draws cards until the dealer's
         total is > 16.  If dealer goes over 21, the dealer loses.
         */
        msj = "\nUsuario parado\n"
                + "Las tarjetas del distribuidor son: \n"
                + "" + manoDistribuidor.getCarta(0)
                + "\n" + manoDistribuidor.getCarta(1);
        datos += msj;
        System.out.println(msj);
        while (manoDistribuidor.getValorBlackjack() <= 16) {
            Carta newCard = mazo.dealCard();
            msj = "\nEl distribuidor golpea y obtiene la carta " + newCard + "\n";
            datos += msj;
            System.out.println(msj);
            manoDistribuidor.agregarCarta(newCard);
            if (manoDistribuidor.getValorBlackjack() > 21) {
                msj = "\\nDistribuidor detenido por pasar de 21. Usted gana.\\n";
                datos += msj;
                System.out.println(msj);
                return true;
            }
        }
        msj = "\nEl total del distribuidor es  " + manoDistribuidor.getValorBlackjack() + "\n";
        datos += msj;
        System.out.println(msj);
        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
         puede determinar el ganador comparando los valores de sus manos. */
        if (manoDistribuidor.getValorBlackjack() == manoUsuario.getValorBlackjack()) {
            msj = "\nEl distribuidor gana en un empate. Pierdes\n";
            datos += msj;
            System.out.println(msj);
            return false;
        } else if (manoDistribuidor.getValorBlackjack() > manoUsuario.getValorBlackjack()) {
            msj = "\nEl distribuidor gana, " + manoDistribuidor.getValorBlackjack()
                    + "  puntos para  " + manoUsuario.getValorBlackjack() + ".\n";
            datos += msj;
            System.out.println(msj);
            return false;
        } else {
            msj = "\nTú ganas, " + manoUsuario.getValorBlackjack()
                    + " puntos para " + manoDistribuidor.getValorBlackjack() + ".\n";
            datos += msj;
            System.out.println(msj);
            return true;
        }

    }  // fin playBlackjack()

    public static void guardar(String cadena) {
        try {
            BufferedWriter salida = new BufferedWriter(new FileWriter("datos.txt"));
            salida.write(cadena);
            salida.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

} // fin class Blackjack
