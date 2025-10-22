public class Atleta implements Runnable {

    @Override
    public void run() {
        //Quiero crear un String que avance hasta 100m del siguiente modo.
        //Atleta nªX: ----------10----------20----------30----------40----------50----------60----------70----------80----------90----------||100 !Felicidades!
        int metros = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i <= 100; i++) {
            str.append("-");
            if(i % 10 == 0){
                metros = i;
                if(i==100){
                    str.append("|| !Felicidades!");
                }
                str.append(metros);
                System.out.print(Thread.currentThread().getName() + ": " + str + "\n");
            }

        }
    }
}
