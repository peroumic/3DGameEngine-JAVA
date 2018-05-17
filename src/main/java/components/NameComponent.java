package components;

import componentArchitecture.Component;

public class NameComponent implements Component {
    //what kind of entity this is
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
