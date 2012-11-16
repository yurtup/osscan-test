package com.outscale.osscan.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Test file for testing {@link com.outscale.osscan.ejb.analyzer.OsscanAnalyzerService}. Contains some methods of
 * http://svn.apache.org/repos/asf/commons/proper/collections/trunk/src/java/org/apache/commons/collections/ListUtils.java
 *
 */
public class ListUtilsTest
{
	/**
	 * An empty unmodifiable list.
	 * This uses the {@link Collections Collections} implementation
	 * and is provided for completeness.
	 */
	public static final List<Object> EMPTY_LIST = Collections.<Object>emptyList();

	/**
	 * <code>ListUtils</code> should not normally be instantiated.
	 */
	public ListUtilsTest()
	{
	}

	//-----------------------------------------------------------------------

	/**
	 * Returns a new list containing all elements that are contained in
	 * both given lists.
	 *
	 * @param list1 the first list
	 * @param list2 the second list
	 * @return the intersection of those two lists
	 * @throws NullPointerException if either list is null
	 */
	public static <E> List<E> intersection(final List<? extends E> list1, final List<? extends E> list2)
	{
		final List<E> result = new ArrayList<E>();

		List<? extends E> smaller = list1;
		List<? extends E> larger = list2;
		if (list1.size() > list2.size())
		{
			smaller = list2;
			larger = list1;
		}

		HashSet<E> hashSet = new HashSet<E>(smaller);

		for (E e : larger)
		{
			if (hashSet.contains(e))
			{
				result.add(e);
				hashSet.remove(e);
			}
		}
		return result;
	}

	/**
	 * Subtracts all elements in the second list from the first list,
	 * placing the results in a new list.
	 * <p/>
	 * This differs from {@link List#removeAll(Collection)} in that
	 * cardinality is respected; if <Code>list1</Code> contains two
	 * occurrences of <Code>null</Code> and <Code>list2</Code> only
	 * contains one occurrence, then the returned list will still contain
	 * one occurrence.
	 *
	 * @param list1 the list to subtract from
	 * @param list2 the list to subtract
	 * @return a new list containing the results
	 * @throws NullPointerException if either list is null
	 */
	public static <E> List<E> subtract(final List<E> list1, final List<? extends E> list2)
	{
		final ArrayList<E> result = new ArrayList<E>(list1);
		for (E e : list2)
		{
			result.remove(e);
		}
		return result;
	}

	/**
	 * Returns the sum of the given lists.  This is their intersection
	 * subtracted from their union.
	 *
	 * @param list1 the first list
	 * @param list2 the second list
	 * @return a new list containing the sum of those lists
	 * @throws NullPointerException if either list is null
	 */
	public static <E> List<E> sum(final List<? extends E> list1, final List<? extends E> list2)
	{
		return subtract(union(list1, list2), intersection(list1, list2));
	}

	/**
	 * Returns a new list containing the second list appended to the
	 * first list.  The {@link List#addAll(Collection)} operation is
	 * used to append the two given lists into a new list.
	 *
	 * @param list1 the first list
	 * @param list2 the second list
	 * @return a new list containing the union of those lists
	 * @throws NullPointerException if either list is null
	 */
	public static <E> List<E> union(final List<? extends E> list1, final List<? extends E> list2)
	{
		final ArrayList<E> result = new ArrayList<E>(list1);
		result.addAll(list2);
		return result;
	}

