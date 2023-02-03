import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {

        do {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, """
                    ***CALCULADORA CUÑADA***
                    1.Equivalente en campos de fútbol
                    2.Meses restantes para la jubilación
                    3.Equivalente en obras de Pérez Reverte
                    4.Diferenza de prezo coa gasoliñeira mais barata""", "CLIENTE", JOptionPane.QUESTION_MESSAGE));

            switch (opcion) {
                case 1, 2, 3, 4 -> {
                    int dato1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Dime un número:", "CLIENTE", JOptionPane.QUESTION_MESSAGE));
                    enviarDatos(dato1, opcion);
                }
            }
        } while (true);

    }

    public static void enviarDatos(int dato, int opcion) {
        try {
            System.out.println("Creando socket cliente");
            Socket clienteSocket = new Socket();
            System.out.println("Estableciendo la conexión");

            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            clienteSocket.connect(addr);

            OutputStream os = clienteSocket.getOutputStream();

            System.out.println("Enviando dato");

            os.write(opcion);
            os.write(dato);

            System.out.println("Dato enviado");

            System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
