package util;

import java.util.Arrays;
import java.util.List;

/**
 * Hilfsklasse für Kollektionen.
 */
public final class CollectionHelper {

	private CollectionHelper() {

	}

	/**
	 * Erstellt eine Liste.
	 *
	 * @param <T>
	 * 		Typ
	 * @param t
	 * 		Werte
	 *
	 * @return Liste.
	 */
	@SafeVarargs
	public static <T> List<T> createList(final T... t) {
		return Arrays.asList(t);
	}

}
