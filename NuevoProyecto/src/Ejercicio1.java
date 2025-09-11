import java.io.IOException;

public class Ejercicio1 {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("firefox");

            ProcessBuilder pb = new ProcessBuilder("firefox");
            Process p = pb.start();

            long pid = p.pid();

            String nombre = p.info().startInstant().toString();
            String tiempoCPU = String.valueOf(p.info().totalCpuDuration().get().toNanos());
            String usuario = String.valueOf(p.info().user());
            Boolean estado = p.isAlive();

            if(estado){
                System.out.println("El proceso está activo.");
            }else {
                System.out.println("El proceso no está activo.");
            }
            System.out.println(pid+"\nNombre del proceso: "+nombre+"\nTiempo de CPU: "+tiempoCPU+"\nUsuario: "+usuario);

            Boolean salir = false;

            while(salir = false){
                System.out.println("Para cerrar Firefox escriba 1");
                int opcion = Integer.parseInt(System.console().readLine());
                if(opcion == 1){
                    salir = true;
                    p.destroy();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
