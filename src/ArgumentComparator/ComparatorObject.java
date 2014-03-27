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

    public ComparatorObject(ArgumentObject user, ArgumentObject gen){
        this.generatorObject = gen;
        this.userObject = user;
        if (user != null && gen != null) {
            this.equivalent = gen.equals(user);
            this.accuracy = calculateAccuracy();
        }else {
            this.equivalent = false;
            this.accuracy = 0;
        }
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

    public boolean compareHypothesis(){
        return generatorObject.hypothesisEqual(userObject);
    }

    public boolean compareGeneralization(){
        return generatorObject.generalizationsEqual(userObject);
    }

    public boolean compareDatum(){
        return generatorObject.datumEqual(userObject);
    }

    private float calculateAccuracy(){
        float total = 2 + generatorObject.getGeneralizations().size();
        float sum = 0;
        if (compareHypothesis())
            sum++;
        if (compareDatum())
            sum++;
        for (Generalization g : generatorObject.getGeneralizations()){
            if (userObject.containsGeneralization(g))
                sum++;
        }
        return sum/total;
    }
}
