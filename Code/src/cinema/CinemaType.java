package cinema;

public enum CinemaType {
	PLATINUM("Platinum"),
	GOLD_CLASS("Gold Class"),
	NORMAL("Normal");

    private String name;

	CinemaType(String name) {
        this.name = name;
    }
    public String getName() {
		return name;
	}
}