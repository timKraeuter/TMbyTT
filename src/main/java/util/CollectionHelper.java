package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	/**
	 * Erstellt ein Set.
	 *
	 * @param <T>
	 * 		Typ
	 * @param t
	 * 		Werte
	 *
	 * @return Set.
	 */
	@SafeVarargs
	public static <T> Set<T> createSet(final T... t) {
		return new HashSet<>(Arrays.asList(t));
	}
}
