package model;
/** In-house class inherited from the abstract part model*/
public class InHouse extends Part {
    private int machineId;
    /**
     * The constructors are created for the part ID, name, price, stock, min, max and machineId attributes.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * @return machineID getter
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * @param machineId setter
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}



