public class MovimentoRelatorException extends Exception{
    public MovimentoRelatorException(){
        super("RoboAereoRelator nao eh capaz de realizar movimento multidimensional simultaneamente nas tres dimensoes (movimento dinamico)");
    }
}
