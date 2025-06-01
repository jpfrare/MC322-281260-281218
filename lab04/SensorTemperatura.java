public  class SensorTemperatura extends Sensor {
    private final Robo dono;
    private int[] maior_temperatura; //ponto de maior temperatura
    private int[] menor_temperatura; //ponto de menor temperatura

    public SensorTemperatura(Robo dono, int raio) {
        super(raio);
        this.dono = dono;
        this.maior_temperatura = new int[3];
        this.menor_temperatura = new int[3];

        System.out.println("\nPrimeira análise do Robô! Automática!:");

        this.analise_temperatura();
    }

    final public void analise_temperatura() {

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
        this.menor_temperatura[0] = dono.getX();
        this.menor_temperatura[1] = dono.getY();
        this.menor_temperatura[2] = dono.getZ();
        this.maior_temperatura[0] = this.menor_temperatura[0];
        this.maior_temperatura[1] = this.menor_temperatura[1];
        this.maior_temperatura[2] = this.menor_temperatura[2];


        //análises
        if (dono instanceof RoboTerrestre) { //análise 2d
            for (int i = menorx; i <= maiorx; i++) {
                for (int j = menory; j <= maiory; j++) {
                    if (mapa_temperatura[i][j][0] > maior_temperatura){
                        this.maior_temperatura[0] = i;
                        this.maior_temperatura[1] = j;
                        maior_temperatura = mapa_temperatura[i][j][0];
                    }

                    else if (mapa_temperatura[i][j][0] < menor_temperatura){
                        this.menor_temperatura[0] = i;
                        this.menor_temperatura[1] = j;
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
        if (maiorz > dono.getAmbiente().getAmbienteZ()) maiorz = dono.getAmbiente().getAmbienteZ();


            for (int i = menorx; i <= maiorx; i++) {
                for (int j = menory; j <= maiory; j++) {
                    for (int k = menorz; k <= maiorz; k++) {

                        if (mapa_temperatura[i][j][k] > maior_temperatura) {
                            this.maior_temperatura[0]= i;
                            this.maior_temperatura[1]= j;
                            this.maior_temperatura[2] = k;
                            maior_temperatura = mapa_temperatura[i][j][k];

                        } else if (mapa_temperatura[i][j][k] < menor_temperatura) {
                            this.menor_temperatura[0] = i;
                            this.menor_temperatura[1] = j;
                            this.menor_temperatura[2] = k;
                            menor_temperatura = mapa_temperatura[i][j][k];
                        }
                    }
                }
            }
        }

        System.out.printf("Análise de Temperatura da região ao redor do Robô %s \n", dono.getNome());
        System.out.printf("Temperatura local (%d, %d, %d) = %d \n", dono.getX(), dono.getY(), dono.getZ(), temperatura_local);
        System.out.printf("Menor temperatura (%d, %d, %d) = %d \n" , this.menor_temperatura[0], this.menor_temperatura[1],this.menor_temperatura[2], menor_temperatura);
        System.out.printf("Maior temperatura (%d, %d, %d) = %d \n", this.maior_temperatura[0], this.maior_temperatura[1], this.maior_temperatura[2], maior_temperatura);
    }

    public int[] getMaiorTemperatura() {
        return this.maior_temperatura;
    }

    public int[] getMenorTemperatura() {
        return this.menor_temperatura;
    }
} 
