import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        System.out.println("**CLIENTE INICIADO**\n");
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

                    //Creamos un nuevo socket (DEVOLUCION)
                    ServerSocket clienteSocket = new ServerSocket();
                    //Asignamos ip y puerto al socket para recibir los datos del servidor
                    InetSocketAddress addr = new InetSocketAddress("localhost", 6666);
                    clienteSocket.bind(addr);


                    //**ENVIAR DATO**

                    //Recogida de dato en variable
                    String dato = JOptionPane.showInputDialog(null, "Dime un número:", "CLIENTE", JOptionPane.QUESTION_MESSAGE);
                    //Recogida del temaño del dato en variable
                    String numeroDigitosDato = String.valueOf(dato.length());

                    //Enviamos al metodo enviarDatos() el dato, la opcion escogida y el tamaño del dato
                    enviarDatos(dato, String.valueOf(opcion), numeroDigitosDato);


                    //**DEVOLUCION DEL DATO**

                    //Esperamos a que llegue la conexion y la acepte
                    System.out.println("Esperando respuesta del servidor...\n");
                    Socket newSocket = clienteSocket.accept();
                    System.out.println("Respuesta recibida...\n");
                    System.out.println("Mostrando respuesta al cliente...\n");

                    //Instanciamos el objeto de entrada
                    InputStream is = newSocket.getInputStream();

                    //Metemos el dato recibido en una variable
                    byte[] arrayDatoDevuelto = new byte[20];
                    is.read(arrayDatoDevuelto);
                    String datoDevuelto = new String(arrayDatoDevuelto);

                    //diferentes mensajes en funcion de la opcion escogida por el cliente
                    switch (opcion) {
                        case 1 -> JOptionPane.showMessageDialog(null, "Eso son " + datoDevuelto + " campos de fútbol", "CLIENTE",JOptionPane.INFORMATION_MESSAGE);
                        case 2 -> JOptionPane.showMessageDialog(null, "Te quedan " + datoDevuelto + " meses para la jubilación con paga completa", "CLIENTE",JOptionPane.INFORMATION_MESSAGE);
                        case 3 -> JOptionPane.showMessageDialog(null, "Eso son " + datoDevuelto + " obras de Pérez Reverte", "CLIENTE",JOptionPane.INFORMATION_MESSAGE);
                        case 4 -> JOptionPane.showMessageDialog(null, "Eso son " + datoDevuelto + "€/L mas cara que la gasolinera mas barata de la zona", "CLIENTE",JOptionPane.INFORMATION_MESSAGE);
                    }
                    //Una vez mostrado el dato junto al mensaje, ceramos los sockets
                    newSocket.close();
                    clienteSocket.close();
                }
                case 5 -> {
                    enviarDatos("0", String.valueOf(opcion), "0");
                    System.out.println("**CLIENTE CERRADO**");
                    System.exit(0);
                }
            }

        } while (true);

    }


    // Método para enviar un dato determinado al servidor
    public static void enviarDatos(String dato, String opcion, String numeroDigitosDato) throws IOException {

        //Creando socket de cliente
        Socket newSocket = new Socket();
        System.out.println("Estableciendo la conexión con el servidor...\n");

        //Asignando direccion ip y puerto
        InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
        newSocket.connect(addr);

        //Instancia del objeto de salida
        OutputStream os = newSocket.getOutputStream();
        System.out.println("¡¡Conexión aceptada!!\n");

        //Condicionante que determina si quiero enviar un dato al servidor o cerrarlo
        if (opcion.equals("5")) {

            //Envia instruccion de que se cierre el servidor
            System.out.println("Cerrando servidor...\n");
            os.write(opcion.getBytes());
            os.write(numeroDigitosDato.getBytes());

            //Cerrando socket del cliente(puerto:5555)
            newSocket.close();

            System.out.println("**Servidor cerrado**");

        } else {

            //Enviando datos
            System.out.println("Enviando datos...\n");
            os.write(opcion.getBytes());                //Numero de instrucción
            os.write(numeroDigitosDato.getBytes());     //Numero de dígitos del dato
            os.write(dato.getBytes());                  //Dato
            System.out.println("¡¡Datos enviados!!\n");

            //Cerrando socket del cliente (port:5555)
            newSocket.close();
        }

    }
}
