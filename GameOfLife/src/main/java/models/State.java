package models;

public enum State {
	DEAD("Dead"), ALIVE("Alive");

	private String value;

	private State(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
};
