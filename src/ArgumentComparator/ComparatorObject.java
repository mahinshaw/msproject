package ArgumentComparator;

import ArgumentStructure.ArgumentObject;
import ArgumentStructure.Generalization;
import GAIL.src.model.Argument;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 3/12/14
 * github: https://github.com/mahinshaw/msproject
 */
public class ComparatorObject {
    private final ArgumentObject generatorObject;
    private final ArgumentObject userObject;
    private final boolean equivalent;
    private final float accuracy;

    public ComparatorObject(ArgumentObject gen, ArgumentObject user){
        this.generatorObject = gen;
        this.userObject = user;
        this.equivalent = gen.equals(user);
        this.accuracy = calculateAccuracy();
    }

    public boolean isEquivalent(){
        return this.equivalent;
    }

    public ArgumentObject getGeneratorObject(){
        return generatorObject;
    }

    public ArgumentObject getUserObject(){
        return userObject;
    }

    public float getAccuracy(){ return this.accuracy; }

    private float calculateAccuracy(){
        float total = 2 + generatorObject.getGeneralizations().size();
        float sum = 0;
        if (generatorObject.getHypothesis().equals(userObject.getHypothesis()))
            sum++;
        if (generatorObject.getDatum().equals(userObject.getDatum()))
            sum++;
        for (Generalization g : generatorObject.getGeneralizations()){
            if (userObject.containsGeneralization(g))
                sum++;
        }
        return sum/total;
    }
}
