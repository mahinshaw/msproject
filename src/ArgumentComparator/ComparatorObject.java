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
    private final double accuracy;

    public ComparatorObject(ArgumentObject user, ArgumentObject gen){
        this.generatorObject = gen;
        this.userObject = user;
        if (user != null && gen != null) {
            this.equivalent = user.equals(gen);
            this.accuracy = calculateAccuracy();
        }else {
            this.equivalent = false;
            this.accuracy = 0.0;
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

    public double getAccuracy(){ return this.accuracy; }

    public boolean compareHypothesis(){
        if (userObject == null || generatorObject == null)
            return false;
        return userObject.hypothesisEqual(generatorObject);
    }

    public boolean compareGeneralization(){
        if (userObject == null || generatorObject == null)
            return  false;
        return userObject.generalizationsEqual(generatorObject);
    }

    public boolean compareDatum(){
        if (userObject == null || generatorObject == null)
            return false;
        return userObject.datumEqual(generatorObject);
    }

    private double calculateAccuracy(){
        if (this.equivalent)
            return 1.0;
        double total = 2.0 + generatorObject.getGeneralizations().size();
        double sum = 0;
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
