package interfaces;

public interface InterfaceRoboMissionario {
    //interface implementada pelos agentes inteligentes (robos que implementam missoes)
    public void executarMissao(String caminhoArquivo);
    public void definirMissao(InterfaceMissao m);
    public boolean temMissao();
}
