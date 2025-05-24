public  class SensorTemperatura extends Sensor {
    private final Robo dono;

    public SensorTemperatura(Robo dono, int raio) {
        super(raio);
        this.dono = dono;
    }

    public void analise_temperatura() {
        if (dono.getZ() < 0) {
            System.out.println("Não é possível fazer análise de temperatura para o robô (está no subsolo)");
            return; //não é possível fazer análise de temperatura no subsolo
        }

        //ajustando limites em xy
        int menorx = dono.getX() - this.getRaio();
        if (menorx < 0) menorx = 0;

        int maiorx = dono.getX() + this.getRaio();
        if (maiorx > dono.getAmbiente().getAmbienteX()) maiorx = dono.getAmbiente().getAmbienteX();

        int menory = dono.getY() - this.getRaio();
        if (menory < 0) menory = 0;

        int maiory = dono.getY() + this.getRaio();
        if (maiory > dono.getAmbiente().getAmbienteY()) maiory = dono.getAmbiente().getAmbienteY();

        //inicializando as variaveis que guardam os picos de temperatura, bem como a temperatura local
        int[][][] mapa_temperatura = dono.getAmbiente().getTemperatura();
        int temperatura_local = mapa_temperatura[dono.getX()][dono.getY()][dono.getZ()];
        int menor_temperatura = temperatura_local;
        int maior_temperatura = temperatura_local;
        int i_menor = dono.getX(), j_menor = dono.getY(), k_menor = dono.getZ();
        int i_maior = i_menor, j_maior = j_menor, k_maior = k_menor;


        //análises
        if (dono instanceof RoboTerrestre) { //análise 2d
            for (int i = menorx; i <= maiorx; i++) {
                for (int j = menory; j <= maiory; j++) {
                    if (mapa_temperatura[i][j][0] > maior_temperatura){
                        i_maior = i;
                        j_maior = j;
                        maior_temperatura = mapa_temperatura[i][j][0];
                    }

                    else if (mapa_temperatura[i][j][0] < menor_temperatura){
                        i_menor = i;
                        j_menor = j;
                        menor_temperatura = mapa_temperatura[i][j][0];
                    }
                }
            }
        }


        else if (dono instanceof RoboAereo) {

        //ajustando limites em z
        int menorz = dono.getZ() - this.getRaio();
        if (menorz < 0) menorz = 0;

        int maiorz = dono.getZ() + this.getRaio();
        if (maiorz > dono.getAmbiente().getAltura()) maiorz = dono.getAmbiente().getAltura();


            for (int i = menorx; i <= maiorx; i++) {
                for (int j = menory; j <= maiory; j++) {
                    for (int k = menorz; k <= maiorz; k++) {

                        if (mapa_temperatura[i][j][k] > maior_temperatura) {
                            i_maior = i;
                            j_maior = j;
                            k_maior = k;
                            maior_temperatura = mapa_temperatura[i][j][k];

                        } else if (mapa_temperatura[i][j][k] < menor_temperatura) {
                            i_menor = i;
                            j_menor = j;
                            k_menor = k;
                            menor_temperatura = mapa_temperatura[i][j][k];
                        }
                    }
                }
            }
        }

        System.out.printf("Análise de Temperatura da região ao redor do Robô %s \n", dono.getNome());
        System.out.printf("Temperatura local (%d, %d, %d) = %d \n", dono.getX(), dono.getY(), dono.getZ(), temperatura_local);
        System.out.printf("Menor temperatura (%d, %d, %d) = %d \n" , i_menor, j_menor, k_menor, menor_temperatura);
        System.out.printf("Maior temperatura (%d, %d, %d) = %d \n", i_maior, j_maior, k_maior, maior_temperatura);
    }
} 
