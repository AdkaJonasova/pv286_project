package converters;

public interface IConverter {
	String convertTo(String bitStr, String option);
	String convertFrom(String str, String option);
}
