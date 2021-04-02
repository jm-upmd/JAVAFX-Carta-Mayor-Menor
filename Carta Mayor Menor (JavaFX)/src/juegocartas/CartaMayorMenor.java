package juegocartas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Esta clase implementa un juego de cartas. El jugador ve una carta y tiene que
 * adivinar si la próxima carta será mayor o menor. El As es la carta de menor
 * valor. Si el jugador hace tres predicciones correctas gana, sino pierde. Esta
 * clase utiliza de otros ficheros fuente: Baraja.java, Carta.java, Mano.java y
 * cards.png
 */

public class CartaMayorMenor extends Application {
	private Canvas tapete; // Canvas (lienzo) donde se dibujan las cartas y mensaje del juego.
	private Image cartasImagen; // Una imagen que contiene todas las cartas de la baraja.
	private Baraja baraja; // Baraja de cartas usada en la partida.
	private Mano mano; // Cartas que han sido repartidas en la partida.
	private String mensaje; // Mensaje pintados en el canvas que indica estado de la partida.
	private boolean partidaEnCurso; // True se el juego esta en curso, false si ha teremiando.

	/**
	 * El método start inicializa los componentes gráficos (GUI) y el manejo de
	 * eventos. El root pane es un BorderPane. Un Canvas, donde las cartas serán
	 * mostradas se coloca en el centro del BorderPane. En la parte inferior se
	 * coloca un HBox que contiene tres botones. Se implementan manejadores
	 * ActionEvent que llaman a métodos definidos en esta clase cuando el usuario
	 * hace click en los botones.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		cartasImagen = new Image("cards.png");

		// Cada carta tiene 79 x 123 pixels, luego la carta que está en la esquina
		// superior izquierda
		// será (79*C,123*F) C= núm. columna, F= núm fila
		// Espacio para 4 cartas, con 20 pixels espacio de separación
		// y espacio en parte inferior para el mensaje.

		tapete = new Canvas(4 * 99 + 20, 123 + 80);

		Button mayor = new Button("Mayor");
		mayor.setOnAction(e -> juegaMayor());
		Button menor = new Button("Menor");
		menor.setOnAction(e -> juegaMenor());
		Button nuevaPartida = new Button("Nueva Partida");
		nuevaPartida.setOnAction(e -> nuevaPartida());

		HBox barraBotones = new HBox(mayor, menor, nuevaPartida);

		/*
		 * Ajustamos el layout de la barra de botones. Si lo dejamos así los botones se
		 * verán agrupados a la izquierda y abajo del HBox. La solución es darles una
		 * preferred width de 1/3 del ancho del tapete para que todos tengan el mismo
		 * tamaño y hacer que ocupen todo el HBox.
		 */

		double tercioTapete = tapete.getWidth() / 3.0;
		mayor.setPrefWidth(tercioTapete);
		menor.setPrefWidth(tercioTapete);
		nuevaPartida.setPrefWidth(tercioTapete);

		// Forma alternativa de ajustar los botones en el hbox:
		// buttonBar.setAlignment(Pos.CENTER); // alinea botones en el centro del hbox.
		// higher.setMaxWidth(1000); // pone un ancho maximo más grande del que tendrá
		// lower.setMaxWidth(1000);
		// newGame.setMaxWidth(1000);
		// indica al HBox que permita crecer a los botones.
		// HBox.setHgrow(higher, Priority.ALWAYS);
		// HBox.setHgrow(lower, Priority.ALWAYS);
		// HBox.setHgrow(newGame, Priority.ALWAYS);

		// Enlazamos los botones con la propiedad partidaEnCurso

		BorderPane root = new BorderPane();
		root.setCenter(tapete);
		root.setBottom(barraBotones);

