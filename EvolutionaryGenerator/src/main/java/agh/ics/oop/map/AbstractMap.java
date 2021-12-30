package agh.ics.oop.map;

import agh.ics.oop.IAnimalChangeObserver;
import agh.ics.oop.Vector2d;
import agh.ics.oop.gui.App;
import agh.ics.oop.gui.IDisplayChangeObserver;
import agh.ics.oop.gui.IMapChangeObserver;
import agh.ics.oop.mapElement.*;

import java.util.*;

public abstract class AbstractMap implements IMap, IAnimalChangeObserver {
    protected final Vector2d upperRight;
    protected final Vector2d lowerLeft = new Vector2d(0,0);
    protected final double jungleRatio = App.getJungleRatio();
    protected final Vector2d jungleLowerLeft;
    protected final Vector2d jungleUpperRight;
    protected final int jungleFields;
    protected final int savannahFields;
    protected int freeJungleFields;
    protected int freeSavannahFields;
    protected final HashMap<Vector2d, PriorityQueue<Animal>> animals = new HashMap<>();
    protected final Set<Vector2d> grass = new HashSet<>();
    protected final List<IMapChangeObserver> mapObservers = new LinkedList<>();
    protected final List<IDisplayChangeObserver> displayObservers = new LinkedList<>();

    public AbstractMap(int width, int height) {
        upperRight = new Vector2d(width-1, height-1);
        jungleLowerLeft = new Vector2d( (int)((1-jungleRatio) *(double)(upperRight.x+1)*0.5),
                                                    (int)((1-jungleRatio)*(double)(upperRight.y+1)*0.5));
        jungleUpperRight = jungleLowerLeft.add(new Vector2d((int)(jungleRatio*(double)(upperRight.x+1))-1,
                                                             (int)(jungleRatio*(double)(upperRight.y+1))-1));
        freeJungleFields = (jungleUpperRight.subtract(jungleLowerLeft).x+1) * (jungleUpperRight.subtract(jungleLowerLeft).y+1);
        freeSavannahFields = (upperRight.x+1) * (upperRight.y+1) - freeJungleFields;
        jungleFields = freeJungleFields;
        savannahFields = freeJungleFields;
    }

