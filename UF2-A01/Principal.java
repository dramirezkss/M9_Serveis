public class Principal {
    public static void main(String[] args) {
        Fil juan = new Fil("Juan");
        Fil pepe = new Fil("Pepe");

        pepe.start();
        try {
            pepe.join(); // Espera fins que Pepe acabi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        juan.start();
        System.out.println("Termina thread main");
    }
}