		nuevaPartida();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Carta Mayor o Menor");
		primaryStage.setResizable(false);
		primaryStage.show();

	} // fin start()

	private void nuevaPartida() {
		baraja = new Baraja();
		baraja.barajar();
		mano = new Mano();
		mano.cogeCarta(baraja.extraeCarta());
		mensaje = "¿Es la próxima carta mayor o menor?";
		partidaEnCurso = true;
		pintaTapete();
	}

	/**
	 * Pinta el mensaje en la parte inferior del canvas y y todas las cartas
	 * extraídas distrubuidas en el tapete. Si la partida está en curso se muestra
	 * una carta extra boca abajo representando la próxima carta de la baraja.
	 */
	private void pintaTapete() {
		GraphicsContext g = tapete.getGraphicsContext2D();
		g.setFill(Color.DARKGREEN);
		g.fillRect(0, 0, tapete.getWidth(), tapete.getHeight());
		g.setFill(Color.rgb(220, 255, 220));
		g.setFont(Font.font(16));
		g.fillText(mensaje, 20, 180);
		int cartasMano = mano.dameNumCartas();
		for (int i = 0; i < cartasMano; i++)
			pintaCarta(g, mano.dameCarta(i), 20 + i * 99, 20);

		if (partidaEnCurso)
			pintaCarta(g, null, 20 + cartasMano * 99, 20);

	}

	/**
	 * Pinta una carta con la esquina superior izquierda en (x,y). Si carta es null
	 * pinta una carta boca abajo. Las imágenes de las cartas son extraidas del
	 * fichero cards.png, que es un fichero de recurso del programa.
	 * 
	 * @param g     GraphicsContext del tapete.
	 * @param carta Carta a pintar en el tapete
	 * @param x     Coordenada x de la carta en el tapete
	 * @param y     Coordenada y de la carta en el tapete
	 */
	private void pintaCarta(GraphicsContext g, Carta carta, int x, int y) {
		int filaCarta, columCarta;

		if (carta == null) {
			filaCarta = 4;
			columCarta = 2;
		} else {
			// el palo en la imagen están en orden inverso
			filaCarta = 3 - carta.getPalo();
			columCarta = carta.getValor() - 1;
		}

		double sx, sy; // esq. sup izq de la carta en la imagen cards.png
		sx = 79 * columCarta;
		sy = 123 * filaCarta;
		g.drawImage(cartasImagen, sx, sy, 79, 123, x, y, 79, 123);

	} // fin pintaCarta()

	private void juegaMenor() {
		if (partidaEnCurso == false) {
			mensaje = "Pulsa \"Nueva Partida\" para jugar una nueva partida";
			pintaTapete();
			return;
		}

		Carta nuevaCarta = baraja.extraeCarta();
		Carta ultimaCartaMano = mano.dameCarta(mano.dameNumCartas() - 1);

		mano.cogeCarta(nuevaCarta);

		if (nuevaCarta.getValor() < ultimaCartaMano.getValor()) {
			if (mano.dameNumCartas() == 4)
				mensaje = "¡Has ganado! Has acertado tres tiradas seguidas.";
			else {
				mensaje = "¡Acertaste! Prueba con la siguiente";
			}

		} else {
			mensaje = "¡No acertaste! Has perdido.";
			partidaEnCurso = false;
		}

		pintaTapete();

	} // fin JuegaMenor

	private void juegaMayor() {
		if (partidaEnCurso == false) {
			mensaje = "Pulsa \"Nueva Partida\" para jugar una nueva partida";
			pintaTapete();
			return;
		}
		Carta nuevaCarta = baraja.extraeCarta();
		Carta ultimaCartaMano = mano.dameCarta(mano.dameNumCartas() - 1);

		mano.cogeCarta(nuevaCarta);

		if (nuevaCarta.getValor() > ultimaCartaMano.getValor()) {
			if (mano.dameNumCartas() == 4)
				mensaje = "¡Has ganado! Has acertado tres tiradas seguidas.";
			else {
				mensaje = "¡Acertaste! Prueba con la siguiente";
			}

		} else {
			mensaje = "¡No acertaste! Has perdido.";
			partidaEnCurso = false;
		}

		pintaTapete();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
