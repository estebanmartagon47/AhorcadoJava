package ahorcadojava;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Juego del Ahorcado en Java usando AWT, versión mejorada.
 */
public class Ahorcado {

    Frame ventana = new Frame("Ahorcado");
    Panel panel1 = new Panel();
    Panel panel2 = new Panel();
    Panel panel3 = new Panel();

    Label lblScrt = new Label("Adivina la palabra secreta:");
    Label lblTries = new Label("Intentos restantes: 10 de 10");
    Label lblLtra = new Label("Letras probadas:");

    TextField txtScrt = new TextField(15);
    TextField txtLtra = new TextField(2);
    TextField txtTries = new TextField(30);

    Button btnLtra = new Button("Probar con la letra...");

    String[] palabras = {
        "ÁRBOL", "CANCIÓN", "TELÉFONO", "MÉXICO", "ÍNDICE", "AVIÓN",
        "LOGÍSTICA", "ESTACIÓN", "ÁLGEBRA", "SÍMBOLO", "INFORMÁTICA",
        "PÁGINA", "ÚLTIMO", "GEOMETRÍA", "CÉLULA", "ACCIÓN", "MÉTODO",
        "LÓGICA", "GRÁFICO", "MENÚ"
    };

    String secreta;        
    String secretaGuiones; 
    int intentos;           
    String letrasProbadas;  

    public Ahorcado() {
        ventana.setLayout(new FlowLayout());

        // Panel 1: palabra secreta
        panel1.add(lblScrt);
        txtScrt.setEnabled(false);
        panel1.add(txtScrt);
        ventana.add(panel1);

        // Panel 2: intento y botón
        panel2.add(btnLtra);
        panel2.add(txtLtra);
        panel2.add(lblTries);
        ventana.add(panel2);

        // Panel 3: letras probadas
        panel3.add(lblLtra);
        txtTries.setEnabled(false);
        panel3.add(txtTries);
        ventana.add(panel3);

        ventana.setSize(500, 180);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        // Listener para cerrar la ventana
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
            }
        });

        reiniciar();

        // Acción del botón
        btnLtra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                probarLetra();
            }
        });

        ventana.setVisible(true);
    }

    public void reiniciar() {
        Random aleatorio = new Random();
        intentos = 10;
        secreta = palabras[aleatorio.nextInt(palabras.length)];
        secretaGuiones = "";
        letrasProbadas = "";

        for (int i = 0; i < secreta.length(); i++) {
            if (secreta.charAt(i) == ' ') {
                secretaGuiones += "  ";
            } else {
                secretaGuiones += "_ ";
            }
        }

        txtScrt.setText(secretaGuiones);
        txtTries.setText("");
        lblTries.setText("Intentos restantes: " + intentos + " de 10");
        txtLtra.setText("");
    }

    public void probarLetra() {
        String letra = txtLtra.getText().toUpperCase();
        if (letra.isEmpty() || letra.length() > 1) {
            return;
        }

        if (letrasProbadas.contains(letra)) {
            txtLtra.setText("");
            return;
        }

        letrasProbadas += letra + " ";
        txtTries.setText(letrasProbadas);

        boolean acierto = false;
        StringBuilder nuevaPalabra = new StringBuilder();

        for (int i = 0; i < secreta.length(); i++) {
            char c = secreta.charAt(i);
            if (String.valueOf(c).equals(letra)) {
                nuevaPalabra.append(c).append(" ");
                acierto = true;
            } else {
                nuevaPalabra.append(secretaGuiones.charAt(i * 2)).append(" ");
            }
        }

        secretaGuiones = nuevaPalabra.toString();
        txtScrt.setText(secretaGuiones);

        if (!acierto) {
            intentos--;
            lblTries.setText("Intentos restantes: " + intentos + " de 10");
        }

        // Comprobar fin del juego
        if (!secretaGuiones.contains("_")) {
            mostrarMensaje("¡Felicidades! Has adivinado la palabra: " + secreta);
            bloquearJuego();
        } else if (intentos == 0) {
            mostrarMensaje("¡Has perdido! La palabra era: " + secreta);
            bloquearJuego();
        }

        txtLtra.setText("");
    }

    // Bloquea los controles cuando termina el juego
    private void bloquearJuego() {
        txtLtra.setEnabled(false);
        btnLtra.setEnabled(false);
    }

    // Muestra un mensaje emergente
    private void mostrarMensaje(String mensaje) {
        Frame dialogFrame = new Frame();
        dialogFrame.setSize(400, 100);
        dialogFrame.setLayout(new FlowLayout());
        dialogFrame.add(new Label(mensaje));
        dialogFrame.setLocationRelativeTo(ventana);
        dialogFrame.setVisible(true);

        // Listener para cerrar el mensaje
        dialogFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialogFrame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new Ahorcado();
    }
}

