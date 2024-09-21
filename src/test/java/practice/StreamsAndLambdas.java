package practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsAndLambdas {

	public static void main(String[] args) {
		
		ArrayList<String> names = new ArrayList<String>();
		names.add("aish");names.add("ananya");names.add("ananya");
		names.add("ela");names.add("karthi");names.add("keerthi");
		names.add("kanchana");names.add("kanchana");names.add("karthi");
		names.add("nandhini");names.add("madhu");names.add("latha");
		names.add("prabha");names.add("prabha");names.add("madhu");
		
		List<String> names2 = Arrays.asList("ana", "dinesh", "harish", "mani", "nithilan", "nalini");
		
		long count = names.stream().filter(s->s.startsWith("a")).count();
		System.out.println("Startswith letter a: "+count);System.out.println("\nLength greater than 4");
		
		names.stream().filter(s->s.length()>4).forEach(s->System.out.println(s));
		System.out.println("\nStartswith letter A but returns true for all the conditions");
		
		count = Stream.of("Apple", "Apricot", "Mango", "Blueberries").filter(s->
				{
					s.startsWith("A");
					return true;
				}).count();
		System.out.println(count);System.out.println("\nCount of Startswith letter k and has more than 4 letters");
		
		count = names.stream().filter(s->
		{
			if(s.startsWith("k") && s.length()>4)
				return true;
			else
				return false;
		}).count();
		System.out.println(count);System.out.println("\nStartswith letter k and has more than 4 letters");
		
		names.stream().filter(s->
		{
			if(s.startsWith("k") && s.length()>4)
				return true;
			else
				return false;
		}).forEach(s->System.out.println(s));
		System.out.println("\nLength greater than 4 and is distinct");
		
		names.stream().filter(s->s.length()>4).distinct().forEach(s->System.out.println(s));
		
		System.out.println("\nLength greater than 4 and is distinct and limit to 2 values");
		
		names.stream().filter(s->s.length()>4).distinct().limit(2).forEach(s->System.out.println(s));
		
		System.out.println("\nLength greater than 4 and is distinct and ordered and limit to 2 values");
		
		names.stream().filter(s->s.length()>4).distinct().sorted().map(s->s.toUpperCase())
		.forEach(s->System.out.println(s));
		
		System.out.println("\nConcat 2 streams");
		
		Stream<String> newStream = Stream.concat(names.stream(), names2.stream());
		List<String> ls = newStream.distinct().sorted().collect(Collectors.toList());
		System.out.println(ls);
		
		System.out.println("\nCheck for a string in the list");
		System.out.println(names.stream().anyMatch(s->s.equalsIgnoreCase("aish")));
		
		List<Integer> nums = Arrays.asList(3,4,4,3,5,6,7,4,8,4,3,7,6);
		List<Integer> nums2 = nums.stream().distinct().sorted().collect(Collectors.toList());
		System.out.println(nums2);
		
		
	}

}
