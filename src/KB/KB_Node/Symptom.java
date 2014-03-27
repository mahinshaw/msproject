package KB.KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Symptom extends KB_Node {

    private String symptom_id;
    private String symptom_name;
    private String degree;

    public Symptom(int id, int person_id, String symptom_id, String symptom_name, String degree, String bioAbnormal) {
        super(id, person_id, bioAbnormal);
        this.symptom_id = symptom_id;
        this.symptom_name = symptom_name;
        this.degree = degree;
    }

    public String getSymptom_id() {
        return symptom_id;
    }

    public void setSymptom_id(String symptom_id) {
        this.symptom_id = symptom_id;
    }

    public String getSymptom_name() {
        return symptom_name;
    }

    public void setSymptom_name(String symptom_name) {
        this.symptom_name = symptom_name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String toString() {
        return super.toString() +
                "Symptom ID: " + this.symptom_id + "\n" +
                "Symptom name: " + this.symptom_name + "\n" +
                "Degree: " + this.degree + "\n";
    }
}