    @Override
    public Vector2d getUpperRight() {
        return upperRight;
    }
    @Override
    public HashMap<Vector2d, PriorityQueue<Animal>> getAnimals() {
            return animals;
    }
    @Override
    public Set<Vector2d> getGrass() {
        return grass;
    }
    @Override
    public boolean inJungle(Vector2d position){
        return position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight);
    }
    @Override
    public boolean noAnimal(Vector2d position){
        return !animals.containsKey(position);
    }
    @Override
    public boolean isGrassOn(Vector2d position){
        return grass.contains(position);
    }
    @Override
    public int maxEnergy(Vector2d position){
        return Objects.requireNonNull(animals.get(position).peek()).getEnergy();
    }
    @Override
    public int nOfStrongest(Vector2d position){
        int n=0;
        for (Animal animal : animals.get(position)) {
            if(animal.getEnergy() == maxEnergy(position)) n++;
        }
        return n;
    }

    private void updateFreeFields(Vector2d position, boolean elementAppears){
        if(inJungle(position)){
            if (elementAppears) freeJungleFields--;
            else freeJungleFields++;
        }
        else{
            if (elementAppears) freeSavannahFields--;
            else freeSavannahFields++;
        }
    }

    @Override
    public void placeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        if (noAnimal(position)) {
            animals.put(position, new PriorityQueue<>());
            updateFreeFields(position, true);
        }
        animals.get(position).add(animal);
        animal.addAnimalObserver(this);
        animalAdded(animal);
        animalArrived(animal);
    }

    @Override
    public void removeAnimal(Animal animal){
        Vector2d position = animal.getPosition();
        animals.get(position).remove(animal);
        if (animals.get(position).isEmpty()) {
            animals.remove(position);
            updateFreeFields(position, false);
        }
        animal.removeAnimalObserver(this);
        animalRemoved(animal);
        animalLeft(position);
    }

    @Override
    public void placeGrass(){
        if(freeJungleFields > 0) {
            Random random = new Random();
            Vector2d position;
            do {
                position = jungleLowerLeft.add(new Vector2d(random.nextInt(jungleUpperRight.x - jungleLowerLeft.x+1),
                        random.nextInt(jungleUpperRight.x - jungleLowerLeft.x + 1)));
            }
            while (isGrassOn(position) || !noAnimal(position));
            grass.add(position);
            updateFreeFields(position, true);
            grassAdded(position);
        }
        if(freeSavannahFields > 0){
            Random random = new Random();
            Vector2d position;
            do{
                position = lowerLeft.add(new Vector2d(random.nextInt(upperRight.x - lowerLeft.x + 1),
                        random.nextInt(upperRight.y - lowerLeft.y + 1)));
            }
            while(inJungle(position) || isGrassOn(position) || !noAnimal(position));
            grass.add(position);
            updateFreeFields(position, true);
            grassAdded(position);
        }
    }

    @Override
    public void removeGrass(Vector2d position){
        grass.remove(position);
        updateFreeFields(position, false);
        grassRemoved(position);
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition) {
        animals.get(oldPosition).remove(animal);
        if (animals.get(oldPosition).isEmpty()) {
            animals.remove(oldPosition);
            updateFreeFields(oldPosition, false);
        }
        animalLeft(oldPosition);
        if (noAnimal(animal.getPosition())) {
            animals.put(animal.getPosition(), new PriorityQueue<>());
            updateFreeFields(animal.getPosition(), true);
        }
        animals.get(animal.getPosition()).add(animal);
        animalArrived(animal);
    }

    @Override
    public void energyChanged(Animal animal, int oldEnergy){
        animals.get(animal.getPosition()).remove(animal);
        animals.get(animal.getPosition()).add(animal);
        for (IDisplayChangeObserver observer : displayObservers) {
            observer.updateMaxEnergy(animal.getPosition(),
                    Objects.requireNonNull(animals.get(animal.getPosition()).peek()).getEnergy());
        }
    }

    @Override
    public void animalDied(Animal animal){
        for (IMapChangeObserver observer : mapObservers) {
            observer.animalDied(animal);
        }
    }

    private void animalRemoved(Animal animal) {
        for (IMapChangeObserver observer : mapObservers) {
            observer.animalRemoved(animal);
        }
    }

    private void animalAdded(Animal animal) {
        for (IMapChangeObserver observer : mapObservers) {
            observer.animalAdded(animal);
        }
    }

    private void grassRemoved(Vector2d position) {
            for (IDisplayChangeObserver observer : displayObservers) {
                observer.grassRemoved(position);
            }
        for (IMapChangeObserver observer : mapObservers) {
            observer.grassRemoved(position);
        }
    }

    private void grassAdded(Vector2d position) {
            for (IDisplayChangeObserver observer : displayObservers) {
                observer.grassAdded(position);
            }
        for (IMapChangeObserver observer : mapObservers) {
            observer.grassAdded(position);
        }
    }

    private void animalLeft(Vector2d position){
        for (IDisplayChangeObserver observer : displayObservers) {
            observer.animalLeft(position, noAnimal(position),
                    noAnimal(position) ? 0 : Objects.requireNonNull(animals.get(position).peek()).getEnergy());
        }
    }

    private void animalArrived(Animal animal){
        for (IDisplayChangeObserver observer : displayObservers) {
            observer.animalArrived(animal.getPosition(), animal.getEnergy());
        }
    }

    public void addMapObserver(IMapChangeObserver observer){
        mapObservers.add(observer);
    }
    public void removeMapObserver(IMapChangeObserver observer){
        mapObservers.remove(observer);
    }
    public void addDisplayObserver(IDisplayChangeObserver observer){
        displayObservers.add(observer);
    }
    public void removeDisplayObserver(IDisplayChangeObserver observer){
        displayObservers.remove(observer);
    }

    public void newChild(int children){};
    public void newDescendant(int descendants){};
}
