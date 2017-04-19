package com.gmail.fitostpm.spellbook.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionsHelper 
{
	public static <T> List<T> toList(T[] array)
	{
		List<T> result = new ArrayList<T>();
		for(T elem : array)
			result.add(elem);
		return result;
	}

}
