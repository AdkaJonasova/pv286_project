package converters;

/**
 * @author Michal Badin
 */
public interface IConverter {
	String convertTo(String bitStr, boolean isBigEndian);
	String convertFrom(String str, boolean isBigEndian);
}
