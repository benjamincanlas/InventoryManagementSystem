package model;
/** Outsource class inherited from the abstract part model*/
public class Outsourced extends Part {
    private String companyName;
    /**
     * The constructors are created for the part ID, name, price, stock, min, max and company name attributes.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * @return companyName getter
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * @param companyName setter
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

