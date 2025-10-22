import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Intro {
    public static void main(String[] args) {
        ProcessBuilder proceso = new ProcessBuilder("kcalc");
        ProcessBuilder comandos = new ProcessBuilder("konsole","--hold", "-e", "ls -la");
        try {
            Process calcu = proceso.start();
            comandos.inheritIO();
            comandos.start();

            Runtime.getRuntime().exec("kcalc");

            calcu.destroy();
            calcu.destroyForcibly();//Cuando el destroy no cierra el proceso.

            Runtime r = Runtime.getRuntime();
            String comando = "cmd /c dir";
            
            Process p;
            p = r.exec(comando);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}