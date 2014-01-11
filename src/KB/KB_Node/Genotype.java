package KB.KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Genotype extends KB_Node {

    private String gene_id;
    private String gene_name;
    private String mutated;
    private String autosomal_type;
    private String disease;
    private String cell;

    public Genotype(int id, int person_id, String gene_id, String gene_name, String mutated, String autosomal_type, String disease) {
        super(id, person_id, false);//TODO Temp boolean for test
        this.gene_id = gene_id;
        this.gene_name = gene_name;
        this.mutated = mutated;
        this.autosomal_type = autosomal_type;
        this.disease = disease;
        this.cell = null;
    }

    public String getGene_id() {
        return gene_id;
    }

    public String getGene_name() {
        return gene_name;
    }

    public String getMutated() {
        return mutated;
    }

    public String getAutosomal_type() {
        return autosomal_type;
    }

    public String getDisease() {
        return disease;
    }

    public String getCell() {
        return cell;
    }

    public void setGene_id(String gene_id) {
        this.gene_id = gene_id;
    }

    public void setGene_name(String gene_name) {
        this.gene_name = gene_name;
    }

    public void setMutated(String mutated) {
        this.mutated = mutated;
    }

    public void setAutosomal_type(String autosomal_type) {
        this.autosomal_type = autosomal_type;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String toString(){
        return super.toString() +
                "Gene ID: " + this.gene_id + "\n" +
                "Gene Name: " + this.gene_name + "\n" +
                "Mutated: " + this.mutated + "\n" +
                "Autosomal type: " + this.autosomal_type + "\n" +
                "Disease: " + this.disease + "\n" +
                "Cell: " + this.cell + "\n";
    }
}
