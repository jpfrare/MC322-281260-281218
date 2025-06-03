public interface InterfaceFurtoCombustivel {
    // interface que permite que robos furtem combustivel (energia/lubrificante) uns dos outros

    //funcao que inicia o processo de furto de comustivel
    public void furtar_combustivel(InterfaceFurtoCombustivel furtado) throws EntradaException;

    // retorna o valor correspondente ao combustivel de quem sera furtado ou a quantidade a depender
    //do quanto de combustivel disponivel entre o robo que vai furtar e o que sera furtado
    //Robo AereoDinamico: Devido a dificuldades logisticas aereas retorna no maximo 30% de seu combustivel (coeficiente)
    //Robo TerrestreaOleo: Sendo um alvo mais facil, retorna ate 50% do seu combustivel (coeficiente)
    public float perder_combustivel(float quantidade) throws EntradaException;
    public float getCoeficiente();
    public void setCoeficiente(float coeficiente) throws EntradaException;

}
