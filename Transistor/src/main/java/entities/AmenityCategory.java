package entities;

public enum AmenityCategory {
    HEALTHCARE("Healthcare"),
    ENTERTAINMENT("Entertainment"),
    EDUCATION("Education"),
    TOURISM("Tourism"),
    PUBLIC_SERVICES("Public services"),
    SHOP("Shopping"),
    TRANSPORTATION("Transport");

    private final String name;
    private AmenityCategory(String name)
    {
     this.name = name;
    }
    public String getSpeedInKilometersPerSecond() { return name; }
}
