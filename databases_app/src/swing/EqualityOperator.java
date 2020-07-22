package swing;

public enum EqualityOperator {
	First(""), Second("="), Third(">"), Fourth("<"), Fifth(">="), Sixth("<=");
	private final String display;
	private EqualityOperator(String s) {
		display = s;
	}
	@Override
	public String toString() {
		return display;
	}
}
