package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import agh.ics.oop.map.IMap;
import agh.ics.oop.mapElement.Animal;
import agh.ics.oop.mapElement.Genotype;

import java.util.*;

public class SimulationEngine implements Runnable, IMapChangeObserver{
    private final IMap map;
    private final App app;

    private int currentDay=1;
    private final List<Animal> animals = new LinkedList<>();
    private final List<Animal> deadAnimals = new LinkedList<>();
    private final List<Animal> bornAnimals = new LinkedList<>();
    private final List<Animal> copyAnimals = new LinkedList<>();
    private int nOfGrass;
    private final HashMap<Integer[], Integer> genotypes = new HashMap<>();
    private int sumAgeOfDead = 0;
    private  int nOfDead;
    private boolean engineStopped = false;

    private final int startEnergy = App.getStartEnergy();
    private final int plantEnergy = App.getPlantEnergy();
    private final  int animalsAtStart = App.getAnimalsAtStart();
    private final int moveDelay = App.getMoveDelay();
    private final boolean isMagic;
    private int magicTokens = 3;

    private final List<ISimulationObserver> simulationObservers = new LinkedList<>();

    public SimulationEngine(IMap map, App app, boolean isMagic) {
        this.map = map;
        map.addMapObserver(this);
        this.app = app;
        nOfGrass = 0;
        this.isMagic = isMagic;
    }

    public boolean isEngineStopped() {
        return engineStopped;
    }

    @Override
    public void run() {
            Set<Vector2d> initialPositions = new HashSet<>();
            Random random = new Random();
            Vector2d position;
            for (int i = 0; i < animalsAtStart; i++) {
                do{
                    position = new Vector2d(random.nextInt(map.getUpperRight().x), random.nextInt(map.getUpperRight().y));
                }while (initialPositions.contains(position));
                new Animal(position, startEnergy, map, new Genotype(), currentDay);
                initialPositions.add(position);
            }
            animals.addAll(bornAnimals);
            bornAnimals.clear();
            addMagically();

            while(animals.size() > 0){
                removeDead();
                animalsMove();
                animalsEat();
                animalsReproduce();
                map.placeGrass();
                endDay();
            }
    }
    
    private void removeDead(){
        for (Animal animal : deadAnimals) {
            this.map.removeAnimal(animal);
            checkForStop();
            addMagically();
        }
        deadAnimals.clear();
    }
    
    private void animalsMove(){
        for (Animal animal : animals) {
            try {
                Thread.sleep(moveDelay);
                animal.move();
                checkForStop();
                addMagically();
            }
            catch (InterruptedException interruptedException){
                System.out.println(interruptedException.getMessage());
                System.exit(0);
            }
        }
    }

    private void animalsEat(){
        for (Vector2d position : this.map.getAnimals().keySet()) {
            if(map.isGrassOn(position)){
                PriorityQueue<Animal> animalsHere = this.map.getAnimals().get(position);
                int nOfStrongest = map.nOfStrongest(position);

                int energy = (int)((double)plantEnergy/(double)nOfStrongest);
                List<Animal> strongest = new LinkedList<>();
                for (Animal animal : animalsHere) {
                    if(animal.getEnergy() == map.maxEnergy(position)) strongest.add(animal);
                }
                for (Animal animal : strongest) {
                    animal.increaseEnergy(energy);
                }
                map.removeGrass(position);
            }
            checkForStop();
        }
    }

    private void animalsReproduce(){
        for (Vector2d position : map.getAnimals().keySet()) {

            int maxEnergy = map.maxEnergy(position);
            PriorityQueue<Animal> animalsHere = map.getAnimals().get(position);

            if(maxEnergy * 2 >= startEnergy && animalsHere.size() > 1){
                int nOfStrongest = map.nOfStrongest(position);

                if(nOfStrongest > 1){
                    Random random = new Random();
                    int first = random.nextInt(nOfStrongest);
                    int second;
                    do{
                        second = random.nextInt(nOfStrongest);
                    }while (first != second);

                    List<Animal> strongest = new LinkedList<>();
                    for (Animal animal : animalsHere) {
                        if(animal.getEnergy() == maxEnergy) strongest.add(animal);
                    }

                    strongest.get(first).reproduce(strongest.get(second), currentDay);
                }
                else{
                    Animal strongest = animalsHere.poll();
                    int secondEnergy = map.maxEnergy(position);
                    animalsHere.add(strongest);
                    if(secondEnergy * 2 > startEnergy){
                        List<Animal> secondStrongest = new LinkedList<>();
                        for (Animal animal : animalsHere) {
                            if(animal.getEnergy() == secondEnergy) secondStrongest.add(animal);
                        }

                        Random random = new Random();

                        strongest.reproduce(secondStrongest.get(random.nextInt(secondStrongest.size())), currentDay);
                    }

                }
            }
            checkForStop();
            addMagically();
        }
    }

