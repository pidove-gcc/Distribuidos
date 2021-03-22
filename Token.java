import java.io.*;
import java.net.*;
import java.lang.*;


class Token
{
  static DataInputStream entrada;
  static DataOutputStream salida;
  static boolean primera_vez = true;
  static String ip;
  static int nodo;
  static int token;
  static int contador = 0;

  static class Worker extends Thread
  {
    public void run(){
      //   Inicia Algoritmo 1
      try {
        // Declarar la variable servidor de tipo ServerSocket
        ServerSocket servidor;
        // Asignar a la variable servidor el objeto: new ServerSocket(50000)
        servidor = new ServerSocket(50000);
        // Declarar la variable conexion de tipo Socket.
        Socket conexion;
        // Asignar a la variable conexion el objeto servidor.accept().
        conexion = servidor.accept();
        // Asignar a la variable entrada el objeto new DataInputStream(conexion.getInputStream())
        entrada = new DataInputStream(conexion.getInputStream());

        
      } catch (Exception e) {
        e.printStackTrace();
      }      
    }//termina algoritmo 1
  }

  public static void main(String[] args) throws Exception
  {
    if (args.length != 2){
      System.err.println("Se debe pasar como parametros el numero del nodo y la IP del siguiente nodo");
      System.exit(1);
    }

    nodo = Integer.valueOf(args[0]);  // el primer parametro es el numero de nodo
    ip = args[1];  // el segundo parametro es la IP del siguiente nodo en el anillo

    // inicia Algoritmo 2

    // Declarar la variable w de tipo Worker.
    Worker w;
    // Asignar a la variable w el objeto new Worker().
    w = new Worker();
    // Invocar el método w.start().
    w.start();
    // Declarar la variable conexion de tipo Socket. Asignar null a la variable conexion.
    Socket conexion = null;
    // En un ciclo:
    while(true) {
      // En un bloque try:
      try {
        // Asignar a la variable conexion el objeto Socket(ip,50000).
        conexion = new Socket(ip, 50000);
        // Ejecutar break para salir del ciclo.
        break;
        
      } 
      // En el bloque catch:
      catch (Exception e) {
        //Invocar el método Thread.sleep(500).
        Thread.sleep(500);
      }
    }    
    // Asignar a la variable salida el objeto new DataOutputStream(conexion.getOutputStream()).
    salida = new DataOutputStream(conexion.getOutputStream());
    // Invocar el método w.join().
    w.join();
    // En un ciclo:
    while (true){

      // Si la variable nodo es cero:
      if (nodo == 0){
        // Si la variable primera_vez es true:
        if (primera_vez){
          // Asignar false a la variable primera_vez.
          primera_vez = false;
          token=1;
        }
        //Si la variable primera_vez es false:
        else {
          // Asignar a la variable token el resultado del método entrada.readInt().
          token = entrada.readInt();
          //Incrementa la variable contador
          contador++;
          //Desplegar las variables nodo,contador y token
          System.out.println("\nLos valores obtenidos son: \nnodo: " + nodo+" contador: " +contador +" token:" +token);
        }        
      }
      // Si la variable nodo no es cero:
      else {
        // Asignar a la variable token el resultado del método entrada.readInt().
        token = entrada.readInt();
        //incrementar la variable contador
        contador++;
        //Desplegar las variables nodo,contador y token 
          System.out.println("\nLos valores obtenidos son: \nnodo: " + nodo+" contador: " +contador +" token:" +token);
      }
      //Si la variable nodo es cero y la variable contador es igual a 1000:
      if (nodo==0 && contador==1000) {
        //Salir del ciclo
        break;
      }
      // Invocar el método salida.writeInt(token).    
      salida.writeInt(token);
    }
    //Termina algoritmo 2
  
  }
}