package converters;

/**
 * @author Michal Badin
 */
public interface IConverter {
	String convertTo(String bitStr);
	String convertFrom(String str);
}
