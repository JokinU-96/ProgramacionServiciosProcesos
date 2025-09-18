public class Ejercicio2 {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "whoami");
        Process proceso = null;
        try {
            pb.inheritIO();
            proceso = pb.start();
            //proceso.getOutputStream().close();
        }catch (Exception e){

        }
    }
}
