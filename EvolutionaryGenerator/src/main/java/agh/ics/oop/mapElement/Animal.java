package agh.ics.oop.mapElement;

import agh.ics.oop.*;
import agh.ics.oop.gui.App;
import agh.ics.oop.map.IMap;

import java.util.LinkedList;
import java.util.List;

public class Animal implements Comparable<Animal>, IMapElement, IChildObserver{
    private Vector2d position;
    private int energy;
    private MapDirection direction;
    private int age;
    private int children;
    private int descendants;

    private final IMap map;
    private final Genotype genotype;
    private final int dayOfBirth;
    private final Vector2d startPosition;

    private final List<IAnimalChangeObserver> animalChangeObservers = new LinkedList<>();
    private final List<IChildObserver> childObservers = new LinkedList<>();

    private final int moveEnergy = App.getMoveEnergy();
    private final int startEnergy = App.getStartEnergy();

    public Animal(Vector2d position, int energy, IMap map, Genotype genotype, int dayOfBirth) {
        this.position = position;
        this.energy = energy;
        this.direction = MapDirection.randomDirection();
        this.age = 0;
        this.children = 0;

        this.map = map;
        this.genotype = genotype;
        this.dayOfBirth = dayOfBirth;
        startPosition = position;

        map.placeAnimal(this);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public Genotype getGenotype() {
        return this.genotype;
    }

    public int getAge() {
        return age;
    }

    public int getChildren() {
        return children;
    }

    public int getDescendants() {
        return descendants;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    @Override
    public int compareTo(Animal o) {
        if(this.equals(o)){
            return 0;
        }
        if(this.energy != o.energy){
            return o.energy - this.energy;
        }
        if(this.dayOfBirth != o.dayOfBirth){
            return o.dayOfBirth - this.dayOfBirth;
        }
        if(this.startPosition.x != o.startPosition.x){
            return o.startPosition.x - this.startPosition.x;
        }
        return o.startPosition.y - this.startPosition.y;
    }

    private void moveUtil(boolean whetherForward){
        Vector2d oldPosition = position;
        Vector2d r = whetherForward ? this.direction.toVector() : this.direction.toVector().opposite();
        Vector2d newPosition = map.NewPosition(oldPosition, r);
        if(!newPosition.equals(oldPosition)){
            this.position = newPosition;
            positionChanged(oldPosition);
        }
    }

    private void rotate(int n){
        for (int i = 0; i < n; i++) {
        direction = direction.rotateRight();
    }
}

    public void move(){
        int direction = genotype.newMove();
        int oldEnergy = this.energy;
        this.energy -= moveEnergy;

        if(this.energy < 0){
            animalDied();
        }
        else{
            energyChanged(oldEnergy);
            switch (direction) {
                case 0 -> this.moveUtil(true);
                case 4 -> this.moveUtil(false);
                default -> this.rotate(direction);
            }
        }
    }

    public void reproduce(Animal animal, int currentDay){
        int oldEnergy1 = this.energy;
        int oldEnergy2 = animal.energy;
        int energyLost1 = (int)((double)this.energy/4.0);
        int energyLost2 = (int)((double)animal.energy/4.0);
        int genesFromThis = (int)((double)(this.energy*Genotype.length)/(double)(this.energy + animal.energy));
        this.energy -= energyLost1;
        this.energyChanged(oldEnergy1);
        animal.energy -= energyLost2;
        animal.energyChanged(oldEnergy2);
        Animal child = new Animal(this.position, energyLost1+energyLost2, this.map,
                new Genotype(this.genotype, animal.genotype, genesFromThis), currentDay);

        child.addChildObserver(this);
        child.addChildObserver(animal);
        newChild();
        animal.newChild();
    }

    public void newChild() {
        this.children++;
        for (IAnimalChangeObserver observer : animalChangeObservers) {
            observer.newChild(this.children);
        }
        newDescendant();
    }

    @Override
    public void newDescendant(){
        this.descendants++;
        for (IChildObserver predecessor : childObservers) {
            predecessor.newDescendant();
        }
        for (IAnimalChangeObserver observer : animalChangeObservers) {
            observer.newDescendant(this.descendants);
        }
    }

    public void increaseEnergy(int energy){
        int oldEnergy = this.energy;
        if(oldEnergy < startEnergy){
            this.energy = Math.min(this.energy + energy, startEnergy);
            energyChanged(oldEnergy);
        }
    }

    public void increaseAge(){
        age++;
    }

    private void positionChanged(Vector2d oldPosition){
        for(IAnimalChangeObserver observer : animalChangeObservers){
            observer.positionChanged(this, oldPosition);
        }
    }
    private void energyChanged(int oldEnergy){
        for (IAnimalChangeObserver observer : animalChangeObservers){
            observer.energyChanged(this, oldEnergy);
        }
    }

    private void animalDied(){
        for(IAnimalChangeObserver observer : animalChangeObservers){
            observer.animalDied(this);
        }
    }

    public void addAnimalObserver(IAnimalChangeObserver observer){
        animalChangeObservers.add(observer);
    }
    public void removeAnimalObserver(IAnimalChangeObserver observer){
        animalChangeObservers.remove(observer);
    }
    public void addChildObserver(IChildObserver observer){
        childObservers.add(observer);
    }
    public void removeChildObserver(IChildObserver observer){
        childObservers.remove(observer);
    }


}
