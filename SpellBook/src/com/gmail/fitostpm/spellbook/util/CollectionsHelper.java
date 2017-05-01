package com.gmail.fitostpm.spellbook.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class CollectionsHelper 
{
	public static <T> List<T> toList(T[] array)
	{
		List<T> result = new ArrayList<T>();
		for(T elem : array)
			result.add(elem);
		return result;
	}
	
	public static <T1, T2> List<T1> ConvertAll(List<T2> source, Function<T2, T1> conversion)
	{
		List<T1> result = new LinkedList<T1>();
		for(T2 elem : source)
			result.add(conversion.apply(elem));
		return result;
	}

}
