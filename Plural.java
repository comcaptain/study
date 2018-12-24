package com.willhains.equality.value;

import com.willhains.equality.Equality;
import com.willhains.equality.value.annotations.Value;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.Collections.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public final @Value class Plural<@Value Element> implements Iterable<Element>
{
	private static final Plural<?> _EMPTY = new Plural<>(new Reading<>(Collections.emptyList()));
	
	public static <@Value Element> Plural<Element> empty()
	{
		@SuppressWarnings("unchecked") final Plural<Element> empty = (Plural<Element>)_EMPTY;
		return empty;
	}
	
	public static <@Value Element> Plural<Element> ofNullable(final Element possiblyNullElement)
	{
		return possiblyNullElement == null ? empty() : Plural.of(possiblyNullElement);
	}
	
	// @formatter:off
	public static <@Value E> Plural<E> of(E e1) { return new Plural<>(new Reading<>(singletonList(e1))); }
	public static <@Value E> Plural<E> of(E e1, E e2) { return copy(e1, e2); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3) { return copy(e1, e2, e3); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4) { return copy(e1, e2, e3, e4); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5) { return copy(e1, e2, e3, e4, e5); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6) { return copy(e1, e2, e3, e4, e5, e6); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return copy(e1, e2, e3, e4, e5, e6, e7); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return copy(e1, e2, e3, e4, e5, e6, e7, e8); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E e13) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E e13, E e14) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E e13, E e14, E e15) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E e13, E e14, E e15, E e16) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E e13, E e14, E e15, E e16, E e17) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E e13, E e14, E e15, E e16, E e17, E e18) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E e13, E e14, E e15, E e16, E e17, E e18, E e19) { return copy(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19); }
	public static <@Value E> Plural<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E e13, E e14, E e15, E e16, E e17, E e18, E e19, E e20, E... more)
	// @formatter:on
	{
		final int argsLength = 20;
		if(more.length > Integer.MAX_VALUE - argsLength) throw new IllegalArgumentException("too many elements");
		final Object[] objectArray = new Object[argsLength + more.length];
		objectArray[0] = e1;
		objectArray[1] = e2;
		objectArray[2] = e3;
		objectArray[3] = e4;
		objectArray[4] = e5;
		objectArray[5] = e6;
		objectArray[6] = e7;
		objectArray[7] = e8;
		objectArray[8] = e9;
		objectArray[9] = e10;
		objectArray[10] = e11;
		objectArray[11] = e12;
		objectArray[12] = e13;
		objectArray[13] = e14;
		objectArray[14] = e15;
		objectArray[15] = e16;
		objectArray[16] = e17;
		objectArray[17] = e18;
		objectArray[18] = e19;
		objectArray[19] = e20;
		System.arraycopy(more, 0, objectArray, argsLength, more.length);
		@SuppressWarnings("unchecked") final E[] elementArray = (E[])objectArray;
		return copy(elementArray);
	}
	
	@SafeVarargs
	public static <@Value Element> Plural<Element> copy(final Element... elements)
	{
		switch(elements.length)
		{
			case 0: return empty();
			case 1: return of(elements[0]);
			default: return new Plural<>(new Reading<>(Arrays.asList(elements.clone())));
		}
	}
	
	public static <@Value Element> Plural<Element> copy(final Iterable<Element> elements)
	{
		if(elements instanceof Plural) return (Plural<Element>)elements;
		if(elements instanceof Collection) return copy((Collection<Element>)elements);
		final ArrayList<Element> list = new ArrayList<>();
		elements.forEach(list::add);
		if(list.isEmpty()) return empty();
		return new Plural<>(new Reading<>(list));
	}
	
	public static <@Value Element> Plural<Element> copy(final Collection<Element> elements)
	{
		if(elements.isEmpty()) return empty();
		return new Plural<>(new Reading<>(new ArrayList<>(elements)));
	}
	
	public static <@Value Element> Collector<Element, ?, Plural<Element>> toPlural()
	{
		return collectingAndThen(toList(), list -> new Plural<>(new Reading<>(list)));
	}
	
	private final boolean _distinct;
	private MutationState<Element> _state;
	private Plural(final MutationState<Element> state) { this(state, false); }
	private Plural(final MutationState<Element> state, final boolean distinct) { _state = state; _distinct = distinct; }
	
	private static final Equality<Plural<?>> _EQ = Equality.ofProperties(Plural::_prepareForRead);
	public boolean equals(final Object that) { return _EQ.compare(this, that); }
	public int hashCode() { return _EQ.hash(this); }
	public String toString() { return _EQ.format(this); }
	
	private @Value interface MutationState<@Value Element>
	{
		int generation();
		default Reading<Element> prepareForRead() { return new Reading<>(prepareForWrite()); }
		List<Element> prepareForWrite();
	}
	
	private static final @Value class Reading<@Value Element> implements MutationState<Element>
	{
		private final List<Element> _elements;
		Reading(final List<Element> elements) { _elements = elements; }
		@Override public int generation() { return 0; }
		@Override public Reading<Element> prepareForRead() { return this; }
		@Override public List<Element> prepareForWrite() { return new ArrayList<>(_elements); }
	}
	
	private List<Element> _prepareForRead()
	{
		final Reading<Element> state = _state.prepareForRead();
		_state = state;
		return state._elements;
	}
	
	public List<Element> asList() { return unmodifiableList(_prepareForRead()); }
	public Set<Element> asSet() { return unmodifiableSet(_index()); }
	
	@Override public Iterator<Element> iterator() { return asList().iterator(); }
	public ListIterator<Element> listIterator() { return asList().listIterator(); }
	public Stream<Element> stream() { return _prepareForRead().stream(); }
	
	public Element get(int elementAtIndex) { return _prepareForRead().get(elementAtIndex); }
	public int size() { return _prepareForRead().size(); }
	public boolean isEmpty() { return _prepareForRead().isEmpty(); }
	public boolean allMatch(final Predicate<Element> condition) { return stream().allMatch(condition); }
	public boolean anyMatch(final Predicate<Element> condition) { return stream().anyMatch(condition); }
	public boolean noneMatch(final Predicate<Element> condition) { return stream().noneMatch(condition); }
	public Optional<Element> maxBy(final Comparator<Element> comparator) { return stream().max(comparator); }
	public Optional<Element> minBy(final Comparator<Element> comparator) { return stream().min(comparator); }
	
	public OptionalInt indexOf(final Predicate<Element> where)
	{
		final List<Element> list = _prepareForRead();
		for(int i = 0; i < list.size(); i++)
		{
			final Element element = list.get(i);
			if(where.test(element)) return OptionalInt.of(i);
		}
		return OptionalInt.empty();
	}
	
	public OptionalInt lastIndexOf(final Predicate<Element> where)
	{
		final List<Element> list = _prepareForRead();
		for(int i = list.size() - 1; i >= 0; i--)
		{
			final Element element = list.get(i);
			if(where.test(element)) return OptionalInt.of(i);
		}
		return OptionalInt.empty();
	}
	
	public Optional<Element> getFirst()
	{
		final List<Element> list = _prepareForRead();
		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
	}
	
	public Optional<Element> getLast()
	{
		final List<Element> list = _prepareForRead();
		final int size = list.size();
		return size == 0 ? Optional.empty() : Optional.of(list.get(size - 1));
	}
	
	public String join(final CharSequence delim) { return stream().map(Object::toString).collect(joining(delim)); }
	
	public <Result> Result reduce(final Result initialValue, final BiFunction<Result, Element, Result> reducer)
	{
		Result result = initialValue;
		for(final Element element: this)
		{
			result = reducer.apply(result, element);
		}
		return result;
	}
	
	/// Lazy index of entries for fast contains() operation ///
	
	private Set<Element> _index;
	private Set<Element> _index() { return _index == null ? _index = new HashSet<>(_prepareForRead()) : _index; }
	
	public boolean contains(final Element element) { return _index().contains(element); }
	public boolean containsAll(final Element... elements) { return _index().containsAll(Arrays.asList(elements)); }
	public boolean containsAll(final Collection<Element> elements) { return _index().containsAll(elements); }
	
	/// Mutations ///
	
	private static final class Mutating<@Value Element, @Value Converted> implements MutationState<Converted>
	{
		private static final int _MAX_GENERATION = 4096;
		private final MutationState<Element> _inner;
		private final Function<List<Element>, List<Converted>> _mutator;
		private final int _generation;
		
		Mutating(final MutationState<Element> inner, final Function<List<Element>, List<Converted>> mutator)
		{
			_inner = inner.generation() > _MAX_GENERATION ? inner.prepareForRead() : inner;
			_mutator = mutator;
			_generation = _inner.generation() + 1;
		}
		
		@Override public int generation() { return _generation; }
		@Override public List<Converted> prepareForWrite() { return _mutator.apply(_inner.prepareForWrite()); }
	}
	
	private Plural<Element> _mutate(final Consumer<List<Element>> mutator)
	{
		return new Plural<>(new Mutating<>(_state, list ->
		{
			mutator.accept(list);
			return list;
		}));
	}
	
	private <Converted> Plural<Converted> _transform(final Function<List<Element>, List<Converted>> transformer)
	{
		return new Plural<>(new Mutating<>(_state, transformer));
	}
	
	public Plural<Element> append(final Element element) { return _mutate(list -> list.add(element)); }
	public Plural<Element> append(final Plural<Element> p) { return _mutate(list -> list.addAll(p._prepareForRead())); }
	public Plural<Element> append(final Collection<Element> c) { return _mutate(list -> list.addAll(c)); }
	public Plural<Element> delete(final Element element) { return _mutate(list -> list.remove(element)); }
	public Plural<Element> delete(final Plural<Element> p) { return _mutate(list -> list.removeAll(p._prepareForRead())); }
	public Plural<Element> delete(final Collection<Element> c) { return _mutate(list -> list.removeAll(c)); }
	public Plural<Element> deleteIf(final Predicate<Element> where) { return _mutate(list -> list.removeIf(where)); }
	public Plural<Element> filter(final Predicate<Element> where) { return deleteIf(where.negate()); }
	public Plural<Element> reverse() { return _mutate(Collections::reverse); }
	
	public Plural<Element> distinct()
	{
		if(_distinct) return this;
		return new Plural<>(new Mutating<>(_state, list -> list.stream().distinct().collect(toList())), true);
	}
	
	public Plural<Element> deleteFirst()
	{
		return _mutate(list -> { if(!list.isEmpty()) list.remove(0); });
	}
	
	public Plural<Element> deleteLast()
	{
		return _mutate(list -> { if(!list.isEmpty()) list.remove(list.size() - 1); });
	}
	
	public Plural<Element> fromIndex(final int start)
	{
		if(start < 0) throw new IndexOutOfBoundsException("start(" + start + ") < 0");
		return _transform(list ->
		{
			final int end = list.size();
			return list.subList(Math.min(end, start), end);
		});
	}
	
	public Plural<Element> truncate(final int length)
	{
		if(length < 0) throw new IndexOutOfBoundsException("length(" + length + ") < 0");
		return _transform(list ->
		{
			final int end = Math.min(list.size(), length);
			return list.subList(0, end);
		});
	}
	
	public Plural<Element> insert(final Element element, int atIndex)
	{
		if(atIndex < 0) throw new IndexOutOfBoundsException("atIndex(" + atIndex + ") < 0");
		return _mutate(list ->
		{
			final int index = Math.min(atIndex, list.size());
			list.add(index, element);
		});
	}
	
	public Plural<Element> delete(final int atIndex)
	{
		if(atIndex < 0) throw new IndexOutOfBoundsException("atIndex(" + atIndex + ") < 0");
		return _mutate(list ->
		{
			if(atIndex < list.size()) list.remove(atIndex);
		});
	}
	
	public <@Value Converted> Plural<Converted> map(final Function<Element, Converted> mapper)
	{
		return _transform(list ->
		{
			@SuppressWarnings("unchecked") final List<Object> before = (List<Object>)list;
			for(final ListIterator<Object> i = before.listIterator(); i.hasNext();)
			{
				@SuppressWarnings("unchecked") final Element element = (Element)i.next();
				i.set(mapper.apply(element));
			}
			@SuppressWarnings("unchecked") final List<Converted> after = (List<Converted>)before;
			return after;
		});
	}
	
	public <@Value Converted> Plural<Converted> flatMap(final Function<Element, Plural<Converted>> mapper)
	{
		return _transform(list ->
		{
			final List<Converted> converted = new ArrayList<>();
			list.forEach(element -> converted.addAll(mapper.apply(element)._prepareForRead()));
			return converted;
		});
	}
	
	public <@Value Right> Plural<Pair<Element, Right>> zip(final Plural<Right> rightElements)
	{
		return _transform(left ->
		{
			final List<Right> right = rightElements._prepareForRead();
			final int zipSize = Math.min(left.size(), right.size());
			final List<Pair<Element, Right>> zipped = new ArrayList<>(zipSize);
			for(int i = 0; i < zipSize; i++) zipped.add(Pair.of(left.get(i), right.get(i)));
			return zipped;
		});
	}
	
	public Plural<Element> sortedBy(final Comparator<Element> order) { return _mutate(list -> sort(list, order)); }
	
	public <Property extends Comparable<Property>> Plural<Element> sortedBy(final Function<Element, Property> property)
	{
		return sortedBy(comparing(property));
	}
	
	public Plural<Element> sorted()
	{
		return sortedBy((a, b) ->
		{
			if(a instanceof Comparable)
			{
				@SuppressWarnings("unchecked") final Comparable<Element> comparableA = (Comparable<Element>)a;
				return comparableA.compareTo(b);
			}
			return a.toString().compareTo(b.toString());
		});
	}
}