	/**
	 * Tests two lists for value-equality as per the equality contract in
	 * {@link java.util.List#equals(java.lang.Object)}.
	 * <p/>
	 * This method is useful for implementing <code>List</code> when you cannot
	 * extend AbstractList. The method takes Collection instances to enable other
	 * collection types to use the List implementation algorithm.
	 * <p/>
	 * The relevant text (slightly paraphrased as this is a static method) is:
	 * <blockquote>
	 * Compares the two list objects for equality.  Returns
	 * <tt>true</tt> if and only if both
	 * lists have the same size, and all corresponding pairs of elements in
	 * the two lists are <i>equal</i>.  (Two elements <tt>e1</tt> and
	 * <tt>e2</tt> are <i>equal</i> if <tt>(e1==null ? e2==null :
	 * e1.equals(e2))</tt>.)  In other words, two lists are defined to be
	 * equal if they contain the same elements in the same order.  This
	 * definition ensures that the equals method works properly across
	 * different implementations of the <tt>List</tt> interface.
	 * </blockquote>
	 * <p/>
	 * <b>Note:</b> The behaviour of this method is undefined if the lists are
	 * modified during the equals comparison.
	 *
	 * @param list1 the first list, may be null
	 * @param list2 the second list, may be null
	 * @return whether the lists are equal by value comparison
	 * @see java.util.List
	 */
	public static boolean isEqualList(final Collection<?> list1, final Collection<?> list2)
	{
		if (list1 == list2)
		{
			return true;
		}
		if (list1 == null || list2 == null || list1.size() != list2.size())
		{
			return false;
		}

		Iterator<?> it1 = list1.iterator();
		Iterator<?> it2 = list2.iterator();
		Object obj1 = null;
		Object obj2 = null;

		while (it1.hasNext() && it2.hasNext())
		{
			obj1 = it1.next();
			obj2 = it2.next();

			if (!(obj1 == null ? obj2 == null : obj1.equals(obj2)))
			{
				return false;
			}
		}

		return !(it1.hasNext() || it2.hasNext());
	}

	/**
	 * Generates a hash code using the algorithm specified in
	 * {@link java.util.List#hashCode()}.
	 * <p/>
	 * This method is useful for implementing <code>List</code> when you cannot
	 * extend AbstractList. The method takes Collection instances to enable other
	 * collection types to use the List implementation algorithm.
	 *
	 * @param list the list to generate the hashCode for, may be null
	 * @return the hash code
	 * @see java.util.List#hashCode()
	 */
	public static <E> int hashCodeForListMy(final Collection<E> list)
	{
		if (list == null)
		{
			return 0;
		}
		int hashCodeMy = 1;
		Iterator<E> it = list.iterator();

		while (it.hasNext())
		{
			E obj = it.next();
			hashCodeMy = 31 * hashCodeMy + (obj == null ? 0 : obj.hashCode());
		}
		return hashCodeMy;
	}

	//-----------------------------------------------------------------------

	/**
	 * Returns a List containing all the elements in <code>collection</code>
	 * that are also in <code>retain</code>. The cardinality of an element <code>e</code>
	 * in the returned list is the same as the cardinality of <code>e</code>
	 * in <code>collection</code> unless <code>retain</code> does not contain <code>e</code>, in which
	 * case the cardinality is zero. This method is useful if you do not wish to modify
	 * the collection <code>c</code> and thus cannot call <code>collection.retainAll(retain);</code>.
	 *
	 * @param collection the collection whose contents are the target of the #retailAll operation
	 * @param retain	 the collection containing the elements to be retained in the returned collection
	 * @return a <code>List</code> containing all the elements of <code>c</code>
	 *         that occur at least once in <code>retain</code>.
	 * @throws NullPointerException if either parameter is null
	 * @since Commons Collections 3.2
	 */
	public static <E> List<E> retainAllMy(Collection<E> collection, Collection<?> retain)
	{
		List<E> list = new ArrayList<E>(Math.min(collection.size(), retain.size()));

		for (E obj : collection)
		{
			if (retain.contains(obj))
			{
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * Removes the elements in <code>remove</code> from <code>collection</code>. That is, this
	 * method returns a list containing all the elements in <code>c</code>
	 * that are not in <code>remove</code>. The cardinality of an element <code>e</code>
	 * in the returned collection is the same as the cardinality of <code>e</code>
	 * in <code>collection</code> unless <code>remove</code> contains <code>e</code>, in which
	 * case the cardinality is zero. This method is useful if you do not wish to modify
	 * <code>collection</code> and thus cannot call <code>collection.removeAll(remove);</code>.
	 *
	 * @param collection the collection from which items are removed (in the returned collection)
	 * @param remove	 the items to be removed from the returned <code>collection</code>
	 * @return a <code>List</code> containing all the elements of <code>c</code> except
	 *         any elements that also occur in <code>remove</code>.
	 * @throws NullPointerException if either parameter is null
	 * @since Commons Collections 3.2
	 */
	public static <E> List<E> removeAllMy(Collection<E> collection, Collection<?> remove)
	{
		List<E> list = new ArrayList<E>();
		for (E obj : collection)
		{
			if (!remove.contains(obj))
			{
				list.add(obj);
			}
		}
		return list;
	}
}