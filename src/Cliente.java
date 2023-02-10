import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {

        do {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, """
                    ***CALCULADORA CUÑADA***
                    1.Equivalente en campos de fútbol
                    2.Meses restantes para la jubilación
                    3.Equivalente en obras de Pérez Reverte
                    4.Diferenza de prezo coa gasoliñeira mais barata
                    5.Sair""", "CLIENTE", JOptionPane.QUESTION_MESSAGE));

            switch (opcion) {
                case 1, 2, 3, 4 -> {
                    String dato = JOptionPane.showInputDialog(null, "Dime un número:", "CLIENTE", JOptionPane.QUESTION_MESSAGE);
                    String numeroDigitosDato = String.valueOf(dato.length());

                    enviarDatos(dato, String.valueOf(opcion), numeroDigitosDato);

                    ServerSocket clientSocket = new ServerSocket();
                    InetSocketAddress addr = new InetSocketAddress("localhost", 5566);
                    clientSocket.bind(addr);
                    Socket newSocket = clientSocket.accept();
                    InputStream is = newSocket.getInputStream();
                    byte[] arrayDatoDevuelto = new byte[20];
                    is.read(arrayDatoDevuelto);

                    switch (opcion) {
                        case 1 -> JOptionPane.showMessageDialog(null, "Eso son " + new String(arrayDatoDevuelto) + " campos de fútbol");
                        case 2 -> JOptionPane.showMessageDialog(null, "Te quedan " + new String(arrayDatoDevuelto) + " meses para la jubilación con paga completa");
                        case 3 -> JOptionPane.showMessageDialog(null, "Eso son " + new String(arrayDatoDevuelto) + " obras de Pérez Reverte");
                        case 4 -> JOptionPane.showMessageDialog(null, "Eso son " + new String(arrayDatoDevuelto) + "€/L mas cara que la gasolinera mas barata de la zona");
                    }

                    newSocket.close();
                    clientSocket.close();
                }
                case 5 -> {
                    enviarDatos("0", String.valueOf(opcion), "0");
                    System.exit(0);
                }
            }
        } while (true);

    }

    public static void enviarDatos(String dato, String opcion, String numeroDigitosDato) {
        try {
            System.out.println("Creando socket cliente");
            Socket clienteSocket = new Socket();
            System.out.println("Estableciendo la conexión");

            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            clienteSocket.connect(addr);

            OutputStream os = clienteSocket.getOutputStream();

            System.out.println("Enviando dato");

            os.write(opcion.getBytes());
            os.write(numeroDigitosDato.getBytes());
            os.write(dato.getBytes());

            System.out.println("Dato enviado");

            System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
