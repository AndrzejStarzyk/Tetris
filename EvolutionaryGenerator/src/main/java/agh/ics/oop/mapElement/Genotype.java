package agh.ics.oop.mapElement;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    public static final int length = 32;
    public static final int differentGenes = 8;
    private final Integer[] genes;

    public Genotype(){
        Random random = new Random();
        Integer[] genotype = new Integer[length];
        for (int i = 0; i < length; i++) {
            genotype[i] = random.nextInt(differentGenes);
        }
        Arrays.sort(genotype);
        this.genes = genotype;
    }

    public Genotype(Genotype genotype1, Genotype genotype2, int genesFrom1){
        Random random = new Random();
        Integer[] genotype = new Integer[length];
        Integer[] genes1 = genotype1.genes;
        Integer[] genes2 = genotype2.genes;
        if(random.nextBoolean()){
            for (int i = 0; i < length; i++) {
                genotype[i] = i < genesFrom1 ? genes1[i] : genes2[i];
            }
        }
        else{
            for (int i = 0; i < length; i++) {
                genotype[i] = i < length - genesFrom1 ? genes2[i] : genes1[i];
            }
        }
        Arrays.sort(genotype);
        this.genes = genotype;
    }

    public Integer[] getGenes() {
        return genes;
    }

    public int newMove(){
        Random random = new Random();
        return genes[random.nextInt(length)];
    }
}
