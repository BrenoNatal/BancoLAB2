
public class Correntista {

    private static String nome;
    public static long cpf;

    public Correntista(String nomeDoCorrentista, long cpfDoCorrentista) {
        cpf = cpfDoCorrentista;
        nome = nomeDoCorrentista;


    }

    public static String getNome() { return nome; }

    public static void setNome(String Nome){
        nome = Nome;
    }

    public static long GetCpfDoCorrentista(){
        return cpf;
    }

}