    private void endDay(){
        animals.addAll(bornAnimals);
        bornAnimals.clear();

        for (ISimulationObserver observer : simulationObservers) {
            observer.updateStatistics(currentDay, animals.size(), nOfGrass, getDominantGenotype(),
                    getAverageEnergy(), getAverageLiveLength(), getAverageChildren());
        }

        currentDay++;
        for (Animal animal : animals) {
            animal.increaseAge();
        }
        try{
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void addMagically(){
        if(magicTokens > 0 && isMagic && copyAnimals.size() == 5){
            for (Animal animal : copyAnimals) {
                new Animal(animal.getPosition(), startEnergy, map, animal.getGenotype(), currentDay);
                checkForStop();
            }
            magicTokens--;
        }
    }


    private Integer[] getDominantGenotype(){
        Integer[] res = new Integer[0];
        int mostOccurrences = 0;
        for (Integer occurrences : genotypes.values()) {
            mostOccurrences = Math.max(mostOccurrences, occurrences);
        }
        for (Integer[] genes : genotypes.keySet()) {
            if(genotypes.get(genes) == mostOccurrences) res = genes;
        }
        return res;
    }

    private double getAverageEnergy(){
        int s = 0;
        for (Animal animal : animals) {
            s += animal.getEnergy();
        }
        return (double)s/(double)animals.size();
    }

    private double getAverageLiveLength(){
        return (double)sumAgeOfDead/(double)nOfDead;
    }

    private double getAverageChildren(){
        int s = 0;
        for (Animal animal : animals) {
            s += animal.getDescendants();
        }
        return (double)s/(double)animals.size();
    }

    private void checkForStop(){
        synchronized (this){
            while(this.app.checkForStop(this)){
                try {
                    engineStopped = true;
                    this.wait();
                    engineStopped = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Vector2d> getDominantPositions(){
        List<Vector2d> res = new LinkedList<>();
        Integer[] dominantGenotype = getDominantGenotype();
        for (Vector2d position: map.getAnimals().keySet()) {
            for (Animal animal : map.getAnimals().get(position)) {
                if (Arrays.equals(animal.getGenotype().getGenes(), dominantGenotype)) {
                    res.add(position);
                    break;
                }
            }
        }
        return res;
    }

    @Override
    public void animalRemoved(Animal animal) {
        animals.remove(animal);
        copyAnimals.remove(animal);
        Integer[] genes = animal.getGenotype().getGenes();
        int n = genotypes.get(genes);
        if(n > 1){
            genotypes.put(genes, n-1);
        }
        else {
            genotypes.remove(genes);
        }
    }

    @Override
    public void animalAdded(Animal animal) {
        bornAnimals.add(animal);
        copyAnimals.add(animal);
        Integer[] genes = animal.getGenotype().getGenes();
        if(genotypes.containsKey(genes)){
            int n = genotypes.get(genes);
            genotypes.put(genes, n+1);
        }
        else {
            genotypes.put(genes, 1);
        }
    }

    @Override
    public void animalDied(Animal animal) {
        deadAnimals.add(animal);
        nOfDead++;
        sumAgeOfDead += animal.getAge();
    }

    @Override
    public void grassRemoved(Vector2d position) {
        nOfGrass--;
    }

    @Override
    public void grassAdded(Vector2d position) {
        nOfGrass++;
    }

    public void addSimulationObserver(ISimulationObserver observer){
        simulationObservers.add(observer);
    }
    
}
