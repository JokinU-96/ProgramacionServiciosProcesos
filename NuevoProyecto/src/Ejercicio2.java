public class Ejercicio2 {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("konsole", "--hold", "-e", "whoami");
        Process proceso = null;
        try {
            pb.inheritIO();
            proceso = pb.start();
            //proceso.getOutputStream().close();
        }catch (Exception e){

        }
    }
}
